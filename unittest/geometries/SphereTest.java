package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * unit test for Geometries.Sphere
 *
 * @author Ishai zigdon
 * @author Zaki zafrani
 */
class SphereTest {
    /**
     * Test method for {@link Sphere#getNormal(Point)}
     */
    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============

        //TC1: simple test
        Point pt = new Point(2, 0, 0);
        Sphere sphere = new Sphere(pt, 2);
        Vector n = sphere.getNormal(Point.ZERO);
        //ensure |n| = 1
        assertEquals(1, n.length(), "Sphere's normal is not a unit vector");
        // ensure the result is orthogonal to the sphere
        assertThrows(IllegalArgumentException.class, () ->
                n.crossProduct(Point.ZERO.subtract(pt)), "Sphere: wrong normal values");
    }
}