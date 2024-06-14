package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.List;

/**
 * class for simple ray tracing
 *
 * @author Ishai zigdon
 * @author Zaki zafrani
 */
public class SimpleRayTracer extends RayTracerBase {
    /**
     * ctor with given scene
     *
     * @param s the given scene
     */
    public SimpleRayTracer(Scene s) {
        super(s);
    }

    /**
     * calculates the color of the given point in the image
     *
     * @param geoPoint the given point
     * @return the color of geoPoint
     */
    private Color calcColor(GeoPoint geoPoint) {
        return geoPoint.geometry.getEmission();
    }

    @Override
    public Color traceRay(Ray r) {
        List<GeoPoint> geoPoints = scene.geometries.findGeoIntersections(r);
        if (geoPoints == null) {
            return scene.background;
        }
        GeoPoint closestGeoPoint = r.findClosestGeoPoint(geoPoints);
        return calcColor(closestGeoPoint);
    }
}
