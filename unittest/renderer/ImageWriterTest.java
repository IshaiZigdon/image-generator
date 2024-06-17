package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

import static primitives.Util.isZero;

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
        //TC1: simple test
        ImageWriter imageWriter = new ImageWriter("skadush", 800, 500);

        for (int i = 0; i < 500; i++) {
            for (int j = 0; j < 800; j++) {
                if (isZero(i % 50) || isZero(j % 50))
                    imageWriter.writePixel(j, i, new Color(0, 0, 128));
                else
                    imageWriter.writePixel(j, i, new Color(255, 215, 0));
            }
        }
        imageWriter.writeToImage();
    }
}