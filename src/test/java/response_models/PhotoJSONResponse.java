package response_models;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import io.restassured.response.ResponseBody;
import lombok.Getter;

@Getter
public class PhotoJSONResponse {
    private static final ISettingsFile API_PARAM = new JsonSettingsFile("api-params.json");
    private static final String HASH_PARAM = API_PARAM.getValue("/HASH_PARAM").toString();
    private static final String SERVER_PARAM = API_PARAM.getValue("/SERVER_PARAM").toString();
    private static final String PHOTO_PARAM = API_PARAM.getValue("/PHOTO_PARAM").toString();

    private int server;
    private String photo;
    private String hash;

    public PhotoJSONResponse(ResponseBody responseBody) {
        this.server = responseBody.jsonPath().get(SERVER_PARAM);
        this.photo = responseBody.jsonPath().get(PHOTO_PARAM);
        this.hash = responseBody.jsonPath().get(HASH_PARAM);
    }
}
