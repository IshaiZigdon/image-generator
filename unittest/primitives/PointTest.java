package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * unit test for primitives.Point
 *
 * @author Ishai zigdon
 * @author Zaki zafrani
 */
public class PointTest {
    /**
     * Default constructor for PointTest.
     */
    public PointTest() {/*just fot the javadoc*/}
    /**
     * Test method for {@link Point#Point(double, double, double)}.
     * and Test method for {@link Point#Point(Double3)}.
     */
    @Test
    public void testConstructors() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: simple test
        assertDoesNotThrow(() -> new Point(1, 2, 3), "TC01: Point Constructor: failed 3 doubles constructor");
        assertDoesNotThrow(() -> new Point(new Double3(1, 2, 3)), "TC01: Point Constructor: failed Double3 constructor");
    }

    /**
     * Test method for {@link Point#add(Vector)}
     */
    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============

        //TC01: simple test
        assertEquals(new Point(2, 0, 1),
                new Point(1, 0, 0).add(new Vector(1, 0, 1)),
                "TC01: Point add: simple add didn't work");

        // =============== Boundary Values Tests ==================

        //TC10: 0 test
        assertEquals(Point.ZERO,
                new Point(1, 0, 0).add(new Vector(-1, 0, 0)),
                "TC10: Point add: opposite values didn't work");
    }

    /**
     * Test method for {@link Point#subtract(Point)}
     */
    @Test
    void testSubtract() {
        // ============ Equivalence Partitions Tests ==============

        //TC01: simple test
        assertEquals(new Vector(1, 0, 0),
                new Point(2, 0, 1).subtract(new Point(1, 0, 1)),
                "TC01: Point subtract: simple sub didn't work");

        // =============== Boundary Values Tests ==================

        //TC10: vector 0 test
        assertThrows(IllegalArgumentException.class, () ->
                        new Point(1, 0, 0).subtract(new Point(1, 0, 0)),
                "TC10: Point subtract: zero vector didn't threw an exception");
    }

    /**
     * Test method for {@link Point#distanceSquared(Point)}
     */
    @Test
    void testDistanceSquared() {
        Point p201 = new Point(2, 0, 1);
        // ============ Equivalence Partitions Tests ==============

        //TC01: simple test
        assertEquals(1,
                p201.distanceSquared(new Point(1, 0, 1)),
                "TC01: Point DistanceSquared: simple DistanceSquared didn't work");

        // =============== Boundary Values Tests ==================

        //TC10: 0 test
        assertEquals(0,
                p201.distanceSquared(p201),
                "TC10: Point DistanceSquared: 0 DistanceSquared didn't work");
    }

    /**
     * Test method for {@link Point#distance(Point)}
     */
    @Test
    void testDistance() {
        Point p201 = new Point(2, 0, 1);
        // ============ Equivalence Partitions Tests ==============

        //TC01: simple test
        assertEquals(1,
                p201.distance(new Point(1, 0, 1)),
                "TC01: Point Distance: simple Distance didn't work");

        // =============== Boundary Values Tests ==================

        //TC10: 0 test
        assertEquals(0,
                p201.distance(p201),
                "TC10: Point Distance: 0 Distance didn't work");
    }

    /**
     * Test method for {@link Point#equals(Object)}
     */
    @Test
    void testEquals() {
        // ============ Equivalence Partitions Tests ==============

        //TC01: simple test
        Point p1 = new Point(1, 2, 3);
        Point p2 = new Point(1, 2, 3);
        assertTrue(p1.equals(p2), "TC01: Point equals didn't work");
        Point p3 = new Point(1, 2, 4);
        assertFalse(p1.equals(p3), "TC01: Point equals didn't work");
    }

    /**
     * Test method for {@link Point#toString()}
     */
    @Test
    void testToString() {
        // ============ Equivalence Partitions Tests ==============

        //TC01: simple test
        assertEquals("Point: (1.0,2.0,3.0)", new Point(1, 2, 3).toString(),
                "TC01: Point- toString: Regular case didnt work");
    }
}