package utils;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import io.restassured.builder.RequestSpecBuilder;
import response_models.JSONResponse;

public class RESTAPIUtils {
    private static final ISettingsFile CONFIG_DATA = new JsonSettingsFile("config-data.json");
    private static final String BASE_API_URL = CONFIG_DATA.getValue("/BASE_API_URL").toString();

    private static final ISettingsFile RESPONSE_DATA = new JsonSettingsFile("response-data.json");
    private static final String RESPONSE_POST_ID_KEY = RESPONSE_DATA.getValue("/RESPONSE_POST_ID_KEY").toString();
    private static final String RESPONSE_ID_KEY = RESPONSE_DATA.getValue("/RESPONSE_ID_KEY").toString();

    public static RequestSpecBuilder requestBuilder(String method) {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_API_URL + method)
                .addParams(ParamsUtils.baseParams());
    }

    public static String getPostId(JSONResponse response) {
        return response.getBody().path(RESPONSE_POST_ID_KEY).toString();
    }

    public static String getUploadedPhotoId(JSONResponse response) {
        return StringModifier.getReplacedString(response.getBody().path(RESPONSE_ID_KEY).toString());
    }
}
