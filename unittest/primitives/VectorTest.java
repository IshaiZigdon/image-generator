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
     * @todo
     */
    @Test
    public void testConstructors() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct concave Vector with 0s in itself
        assertDoesNotThrow(() -> new Vector(1.0, 2.0, 3.0),"Vector Constructor: failed 3 doubles constructor");
        assertDoesNotThrow(() -> new Vector(new Double3(1.0,2.0,3.0)),"Vector Constructor: failed Double3 constructor");


        // =============== Boundary Values Tests ==================
        //TC02: Throws an error when implemented with 0 values
        assertThrows(IllegalArgumentException.class,//
                ()->new Vector(Double3.ZERO),"Vector Constructor: zero vector does not throw an exception");

    }

    /**
     * Test method for {@link Vector#add(Vector)}
     */
    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        //TC1: Less then 90 degree between vectors
        assertEquals(new Vector(2.0,0.0,1.0),
                new Vector(1.0, 0.0, 0.0).add(new Vector(1.0, 0.0, 1.0)),
                "add: Less then 90 degree Didnt work");
        //TC2: More the 90 degree between vectors
        assertEquals(new Vector(0.0,0.0,1.0),
                new Vector(1.0, 0.0, 0.0).add(new Vector(-1.0, 0.0, 1.0)),
                "add: More then 90 degree Didnt work");
        // =============== Boundary Values Tests ==================
        //TC3: Opposite side vectors
        assertThrows(IllegalArgumentException.class,
                ()-> new Vector(1.0, 0.0, 0.0).add(new Vector(-1.0, 0.0, 0.0)),
                "add: Opposite Vector don't add up to zero");
 ;   }

    /**
     * Test method for {@link Vector#scale(double)}
     */
    @Test
    void testScale() {
        // ============ Equivalence Partitions Tests ==============
        //TC1: Checks if the function works
        assertEquals(new Vector(2.0,0.0,0.0),
                new Vector(1.0, 0.0, 0.0).scale(2.0),
                "Scale: Regular vector scale didn't work");

        // =============== Boundary Values Tests ==================
        //TC2: Checks multiplication with 0
        assertThrows(IllegalArgumentException.class,()->new Vector(1.0, 0.0, 0.0).scale(0.0),
                "Scale: Multiplication with 0 didn't work");
    }

    /**
     * Test method for {@link Vector#dotProduct(Vector)}
     */
    @Test
    void testDotProduct() {
        // ============ Equivalence Partitions Tests ==============
        //TC1: Less then 90 degree between vectors
        assertEquals(2.0,
                new Vector(2.0, 0.0, 0.0).dotProduct(new Vector(1.0, 0.0, 1.0)),
                "dot product: Less then 90 degree Didnt work");
        //TC2: More the 90 degree between vectors
        assertEquals(-2.0,
                new Vector(2.0, 0.0, 0.0).dotProduct(new Vector(-1.0, 0.0, 1.0)),
                "dot product: More then 90 degree Didnt work");
        // =============== Boundary Values Tests ==================
        //TC3: Vertical vectors
        assertEquals(0.0,
                new Vector(2.0, 0.0, 0.0).dotProduct(new Vector(0.0, 1.0, 0.0)),
                "dot product: Vertical vectors result is not 0");
        //TC4: Unit vector
        assertEquals(3.0,
                new Vector(1.0, 2.0, 3.0).dotProduct(new Vector(0.0, 0.0, 1.0)),
                "dot product: Unit vector didn't work");

    }

    /**
     * Test method for {@link Vector#crossProduct(Vector)}
     */
    @Test
    void testCrossProduct() {
        // ============ Equivalence Partitions Tests ==============
        //TC1: Less then 90 degree between vectors
        assertEquals(new Vector(0.0,-2.0,0.0),
                new Vector(2.0, 0.0, 0.0).crossProduct(new Vector(1.0, 0.0, 1.0)),
                "cross product: Less then 90 degree Didnt work");
        //TC2: More the 90 degree between vectors
        assertEquals(new Vector(0.0,-2.0,0.0),
                new Vector(2.0, 0.0, 0.0).crossProduct(new Vector(-1.0, 0.0, 1.0)),
                "cross product: More then 90 degree Didnt work");

        // =============== Boundary Values Tests ==================
        //TC3: Parallel vectors, different length
        assertThrows(IllegalArgumentException.class,()->
                new Vector(1.0, 0.0, 0.0).crossProduct(new Vector(2.0, 0.0, 0.0)),
                "cross product:  Parallel vectors, different length didn't work");
        //TC4: Parallel vectors, same length
        assertThrows(IllegalArgumentException.class,()->
                new Vector(1.0, 0.0, 0.0).crossProduct(new Vector(1.0, 0.0, 0.0)),
                "cross product: Parallel vectors, same length didn't work");
        //TC5: different directions , same length
        assertThrows(IllegalArgumentException.class,()->
                new Vector(1.0, 0.0, 0.0).crossProduct(new Vector(-1.0, 0.0, 0.0)),
                "cross product:  Different directions, same length didn't work");
        //TC6: different directions , different length
        assertThrows(IllegalArgumentException.class,()->
                new Vector(1.0, 0.0, 0.0).crossProduct(new Vector(-2.0, 0.0, 0.0)),
                "cross product: Different directions, different length didn't work");
    }

    /**
     * Test method for {@link Vector#lengthSquared()}
     */
    @Test
    void testLengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        //TC1: regular case
        assertEquals(14.0,new Vector(1.0,2.0,3.0).lengthSquared()
                ,"LengthSquared: Regular case didnt work");

        // =============== Boundary Values Tests ==================
        //TC2: Unit vector
        assertEquals(1.0,new Vector(0.0,1.0,0.0).lengthSquared()
                ,"LengthSquared: Unit vector didnt work");

    }

    /**
     * Test method for {@link Vector#length()}
     */
    @Test
    void testLength() {
        // ============ Equivalence Partitions Tests ==============
        //TC1: regular case
        assertEquals(5.0,new Vector(3.0,4.0,0.0).length()
                ,"Length: Regular case didnt work");
        // =============== Boundary Values Tests =================
        //TC2: Unit vector
        assertEquals(1.0,new Vector(0.0,1.0,0.0).length()
                ,"Length: Unit vector didnt work");
    }

    /**
     * Test method for {@link Vector#normalize()}
     */
    @Test
    void testNormalize() {
        Vector v = new Vector(0.0,3.0,4.0);
        Vector n = v.normalize();
        // ============ Equivalence Partitions Tests ==============
        //TC1: regular case
        assertEquals(1.0,n.lengthSquared(),0.0001,"Normalize: Regular case didnt work");
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
        assertEquals("Vector: (1.0,2.0,3.0)",new Vector(1.0,2.0,3.0).toString(),
                "Vector- toString: Regular case didnt work");

    }

    /**
     * Test method for {@link Vector#equals(Object)}
     */
    @Test
    void testEquals() {
        // ============ Equivalence Partitions Tests ==============
        //TC1: simple test
        Vector v1 = new Vector(1.0,2.0,3.0);
        Vector v2 = new Vector(1.0,2.0,3.0);
        assertTrue(v1.equals(v2), "Vector equals didn't work");
        Vector v3 = new Vector(1.0,2.0,4.0);
        assertFalse(v1.equals(v3), "Vector equals didn't work");

    }
}