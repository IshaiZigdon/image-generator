package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * unit test for Geometries.Cylinder
 *
 * @author Ishai zigdon
 * @author Zaki zafrani
 */
class CylinderTest {
    /**
     * Test method for {@link Cylinder#getNormal(primitives.Point)}
     */
    @Test
    void testGetNormal() {
        Vector v100 = new Vector(1,0,0);
        Cylinder c = new Cylinder(
                new Ray(Point.ZERO,v100), 1,1);

        // ============ Equivalence Partitions Tests ==============
        //TC1: point on the side of the cylinder
        Point p010= new Point(0,1,0);
        // ensure there are no exceptions
        assertDoesNotThrow(() -> c.getNormal(p010), "");
        // generate the test result
        Vector normal = c.getNormal(p010);
        // ensure |result| = 1
        assertEquals(1, normal.length(), 0.000001, "Cylinder's normal is not a unit vector");
        // ensure the result is orthogonal to the tube
        assertEquals(0d, normal.dotProduct(v100),
                "Cylinder: wrong normal values");

        //TC2: point on base 1(not in the middle)
        Point p1half0 = new Point(1,0.5,0);
        assertDoesNotThrow(() -> c.getNormal(p1half0), "");
        // generate the test result
        Vector normal2 = c.getNormal(p1half0);
        // ensure |result| = 1
        assertEquals(1, normal2.length(), 0.000001, "Cylinder's normal is not a unit vector");
        // ensure the result is orthogonal to the tube
        assertThrows(IllegalArgumentException.class,()->
                normal2.crossProduct(v100), "Cylinder: wrong normal values");

        //TC3: point on base 2(not in the middle)
        Point pM1half0 = new Point(-1,0.5,0);
        assertDoesNotThrow(() -> c.getNormal(pM1half0), "");
        // generate the test result
        Vector normal3 = c.getNormal(pM1half0);
        // ensure |result| = 1
        assertEquals(1, normal3.length(), 0.000001, "Cylinder's normal is not a unit vector");
        // ensure the result is orthogonal to the tube
        assertThrows(IllegalArgumentException.class,()->
                normal3.crossProduct(v100), "Cylinder: wrong normal values");

        // =============== Boundary Values Tests ==================
        //TC4: point on base 1 IN THE MIDDLE
        Point p100 = new Point(1,0,0);
        assertDoesNotThrow(() -> c.getNormal(p100), "");
        // generate the test result
        Vector normal4 = c.getNormal(p100);
        // ensure |result| = 1
        assertEquals(1, normal4.length(), 0.000001, "Cylinder's normal is not a unit vector");
        // ensure the result is orthogonal to the tube
        assertThrows(IllegalArgumentException.class,()->
                normal4.crossProduct(v100), "Cylinder: wrong normal values");

        //TC5: point on base 2 IN THE MIDDLE
        Point pM100 = new Point(-1,0,0);
        assertDoesNotThrow(() -> c.getNormal(pM100), "");
        // generate the test result
        Vector normal5 = c.getNormal(pM100);
        // ensure |result| = 1
        assertEquals(1, normal5.length(), 0.000001, "Cylinder's normal is not a unit vector");
        // ensure the result is orthogonal to the tube
        assertThrows(IllegalArgumentException.class,()->
                normal5.crossProduct(v100), "Cylinder: wrong normal values");


    }
}