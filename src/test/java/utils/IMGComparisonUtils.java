package utils;

import com.github.romankh3.image.comparison.ImageComparison;
import com.github.romankh3.image.comparison.ImageComparisonUtil;
import com.github.romankh3.image.comparison.model.ImageComparisonResult;
import com.github.romankh3.image.comparison.model.ImageComparisonState;

import java.awt.image.BufferedImage;
import java.io.File;

public class IMGComparisonUtils {

    public static boolean compareImages(String expectedIMG, String actualIMG) {
        BufferedImage actualImage = ImageComparisonUtil.readImageFromResources(actualIMG);
        BufferedImage expectedImage = ImageComparisonUtil.readImageFromResources(expectedIMG);
        ImageComparisonResult imageComparisonResult = new ImageComparison(expectedImage, actualImage).compareImages();
        return ImageComparisonState.MATCH.equals(imageComparisonResult.getImageComparisonState());
    }

    public static void deleteComparedImage(String actualPhotoPath) {
        File file = new File(actualPhotoPath);
        if (file.exists()) {
            file.delete();
        }
    }
}
