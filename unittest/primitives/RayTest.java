package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * unit test for primitives.Ray
 *
 * @author Ishai zigdon
 * @author Zaki zafrani
 */
class RayTest {
    /**
     * Test method for {@link Point#Point(double, double, double)}.
     * and Test method for {@link Point#Point(Double3)}.
     */
    @Test
    public void testConstructors() {
        // ============ Equivalence Partitions Tests ==============
        Point p123 = new Point(1,2,3);
        // TC01: simple test
        assertDoesNotThrow(() -> new Ray(p123,new Vector(1,0,0)),"Ray Constructor: failed");
        // =============== Boundary Values Tests ==================
        assertThrows(IllegalArgumentException.class, ()->
                new Ray(p123,new Vector(Double3.ZERO)),
                        "Ray Constructor: 0 vector did not throw IllegalArgumentException");
    }
    /**
     * Test method for {@link Ray#equals(Object)}
     */
    @Test
    void testEquals() {
        // ============ Equivalence Partitions Tests ==============
        Point p123 = new Point(1,2,3);
        Vector v100 = new Vector(1,0,0);
        //TC1: simple test
        Ray r1 = new Ray(p123,v100);
        Ray r2 = new Ray(p123,v100);
        assertTrue(r1.equals(r2), "Ray equals didn't work");
        Ray r3 = new Ray(new Point(1,2,4),new Vector(1,0,1));
        assertFalse(r1.equals(r3), "Ray equals didn't work");
    }

    /**
     * Test method for {@link Ray#toString()}
     */
    @Test
    void testToString() {
        // ============ Equivalence Partitions Tests ==============
        //TC1: simple test
        assertEquals("Ray:(1.0,1.0,1.0)->(0.0,0.0,1.0)",
                new Ray(new Point(1,1,1),new Vector(0,0,1)).toString(),
                "Ray- toString: Regular case didnt work");
    }
}