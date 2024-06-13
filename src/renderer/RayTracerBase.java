package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * abstract class for ray tracing a scene with ray
 *
 * @author Ishai zigdon
 * @author Zaki zafrani
 */
public abstract class RayTracerBase {
    /**
     * the scene for ray tracing
     */
    protected Scene scene;

    /**
     * ctor withe given scene
     *
     * @param scene the given scene
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * ray tracing the scene and finding the color of the image
     *
     * @param r the ray
     * @return the color
     */
    public abstract Color traceRay(Ray r);
}
