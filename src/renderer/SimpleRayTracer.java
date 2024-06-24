package renderer;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * class for simple ray tracing
 *
 * @author Ishai zigdon
 * @author Zaki zafrani
 */
public class SimpleRayTracer extends RayTracerBase {
    /**
     * Delta value for accuracy
     */
    private static final double DELTA = 0.1;
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
     * with all affecting lights
     *
     * @param geoPoint the given point
     * @param ray      the ray from the camera
     * @return the color of geoPoint
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        return scene.ambientLight.getIntensity()
                .add(calcLocalEffects(geoPoint, ray));
    }

    /**
     * calculating the color from the given light on the given GeoPoint
     *
     * @param gp  the shape and the point
     * @param ray the ray from the camera
     * @return the result color
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray) {
        Vector n = gp.geometry.getNormal(gp.point);
        Vector v = ray.getDirection();
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return Color.BLACK;

        Material material = gp.geometry.getMaterial();
        Color color = gp.geometry.getEmission();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double ln = alignZero(l.dotProduct(n));
            if ((ln * nv > 0) && unshaded(gp,l,n,lightSource, ln)) {
                Color iL = lightSource.getIntensity(gp.point);
                color = color.add(
                        iL.scale(calcDiffusive(material, ln)
                                .add(calcSpecular(material, n, l, ln, v))));
            }
        }
        return color;
    }

    /**
     * calculates the diffusive light on a given material
     *
     * @param mat the material
     * @param ln  l dot product n
     * @return the calculation of the diffused light
     */
    private Double3 calcDiffusive(Material mat, double ln) {
        return mat.kD.scale(Math.abs(ln));
    }

    /**
     * calculates the specular light on a given material
     *
     * @param mat the material
     * @param n   the normal of the shape
     * @param l   the vector from the light to the point
     * @param ln  l dot product n
     * @param v   direction of the ray from the camera
     * @return the calculation of it all
     */
    private Double3 calcSpecular(Material mat, Vector n, Vector l, double ln, Vector v) {
        Vector r = l.subtract(n.scale(2 * ln));
        double minusVR = -v.dotProduct(r);
        return alignZero(minusVR) <= 0 ? Double3.ZERO : mat.kS.scale(Math.pow(minusVR, mat.nShininess));
    }

    /**
     * check function for if the point should be shaded or not
     * @param gp the shape with the intersection point
     * @param l direction from light to point
     * @param n normal of the shape
     * @return if there is other shapes between camera and gp
     */
    private boolean unshaded(GeoPoint gp, Vector l, Vector n,LightSource lightSource,double ln)
    {
        Vector lightDirection = l.scale(-1); //from point to light source
        Vector deltaVec = n.scale(ln<0 ? DELTA: -DELTA);
        Point point = gp.point.add(deltaVec);
        Ray ray = new Ray(point, lightDirection);
        var intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections==null) return true;
        double distance =lightSource.getDistance(point);
        for (GeoPoint intersection : intersections) {
            if(point.distance(intersection.point)<distance)
                return false;
        }
        return true;
    }


    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> geoPoints = scene.geometries.findGeoIntersections(ray);
        return geoPoints == null ? scene.background :
                calcColor(ray.findClosestGeoPoint(geoPoints), ray);
    }
}
