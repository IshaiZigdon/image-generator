package renderer;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

import static java.lang.Double.max;
import static primitives.Util.alignZero;

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
    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        Color intensityAndEmission = scene.ambientLight.getIntensity().add(geoPoint.geometry.getEmission());
        Color sumColor = Color.BLACK;
        Vector v = ray.getDirection();
        for (LightSource light : scene.lights) {
            sumColor = sumColor.add(phongLight(geoPoint, light, v));
        }
        return intensityAndEmission.add(sumColor);
    }

    /**
     * calculating the color from the given lighton the given GeoPoint
     *
     * @param geoPoint the shape and the point
     * @param light    the given light
     * @param v        the direction of the camera
     * @return the result color
     */
    private Color phongLight(GeoPoint geoPoint, LightSource light, Vector v) {
        Material material = geoPoint.geometry.getMaterial();
        Double3 kD = material.kD;
        Double3 kS = material.kS;
        int nShininess = material.nShininess;

        Vector n = geoPoint.geometry.getNormal(geoPoint.point);
        Vector l = light.getL(geoPoint.point);
        double ln = alignZero(l.dotProduct(n));
        double vn = alignZero(v.dotProduct(n));
        if (ln * vn <= 0) return Color.BLACK;

        Color iL = light.getIntensity(geoPoint.point);
        Color diffuse = iL.scale(kD).scale(Math.abs(ln));
        Vector r = l.subtract(n.scale(2 * ln));
        double maxVal = Math.pow(max(0, -v.dotProduct(r)), nShininess);
        Color specular = iL.scale(kS).scale(maxVal);
        return diffuse.add(specular);
    }


    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> geoPoints = scene.geometries.findGeoIntersections(ray);
        return geoPoints == null ? scene.background : calcColor(ray.findClosestGeoPoint(geoPoints), ray);
    }
}
