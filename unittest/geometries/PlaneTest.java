package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * unit test for Geometries.Plane
 *
 * @author Ishai zigdon
 * @author Zaki zafrani
 */
public class PlaneTest {
    /**
     * point 100 for testing
     */
    private final Point p100 = new Point(1, 0, 0);
    /**
     * point 010 for testing
     */
    private final Point p010 = new Point(0, 1, 0);

    private final Point p110 = new Point(1, 1, 0);

    /**
     * Test method for {@link Plane#Plane(Point, Point, Point)}.
     * and Test method for {@link Plane#Plane(Point, Vector)}.
     */
    @Test
    public void testConstructors() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: simple test
        assertDoesNotThrow(() -> new Plane(new Point(1, 1, 1), new Vector(1, 0, 0)),
                "Plane Constructor: failed point and vector constructor");
        assertDoesNotThrow(() -> new Plane(p100, new Point(0, 0, 1), p010)
                , "Plane Constructor: failed 3 points constructor");

        // =============== Boundary Values Tests ==================

        //TC2: same points
        assertThrows(IllegalArgumentException.class, () -> new Plane(p100, p100, p010)
                , "Plane Constructor: same points didn't throw an exception");
        assertThrows(IllegalArgumentException.class, () -> new Plane(p100, p100, p100)
                , "Plane Constructor: same points didn't throw an exception");
    }

    /**
     * Test method for {@link Plane#getNormal(Point)}
     */
    @Test
    void getNormal() {
        /// ============ Equivalence Partitions Tests ==============

        //TC1: simple test
        Point[] pts = {p100, p010, new Point(-1, 0, 0)};
        Plane p1 = new Plane(pts[0], pts[1], pts[2]);
        // ensure there are no exceptions
        assertDoesNotThrow(() -> p1.getNormal(p100), "");
        // generate the test result
        Vector normal = p1.getNormal(p100);
        // ensure |result| = 1
        assertEquals(1, normal.length(), 0.000001, "Plane's normal is not a unit vector");
        // ensure the result is orthogonal to the plane
        for (int i = 0; i < 2; ++i)
            assertEquals(0d, normal.dotProduct(pts[i].subtract(pts[i == 0 ? 2 : 0])), 0.000001,
                    "Plane's normal is not orthogonal to one of the vectors");
    }

    /**
     * Test method for {@link Plane#getNormal()}
     */
    @Test
    void testGetNormal() {
        /// ============ Equivalence Partitions Tests ==============

        //TC1: simple test
        Point[] pts = {p100, p010, new Point(-1, 0, 0)};
        Plane p1 = new Plane(pts[0], pts[1], pts[2]);
        // ensure there are no exceptions
        assertDoesNotThrow(() -> p1.getNormal(), "");
        // generate the test result
        Vector normal = p1.getNormal();
        // ensure |result| = 1
        assertEquals(1, normal.length(), 0.000001, "Plane's normal is not a unit vector");
        // ensure the result is orthogonal to the plane
        for (int i = 0; i < 2; ++i)
            assertEquals(0d, normal.dotProduct(pts[i].subtract(pts[i == 0 ? 2 : 0])), 0.000001,
                    "Plane's normal is not orthogonal to one of the vectors");
    }

    /**
     * Test method for {@link Plane#findIntersections(Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Plane plane = new Plane(p110, p100, p010);

        /// ============ Equivalence Partitions Tests ==============

        final Vector v101 = new Vector(1, 0, 1);
        //TC01: Ray intersects the plane
        final Point pM1hh0 = new Point(-1.5, 0.5, -1);
        final var result01 = plane.findIntersections(new Ray(pM1hh0, v101));
        var exp = List.of(new Point(-0.5, 0.5, 0));
        assertEquals(exp, result01, "Plane: findIntersections TC01 failed");
        //TC02: Ray doesn't intersect the plane
        final Point pH0h = new Point(0.5, 0, 0.5);
        assertNull(plane.findIntersections(new Ray(pH0h, v101)), "Ray's line out of plane");

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line parallel to the plane
        final Vector v100 = new Vector(1, 0, 0);
        //TC10: Ray is on the plane
        assertNull(plane.findIntersections(new Ray(p100, v100)), "Ray's line is on the plane");
        //TC11: Ray not on the plane
        //pH0h = new Point(0.5, 0, 0.5);
        assertNull(plane.findIntersections(new Ray(pH0h, v100)), "Ray's line out of plane");
        // **** Group: Ray's line vertical to the plane
        final Vector v001 = new Vector(0, 0, 1);
        //TC12: Ray begins before the plane
        final Point pM10M1 = new Point(-1, 0, -1);
        final var result12 = plane.findIntersections(new Ray(pM10M1, v001));
        assertEquals(List.of(new Point(-1, 0, 0)), result12, "Plane: findIntersections TC12 failed");
        //TC13: Ray begins on the plane
        assertNull(plane.findIntersections(new Ray(new Point(2, 1, 0), v001)), "Ray's line out of plane");
        //TC14: Ray begins after the plane
        //pHalf = new Point(0.5, 0, 0.5);
        assertNull(plane.findIntersections(new Ray(pH0h, v001)), "Ray's line out of plane");
        // **** Group: special cases
        //TC15: Ray head point is the same as plane q point
        assertNull(plane.findIntersections(new Ray(p110, v101)), "Ray's line out of plane");
        //TC16: Ray head point is on the plane
        assertNull(plane.findIntersections(new Ray(p100, v101)), "Ray's line out of plane");
    }
}