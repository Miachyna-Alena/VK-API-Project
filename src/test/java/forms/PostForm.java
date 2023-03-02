package forms;

import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.elements.interfaces.ILink;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class PostForm extends Form {
    private static final String POST_FORM_PATH = "post%s_%s";
    private static final String POST_LABEL_PATH = "wpt%s_%s";
    private static final String SHOW_COMMENTS_BUTTON_PATH = "//a[@href = '/wall%s_%s']//span[contains(@class, 'next_label')]";
    private static final String COMMENTS_LABEL_PATH = "//div[contains(@id, '%s') and contains(@id, '%s')]//div[contains(@class, 'wall_reply_text')]";
    private static final String POST_PHOTO_PATH = "//div[@id='post%s_%s']//a[@href='/photo%s_%s']";
    private static final String SRC_ATTRIBUTE = "src";
    private static final String LIKE_POST_BUTTON_PATH = "//div[@data-reaction-target-object = 'wall%s_%s']";
    private final ILink lnkIMAGE = getElementFactory().getLink(By.xpath("//div[@id='pv_photo']/img"), "\"Image\" link");
    private final IButton btnCLOSE_POST = getElementFactory().getButton(By.xpath("//div[@class = 'pv_close_btn']"), "\"Close Post\" button");

    public PostForm(String user_id, String post_id) {
        super(By.id(String.format(POST_FORM_PATH, user_id, post_id)), "\"Post\" form");
    }

    private ILabel getPostLabel(String userId, String postId) {
        return getElementFactory().getLabel(By.id(String.format(POST_LABEL_PATH, userId, postId)), "\"Post Label\"");
    }

    public boolean isPostLabelNotDisplay(String userId, String postId) {
        return getPostLabel(userId, postId).state().waitForNotDisplayed();
    }

    public String getPostLabelText(String userId, String postId) {
        return getPostLabel(userId, postId).getText();
    }

    public Boolean isCorrectUser(String userId, String postId) {
        return getPostLabel(userId, postId).getLocator().toString().contains(userId);
    }

    public void clickShowCommentsButton(String userId, String postId) {
        getElementFactory().getButton(By.xpath(String.format(SHOW_COMMENTS_BUTTON_PATH, userId, postId)), "\"Show Comments\" button").click();
    }

    private ILabel getCommentLabel(String postId, String userId) {
        return getElementFactory().getLabel(By.xpath(String.format(COMMENTS_LABEL_PATH, postId, userId)), "\"Comment Label\"");
    }

    public boolean isCommentLabelDisplayed(String postId, String userId) {
        return getCommentLabel(postId, userId).state().waitForDisplayed();
    }

    private ILabel getPostPhotoLabel(String ownerId, String postId, String photoId) {
        return getElementFactory().getLabel(By.xpath(String.format(POST_PHOTO_PATH, ownerId, postId, ownerId, photoId)), "\"Post Photo\" label");
    }

    public void clickOnPostPhoto(String ownerId, String postId, String photoId) {
        getPostPhotoLabel(ownerId, postId, photoId).clickAndWait();
    }

    public String getImageSrcAttribute() {
        return lnkIMAGE.getAttribute(SRC_ATTRIBUTE);
    }

    public void clickClosePostButton() {
        btnCLOSE_POST.click();
    }

    private IButton getLikePostButton(String userId, String postId) {
        return getElementFactory().getButton(By.xpath(String.format(LIKE_POST_BUTTON_PATH, userId, postId)), "\"Like Post\" button");
    }

    public void clickLikePostButton(String userId, String postId) {
        getLikePostButton(userId, postId).click();
    }
}
