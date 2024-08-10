package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * class for intersecting points of all the shapes
 * with given ray
 *
 * @author Ishai zigdon
 * @author Zaki zafrani
 */
public abstract class Intersectable {

    /**
     * the max x,y,z values of the shape
     */
    public Point max = new Point(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);

    /**
     * the min x,y,z values of the shape
     */
    public Point min = new Point(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);

    /**
     * setting the max and min values
     */
    public abstract void setMinMax();


    /**
     * calculates where are the intersecting GeoPoints of the
     * shapes with the given ray
     *
     * @param ray         the ray
     * @param maxDistance the max distance of the intersection points
     * @return list of intersecting GeoPoints that are within the given distance
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance);

    /**
     * calculates where are the intersecting GeoPoints of the
     * shapes with the given ray
     *
     * @param ray         the ray
     * @param maxDistance the max distance of the intersection points
     * @return list of intersecting GeoPoints that are within the given distance
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        return findGeoIntersectionsHelper(ray, maxDistance);
    }

    /**
     * calculates where are the intersecting GeoPoints of the
     * shapes with the given ray
     *
     * @param ray the ray
     * @return list of intersecting GeoPoints
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
    }

    /**
     * calculates where are the intersecting points of the
     * shape with the given ray
     *
     * @param ray the ray
     * @return list of intersecting points
     */
    public final List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }

    /**
     * class for geoPoint, withe shape and point on the shape
     */
    public static class GeoPoint {
        /**
         * the shape
         */
        public Geometry geometry;
        /**
         * the point on the shape
         */
        public Point point;

        /**
         * ctor with given shape and point
         *
         * @param geometry the given geometry shape
         * @param point    the given point
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            return obj instanceof GeoPoint gp && geometry == gp.geometry && point.equals(gp.point);
        }

        @Override
        public String toString() {
            return geometry + " " + point;
        }
    }
}
