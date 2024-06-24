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
     * with all affecting lights
     *
     * @param geoPoint the given point
     * @param ray      the ray from the camera
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
     * calculating the color from the given light on the given GeoPoint
     *
     * @param geoPoint the shape and the point
     * @param light    the given light
     * @param v        the direction of the camera
     * @return the result color
     */
    private Color phongLight(GeoPoint geoPoint, LightSource light, Vector v) {
        //---------------- creating objects ----------------------
        Material material = geoPoint.geometry.getMaterial();
        Double3 kD = material.kD;
        Double3 kS = material.kS;
        int nShininess = material.nShininess;
        //------- creating and checking vectors ---------------
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);
        Vector l = light.getL(geoPoint.point);
        double ln = alignZero(l.dotProduct(n));
        double vn = alignZero(v.dotProduct(n));
        if (ln * vn <= 0) return Color.BLACK;
        //-------- implementing the phong method -------------
        Color iL = light.getIntensity(geoPoint.point);
        Vector r = l.subtract(n.scale(2 * ln));
        return calcDiffuse(iL, kD, ln).add(calcSpecular(iL, kS, v,r,nShininess));
    }

    /**
     * calculates the diffusive light after intersection
     * @param iL the color in the specific point
     * @param kD the constant of the material that absorbs light
     * @param ln the direction from the position of the light to the current point
     * @return the calculation of the diffused light
     */
    private Color calcDiffuse(Color iL, Double3 kD, double ln) {
        return iL.scale(kD).scale(Math.abs(ln));
    }

    /**
     * calculates the specular light after intersection
     * @param iL the light in a specific point
     * @param kS how smooth is the material
     * @param v direction of the ray from the camera
     * @param r reflection of the light with attenuation coefficient
     * @param nShininess how much shine there is
     * @return the calculation of it all
     */
    private Color calcSpecular(Color iL, Double3 kS, Vector v,Vector r,int nShininess) {
        double maxVal = max(0, -v.dotProduct(r));
        maxVal = maxVal == 0 && nShininess != 0 ? 0 : Math.pow(maxVal, nShininess);
        return iL.scale(kS).scale(maxVal);
    }

    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> geoPoints = scene.geometries.findGeoIntersections(ray);
        return geoPoints == null ? scene.background : calcColor(ray.findClosestGeoPoint(geoPoints), ray);
    }
}
