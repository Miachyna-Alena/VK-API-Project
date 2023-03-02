package tests;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.logging.Logger;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import forms.*;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;
import response_models.JSONResponse;
import utils.*;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


public class VkAPITest extends BaseTest {
    private static final Logger LOGGER = AqualityServices.getLogger();
    private static final ISettingsFile CREDENTIALS_DATA = new JsonSettingsFile("credentials-data.json");
    private static final String LOGIN = CREDENTIALS_DATA.getValue("/LOGIN").toString();
    private static final String PASSWORD = CREDENTIALS_DATA.getValue("/PASSWORD").toString();
    private static final String OWNER_ID = CREDENTIALS_DATA.getValue("/OWNER_ID").toString();

    private static final ISettingsFile TEST_DATA = new JsonSettingsFile("test-data.json");
    private static final int POST_MESSAGE_LENGTH = Integer.parseInt(TEST_DATA.getValue("/POST_MESSAGE_LENGTH").toString());
    private static final String POST_MESSAGE = RandomUtils.generateRandoString(POST_MESSAGE_LENGTH);
    private static final String UPDATE_POST_MESSAGE = RandomUtils.generateRandoString(POST_MESSAGE_LENGTH);
    private static final String PHOTO_PATH = TEST_DATA.getValue("/PHOTO_PATH").toString();
    private static final String ACTUAL_PHOTO_PATH = TEST_DATA.getValue("/ACTUAL_PHOTO_PATH").toString();
    private static final int COMMENT_MESSAGE_LENGTH = Integer.parseInt(TEST_DATA.getValue("/COMMENT_MESSAGE_LENGTH").toString());
    private static final String COMMENT_MESSAGE = RandomUtils.generateRandoString(COMMENT_MESSAGE_LENGTH);
    private static final int LIKES_NUMBER = (int) TEST_DATA.getValue("/LIKES_NUMBER");

    private static final ISettingsFile RESPONSE_DATA = new JsonSettingsFile("response-data.json");
    private static final String WALL_POST_METHOD = RESPONSE_DATA.getValue("/WALL_POST_METHOD").toString();
    private static final String WALL_EDIT_METHOD = RESPONSE_DATA.getValue("/WALL_EDIT_METHOD").toString();
    private static final String SAVE_WALL_PHOTO_METHOD = RESPONSE_DATA.getValue("/SAVE_WALL_PHOTO_METHOD").toString();
    private static final String CREATE_COMMENT_METHOD = RESPONSE_DATA.getValue("/CREATE_COMMENT_METHOD").toString();
    private static final String DELETE_POST_METHOD = RESPONSE_DATA.getValue("/DELETE_POST_METHOD").toString();


    @Test
    public void vkApiTest() {
        VkIDForm vkIDForm = new VkIDForm();

        assertTrue(vkIDForm.state().waitForDisplayed(), String.format("%s is NOT open.", vkIDForm.getName()));
        LOGGER.info("Authorization.");
        vkIDForm.inputLogin(LOGIN);
        vkIDForm.clickSignInButton();

        AuthorizationForm authorizationForm = new AuthorizationForm();

        assertTrue(authorizationForm.state().waitForDisplayed(), String.format("%s is NOT open.", authorizationForm.getName()));
        authorizationForm.inputPassword(PASSWORD);
        authorizationForm.clickContinueButton();
        LOGGER.info("Successful authorization.");

        NewsForm newsForm = new NewsForm();

        assertTrue(newsForm.state().waitForDisplayed(), String.format("%s is NOT open.", newsForm.getName()));
        LOGGER.info(String.format("%s is open.", newsForm.getName()));
        newsForm.clickMyPageButton();

        ProfileForm profileForm = new ProfileForm();

        assertTrue(profileForm.state().waitForDisplayed(), String.format("%s is NOT open.", profileForm.getName()));
        LOGGER.info(String.format("%s is open.", profileForm.getName()));

        JSONResponse createdPostResponse = VKAPIUtils.doVkAPIRequest(WALL_POST_METHOD, ParamsUtils.createPostParams(POST_MESSAGE));
        LOGGER.info(createdPostResponse.getBody().asPrettyString());
        assertEquals(createdPostResponse.getStatusCode(), HttpStatus.SC_OK, String.format("Response status is NOT %s.", HttpStatus.SC_OK));
        LOGGER.info("Post was created.");

        String postId = RESTAPIUtils.getPostId(createdPostResponse);

        PostForm postForm = new PostForm(OWNER_ID, postId);

        assertTrue(postForm.isCorrectUser(OWNER_ID, postId), "User in the post is NOT correct.");
        LOGGER.info("User in the post is correct.");
        assertEquals(postForm.getPostLabelText(OWNER_ID, postId), POST_MESSAGE, "Text in the post is NOT correct.");
        LOGGER.info("Text in the post is correct.");

        String uploadUrl = VKAPIUtils.getUploadUrl(VKAPIUtils.getWallUploadServerResponse());
        LOGGER.info(uploadUrl);

        JSONResponse uploadedPhotoResponse = VKAPIUtils.doVkAPIRequest(SAVE_WALL_PHOTO_METHOD, ParamsUtils.saveUploadWallPhotoParams(VKAPIUtils.uploadPhotoOnTheWall(uploadUrl, PHOTO_PATH)));
        String photoId = RESTAPIUtils.getUploadedPhotoId(uploadedPhotoResponse);
        JSONResponse editedPostResponse = VKAPIUtils.doVkAPIRequest(WALL_EDIT_METHOD, ParamsUtils.editPostParams(UPDATE_POST_MESSAGE, postId, photoId));
        assertEquals(editedPostResponse.getStatusCode(), HttpStatus.SC_OK, String.format("Response status is NOT %s.", HttpStatus.SC_OK));
        LOGGER.info("Post was edited + photo has been added.");
        assertEquals(postForm.getPostLabelText(OWNER_ID, postId), UPDATE_POST_MESSAGE, "Text in the post was NOT updated.");
        LOGGER.info("Text in the post was updated.");
        postForm.clickOnPostPhoto(OWNER_ID, postId, photoId);
        DownloadUtils.downloadPhoto(postForm.getImageSrcAttribute(), ACTUAL_PHOTO_PATH);
        assertTrue(IMGComparisonUtils.compareImages(PHOTO_PATH, ACTUAL_PHOTO_PATH), "Photo in the post is NOT correct.");
        LOGGER.info("Photo in the post is correct.");
        IMGComparisonUtils.deleteComparedImage(ACTUAL_PHOTO_PATH);
        postForm.clickClosePostButton();

        JSONResponse createdCommentResponse = VKAPIUtils.doVkAPIRequest(CREATE_COMMENT_METHOD, ParamsUtils.createPostCommentParams(COMMENT_MESSAGE, postId));
        assertEquals(createdCommentResponse.getStatusCode(), HttpStatus.SC_OK, String.format("Response status is NOT %s.", HttpStatus.SC_OK));
        LOGGER.info("Comment was created.");

        postForm.clickShowCommentsButton(OWNER_ID, postId);
        assertTrue(postForm.isCommentLabelDisplayed(postId, OWNER_ID), "NO comment from given user.");
        LOGGER.info("User in the comment is correct.");

        postForm.clickLikePostButton(OWNER_ID, postId);
        assertTrue(VKAPIUtils.isPostLikedByAuthor(postId, LIKES_NUMBER), String.format("Post with id: %s was NOT liked by user with id: %s.", postId, OWNER_ID));
        LOGGER.info("Post was liked by given user.");

        assertEquals(VKAPIUtils.doVkAPIRequest(DELETE_POST_METHOD, ParamsUtils.deletePostParams(postId)).getStatusCode(), HttpStatus.SC_OK, String.format("Response status is NOT %s.", HttpStatus.SC_OK));
        assertTrue(postForm.isPostLabelNotDisplay(OWNER_ID, postId), "Created post was NOT deleted.");
        LOGGER.info("Created post was deleted.");
    }
}
