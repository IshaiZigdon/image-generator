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
        Cylinder c = new Cylinder(
                new Ray(new Point(1,0,0),new Vector(1,1,1))
                , 1,1);
        Vector normal = c.getNormal()
        // ============ Equivalence Partitions Tests ==============

        // =============== Boundary Values Tests ==================

    }
}