package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
/**
 * unit test for Geometries.Triangle
 *
 * @author Ishai zigdon
 * @author Zaki zafrani
 */
class TriangleTest {
    /**
     * Test method for {@link Triangle#getNormal(Point)}
     */
    @Test
    void getNormal() {
        /// ============ Equivalence Partitions Tests ==============
        //TC1: simple test
        Point[] pts = {new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 0, 0)};
        Triangle t1 = new Triangle();
        // ensure there are no exceptions
        assertDoesNotThrow(() -> t1.getNormal(new Point(1, 0, 0)), "");
        // generate the test result
        Vector normal = t1.getNormal(new Point(1, 0, 0));
        // ensure |result| = 1
        assertEquals(1, normal.length(), 0.000001, "Triangle's normal is not a unit vector");
        // ensure the result is orthogonal to the triangle
        for (int i = 0; i < 2; ++i)
            assertEquals(0d, normal.dotProduct(pts[i].subtract(pts[i == 0 ? 2 : i - 1])), 0.000001,
                    "Triangle's normal is not orthogonal to one of the vectors");
    }
}