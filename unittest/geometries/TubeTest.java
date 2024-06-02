package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * unit test for Geometries.Tube
 *
 * @author Ishai zigdon
 * @author Zaki zafrani
 */
public class TubeTest {
    /**
     * Test method for {@link Tube#getNormal(Point)}
     */
    @Test
    void getNormal() {
        Point pM110 = new Point(-1, 1, 0);
        Point p100 = new Point(1, 0, 0);
        Vector v100 = new Vector(1, 0, 0);
        Tube t = new Tube(new Ray(p100, v100), 1);
        // ============ Equivalence Partitions Tests ==============

        //TC1: simple test
        // ensure there are no exceptions
        assertDoesNotThrow(() -> t.getNormal(pM110), "");
        // generate the test result
        Vector normal = t.getNormal(pM110);
        // ensure |result| = 1
        assertEquals(1, normal.length(), 0.000001, "Tube's normal is not a unit vector");
        // ensure the result is orthogonal to the tube
        assertEquals(0d, normal.dotProduct(v100),
                "Tube: wrong normal values");

        // =============== Boundary Values Tests ==================

        Point p110 = new Point(1, 1, 0);
        /*
        TC2: vector between the points is vertical to the direction vector
         ensure there are no exceptions
        */
        assertDoesNotThrow(() -> t.getNormal(p110), "");
        // generate the test result
        Vector normal2 = t.getNormal(p110);
        // ensure |result| = 1
        assertEquals(1, normal2.length(), 0.000001, "Tube's normal is not a unit vector");
        // ensure the result is orthogonal to the tube
        assertThrows(IllegalArgumentException.class, () ->
                normal2.crossProduct(p110.subtract(p100)), "Tube: wrong normal values");
    }

    /**
     * Test method for {@link Tube#findIntersections(Ray)}.
     */
    @Test
    public void testFindIntersections() {
        // ============ Equivalence Partitions Tests ==============
        //TC01:

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line goes through the axis
        //1:start before the axis
        //2:starts with the axis
        //3:start after the axis
        // **** Group: Ray's line is tangent to the Tube (all tests 0 points)
        //4:only one case that i can think of/ regular case
        // **** Group: Ray's line crosses the Tube (but not the center)
        //
    }
}