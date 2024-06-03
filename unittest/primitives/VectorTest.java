package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * unit test for primitives.Vector
 *
 * @author Ishai zigdon
 * @author Zaki zafrani
 */
public class VectorTest {
    /**
     * Default constructor for VectorTest.
     */
    public VectorTest() {/*just fot the javadoc*/}

    /**
     * Test method for {@link Vector#Vector(double, double, double)}.
     * and Test method for {@link Vector#Vector(Double3)}.
     */
    @Test
    public void testConstructors() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: simple test
        assertDoesNotThrow(() -> new Vector(1, 2, 3), "TC01: Vector Constructor: failed 3 doubles constructor");
        assertDoesNotThrow(() -> new Vector(new Double3(1, 2, 3)), "TC01: Vector Constructor: failed Double3 constructor");

        // =============== Boundary Values Tests ==================

        //TC02: if Throws an error when implemented with 0 values
        assertThrows(IllegalArgumentException.class,
                () -> new Vector(Double3.ZERO), "TC02: Vector Constructor: zero vector does not throw an exception");
    }

    /**
     * Test method for {@link Vector#add(Vector)}
     */
    @Test
    void testAdd() {
        Vector v100 = new Vector(1, 0, 0);
        // ============ Equivalence Partitions Tests ==============

        //TC01: Less then 90 degree between vectors
        assertEquals(new Vector(2, 0, 1),
                v100.add(new Vector(1, 0, 1)),
                "TC01: Vector add: Less then 90 degree Didnt work");

        //TC02: More the 90 degree between vectors
        assertEquals(new Vector(0, 0, 1),
                v100.add(new Vector(-1, 0, 1)),
                "TC02: Vector add: More then 90 degree Didnt work");

        // =============== Boundary Values Tests ==================

        //TC10: Opposite side vectors
        assertThrows(IllegalArgumentException.class,
                () -> v100.add(new Vector(-1, 0, 0)),
                "TC10: Vector add: Opposite Vector didn't throw an exception");
    }

    /**
     * Test method for {@link Vector#subtract(Point)}
     */
    @Test
    void testSubtract() {
        Vector v100 = new Vector(1, 0, 0);
        // ============ Equivalence Partitions Tests ==============

        //TC01: simple test
        assertEquals(v100,
                new Vector(2, 0, 1).subtract(new Point(1, 0, 1)),
                "TC01: Vector subtract: simple sub didn't work");

        // =============== Boundary Values Tests ==================

        //TC10: vector 0 test
        assertThrows(IllegalArgumentException.class, () ->
                        v100.subtract(v100),
                "TC10: Vector subtract: zero vector didn't threw an exception");
    }

    /**
     * Test method for {@link Vector#scale(double)}
     */
    @Test
    void testScale() {
        Vector v100 = new Vector(1, 0, 0);
        // ============ Equivalence Partitions Tests ==============

        //TC01: Checks if the function works
        assertEquals(new Vector(2, 0, 0),
                v100.scale(2),
                "TC01: Vector Scale: Regular vector scale didn't work");

        // =============== Boundary Values Tests ==================

        //TC10: Checks multiplication with 0
        assertThrows(IllegalArgumentException.class, () -> v100.scale(0),
                "TC10: Vector Scale: Multiplication with 0 didn't throw an exception");
    }

    /**
     * Test method for {@link Vector#dotProduct(Vector)}
     */
    @Test
    void testDotProduct() {
        Vector v200 = new Vector(2, 0, 0);
        Vector v001 = new Vector(0, 0, 1);
        // ============ Equivalence Partitions Tests ==============

        //TC01: Less then 90 degree between vectors
        assertEquals(2,
                v200.dotProduct(new Vector(1, 0, 1)),
                "TC01: Dot product: Less then 90 degree Didnt work");

        //TC02: More the 90 degree between vectors
        assertEquals(-2,
                v200.dotProduct(new Vector(-1, 0, 1)),
                "TC02: Dot product: More then 90 degree Didnt work");

        // =============== Boundary Values Tests ==================

        //TC10: Vertical vectors
        assertEquals(0,
                v200.dotProduct(v001),
                "TC10: Dot product: Vertical vectors result is not 0");

        //TC11: Unit vector
        assertEquals(3,
                new Vector(1, 2, 3).dotProduct(v001),
                "TC11: Dot product: Unit vector didn't work");
    }

    /**
     * Test method for {@link Vector#crossProduct(Vector)}
     */
    @Test
    void testCrossProduct() {
        Vector vr = new Vector(-13, 2, 3);
        Vector v123 = new Vector(1, 2, 3);
        Vector v03M2 = new Vector(0, 3, -2);
        Vector v100 = new Vector(1, 0, 0);
        // ============ Equivalence Partitions Tests ==============

        //TC01: simple test
        assertEquals(vr,
                v123.crossProduct(v03M2),
                "TC01: Cross product: Less then 90 degree Didnt work");
        assertEquals(v123.length() * v03M2.length(), vr.length(),
                0.000001, "crossProduct: wrong result length");
        assertEquals(0, vr.dotProduct(v123),
                "TC01: Cross product: result is not orthogonal to 1st operand");
        assertEquals(0, vr.dotProduct(v03M2),
                "TC01: Cross product: result is not orthogonal to 2nd operand");

        // =============== Boundary Values Tests ==================

        //TC10: Parallel vectors, different length
        assertThrows(IllegalArgumentException.class, () ->
                        v100.crossProduct(new Vector(2, 0, 0)),
                "TC10: Cross product:  Parallel vectors, different length didn't throw an exception");

        //TC11: Parallel vectors, same length
        assertThrows(IllegalArgumentException.class, () ->
                        v100.crossProduct(v100),
                "TC11: Cross product: Parallel vectors, same length didn't throw an exception");

        //TC12: different directions , same length
        assertThrows(IllegalArgumentException.class, () ->
                        v100.crossProduct(new Vector(-1, 0, 0)),
                "TC12: Cross product:  Different directions, same length didn't throw an exception");

        //TC13: different directions , different length
        assertThrows(IllegalArgumentException.class, () ->
                        v100.crossProduct(new Vector(-2, 0, 0)),
                "TC13: Cross product: Different directions, different length didn't throw an exception");
    }

    /**
     * Test method for {@link Vector#lengthSquared()}
     */
    @Test
    void testLengthSquared() {
        // ============ Equivalence Partitions Tests ==============

        //TC01: regular case
        assertEquals(14, new Vector(1, 2, 3).lengthSquared()
                , "TC01: LengthSquared: Regular case didnt work");

        // =============== Boundary Values Tests ==================

        //TC10: Unit vector
        assertEquals(1, new Vector(0, 1, 0).lengthSquared()
                , "TC10: LengthSquared: Unit vector didnt work");
    }

    /**
     * Test method for {@link Vector#length()}
     */
    @Test
    void testLength() {
        // ============ Equivalence Partitions Tests ==============

        //TC01: regular case
        assertEquals(5, new Vector(3, 4, 0).length()
                , "TC01: Length: Regular case didnt work");

        // =============== Boundary Values Tests =================

        //TC10: Unit vector
        assertEquals(1, new Vector(0, 1, 0).length()
                , "TC10: Length: Unit vector didnt work");
    }

    /**
     * Test method for {@link Vector#normalize()}
     */
    @Test
    void testNormalize() {
        Vector v = new Vector(0, 3, 4);
        Vector n = v.normalize();
        // ============ Equivalence Partitions Tests ==============

        //TC01: regular case
        assertEquals(1, n.lengthSquared(), 0.00001, "TC01: Normalize: Regular case didnt work");
        assertThrows(IllegalArgumentException.class, () -> v.crossProduct(n),
                "TC01: Normalize: Normalize vector not in the same direction");
        assertEquals(new Vector(0.0, 0.6, 0.8), n, "TC01: Normalize: Wrong normalize vector");

        // =============== Boundary Values Tests ==================

        //TC10: Normal of normalized vector
        assertEquals(n.normalize(), n, "TC10: Normalize: Normal of a normalized vector didn't work");
    }

    /**
     * Test method for {@link Vector#equals(Object)}
     */
    @Test
    void testEquals() {
        // ============ Equivalence Partitions Tests ==============

        //TC01: simple test
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(1, 2, 3);
        assertEquals(v1, v2, "TC01: Vector equals didn't work");
        Vector v3 = new Vector(1, 2, 4);
        assertNotEquals(v1, v3, "TC01: Vector equals didn't work");
    }
}