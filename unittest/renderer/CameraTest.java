package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;

import java.util.MissingResourceException;

import static org.junit.jupiter.api.Assertions.*;
// import scene.Scene;

/**
 * Testing Camera Class
 *
 * @author Dan
 */
class CameraTest {
    /**
     * Camera builder for the tests
     */
    private final Camera.Builder cameraBuilder = Camera.getBuilder()
            .setRayTracer(new SimpleRayTracer(new Scene("Test")))
            .setImageWriter(new ImageWriter("Test", 1, 1))
            .setLocation(Point.ZERO)
            .setDirection(new Vector(0, 0, -1), new Vector(0, -1, 0))
            .setVpDistance(10);

    /**
     * just for javaDoc
     */
    CameraTest() {
    }

    /**
     * Test method for
     * {@link Camera#constructRay(int, int, int, int)}.
     */
    @Test
    void testConstructRay() {
        final String badRay = "Bad ray";

        // ============ Equivalence Partitions Tests ==============
        // EP01: 4X4 Inside (1,1)
        Camera camera1 = cameraBuilder.setVpSize(8, 8).build();
        assertEquals(new Ray(Point.ZERO, new Vector(1, -1, -10)),
                camera1.constructRay(4, 4, 1, 1), badRay);

        // =============== Boundary Values Tests ==================
        // BV01: 4X4 Corner (0,0)
        assertEquals(new Ray(Point.ZERO, new Vector(3, -3, -10)),
                camera1.constructRay(4, 4, 0, 0), badRay);

        // BV02: 4X4 Side (0,1)
        assertEquals(new Ray(Point.ZERO, new Vector(1, -3, -10)),
                camera1.constructRay(4, 4, 1, 0), badRay);

        // BV03: 3X3 Center (1,1)
        Camera camera2 = cameraBuilder.setVpSize(6, 6).build();
        assertEquals(new Ray(Point.ZERO, new Vector(0, 0, -10)),
                camera2.constructRay(3, 3, 1, 1), badRay);

        // BV04: 3X3 Center of Upper Side (0,1)
        assertEquals(new Ray(Point.ZERO, new Vector(0, -2, -10)),
                camera2.constructRay(3, 3, 1, 0), badRay);

        // BV05: 3X3 Center of Left Side (1,0)
        assertEquals(new Ray(Point.ZERO, new Vector(2, 0, -10)),
                camera2.constructRay(3, 3, 0, 1), badRay);

        // BV06: 3X3 Corner (0,0)
        assertEquals(new Ray(Point.ZERO, new Vector(2, -2, -10)),
                camera2.constructRay(3, 3, 0, 0), badRay);
    }

    /**
     * Test method for
     * {@link Camera.Builder#build()}
     */
    @Test
    void testBuild() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: simple test
        assertDoesNotThrow(() -> cameraBuilder.setVpSize(10, 10).setVpDistance(10));
        Camera camera1 = cameraBuilder.setVpSize(10, 10).setVpDistance(10).build();
        assertEquals(10, camera1.getViewPlaneHeight(), "wrong view plane height");
        assertEquals(10, camera1.getViewPlaneDistance(), "wrong view plane distance");
        assertEquals(10, camera1.getViewPlaneWidth(), "wrong view plane width");

        assertDoesNotThrow(() -> cameraBuilder.setDirection(new Vector(0, 0, -2), new Vector(0, -2, 0)));
        Camera camera2 = cameraBuilder.build();
        assertEquals(new Vector(-1, 0, 0), camera2.getVRight(), "wrong right vector");
        assertEquals(1, camera2.getVRight().length(), "vector right is not normalized");
        assertEquals(1, camera2.getVUp().length(), "vector up is not normalized");
        assertEquals(1, camera2.getVTo().length(), "vector to is not normalized");

        // =============== Boundary Values Tests ==================
        //**** Group: missing resources
        //TC10: missing 1 resource
        assertThrows(MissingResourceException.class,
                () -> Camera.getBuilder().setVpSize(10, 10).build(),
                "didnt throw when distance is 0");
        //TC12: missing 2 resource
        assertThrows(MissingResourceException.class,
                () -> Camera.getBuilder().setVpDistance(10).build(),
                "didnt throw when width and height are 0");
        //TC13: missing 3 resource
        assertThrows(MissingResourceException.class,
                () -> Camera.getBuilder().build(),
                "didnt throw when width and height and distance are 0");

        //TC14
        assertThrows(IllegalArgumentException.class,
                () -> Camera.getBuilder().setDirection(new Vector(1, 2, 3), new Vector(0, 0, -1)).build(),
                "vUp and vTo not verticals");
    }

    /**
     * todo Test method for
     * {@link Camera#constructRay(int, int, int, int)}
     */
    @Test
    void constructRay() {
    }

    /**
     * todo Test method for
     * {@link Camera#renderImage()}
     */
    @Test
    void renderImage() {
    }

    /**
     * todo Test method for
     * {@link Camera#printGrid(int, Color)}
     */
    @Test
    void printGrid() {
    }
}
