package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import geometries.Intersectable.GeoPoint;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing Polygons
 *
 * @author Dan
 */
public class PolygonTests {
    /**
     * Delta value for accuracy when comparing the numbers of type 'double' in
     * assertEquals
     */
    private final double DELTA = 0.000001;

    /**
     * Default constructor for PolygonTests.
     */
    public PolygonTests() {/*just fot the javadoc*/}

    /**
     * Test method for {@link Polygon#Polygon(Point...)}.
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct concave quadrangular with vertices in correct order
        assertDoesNotThrow(() -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1)), "Failed constructing a correct polygon");

        // TC02: Wrong vertices order
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(0, 1, 0), new Point(1, 0, 0), new Point(-1, 1, 1)), //
                "Constructed a polygon with wrong order of vertices");

        // TC03: Not in the same plane
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 2, 2)), //
                "Constructed a polygon with vertices that are not in the same plane");

        // TC04: Concave quadrangular
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0.5, 0.25, 0.5)), //
                "Constructed a concave polygon");

        // =============== Boundary Values Tests ==================

        // TC10: Vertex on a side of a quadrangular
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0.5, 0.5)), "Constructed a polygon with vertix on a side");

        // TC11: Last point = first point
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1)), "Constructed a polygon with vertice on a side");

        // TC12: Co-located points
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 1, 0)), "Constructed a polygon with vertice on a side");

    }

    /**
     * Test method for {@link Polygon#getNormal(Point)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here - using a quad
        Point[] pts = {new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1)};
        Polygon pol = new Polygon(pts);
        // ensure there are no exceptions
        assertDoesNotThrow(() -> pol.getNormal(new Point(0, 0, 1)), "");
        // generate the test result
        Vector result = pol.getNormal(new Point(0, 0, 1));
        // ensure |result| = 1
        assertEquals(1, result.length(), DELTA, "Polygon's normal is not a unit vector");
        // ensure the result is orthogonal to all the edges
        for (int i = 0; i < 3; ++i)
            assertEquals(0d, result.dotProduct(pts[i].subtract(pts[i == 0 ? 3 : i - 1])), DELTA, "Polygon's normal is not orthogonal to one of the edges");
    }

    /**
     * Test method for {@link Polygon#findIntersections(Ray)}.
     */
    @Test
    public void testFindGeoIntersectionsHelper() {
        Point[] pts = {new Point(1, 0, 0), new Point(2, 0, 0), new Point(2, 1, 0), new Point(1, 1, 0)};
        Polygon polygon = new Polygon(pts);
        final Vector v001 = new Vector(0, 0, 1);

        /// ============ Equivalence Partitions Tests ==============

        //TC01: Ray intersects the polygon
        final var result01 = polygon.findGeoIntersectionsHelper(new Ray(new Point(1.5, 0.5, -1), v001));
        var exp = List.of(new GeoPoint(polygon, new Point(1.5, 0.5, 0)));
        assertEquals(exp, result01, "TC01: Polygon: findGeoIntersectionsHelper TC01 didnt work");
        // **** Group: Ray doesn't intersect the polygon (but does the plane he lays in)
        //TC02: in front of edge
        assertNull(polygon.findGeoIntersectionsHelper(new Ray(new Point(1.5, 1.5, -1), v001)), "TC02: Ray's line is outside of the polygon");
        //TC03: in front of vertex
        assertNull(polygon.findGeoIntersectionsHelper(new Ray(new Point(0.5, 1.5, -1), v001)), "TC03: Ray's line is outside of the polygon");

        // =============== Boundary Values Tests ==================

        //TC10: Ray intersects the polygon on the edge
        assertNull(polygon.findGeoIntersectionsHelper(new Ray(new Point(1.5, 1, -1), v001)), "TC10: Polygon: findGeoIntersectionsHelper TC10 didnt work");
        //TC11: Ray intersects the polygon on the vertex
        assertNull(polygon.findGeoIntersectionsHelper(new Ray(new Point(2, 0, -1), v001)), "PTC11: Polygon: findGeoIntersectionsHelper TC11 didnt work");
        //TC12: Ray on the continued edge
        assertNull(polygon.findGeoIntersectionsHelper(new Ray(new Point(1, 1.5, -1), v001)), "TC12: Ray's line is outside of the polygon");
    }
}
