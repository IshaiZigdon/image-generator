package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * unit test for Geometries.Cylinder
 *
 * @author Ishai zigdon
 * @author Zaki zafrani
 */
public class CylinderTest {
    /**
     * Default constructor for CylinderTest.
     */
    public CylinderTest() {/*just fot the javadoc*/}

    /**
     * Test method for {@link Cylinder#getNormal(Point)}
     */
    @Test
    void testGetNormal() {
        Vector v100 = new Vector(1, 0, 0);
        Cylinder c = new Cylinder(
                new Ray(Point.ZERO, v100), 1, 1);

        // ============ Equivalence Partitions Tests ==============

        //TC01: point on the side of the cylinder
        Point pHalf10 = new Point(0.5, 1, 0);
        // ensure there are no exceptions
        assertDoesNotThrow(() -> c.getNormal(pHalf10), "");
        // generate the test result
        Vector normal = c.getNormal(pHalf10);
        // ensure |result| = 1
        assertEquals(1, normal.length(), 0.000001, "TC01: Cylinder's normal is not a unit vector");
        // ensure the result is orthogonal to the cylinder
        assertEquals(0d, normal.dotProduct(v100),
                "TC01: Cylinder: wrong normal values");

        //TC02: point on base 1(not in the middle)
        Point p0half0 = new Point(0, 0.5, 0);
        assertDoesNotThrow(() -> c.getNormal(p0half0), "");
        // generate the test result
        Vector normal2 = c.getNormal(p0half0);
        // ensure |result| = 1
        assertEquals(1, normal2.length(), 0.000001, "TC02: Cylinder's normal is not a unit vector");
        // ensure the result is orthogonal to the Cylinder
        assertEquals(normal2, v100, ": TC02Cylinder: wrong normal values");

        //TC03: point on base 2(not in the middle)
        Point p1half0 = new Point(1, 0.5, 0);
        assertDoesNotThrow(() -> c.getNormal(p1half0), "");
        // generate the test result
        Vector normal3 = c.getNormal(p1half0);
        // ensure |result| = 1
        assertEquals(1, normal3.length(), 0.000001, "TC03: Cylinder's normal is not a unit vector");
        // ensure the result is orthogonal to the Cylinder
        assertEquals(normal3, v100, "TC03: Cylinder: wrong normal values");

        // =============== Boundary Values Tests ==================

        //TC10: point on base 1 IN THE MIDDLE
        assertDoesNotThrow(() -> c.getNormal(Point.ZERO), "");
        // generate the test result
        Vector normal4 = c.getNormal(Point.ZERO);
        // ensure |result| = 1
        assertEquals(1, normal4.length(), 0.000001, "TC10: Cylinder's normal is not a unit vector");
        // ensure the result is orthogonal to the Cylinder
        assertEquals(normal4, v100, "TC10: Cylinder: wrong normal values");

        //TC11: point on base 2 IN THE MIDDLE
        Point p100 = new Point(1, 0, 0);
        assertDoesNotThrow(() -> c.getNormal(p100), "");
        // generate the test result
        Vector normal5 = c.getNormal(p100);
        // ensure |result| = 1
        assertEquals(1, normal5.length(), 0.000001, "TC11: Cylinder's normal is not a unit vector");
        // ensure the result is orthogonal to the Cylinder
        assertEquals(normal5, v100, "TC11: Cylinder: wrong normal values");
    }

    /**
     * Test method for {@link Cylinder#findIntersections(Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Point p100 = new Point(1, 0, 0);
        Vector v200 = new Vector(2, 0, 0);
        Cylinder cylinder = new Cylinder(new Ray(p100, v200), 1, 1);

        // ============ Equivalence Partitions Tests ==============

        // =============== Boundary Values Tests ==================
        Vector v001 = new Vector(0, 0, 1);
        //1: ray line is on 1 base
        Ray ray1 = new Ray(new Point(1, 0, -2), v001);
        assertNull(cylinder.findIntersections(ray1), "does not return null");
        //2: ray line is on 2 base
        Ray ray2 = new Ray(new Point(2, 0, -2), v001);
        assertNull(cylinder.findIntersections(ray2), "does not return null");

        // **** Group: only intersects on the edge of base 1
        Vector v303 = new Vector(3, 0, 3);
        //11:before
        Ray ray11 = new Ray(new Point(-1, 0, -1), v303);
        assertNull(cylinder.findIntersections(ray11), "does not return null");
        //12:on
        Ray ray12 = new Ray(new Point(1, 0, 1), v303);
        assertNull(cylinder.findIntersections(ray12), "does not return null");
        //13:after
        Ray ray13 = new Ray(new Point(2, 0, 2), v303);
        assertNull(cylinder.findIntersections(ray13), "does not return null");

        // **** Group: only intersects on the edge of base 2
        Vector v30M3 = new Vector(3, 0, -3);
        //21:before
        Ray ray21 = new Ray(new Point(4, 0, -1), v30M3);
        assertNull(cylinder.findIntersections(ray21), "does not return null");
        //22:on
        Ray ray22 = new Ray(new Point(2, 0, 1), v30M3);
        assertNull(cylinder.findIntersections(ray22), "does not return null");
        //23:after
        Ray ray23 = new Ray(new Point(1, 0, 2), v30M3);
        assertNull(cylinder.findIntersections(ray23), "does not return null");
    }
}