package renderer;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.Color;
import primitives.Double3;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;

import java.util.List;

import static java.lang.Double.max;

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
    private Color calcColor(GeoPoint geoPoint,Ray ray) {
        Color intensityAndEmission =  scene.ambientLight.getIntensity().add(geoPoint.geometry.getEmission());
        Color sumColor= new Color(0,0,0);
        Double3 kD = geoPoint.geometry.getMaterial().kD;
        Double3 kS = geoPoint.geometry.getMaterial().kS;
        if(kD.add(kS).equals(Double3.ONE))//we should check if this is greater than 1 but i did this for now
            return intensityAndEmission;
        Color iL;
        double ln;
        Color firstHalf,secondHalf;
        double maxVal;
        Vector normalG;
        Vector vR;

        for( LightSource light : scene.lights)
        {
            normalG =geoPoint.geometry.getNormal(geoPoint.point);

            ln =light.getL(geoPoint.point).dotProduct(normalG);
            double absln = Math.abs(ln);
            iL = light.getIntensity(geoPoint.point);
            firstHalf = iL.scale(absln).scale(kD);


            secondHalf = iL.scale(kS);
            vR = light.getL(geoPoint.point).add(normalG.scale(-2*ln));

            maxVal = max(0,vR.dotProduct(ray.getDirection()));

            for(int i=0; i<geoPoint.geometry.getMaterial().nShininess; i++)
                maxVal = maxVal*maxVal;

            secondHalf = secondHalf.scale(maxVal);

            sumColor = sumColor.add(firstHalf,secondHalf);
        }
        return sumColor;
    }

    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> geoPoints = scene.geometries.findGeoIntersections(ray);
        return geoPoints == null ? scene.background : calcColor(ray.findClosestGeoPoint(geoPoints),ray);
    }
}
