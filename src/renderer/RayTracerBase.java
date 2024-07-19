package renderer;

import primitives.*;
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
    protected final Scene scene;

    /**
     * the black board we are using for soft shadows
     */
    protected BlackBoard blackBoard;

    /**
     * ctor with a given scene
     *
     * @param scene the given scene
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * ctor with a given scene and blackboard
     *
     * @param scene      the given scene
     * @param blackBoard the given blackboard
     */
    public RayTracerBase(Scene scene, BlackBoard blackBoard) {
        this.scene = scene;
        this.blackBoard = blackBoard;
    }

    /**
     * ray tracing the ray through the scene and finding the color of the ray
     *
     * @param r the ray
     * @return the color
     */
    public abstract Color traceRay(Ray r);
}
