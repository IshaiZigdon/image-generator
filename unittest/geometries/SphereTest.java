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
    private final Point p100 = new Point(1, 0, 0);

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
        final Point p02 = new Point(1, 0.6667, 0);
        final Point p03 = new Point(2, 1, 0);
        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(p01, v110)), "Ray's line out of sphere");
        // TC02: Ray starts before and crosses the sphere (2 points)
        final var result1 = sphere.findIntersections(new Ray(p01, v310)).stream().sorted(Comparator.comparingDouble(p -> p.distance(p01))).toList();
        assertEquals(2, result1.size(), "Wrong number of points");
        assertEquals(exp, result1, "Ray crosses sphere");
        // TC03: Ray starts inside the sphere (1 point)
        final var result2 = sphere.findIntersections(new Ray(p02, v310));
        assertEquals(1, result2.size(), "Wrong number of points");
        exp = List.of(gp2);
        assertEquals(exp, result2, "Ray crosses sphere");
        // TC04: Ray starts after the sphere (0 points)
        final var result3 = sphere.findIntersections(new Ray(p03, v310));
        assertEquals(0, result3.size(), "Wrong number of points");
        // =============== Boundary Values Tests ==================
        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 point)
        final var result4 = sphere.findIntersections(new Ray(gp1, v310));
        assertEquals(1, result4.size(), "Wrong number of points");
        //exp = List.of(gp2);
        assertEquals(exp, result4, "Ray crosses sphere");
        // TC12: Ray starts at sphere and goes outside (0 points)
        final var result5 = sphere.findIntersections(new Ray(gp2, v310));
        assertEquals(0, result5.size(), "Wrong number of points");
        // **** Group: Ray's line goes through the center
        final Vector v300 = new Vector(3, 0, 0);
        final Point p200 = new Point(2, 0, 0);
        // TC13: Ray starts before the sphere (2 points)
        final var result6 = sphere.findIntersections(new Ray(p01, v300)).stream().sorted(Comparator.comparingDouble(p -> p.distance(p01))).toList();
        assertEquals(2, result6.size(), "Wrong number of points");
        exp = List.of(Point.ZERO, p200);
        assertEquals(exp, result6, "Ray crosses sphere");
        // TC14: Ray starts at sphere and goes inside (1 point)
        final var result7 = sphere.findIntersections(new Ray(Point.ZERO, v300));
        assertEquals(1, result7.size(), "Wrong number of points");
        exp = List.of(p200);
        assertEquals(exp, result7, "Ray crosses sphere");
        // TC15: Ray starts inside (1 point)
        final Point pHalf = new Point(0.5, 0, 0);
        final var result8 = sphere.findIntersections(new Ray(pHalf, v300));
        assertEquals(1, result8.size(), "Wrong number of points");
        //exp = List.of(p200);
        assertEquals(exp, result8, "Ray crosses sphere");
        // TC16: Ray starts at the center (1 point)
        final var result9 = sphere.findIntersections(new Ray(p100, v300));
        assertEquals(1, result9.size(), "Wrong number of points");
        //exp = List.of(p200);
        assertEquals(exp, result9, "Ray crosses sphere");
        // TC17: Ray starts at sphere and goes outside (0 points)
        final var result10 = sphere.findIntersections(new Ray(p200, v300));
        assertEquals(0, result10.size(), "Wrong number of points");
        // TC18: Ray starts after sphere (0 points)
        final Point p2Half = new Point(2.5, 0, 0);
        final var result11 = sphere.findIntersections(new Ray(p2Half, v300));
        assertEquals(0, result11.size(), "Wrong number of points");
        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        final Point p010 = new Point(0, 1, 0);
        final var result12 = sphere.findIntersections(new Ray(p010, v300));
        assertEquals(0, result12.size(), "Wrong number of points");
        // TC20: Ray starts at the tangent point
        final Point p110 = new Point(1, 1, 0);
        final var result13 = sphere.findIntersections(new Ray(p110, v300));
        assertEquals(0, result13.size(), "Wrong number of points");
        // TC21: Ray starts after the tangent point
        // p03 = new Point(2, 1, 0);
        final var result14 = sphere.findIntersections(new Ray(p03, v300));
        assertEquals(0, result14.size(), "Wrong number of points");
        // **** Group: Special cases
        // TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        final Point p020 = new Point(0, 2, 0);
        final var result15 = sphere.findIntersections(new Ray(p020, v300));
        assertEquals(0, result15.size(), "Wrong number of points");
        // TC23: Ray's line is inside, ray is orthogonal to ray start to sphere's center line
        final Point p04 = new Point(0.6, 0.8, 0);
        final var result16 = sphere.findIntersections(new Ray(p04, v310));
        assertEquals(1, result15.size(), "Wrong number of points");
        exp = List.of(gp2);
        assertEquals(exp, result16, "Ray crosses sphere");
    }
}