package utils;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import response_models.PhotoJSONResponse;

import java.util.HashMap;
import java.util.Map;

public class ParamsUtils {
    private static final ISettingsFile CONFIG_DATA = new JsonSettingsFile("config-data.json");
    private static final String POST_TYPE_VALUE = CONFIG_DATA.getValue("/POST_TYPE_VALUE").toString();
    private static final String API_VERSION = CONFIG_DATA.getValue("/API_VERSION").toString();

    private static final ISettingsFile CREDENTIALS_DATA = new JsonSettingsFile("credentials-data.json");
    private static final String OWNER_ID = CREDENTIALS_DATA.getValue("/OWNER_ID").toString();
    private static final String ACCESS_TOKEN = CREDENTIALS_DATA.getValue("/ACCESS_TOKEN").toString();

    private static final ISettingsFile API_PARAMS = new JsonSettingsFile("api-params.json");
    private static final String MESSAGE_PARAM = API_PARAMS.getValue("/MESSAGE_PARAM").toString();
    private static final String POST_ID_PARAM = API_PARAMS.getValue("/POST_ID_PARAM").toString();
    private static final String ITEM_ID_PARAM = API_PARAMS.getValue("/ITEM_ID_PARAM").toString();
    private static final String ATTACHMENTS_PARAM = API_PARAMS.getValue("/ATTACHMENTS_PARAM").toString();
    private static final String TYPE_PARAM = API_PARAMS.getValue("/TYPE_PARAM").toString();
    private static final String HASH_PARAM = API_PARAMS.getValue("/HASH_PARAM").toString();
    private static final String SERVER_PARAM = API_PARAMS.getValue("/SERVER_PARAM").toString();
    private static final String PHOTO_PARAM = API_PARAMS.getValue("/PHOTO_PARAM").toString();
    private static final String OWNER_ID_PARAM = API_PARAMS.getValue("/OWNER_ID_PARAM").toString();
    private static final String ACCESS_TOKEN_PARAM = API_PARAMS.getValue("/ACCESS_TOKEN_PARAM").toString();
    private static final String API_VERSION_PARAM = API_PARAMS.getValue("/API_VERSION_PARAM").toString();
    private static Map<String, String> parameters;

    public static Map<String, String> baseParams(){
        parameters = new HashMap<String, String>();
        parameters.put(OWNER_ID_PARAM, OWNER_ID);
        parameters.put(ACCESS_TOKEN_PARAM, ACCESS_TOKEN);
        parameters.put(API_VERSION_PARAM, API_VERSION);
        return parameters;
    }

    public static Map<String, String> createPostParams(String message){
      parameters = new HashMap<String, String>();
        parameters.put(MESSAGE_PARAM, message);
        return parameters;
    }

    public static Map<String, String> editPostParams(String message, String postId, String photoId){
       parameters = new HashMap<String, String>();
        parameters.put(POST_ID_PARAM, postId);
        parameters.put(MESSAGE_PARAM, message);
        parameters.put(ATTACHMENTS_PARAM, String.format("photo%s_%s", OWNER_ID, photoId));
        return parameters;
    }

    public static Map<String, String> saveUploadWallPhotoParams(PhotoJSONResponse photoJSONResponse){
        parameters = new HashMap<String, String>();
        parameters.put(HASH_PARAM, photoJSONResponse.getHash());
        parameters.put(SERVER_PARAM, String.valueOf(photoJSONResponse.getServer()));
        parameters.put(PHOTO_PARAM, photoJSONResponse.getPhoto());
        return parameters;
    }

    public static Map<String, String> createPostCommentParams(String message, String postId){
        parameters = new HashMap<String, String>();
        parameters.put(MESSAGE_PARAM, message);
        parameters.put(POST_ID_PARAM, postId);
        return parameters;
    }

    public static Map<String, String> likePostParams(String postId){
        parameters = new HashMap<String, String>();
        parameters.put(TYPE_PARAM, POST_TYPE_VALUE);
        parameters.put(ITEM_ID_PARAM, postId);
        return parameters;
    }

    public static Map<String, String> deletePostParams(String postId){
        parameters = new HashMap<String, String>();
        parameters.put(POST_ID_PARAM, postId);
        return parameters;
    }
}
