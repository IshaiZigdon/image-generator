package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

import static primitives.Util.isZero;

class ImageWriterTest {


    @Test
    void writeToImage() {
        int t =50;
        int height = 10*t, /*500*/ width = 16*t;//800
        ImageWriter imageWriter = new ImageWriter("skadush", width, height);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (isZero(i%t)|| isZero(j%t))
                    imageWriter.writePixel(j,i,new Color(0,0,128));
                else
                    imageWriter.writePixel(j,i,new Color(255,215,0));
            }
        }
        imageWriter.writeToImage();
    }
}