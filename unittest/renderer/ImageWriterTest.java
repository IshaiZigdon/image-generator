package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

/**
 * test for imageWriter
 */
class ImageWriterTest {

    /**
     * Javadoc just for quiet life
     */
    ImageWriterTest() {
    }

    /**
     * test method for
     * {@link ImageWriter#writeToImage()}
     */
    @Test
    void writeToImage() {
        //base case only
        int t = 50;
        int height = 10 * t, /*500*/ width = 16 * t; /*800*/
        Color c1 = new Color(0, 0, 128);
        Color c2 = new Color(255, 215, 0);
        ImageWriter imageWriter = new ImageWriter("skadush", width, height);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                imageWriter.writePixel(j, i, (i % t == 0 || j % t == 0) ? c1 : c2);
            }
        }
        imageWriter.writeToImage();
    }
}