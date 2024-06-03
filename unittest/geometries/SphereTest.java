package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * unit test for Geometries.Sphere
 *
 * @author Ishai zigdon
 * @author Zaki zafrani
 */
public class SphereTest {
    /**
     * center of testing sphere
     */
    private static final Point p100 = new Point(1, 0, 0);

    /**
     * Default constructor for SphereTest.
     */
    public SphereTest() {/*just fot the javadoc*/}

    /**
     * Test method for {@link Sphere#getNormal(Point)}
     */
    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============

        //TC1: simple test
        Point pt = new Point(2, 0, 0);
        Sphere sphere = new Sphere(pt, 2d);
        Vector n = sphere.getNormal(Point.ZERO);
        //ensure |n| = 1
        assertEquals(1, n.length(), "Sphere's normal is not a unit vector");
        // ensure the result is orthogonal to the sphere
        assertThrows(IllegalArgumentException.class, () ->
                n.crossProduct(Point.ZERO.subtract(pt)), "Sphere: wrong normal values");
    }

    /**
     * Test method for {@link Sphere#findIntersections(Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Sphere sphere = new Sphere(p100, 1d);
        final Point gp1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        final Point gp2 = new Point(1.53484692283495, 0.844948974278318, 0);
        var exp = List.of(gp1, gp2);
        final Vector v310 = new Vector(3, 1, 0);
        final Vector v110 = new Vector(1, 1, 0);
        final Point p01 = new Point(-1, 0, 0);
        final Point p02 = new Point(1, 0.66666666666667, 0);
        final Point p03 = new Point(2, 1, 0);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(p01, v110)), "TC01: Ray's line out of sphere");
        // TC02: Ray starts before and crosses the sphere (2 points)
        final var result02 = sphere.findIntersections(new Ray(p01, v310)).stream().sorted(Comparator.comparingDouble(p -> p.distance(p01))).toList();
        assertEquals(2, result02.size(), "TC02: Wrong number of points");
        assertEquals(exp, result02, "TC02: Ray crosses sphere");
        // TC03: Ray starts inside the sphere (1 point)
        final var result03 = sphere.findIntersections(new Ray(p02, v310));
        assertEquals(1, result03.size(), "TC03: Wrong number of points");
        exp = List.of(gp2);
        assertEquals(exp, result03, "TC03: Ray crosses sphere");
        // TC04: Ray starts after the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(p03, v310)), "TC04: Ray's line out of sphere");

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC10: Ray starts at sphere and goes inside (1 point)
        final var result11 = sphere.findIntersections(new Ray(gp1, v310));
        assertEquals(1, result11.size(), "TC10: Wrong number of points");
        //exp = List.of(gp2);
        assertEquals(exp, result11, "TC10: Ray crosses sphere");
        // TC11: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(gp2, v310)), "TC11: Ray's line out of sphere");
        // **** Group: Ray's line goes through the center
        final Vector v300 = new Vector(3, 0, 0);
        final Point p200 = new Point(2, 0, 0);
        // TC12: Ray starts before the sphere (2 points)
        final var result13 = sphere.findIntersections(new Ray(p01, v300)).stream().sorted(Comparator.comparingDouble(p -> p.distance(p01))).toList();
        assertEquals(2, result13.size(), "TC12: Wrong number of points");
        exp = List.of(Point.ZERO, p200);
        assertEquals(exp, result13, "TC12: Ray crosses sphere");
        // TC13: Ray starts at sphere and goes inside (1 point)
        final var result14 = sphere.findIntersections(new Ray(Point.ZERO, v300));
        assertEquals(1, result14.size(), "TC13: Wrong number of points");
        exp = List.of(p200);
        assertEquals(exp, result14, "TC13: Ray crosses sphere");
        // TC14: Ray starts inside (1 point)
        final Point pHalf = new Point(0.5, 0, 0);
        final var result15 = sphere.findIntersections(new Ray(pHalf, v300));
        assertEquals(1, result15.size(), "TC14: Wrong number of points");
        //exp = List.of(p200);
        assertEquals(exp, result15, "TC14: Ray crosses sphere");
        // TC15: Ray starts at the center (1 point)
        final var result16 = sphere.findIntersections(new Ray(p100, v300));
        assertEquals(1, result16.size(), "TC15: Wrong number of points");
        //exp = List.of(p200);
        assertEquals(exp, result16, "TC15: Ray crosses sphere");
        // TC16: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findIntersections(new Ray(p200, v300)), "TC16: Ray's line out of sphere");
        // TC17: Ray starts after sphere (0 points)
        final Point p2Half = new Point(2.5, 0, 0);
        assertNull(sphere.findIntersections(new Ray(p2Half, v300)), "TC17: Ray's line out of sphere");
        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC18: Ray starts before the tangent point
        final Point p010 = new Point(0, 1, 0);
        assertNull(sphere.findIntersections(new Ray(p010, v300)), "TC18: Ray's line out of sphere");
        // TC19: Ray starts at the tangent point
        final Point p110 = new Point(1, 1, 0);
        assertNull(sphere.findIntersections(new Ray(p110, v300)), "TC19: Ray's line out of sphere");
        // TC20: Ray starts after the tangent point
        // p03 = new Point(2, 1, 0);
        assertNull(sphere.findIntersections(new Ray(p03, v300)), "TC20: Ray's line out of sphere");
        // **** Group: Special cases
        // TC21: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        final Point p020 = new Point(0, 2, 0);
        assertNull(sphere.findIntersections(new Ray(p020, v300)), "TC21: Ray's line out of sphere");
        // TC22: Ray's line is inside, ray is orthogonal to ray start to sphere's center line
        final Point p04 = new Point(0.8, 0.6, 0);
        final var result22 = sphere.findIntersections(new Ray(p04, v310));
        assertEquals(1, result22.size(), "TC22: Wrong number of points");
        exp = List.of(gp2);
        assertEquals(exp, result22, "TC22: Ray crosses sphere");
    }
}