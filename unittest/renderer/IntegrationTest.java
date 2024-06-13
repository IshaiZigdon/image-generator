package renderer;

import geometries.Intersectable;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * unit test for Sphere Plane and Triangle with constructRay
 *
 * @author Ishai zigdon
 * @author Zaki zafrani
 */
public class IntegrationTest {
    /**
     * Camera builder for the tests
     */
    private final Camera.Builder cameraBuilder = Camera.getBuilder()
            .setRayTracer(new SimpleRayTracer(new Scene("Test")))
            .setImageWriter(new ImageWriter("Test", 1, 1))
            .setLocation(Point.ZERO)
            .setDirection(new Vector(0, 0, -1), new Vector(0, -1, 0))
            .setVpSize(3, 3)
            .setVpDistance(1);

    /**
     * just for test
     */
    IntegrationTest() {
    }

    /**
     * aid function that construct rays through each pixel
     * and counting the intersection points
     *
     * @param shape  the given shape for checking
     * @param camera the given camera
     * @param exp    the expected amount of intersecting points
     * @param str    the string for the error message for each shape
     */
    public void forLoop(Intersectable shape, Camera camera, int exp, String str) {
        int count = 0;
        List<Point> result;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result = shape.findIntersections(camera.constructRay(3, 3, i, j));
                if (result != null) {
                    count += result.size();
                }
            }
        }
        assertEquals(exp, count, str + "wrong number");
    }

    /**
     * Test method for
     * {@link Sphere#findIntersections(Ray)}.
     * with {@link Camera#constructRay(int, int, int, int)}
     */
    @Test
    public void testSphere() {
        //TC1: sphere with radius 1 and 2 points of intersection
        Camera sphereCamera1 = cameraBuilder.build();
        Sphere sphere1 = new Sphere(new Point(0, 0, -3), 1);
        forLoop(sphere1, sphereCamera1, 2, "sphere TC1: ");

        //TC2:sphere with radius of 2.5 and 18 points of intersection
        Camera sphereCamera2 = cameraBuilder.setLocation(new Point(0, 0, 0.5)).build();
        Sphere sphere2 = new Sphere(new Point(0, 0, -2.5), 2.5);
        forLoop(sphere2, sphereCamera2, 18, "sphere TC2: ");

        //TC3:sphere with radius of 2 and 10 points of intersection
        Sphere sphere3 = new Sphere(new Point(0, 0, -2), 2);
        forLoop(sphere3, sphereCamera2, 10, "sphere TC3: ");

        //TC4:sphere with radius of 4 and 9 points of intersection
        Sphere sphere4 = new Sphere(new Point(0, 0, -2), 4);
        forLoop(sphere4, sphereCamera2, 9, "sphere TC4: ");

        //TC5: sphere with radius of 0.5 and 0 points of intersection
        Sphere sphere5 = new Sphere(new Point(0, 0, 1), 0.5);
        forLoop(sphere5, sphereCamera2, 0, "sphere TC5: ");
    }

    /**
     * Test method for
     * {@link Plane#findIntersections(Ray)}.
     * with {@link Camera#constructRay(int, int, int, int)}
     */
    @Test
    public void planeTest() {
        Camera planeCamera = cameraBuilder.build();

        //TC1:regular plane with 9 points of intersection
        Plane plane1 = new Plane(new Point(1, 0, -10), new Point(0, 1, -10), new Point(1, 1, -10));
        forLoop(plane1, planeCamera, 9, "plane TC1: ");

        //TC2: inclined plane with 9 points of intersection
        Plane plane2 = new Plane(new Point(0, 0, -10), new Point(2, 5, -8), new Point(-2, 2, -10.6));
        forLoop(plane2, planeCamera, 9, "plane TC2: ");

        //TC3:inclined plane with 6 points of intersection
        Plane plane3 = new Plane(new Point(0, 0, -8), new Point(1, 2, -7), new Point(-2, 5, -10));
        forLoop(plane3, planeCamera, 6, "plane TC3: ");
    }

    /**
     * Test method for
     * {@link Triangle#findIntersections(Ray)}.
     * with {@link Camera#constructRay(int, int, int, int)}
     */
    @Test
    public void TriangleTest() {
        Camera triangleCamera = cameraBuilder.build();

        //TC1: regular triangle with 1 point of intersection
        Triangle triangle1 = new Triangle(new Point(0, 1, -2), new Point(1, -1, -2), new Point(-1, -1, -2));
        forLoop(triangle1, triangleCamera, 1, "triangle TC1: ");

        //TC2:regular triangle with 2 points of intersection
        Triangle triangle2 = new Triangle(new Point(0, 20, -2), new Point(1, -1, -2), new Point(-1, -1, -2));
        forLoop(triangle2, triangleCamera, 2, "triangle TC2: ");
    }
}
