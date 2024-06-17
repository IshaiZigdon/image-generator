package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;
import geometries.Intersectable.GeoPoint;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * unit test for Geometries.Tube
 *
 * @author Ishai zigdon
 * @author Zaki zafrani
 */
public class TubeTest {
    /**
     * vector 100 for testing
     */
    private static final Vector v100 = new Vector(1, 0, 0);

    /**
     * Default constructor for TubeTest.
     */
    public TubeTest() {/*just fot the javadoc*/}

    /**
     * Test method for {@link Tube#getNormal(Point)}
     */
    @Test
    void getNormal() {
        Point pM110 = new Point(-1, 1, 0);
        Point p100 = new Point(1, 0, 0);
        Tube t = new Tube(new Ray(p100, v100), 1);
        // ============ Equivalence Partitions Tests ==============

        //TC1: simple test
        // ensure there are no exceptions
        assertDoesNotThrow(() -> t.getNormal(pM110), "");
        // generate the test result
        Vector normal = t.getNormal(pM110);
        // ensure |result| = 1
        assertEquals(1, normal.length(), 0.000001, "Tube's normal is not a unit vector");
        // ensure the result is orthogonal to the tube
        assertEquals(0d, normal.dotProduct(v100),
                "Tube: wrong normal values");

        // =============== Boundary Values Tests ==================

        Point p110 = new Point(1, 1, 0);
        /*
        TC2: vector between the points is vertical to the direction vector
         ensure there are no exceptions
        */
        assertDoesNotThrow(() -> t.getNormal(p110), "");
        // generate the test result
        Vector normal2 = t.getNormal(p110);
        // ensure |result| = 1
        assertEquals(1, normal2.length(), 0.000001, "Tube's normal is not a unit vector");
        // ensure the result is orthogonal to the tube
        assertThrows(IllegalArgumentException.class, () ->
                normal2.crossProduct(p110.subtract(p100)), "Tube: wrong normal values");
    }

    /**
     * Test method for {@link Tube#findIntersections(Ray)}.
     */
    @Test
    public void testFindGeoIntersectionsHelper() {
        Tube tube = new Tube(new Ray(new Point(-1, 0, 0), v100), 1);

        // ============ Equivalence Partitions Tests ==============

        Vector v101 = new Vector(1, 0, 1);
        //TC01: no intersection points
        Ray r01 = new Ray(new Point(-5, 5, -5), v101);
        assertNull(tube.findGeoIntersectionsHelper(r01), "TC01: didnt return null");

        // **** Group: Ray's line crosses the Tube (but not the center)
        Vector v1M11 = new Vector(1, -1, 1);
        Point p010 = new Point(0, 1, 0);
        Point p101 = new Point(1, 0, 1);
        var exp = List.of(new GeoPoint(tube,p010), new GeoPoint(tube,p101));
        //TC02: start before the tube
        Ray r02 = new Ray(new Point(-1, 2, -1), v1M11);
        List<GeoPoint> result02 = tube.findGeoIntersectionsHelper(r02);
        assertEquals(2, result02.size(), "TC02: wrong number of intersections");
        assertEquals(exp, result02, "TC02: wrong points");
        //TC03: start on the tube and goes inside
        Ray r03 = new Ray(p010, v1M11);
        List<GeoPoint> result03 = tube.findGeoIntersectionsHelper(r03);
        exp = List.of(new GeoPoint(tube,p101));
        assertEquals(1, result03.size(), "TC03: wrong number of intersections");
        assertEquals(exp, result03, "TC03: wrong points");
        //TC04: start inside the tube
        Ray r04 = new Ray(new Point(0.5, 0.5, 0.5), v1M11);
        List<GeoPoint> result04 = tube.findGeoIntersectionsHelper(r04);
        //exp = List.of(p101);
        assertEquals(1, result04.size(), "TC04: wrong number of intersections");
        assertEquals(exp, result04, "TC04: wrong points");
        //TC05: start on the tube and goes outside
        Ray r05 = new Ray(p101, v1M11);
        assertNull(tube.findGeoIntersectionsHelper(r05), "TC05: didnt return null");
        //TC06: start after the tube
        Ray r06 = new Ray(new Point(2, -1, 2), v1M11);
        assertNull(tube.findGeoIntersectionsHelper(r06), "TC06: didnt return null");

        // =============== Boundary Values Tests ==================

        //TC10: ray line is on the tube
        Ray r10 = new Ray(new Point(1, 0, 1), v100);
        assertNull(tube.findGeoIntersectionsHelper(r10), "doesn't return null");
        //TC11: ray line is on the axis
        Ray r11 = new Ray(new Point(1, 0, 0), v100);
        assertNull(tube.findGeoIntersectionsHelper(r11), "doesn't return null");
        //TC12: ray line is inside the tube in the direction of the axis
        Ray r12 = new Ray(new Point(0.5, 0, 0), v100);
        assertNull(tube.findGeoIntersectionsHelper(r12), "doesn't return null");
        //TC13 todo fix numbers
        // :ray line is outside the tube in the direction of the axis
        assertNull(tube.findGeoIntersectionsHelper(new Ray(new Point(10,10,10),v100)), "doesn't return null");


        // **** Group: Ray's line vertical to axis line(but not going through the center axis)
        Vector v001 = new Vector(0, 0, 1);
        Point p01 = new Point(1, 0.5, -0.8660254037844386);
        Point p02 = new Point(1, 0.5, 0.8660254037844384);
        //TC13: starts before the tube(2 points)
        Ray ray13 = new Ray(new Point(1, 0.5, -2), v001);
        List<GeoPoint> result13 = tube.findGeoIntersectionsHelper(ray13);
        exp = List.of(new GeoPoint(tube,p01), new GeoPoint(tube, p02));
        assertEquals(2, result13.size(), "wrong number of results");
        assertEquals(exp, result13, "wrong points");
        //TC14: starts on the tube and goes inside (1 point)
        Ray ray14 = new Ray(p01, v001);
        List<GeoPoint> result14 = tube.findGeoIntersectionsHelper(ray14);
        exp = List.of(new GeoPoint(tube,p02));
        assertEquals(1, result14.size(), "wrong number of results");
        assertEquals(exp, result14, "wrong points");
        //TC15: starts inside the tube(1 point)
        Ray ray15 = new Ray(new Point(1, 0.5, -0.5), v001);
        List<GeoPoint> result15 = tube.findGeoIntersectionsHelper(ray15);
        //exp = List.of(p2);
        assertEquals(1, result15.size(), "wrong number of results");
        assertEquals(exp, result15, "wrong points");
        //TC16: starts on the tube and goes outside (0 point)
        Ray ray16 = new Ray(p02, v001);
        assertNull(tube.findGeoIntersectionsHelper(ray16), "doesn't return null");
        //TC17: start after the tube(0 points)
        Ray ray17 = new Ray(new Point(1, 0.5, 2), v001);
        assertNull(tube.findGeoIntersectionsHelper(ray17), "doesn't return null");

        // **** Group: Ray's line goes through the axis and vertical to axis line
        Point p1 = new Point(1, 0, -1);
        Point p2 = new Point(1, 0, 1);
        //TC18: starts before the tube(2 points)
        Ray ray18 = new Ray(new Point(1, 0, -2), v001);
        List<GeoPoint> result18 = tube.findGeoIntersectionsHelper(ray18);
        exp = List.of(new GeoPoint(tube,p1), new GeoPoint(tube, p2));
        assertEquals(2, result18.size(), "wrong number of results");
        assertEquals(exp, result18, "wrong points");
        //TC19: starts on the tube(1 point)
        Ray ray19 = new Ray(p1, v001);
        List<GeoPoint> result19 = tube.findGeoIntersectionsHelper(ray19);
        exp = List.of(new GeoPoint(tube,p2));
        assertEquals(1, result19.size(), "wrong number of results");
        assertEquals(exp, result19, "wrong points");
        //TC20: starts inside the tube(1 point)
        Ray ray20 = new Ray(new Point(1, 0, -0.5), v001);
        List<GeoPoint> result20 = tube.findGeoIntersectionsHelper(ray20);
        //exp = List.of(p2);
        assertEquals(1, result20.size(), "wrong number of results");
        assertEquals(exp, result20, "wrong points");
        //TC21: starts on the axis(1 point)
        Ray ray21 = new Ray(new Point(1, 0, 0), v001);
        List<GeoPoint> result21 = tube.findGeoIntersectionsHelper(ray21);
        //exp = List.of(p2);
        assertEquals(1, result21.size(), "wrong number of results");
        assertEquals(exp, result21, "wrong points");
        //TC22: starts on the tube and goes outside (0 point)
        Ray ray22 = new Ray(p2, v001);
        assertNull(tube.findGeoIntersectionsHelper(ray22), "doesn't return null");
        //TC23: start after the tube(0 points)
        Ray ray23 = new Ray(new Point(1, 0, 2), v001);
        assertNull(tube.findGeoIntersectionsHelper(ray23), "doesn't return null");

        // **** Group: Ray's line crosses the Tube and goes through the center
        //TC1:
        //TC2:
        //TC3:
        //TC4:
        //TC5:
        //TC6:

        // **** Group: Ray's line crosses the Tube and goes through the p0
        //TC1:
        //TC2:
        //TC3:
        //TC4:
        //TC5:
        //TC6:

        // **** Group: Ray's line vertical to axis line and goes through the p0
        //TC1:
        //TC2:
        //TC3:
        //TC4:
        //TC5:
        //TC6:

        // **** Group: Ray's line is tangent to the Tube (all tests 0 points)
        //TC24: starts before the tube
        Ray ray24 = new Ray(new Point(1, -1, -1), v001);
        assertNull(tube.findGeoIntersectionsHelper(ray24), "doesn't return null");
        //TC25: starts on the tube
        Ray ray25 = new Ray(new Point(1, -1, 0), v001);
        assertNull(tube.findGeoIntersectionsHelper(ray25), "doesn't return null");
        //TC26: starts after the tube
        Ray ray26 = new Ray(new Point(1, -1, 1), v001);
        assertNull(tube.findGeoIntersectionsHelper(ray26), "doesn't return null");

        // **** special cases
        //TC27: Ray's line is outside, ray is orthogonal to ray start to axis
        Ray ray27 = new Ray(new Point(1, 2, 0), v001);
        assertNull(tube.findGeoIntersectionsHelper(ray27), "doesn't return null");
        //TC28: Ray's line is inside, ray is orthogonal to ray start to axis
        Ray ray28 = new Ray(new Point(1, 0.5, 0), v001);
        List<GeoPoint> result28 = tube.findGeoIntersectionsHelper(ray28);
        exp = List.of(new GeoPoint(tube,p02));
        assertEquals(1, result28.size(), "wrong number of results");
        assertEquals(exp, result28, "wrong points");
    }
}