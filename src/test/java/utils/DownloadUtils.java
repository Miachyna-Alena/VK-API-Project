package utils;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.logging.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DownloadUtils {
    private static final Logger LOGGER = AqualityServices.getLogger();

    public static void downloadPhoto(String photoUrl, String actualPhotoPath) {
        try (InputStream inputStream = new URL(photoUrl).openStream()) {
            LOGGER.info(String.format("Download photo by URL: %s.", photoUrl));
            Files.copy(inputStream, Paths.get(new File(actualPhotoPath).getAbsolutePath()));
        } catch (IOException exception) {
            LOGGER.error(exception.getMessage());
            throw new IllegalArgumentException("Unchecked IOException!!!");
        }
    }
}
