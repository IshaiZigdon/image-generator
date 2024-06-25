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
     * Maximum recursion level for calculating global effects (reflection and refraction)
     */
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    /**
     * Minimum factor to stop recursion for global effects
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
        return calcColor(geoPoint, ray, MAX_CALC_COLOR_LEVEL,  MIN_CALC_COLOR_K);
                //.add(scene.ambientLight.getIntensity());
    }

    /**
     * Calculates the color of the given point in the image with all affecting lights.
     *
     * @param geoPoint the given point
     * @param ray the ray from the camera
     * @param level the current recursion level
     * @param k the attenuation factor
     * @return the color of geoPoint
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray, int level, Double3 k) {
        Color color = scene.ambientLight.getIntensity()
                .add(calcLocalEffects(geoPoint, ray,k));
        return 1 == level ? color
                : color.add(calcGlobalEffects(geoPoint, ray, level, k));
    }

    /**
     * Calculates the local effects of light on a given GeoPoint.
     *
     * @param gp the shape and the point
     * @param ray the ray from the camera
     * @param k the attenuation factor
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
                Double3 ktr = transparency(gp,lightSource,l,n);
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

    /**
     * Calculates the global effects (reflection and refraction) on the color of the given point.
     *
     * @param geoPoint the given point
     * @param ray the ray from the camera
     * @param level the current recursion level
     * @param k the attenuation factor
     * @return the color with global effects
     */
    private Color calcGlobalEffects(GeoPoint geoPoint, Ray ray, int level, Double3 k) {
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);
        Material material = geoPoint.geometry.getMaterial();
        return calcGlobalEffect(constructRefractedRay(geoPoint, ray), material.kT,level, k)
                .add(calcGlobalEffect(constructReflectedRay(geoPoint, ray), material.kR,level, k));
    }

    /**
     * Calculates a global effect (either reflection or refraction).
     *
     * @param ray the reflection or refraction ray
     * @param kx the material property for reflection or refraction
     * @param level the current recursion level
     * @param k the attenuation factor
     * @return the color due to the global effect
     */
    private Color calcGlobalEffect(Ray ray, Double3 kx, int level, Double3 k) {
        Double3 kkx = k.product(kx);
        if(kkx.lowerThan(MIN_CALC_COLOR_K)) return Color.BLACK;
        GeoPoint gp = findClosestIntersection(ray);
        return gp == null ? scene.background : calcColor(gp, ray,level-1,kkx).scale(kx);
    }

    /**
     * Constructs a refracted ray from the given point and ray.
     *
     * @param geoPoint the point of intersection
     * @param ray the original ray
     * @return the refracted ray
     */
    private Ray constructRefractedRay(GeoPoint geoPoint, Ray ray) {
        Vector incidentDir = ray.getDirection();
        Vector normal = geoPoint.geometry.getNormal(geoPoint.point);

        // Determine the refractive indices for Snell's law
        double n1 = ray.getMaterial().getRefractiveIndex(); // Refractive index of the medium from which the ray is coming
        double n2 = geoPoint.geometry.getMaterial().getRefractiveIndex(); // Refractive index of the medium into which the ray is entering

        // Snell's Law
        double nRatio = n1 / n2;
        double cosThetaI = -incidentDir.dotProduct(normal);
        double sin2ThetaI = 1.0 - cosThetaI * cosThetaI;
        double sin2ThetaT = nRatio * nRatio * sin2ThetaI;

        // Check for total internal reflection
        if (sin2ThetaT > 1.0) {
            // Total internal reflection occurs; return null or handle reflection
            return null;
        }

        // Calculate the direction of the refracted ray using Snell's law
        double cosThetaT = Math.sqrt(1.0 - sin2ThetaT);
        Vector refractedDir = incidentDir.scale(nRatio).add(normal.scale(nRatio * cosThetaI - cosThetaT));

        // Construct the refracted ray with a small offset to avoid self-intersection
        Point offsetPoint = geoPoint.point.add(normal.scale(normal.dotProduct(incidentDir) > 0 ? DELTA : -DELTA));
        return new Ray(offsetPoint, refractedDir);
    }
    }


    /**
     * Constructs a reflected ray from the given point and ray.
     *
     * @param geoPoint the point of intersection
     * @param ray the original ray
     * @return the reflected ray
     */
    private Ray constructReflectedRay(GeoPoint geoPoint,Ray ray){
        Vector v = ray.getDirection();
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);
        return new Ray(geoPoint.point,v.subtract(n.scale(2 * v.dotProduct(n))));
    }

    /**
     * Calculates the transparency factor for a given point.
     *
     * @param gp the intersection point
     * @param light the light source
     * @param l the light direction vector
     * @param n the normal vector at the intersection point
     * @return the transparency factor
     */
    private Double3 transparency(GeoPoint gp, LightSource light,Vector l,Vector n){
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
                ktr = ktr.product(gp.geometry.getMaterial().kT);
        }
        return ktr;
    }

    /**
     * Finds the closest intersection point of the given ray with the scene geometries.
     *
     * @param ray the ray
     * @return the closest intersection point
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
