package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;


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
        Point pM100 = new Point(-1, 0, 0);
        Vector v100 = new Vector(1, 0, 0);
        Cylinder cylinder = new Cylinder(new Ray(pM100, v100), 1, 5);

        Vector v1M11 = new Vector(1, -1, 1);
        // Points of intersection
        Point p010 = new Point(0, 1, 0);
        Point p101 = new Point(1, 0, 1);
        var exp = List.of(p010, p101);
        // TC02: start before the tube
        Ray r02 = new Ray(new Point(-1, 2, -1), v1M11);
        List<Point> result02 = cylinder.findIntersections(r02);
        assertEquals(2, result02 != null ? result02.size() : 0, "TC02: wrong number of intersections");
        assertEquals(exp, result02, "TC02: wrong points");

        // ============ Equivalence Partitions Tests ==============
        //before
        Ray ray01 = new Ray(new Point(-2,0,0), v100);
        var result = cylinder.findIntersections(ray01);
        exp = List.of(pM100,new Point(4,0,0));
        assertEquals(exp,result,"");

        //after
        Ray ray02 = new Ray(Point.ZERO, v100);
        result = cylinder.findIntersections(ray02);
        exp = List.of(new Point(4,0,0));
        assertEquals(exp,result,"");

        // =============== Boundary Values Tests ==================
        Vector v001 = new Vector(0, 0, 1);
        //1: ray line is on 1 base
        Ray ray1 = new Ray(new Point(-1, 0, -2), v001);
        assertNull(cylinder.findIntersections(ray1), "does not return null");
        //2: ray line is on 2 base
        Ray ray2 = new Ray(new Point(4, 0, -2), v001);
        assertNull(cylinder.findIntersections(ray2), "does not return null");

        // **** Group: only intersects on the edge of base 1
        Vector v101 = new Vector(1, 0, 1);
        //11:before
        Ray ray11 = new Ray(new Point(-2, 0, -2), v101);
        var exp11 = List.of(new Point(1,0,1));
        assertEquals(exp11,cylinder.findIntersections(ray11),"not the same point");
        //12:on
        Ray ray12 = new Ray(new Point(1, 0, 1), v101);
        assertNull(cylinder.findIntersections(ray12), "does not return null");
        //13:after
        Ray ray13 = new Ray(new Point(2, 0, 2), v101);
        assertNull(cylinder.findIntersections(ray13), "does not return null");

        // **** Group: only intersects on the edge of base 2
        Vector v10M1 = new Vector(1, 0, -1);
        //21:before
        Ray ray21 = new Ray(new Point(4, 0, -1), v10M1);
        assertNull(cylinder.findIntersections(ray21), "does not return null");
        //22:on
        Ray ray22 = new Ray(new Point(2, 0, 1), v10M1);
        assertNull(cylinder.findIntersections(ray22), "does not return null");
        //23:after
        Ray ray23 = new Ray(new Point(5, 0, -2), v10M1);
        assertNull(cylinder.findIntersections(ray23), "does not return null");

        //****Group: ray parallel to the cylinder ray going through the center

        //24:before (2 points)
        Ray ray24 = new Ray(new Point(-2,0,0), v100);
        var exp24 = List.of(new Point(-1,0,0),new Point(4,0,0));
        assertEquals(exp24,cylinder.findIntersections(ray24));

        //25: in the middle(1 point)
        Ray ray25 = new Ray(new Point(2,0,0), v100);
        var exp25 = List.of(new Point(4,0,0));
        assertEquals(exp25,cylinder.findIntersections(ray25));
        //26: after the second base
        Ray ray26 = new Ray(new Point(5,0,0), v100);
        assertNull(cylinder.findIntersections(ray26),"answer is not null");

        //****Group: on the surface of the tube

        //27:before
        Ray ray27 = new Ray(new Point(-3,0,1), v100);
        assertNull(cylinder.findIntersections(ray27),"answer is not null");

        //28:on
        Ray ray28 = new Ray(new Point(-1,0,1), v100);
        assertNull(cylinder.findIntersections(ray28),"answer is not null");
        //29:after
        Ray ray29 = new Ray(new Point(5,0,1), v100);
        assertNull(cylinder.findIntersections(ray29),"answer is not null");

        //****Group: going through the center with some intersection

        //** mini-group: base 1

        //30:before (2 points)
        Ray ray30 = new Ray(new Point(-2,0,-1), v101);
        var exp30 = List.of(new Point(0,0,1),new Point(-1,0,0));
        assertEquals(exp30,cylinder.findIntersections(ray30));

        //31:on (1 point)
        Ray ray31 = new Ray(new Point(-1,0,0), v101);
        var exp31 = List.of(new Point(0,0,1));
        assertEquals(exp31,cylinder.findIntersections(ray31));

        //32: after(null)
        Ray ray32 = new Ray(new Point(1,0,2), v101);
        assertNull(cylinder.findIntersections(ray32),"answer is not null");

        //** mini-group: base 2:
        //33:before (2 points)
        Ray ray33 = new Ray(new Point(2,0,2), v10M1);
        var exp33 = List.of(new Point(3,0,1),new Point(4,0,0));
        assertEquals(exp33,cylinder.findIntersections(ray33));

        //34:on (1 point)
        Ray ray34 = new Ray(new Point(3,0,1), v10M1);
        var exp34 = List.of(new Point(4,0,0));
        assertEquals(exp34,cylinder.findIntersections(ray34));

        //35: after(null)
        Ray ray35 = new Ray(new Point(5,0,-1), v10M1);
        assertNull(cylinder.findIntersections(ray35),"answer is not null");


    }
}