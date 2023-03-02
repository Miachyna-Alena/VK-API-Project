package response_models;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import lombok.Getter;

@Getter
public class JSONResponse {
    private int statusCode;
    private ResponseBody body;

    public JSONResponse(Response response) {
        this.statusCode = response.getStatusCode();
        this.body = response.getBody();
    }
}
