package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * unit test for Geometries.Plane
 *
 * @author Ishai zigdon
 * @author Zaki zafrani
 */
class PlaneTest {
    /**
     * Test method for {@link Plane#Plane(Point,Point,Point)}.
     * and Test method for {@link Plane#Plane(Point,Vector)}.
     */
    @Test
    public void testConstructors() {
        // ============ Equivalence Partitions Tests ==============
        Point p100 =new Point(1,0,0);
        Point p010 = new Point(0,1,0);
        // TC01: simple test
        assertDoesNotThrow(() -> new Plane(new Point(1,1,1),new Vector(1,0,0)),
                "Point Constructor: failed point and vector constructor");
        assertDoesNotThrow(() -> new Plane(p100,new Point(0,0,1),p010)
                ,"Point Constructor: failed 3 points constructor");
        // =============== Boundary Values Tests ==================
        //TC2: same points
        assertThrows(IllegalArgumentException.class, () -> new Plane(p100,p100,p010)
                ,"Point Constructor: same points didn't throw an exception");
        assertThrows(IllegalArgumentException.class, () -> new Plane(p100,p100,p100)
                ,"Point Constructor: same points didn't throw an exception");
    }
    /**
     * Test method for {@link Plane#getNormal(Point)}
     */
    @Test
    void getNormal() {
        /// ============ Equivalence Partitions Tests ==============
        //TC1: simple test
        Point p100 =new Point(1,0,0);
        Point[] pts ={p100,new Point(0,1,0),new Point(-1,0,0)};
        Plane p1 = new Plane(pts[0],pts[1],pts[2]);
        // ensure there are no exceptions
        assertDoesNotThrow(() -> p1.getNormal(p100),"");
        // generate the test result
        Vector normal = p1.getNormal(p100);
        // ensure |result| = 1
        assertEquals(1, normal.length(), 0.000001, "Plane's normal is not a unit vector");
        // ensure the result is orthogonal to the plane
        for (int i = 0; i < 2; ++i)
            assertEquals(0d, normal.dotProduct(pts[i].subtract(pts[i == 0 ? 2 : i - 1])), 0.000001,
                    "Plane's normal is not orthogonal to one of the vectors");
    }

    /**
     * Test method for {@link Plane#getNormal()}
     */
    @Test
    void testGetNormal() {
        /// ============ Equivalence Partitions Tests ==============
        //TC1: simple test
        Point[] pts ={new Point(1,0,0),new Point(0,1,0),new Point(-1,0,0)};
        Plane p1 = new Plane(pts[0],pts[1],pts[2]);
        // ensure there are no exceptions
        assertDoesNotThrow(() -> p1.getNormal(), "");
        // generate the test result
        Vector normal = p1.getNormal();
        // ensure |result| = 1
        assertEquals(1, normal.length(), 0.000001, "Plane's normal is not a unit vector");
        // ensure the result is orthogonal to the plane
        for (int i = 0; i < 2; ++i)
            assertEquals(0d, normal.dotProduct(pts[i].subtract(pts[i == 0 ? 2 : i - 1])), 0.000001,
                    "Plane's normal is not orthogonal to one of the vectors");
    }
}