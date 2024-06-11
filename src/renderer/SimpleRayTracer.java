package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

import java.util.List;

/**
 *
 */

public class SimpleRayTracer extends RayTracerBase{
    public SimpleRayTracer(Scene s){
        super(s);
    }
    private Color calcColor(Point p){
        return scene.ambientLight.getIntensity();
    }

    /**
     * @param r
     * @return
     */
    @Override
    public Color traceRay(Ray r) {
        List<Point> points= scene.geometries.findIntersections(r);
        if(points == null){
            return scene.background;
        }
        Point closestPoint = r.findClosestPoint(points);
        return calcColor(closestPoint);
    }
}
