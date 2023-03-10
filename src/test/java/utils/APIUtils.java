package utils;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.File;

import static io.restassured.RestAssured.given;

public class APIUtils {
    public static Response post(RequestSpecification spec) {
        return given(spec)
                .post()
                .then()
                .extract()
                .response();
    }

    public static Response postMultiPart(String uploadUrl, String path, String typeOfFile) {
        return given()
                .multiPart(typeOfFile, new File(path))
                .post(uploadUrl)
                .then()
                .extract()
                .response();
    }
}
