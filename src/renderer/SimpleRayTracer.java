package renderer;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

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
     *
     */
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    /**
     *
     */
    private static final Double3 MIN_CALC_COLOR_K = new Double3(0.001);

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
     * @param geoPoint  the given point
     * @param ray the ray from the camera
     * @return the color of geoPoint
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        return calcColor(findClosestIntersection(ray), ray, MAX_CALC_COLOR_LEVEL,  MIN_CALC_COLOR_K)
                .add(scene.ambientLight.getIntensity());
    }

    /**
     * calculates the color of the given point in the image
     * with all affecting lights
     *
     * @param geoPoint the given point
     * @param ray      the ray from the camera
     * @return the color of geoPoint
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray, int level, Double3 k) {
        Color color = scene.ambientLight.getIntensity()
                .add(calcLocalEffects(geoPoint, ray,k));
        return 1 == level ? color
                : color.add(calcGlobalEffects(geoPoint, ray, level, k));
    }

    /**
     * calculating the color from the given light on the given GeoPoint
     *
     * @param gp  the shape and the point
     * @param ray the ray from the camera
     * @return the result color
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k) {
        Vector n = gp.geometry.getNormal(gp.point);
        Vector v = ray.getDirection();
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return Color.BLACK;

        Material material = gp.geometry.getMaterial();
        Color color = gp.geometry.getEmission();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double ln = alignZero(l.dotProduct(n));
            if (ln * nv > 0) {
                Double3 ktr = transparency(gp,lightSource,l,n,nv);
                if(!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
                    Color iL = lightSource.getIntensity(gp.point).scale(ktr);
                    color = color.add(
                            iL.scale(calcDiffusive(material, ln)
                                    .add(calcSpecular(material, n, l, ln, v))));
                }
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

    private Color calcGlobalEffects(GeoPoint geoPoint, Ray ray, int level, Double3 k) {
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);
        Material material = geoPoint.geometry.getMaterial();
        return calcGlobalEffect(constructRefractedRay(geoPoint, ray), material.kR,level, k)
                .add(calcGlobalEffect(constructReflectedRay(geoPoint, ray), material.kT,level, k));
    }

    private Color calcGlobalEffect(Ray ray, Double3 kx, int level, Double3 k) {
        Double3 kkx = k.product(kx);
        if(kkx.lowerThan(MIN_CALC_COLOR_K)) return Color.BLACK;
        GeoPoint gp = findClosestIntersection(ray);
        return gp == null ? scene.background : calcColor(gp, ray,level-1,kkx).scale(kx);
    }

    private Ray constructRefractedRay(GeoPoint geoPoint, Ray ray) {
        Vector v = ray.getDirection();
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);
        Vector deltaVec = n.scale(n.dotProduct(v) > 0 ? DELTA : -DELTA);
        Point offsetPoint = geoPoint.point.add(deltaVec);
        return new Ray(offsetPoint, v);
    }


    /**
     *
     * @param geoPoint
     * @param ray
     * @return
     */
    private Ray constructReflectedRay(GeoPoint geoPoint,Ray ray){
        Vector v = ray.getDirection();
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);
        return new Ray(geoPoint.point,v.subtract(n.scale(2 * v.dotProduct(n))));
    }

    private Double3 transparency(GeoPoint gp, LightSource light,Vector l,Vector n,double nv){
        Vector lightDirection = l.scale(-1);
        double ln = l.dotProduct(n);
        Vector deltaVec = n.scale(ln < 0 ? DELTA : -DELTA);
        Point point = gp.point.add(deltaVec);
        Ray ray = new Ray(point, lightDirection);
        var intersections = scene.geometries.findGeoIntersections(ray);
        if(intersections == null) return Double3.ZERO;
        Double3 ktr = Double3.ONE;
        double distance = light.getDistance(point);
        for (GeoPoint intersection : intersections) {
            if (point.distance(intersection.point) < distance)
                ktr.product(gp.geometry.getMaterial().kT);
        }
        return ktr;
    }

    /**
     * @param ray
     * @return
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        var gp = scene.geometries.findGeoIntersections(ray);
        return ray.findClosestGeoPoint(gp);
    }

    @Override
    public Color traceRay(Ray ray) {
        GeoPoint closestPoint = findClosestIntersection(ray);
        return closestPoint == null ? scene.background
                : calcColor(closestPoint, ray);
    }
}
