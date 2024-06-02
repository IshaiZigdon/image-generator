package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

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
    private final Vector v100 = new Vector(1, 0, 0);
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
    public void testFindIntersections() {
        Tube tube = new Tube(new Ray(new Point(-1,0,0),v100),1);

        // ============ Equivalence Partitions Tests ==============
        //TC01:

        // =============== Boundary Values Tests ==================
        Vector v001 = new Vector(0, 0, 1);
        // **** Group: Ray's line vertical to axis line(but not going through the center axis)
        Point p01 = new Point(1, 0.5, -0.8660254038);
        Point p02 = new Point(1, 0.5, 0.8660254038);
        //1:starts before the tube(2 points)
        Ray ray01 = new Ray(new Point(1,0.5,-2),v001);
        List<Point> result01 = tube.findIntersections(ray01);
        List<Point> exp = List.of(p01,p02);
        assertEquals(2,result01.size(), "wrong number of results");
        assertEquals(exp, result01,"wrong points");
        //2:starts on the tube and goes inside (1 point)
        Ray ray02 = new Ray(p01,v001);
        List<Point> result02 = tube.findIntersections(ray02);
        exp = List.of(p02);
        assertEquals(1,result02.size(), "wrong number of results");
        assertEquals(exp, result02,"wrong points");
        //3:starts inside the tube(1 point)
        Ray ray03 = new Ray(new Point(1,0.5,-0.5),v001);
        List<Point> result03 = tube.findIntersections(ray03);
        //exp = List.of(p2);
        assertEquals(1,result03.size(), "wrong number of results");
        assertEquals(exp, result03,"wrong points");
        //4:starts on the tube and goes outside (1 point)
        Ray ray04 = new Ray(p02,v001);
        List<Point> result04 = tube.findIntersections(ray04);
        //exp = List.of(p2);
        assertEquals(2,result04.size(), "wrong number of results");
        assertEquals(exp, result04,"wrong points");
        //5:start after the tube(0 points)
        Ray ray05 = new Ray(new Point(1,0.5,2),v001);
        assertNull(tube.findIntersections(ray05),"doesn't return null");

        // **** Group: Ray's line goes through the axis and vertical to axis line
        Point p1 = new Point(1, 0, -1);
        Point p2 = new Point(1, 0, 1);
        //1:starts before the tube(2 points)
        Ray ray1 = new Ray(new Point(1,0,-2),v001);
        List<Point> result1 = tube.findIntersections(ray1);
        exp = List.of(p1,p2);
        assertEquals(2,result1.size(), "wrong number of results");
        assertEquals(exp, result1,"wrong points");
        //2:starts on the tube(1 point)
        Ray ray2 = new Ray(new Point(1,0,-1),v001);
        List<Point> result2 = tube.findIntersections(ray2);
        exp = List.of(p2);
        assertEquals(1,result2.size(), "wrong number of results");
        assertEquals(exp, result2,"wrong points");
        //3:starts inside the tube(1 point)
        Ray ray3 = new Ray(new Point(1,0,-0.5),v001);
        List<Point> result3 = tube.findIntersections(ray3);
        //exp = List.of(p2);
        assertEquals(1,result3.size(), "wrong number of results");
        assertEquals(exp, result3,"wrong points");
        //4:starts on the axis(1 point)
        Ray ray4 = new Ray(new Point(1,0,0),v001);
        List<Point> result4 = tube.findIntersections(ray4);
        //exp = List.of(p2);
        assertEquals(2,result4.size(), "wrong number of results");
        assertEquals(exp, result4,"wrong points");
        //5:start after the tube(0 points)
        Ray ray5 = new Ray(new Point(1,0,2),v001);
        assertNull(tube.findIntersections(ray5),"doesn't return null");

        // **** Group: Ray's line is tangent to the Tube (all tests 0 points)
        //1: starts before the tube
        Ray ray11 = new Ray(new Point(1,-1,-1),v001);
        assertNull(tube.findIntersections(ray11),"doesn't return null");
        //2: starts on the tube
        Ray ray12 = new Ray(new Point(1,-1,0),v001);
        assertNull(tube.findIntersections(ray12),"doesn't return null");
        //3: starts after the tube
        Ray ray13 = new Ray(new Point(1,-1,1),v001);
        assertNull(tube.findIntersections(ray13),"doesn't return null");
        // **** Group: Ray's line crosses the Tube (but not the center)
        //
    }
}