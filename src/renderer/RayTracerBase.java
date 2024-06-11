package renderer;

import primitives.*;
import scene.Scene;

/**
 *
 */
public abstract class RayTracerBase {
    /***/
    protected Scene scene ;

    /**
     *
     * @param scene
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     *
     * @param r
     * @return
     */
    public abstract Color traceRay(Ray r);

}
