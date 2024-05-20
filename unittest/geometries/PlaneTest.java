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
        // TC01: simple test
        assertDoesNotThrow(() -> new Plane(new Point(1,1,1),new Vector(1,0,0)),
                "Point Constructor: failed point and vector constructor");
        assertDoesNotThrow(() -> new Plane(new Point(1,0,0),new Point(0,0,1),new Point(0,1,0))
                ,"Point Constructor: failed 3 points constructor");
        // =============== Boundary Values Tests ==================
        //TC2: same points
        assertThrows(IllegalArgumentException.class, () -> new Plane(new Point(1,0,0),new Point(1,0,0),new Point(0,1,0))
                ,"Point Constructor: same points didn't throw an exception");
        assertThrows(IllegalArgumentException.class, () -> new Plane(new Point(1,0,0),new Point(1,0,0),new Point(1,0,0))
                ,"Point Constructor: same points didn't throw an exception");
    }
    /**
     * Test method for {@link Plane#getNormal(Point)}
     */
    @Test
    void getNormal() {
        /// ============ Equivalence Partitions Tests ==============
        //TC1: simple test
        Plane p1 = new Plane(new Point(1,0,0),new Point(0,1,0),new Point(-1,0,0));
        Vector normal = p1.getNormal();
        assertDoesNotThrow(()->new Vector(0,0,1).crossProduct(normal),"Plane getNormal: wrong normal");
        // =============== Boundary Values Tests ==================
    }

    /**
     * Test method for {@link Plane#getNormal()}
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        //TC1: simple test
        Plane p1 = new Plane(new Point(1,0,0),new Point(0,1,0),new Point(-1,0,0));
        Vector normal = p1.getNormal();
        assertDoesNotThrow(()->new Vector(0,0,1).crossProduct(normal),"Plane getNormal: wrong normal");
        // =============== Boundary Values Tests ==================
    }
}