package geometries;

import geometries.Intersectable.GeoPoint;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * unit test for Geometries.Triangle
 *
 * @author Ishai zigdon
 * @author Zaki zafrani
 */
public class TriangleTest {
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
     * Default constructor for TriangleTest.
     */
    public TriangleTest() {/*just fot the javadoc*/}

    /**
     * Test method for {@link Triangle#getNormal(Point)}
     */
    @Test
    void getNormal() {
        /// ============ Equivalence Partitions Tests ==============

        //TC1: simple test
        Point[] pts = {p100, p010, new Point(-1, 0, 0)};
        Triangle t1 = new Triangle(pts[0], pts[1], pts[2]); //
        // ensure there are no exceptions
        assertDoesNotThrow(() -> t1.getNormal(p100), "");
        // generate the test result
        Vector normal = t1.getNormal(p100);
        // ensure |result| = 1
        assertEquals(1, normal.length(), 0.000001, "Triangle's normal is not a unit vector");
        // ensure the result is orthogonal to the triangle
        for (int i = 0; i < 2; ++i)
            assertEquals(0d, normal.dotProduct(pts[i].subtract(pts[i == 0 ? 2 : 0])), 0.000001,
                    "Triangle's normal is not orthogonal to one of the vectors");
    }

    /**
     * Test method for {@link Triangle#findIntersections(Ray)}.
     */
    @Test
    public void testFindGeoIntersectionsHelper() {
        Triangle triangle = new Triangle(p110, p100, p010);
        final Vector v001 = new Vector(0, 0, 1);

        /// ============ Equivalence Partitions Tests ==============

        //TC01: Ray intersects the triangle
        final var result01 = triangle.findGeoIntersectionsHelper(new Ray(new Point(0.7, 0.7, -1), v001));
        var exp = List.of(new GeoPoint(triangle, new Point(0.7, 0.7, 0)));
        assertEquals(exp, result01, "Triangle: findGeoIntersectionsHelper TC01 didnt work");
        // **** Group: Ray doesn't intersect the triangle (but does the plane)
        //TC02: in front of edge
        assertNull(triangle.findGeoIntersectionsHelper(new Ray(new Point(0.3, 0.3, -1), v001)), "TC02: Ray's line is outside of the triangle");
        //TC03: in front of vertex
        assertNull(triangle.findGeoIntersectionsHelper(new Ray(new Point(2, 2, -1), v001)), "TC03: Ray's line is outside of the triangle");

        // =============== Boundary Values Tests ==================

        //TC10: Ray intersects the triangle on the edge
        assertNull(triangle.findGeoIntersectionsHelper(new Ray(new Point(0.5, 0.5, -1), v001)), "TC10: Ray's line is outside of the triangle");
        //TC11: Ray intersects the triangle on the vertex
        assertNull(triangle.findGeoIntersectionsHelper(new Ray(p100, v001)), "TC11: Ray's line is outside of the triangle");
        //TC12: Ray intersects the triangle on the edge line but not on the triangle
        assertNull(triangle.findGeoIntersectionsHelper(new Ray(new Point(-2, 3, -1), v001)), "TC12: Ray's line is outside of the triangle");
    }
}