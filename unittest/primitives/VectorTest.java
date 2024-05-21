package primitives;

import geometries.Polygon;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * unit test for primitives.Vector
 *
 * @author Ishai zigdon
 * @author Zaki zafrani
 */

class VectorTest {

    /**
     * Test method for {@link Vector#Vector(double, double, double)}.
     * and Test method for {@link Vector#Vector(Double3)}.
     */
    @Test
    public void testConstructors() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: simple test
        assertDoesNotThrow(() -> new Vector(1, 2, 3),"Vector Constructor: failed 3 doubles constructor");
        assertDoesNotThrow(() -> new Vector(new Double3(1,2,3)),"Vector Constructor: failed Double3 constructor");

        // =============== Boundary Values Tests ==================
        //TC02: if Throws an error when implemented with 0 values
        assertThrows(IllegalArgumentException.class,
                ()->new Vector(Double3.ZERO),"Vector Constructor: zero vector does not throw an exception");
    }

    /**
     * Test method for {@link Vector#add(Vector)}
     */
    @Test
    void testAdd() {
        Vector v100 = new Vector(1, 0,0);
        // ============ Equivalence Partitions Tests ==============
        //TC1: Less then 90 degree between vectors
        assertEquals(new Vector(2,0,1),
                v100.add(new Vector(1, 0, 1)),
                "Vector add: Less then 90 degree Didnt work");
        //TC2: More the 90 degree between vectors
        assertEquals(new Vector(0,0,1),
                v100.add(new Vector(-1, 0, 1)),
                "Vector add: More then 90 degree Didnt work");
        // =============== Boundary Values Tests ==================
        //TC3: Opposite side vectors
        assertThrows(IllegalArgumentException.class,
                ()-> v100.add(new Vector(-1, 0, 0)),
                "Vector add: Opposite Vector didn't throw an exception");
 ;   }

    /**
     * Test method for {@link Vector#subtract(Point)}
     */
    @Test
    void testSubtract() {
        Vector v100 = new Vector(1, 0,0);
        // ============ Equivalence Partitions Tests ==============
        //TC1: simple test
        assertEquals(v100,
                new Vector(2, 0, 1).subtract(new Point(1, 0, 1)),
                "Vector subtract: simple sub didn't work");
        // =============== Boundary Values Tests ==================
        //TC1: vector 0 test
        assertThrows(IllegalArgumentException.class,()->
                        v100.subtract(v100),
                "Vector subtract: zero vector didn't threw an exception");
    }

    /**
     * Test method for {@link Vector#scale(double)}
     */
    @Test
    void testScale() {
        Vector v100 = new Vector(1, 0,0);
        // ============ Equivalence Partitions Tests ==============
        //TC1: Checks if the function works
        assertEquals(new Vector(2,0,0),
                v100.scale(2),
                "Vector Scale: Regular vector scale didn't work");

        // =============== Boundary Values Tests ==================
        //TC2: Checks multiplication with 0
        assertThrows(IllegalArgumentException.class,()->v100.scale(0),
                "Vector Scale: Multiplication with 0 didn't throw an exception");
    }

    /**
     * Test method for {@link Vector#dotProduct(Vector)}
     */
    @Test
    void testDotProduct() {
        Vector v200 = new Vector(2, 0,0);
        Vector v001 = new Vector(0, 0,1);
        // ============ Equivalence Partitions Tests ==============
        //TC1: Less then 90 degree between vectors
        assertEquals(2,
                v200.dotProduct(new Vector(1, 0, 1)),
                "Dot product: Less then 90 degree Didnt work");
        //TC2: More the 90 degree between vectors
        assertEquals(-2,
                v200.dotProduct(new Vector(-1, 0, 1)),
                "Dot product: More then 90 degree Didnt work");
        // =============== Boundary Values Tests ==================
        //TC3: Vertical vectors
        assertEquals(0,
                v200.dotProduct(v001),
                "Dot product: Vertical vectors result is not 0");
        //TC4: Unit vector
        assertEquals(3,
                new Vector(1, 2, 3).dotProduct(v001),
                "Dot product: Unit vector didn't work");
    }

    /**
     * Test method for {@link Vector#crossProduct(Vector)}
     */
    @Test
    void testCrossProduct() {
        Vector v0M20 = new Vector(0, -2,0);
        Vector v101 = new Vector(1, 0,1);
        Vector v200 = new Vector(2, 0,0);
        Vector v100 = new Vector(1, 0,0);
        // ============ Equivalence Partitions Tests ==============
        //TC1: Less then 90 degree between vectors
        assertEquals(v0M20,
                v200.crossProduct(v101),
                "Cross product: Less then 90 degree Didnt work");
        assertEquals(0,v0M20.dotProduct(v200),
                "Cross product: result is not orthogonal to 1st operand");
        assertEquals(0,v0M20.dotProduct(v101),
                "Cross product: result is not orthogonal to 2nd operand");
        //TC2: More the 90 degree between vectors
        assertEquals(v0M20,
                v200.crossProduct(new Vector(-1, 0, 1)),
                "Cross product: More then 90 degree Didnt work");

        // =============== Boundary Values Tests ==================
        //todo fix the same vectors
        //TC3: Parallel vectors, different length
        assertThrows(IllegalArgumentException.class,()->
                v100.crossProduct(v200),
                "Cross product:  Parallel vectors, different length didn't throw an exception");
        //TC4: Parallel vectors, same length
        assertThrows(IllegalArgumentException.class,()->
                v100.crossProduct(v100),
                "Cross product: Parallel vectors, same length didn't throw an exception");
        //TC5: different directions , same length
        assertThrows(IllegalArgumentException.class,()->
                v100.crossProduct(new Vector(-1, 0, 0)),
                "Cross product:  Different directions, same length didn't throw an exception");
        //TC6: different directions , different length
        assertThrows(IllegalArgumentException.class,()->
                v100.crossProduct(new Vector(-2, 0, 0)),
                "Cross product: Different directions, different length didn't throw an exception");
    }

    /**
     * Test method for {@link Vector#lengthSquared()}
     */
    @Test
    void testLengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        //TC1: regular case
        assertEquals(14,new Vector(1,2,3).lengthSquared()
                ,"LengthSquared: Regular case didnt work");

        // =============== Boundary Values Tests ==================
        //TC2: Unit vector
        assertEquals(1,new Vector(0,1,0).lengthSquared()
                ,"LengthSquared: Unit vector didnt work");

    }

    /**
     * Test method for {@link Vector#length()}
     */
    @Test
    void testLength() {
        // ============ Equivalence Partitions Tests ==============
        //TC1: regular case
        assertEquals(5,new Vector(3,4,0).length()
                ,"Length: Regular case didnt work");
        // =============== Boundary Values Tests =================
        //TC2: Unit vector
        assertEquals(1,new Vector(0,1,0).length()
                ,"Length: Unit vector didnt work");
    }

    /**
     * Test method for {@link Vector#normalize()}
     */
    @Test
    void testNormalize() {
        Vector v = new Vector(0,3,4);
        Vector n = v.normalize();
        // ============ Equivalence Partitions Tests ==============
        //TC1: regular case
        assertEquals(1,n.lengthSquared(),0.00001,"Normalize: Regular case didnt work");
        assertThrows(IllegalArgumentException.class,()->v.crossProduct(n),
                "Normalize: Normalize vector not in the same direction");
        assertEquals(new Vector(0.0,0.6,0.8),n,"Normalize: Wrong normalize vector");
        // =============== Boundary Values Tests ==================
        //TC2: Normal of normalized vector
        assertEquals(n.normalize(),n,"Normalize: Normal of a normalized vector didn't work");
    }

    /**
     * Test method for {@link Vector#toString()}
     */
    @Test
    void testToString() {
        // ============ Equivalence Partitions Tests ==============
        //TC1: simple test
        assertEquals("Vector: (1.0,2.0,3.0)",new Vector(1,2,3).toString(),
                "Vector- toString: Regular case didnt work");
    }

    /**
     * Test method for {@link Vector#equals(Object)}
     */
    @Test
    void testEquals() {
        // ============ Equivalence Partitions Tests ==============
        //TC1: simple test
        Vector v1 = new Vector(1,2,3);
        Vector v2 = new Vector(1,2,3);
        assertTrue(v1.equals(v2), "Vector equals didn't work");
        Vector v3 = new Vector(1,2,4);
        assertFalse(v1.equals(v3), "Vector equals didn't work");
    }
}