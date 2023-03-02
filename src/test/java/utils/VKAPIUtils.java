package utils;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import io.restassured.specification.RequestSpecification;
import response_models.JSONResponse;
import response_models.PhotoJSONResponse;

import java.util.Map;

public class VKAPIUtils {
    private static final ISettingsFile RESPONSE_DATA = new JsonSettingsFile("response-data.json");
    private static final String GET_WALL_UPLOAD_SERVER_METHOD = RESPONSE_DATA.getValue("/GET_WALL_UPLOAD_SERVER_METHOD").toString();
    private static final String RESPONSE_UPLOAD_URL_KEY = RESPONSE_DATA.getValue("/RESPONSE_UPLOAD_URL_KEY").toString();
    private static final String TYPE_PHOTO_KEY = RESPONSE_DATA.getValue("/TYPE_PHOTO_KEY").toString();
    private static final String LIKES_IS_LIKED_KEY = RESPONSE_DATA.getValue("/LIKES_IS_LIKED_KEY").toString();
    private static final String RESPONSE_LIKES_KEY = RESPONSE_DATA.getValue("/RESPONSE_LIKES_KEY").toString();


    public static JSONResponse doVkAPIRequest(String method, Map<String, String> params) {
        RequestSpecification specification = RESTAPIUtils.requestBuilder(method)
                .addParams(params)
                .build();
        return new JSONResponse(APIUtils.post(specification));
    }

    public static JSONResponse getWallUploadServerResponse() {
        RequestSpecification specification = RESTAPIUtils.requestBuilder(GET_WALL_UPLOAD_SERVER_METHOD).build();
        return new JSONResponse(APIUtils.post(specification));
    }

    public static String getUploadUrl(JSONResponse response) {
        return response.getBody().path(RESPONSE_UPLOAD_URL_KEY);
    }

    public static PhotoJSONResponse uploadPhotoOnTheWall(String uploadUrl, String photoPath) {
        JSONResponse response = new JSONResponse(APIUtils.postMultiPart(uploadUrl, photoPath, TYPE_PHOTO_KEY));
        return new PhotoJSONResponse(response.getBody());
    }

    public static boolean isPostLikedByAuthor(String postId, int likes_number) {
        RequestSpecification specification = RESTAPIUtils.requestBuilder(LIKES_IS_LIKED_KEY)
                .addParams(ParamsUtils.likePostParams(postId))
                .build();
        var response = new JSONResponse(APIUtils.post(specification));
        return response.getBody().path(RESPONSE_LIKES_KEY).equals(likes_number);
    }
}
