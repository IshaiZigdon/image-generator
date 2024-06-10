package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit test for geometries.{@link Geometries}
 */
public class GeometriesTest {
    /**
     * point 100 for testing
     */
    private static final Point p100 = new Point(1, 0, 0);
    /**
     * point 010 for testing
     */
    private static final Point p010 = new Point(0, 1, 0);
    /**
     * point 110 for testing
     */
    private static final Point p110 = new Point(1, 1, 0);
    /**
     * triangle using the points for testing
     */
    private static final Triangle triangle = new Triangle(p110, p100, p010);
    /**
     * plane using the points for testing
     */
    private static final Plane plane = new Plane(p110, p100, p010);
    /**
     * sphere using a point for testing
     */
    private static final Sphere sphere = new Sphere(p100, 1d);
    /**
     * array of points for testing
     */
    static Point[] pts = {new Point(0.5, 0, 1), new Point(2, 0, 1), new Point(2, 1, 1), new Point(0.5, 1, 1)};
    /**
     * polygon using a point for testing
     */
    private static final Polygon polygon = new Polygon(pts);

    /**
     * Default constructor for GeometriesTest.
     */
    public GeometriesTest() {/*just fot the javadoc*/}

    /**
     * test method for {@link Geometries#findIntersections(Ray)}
     */
    @Test
    void testFindIntersections() {
        Geometries geometries = new Geometries(polygon, triangle, plane, sphere);

        // ============ Equivalence Partitions Tests ==============
        //TC01: simple test
        //intersects with plane and sphere
        Ray ray01 = new Ray(new Point(0.2, 0.2, -2), new Vector(0, 0, 1));
        assertEquals(3, geometries.findIntersections(ray01).size(), "TC01: wrong amount");
        // =============== Boundary Values Tests ==================
        //TC10: all the shapes are intersecting
        Ray ray10 = new Ray(new Point(0.7, 0.7, -1), new Vector(0, 0, 1));
        assertEquals(5, geometries.findIntersections(ray10).size(), "TC10: wrong amount");
        //TC11 : no shape is intersecting with the ray
        Ray ray11 = new Ray(new Point(10, 10, 10), new Vector(1, 0, 0));
        assertNull(geometries.findIntersections(ray11), "TC11: not working");
        //TC12: empty list
        Geometries geometries12 = new Geometries();
        assertNull(geometries12.findIntersections(ray10), "TC12: not working");
        //TC13: one shape only
        Ray ray13 = new Ray(new Point(10, 10, -3), new Vector(0, 0, 1));
        assertEquals(1, geometries.findIntersections(ray13).size(), "TC13: wrong amount");
    }
}