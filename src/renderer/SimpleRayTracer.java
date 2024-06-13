package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

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
     * @param p the given point
     * @return the color of p
     */
    private Color calcColor(Point p) {
        return scene.ambientLight.getIntensity();
    }

    @Override
    public Color traceRay(Ray r) {
        List<Point> points = scene.geometries.findIntersections(r);
        if (points == null) {
            return scene.background;
        }
        Point closestPoint = r.findClosestPoint(points);
        return calcColor(closestPoint);
    }
}
