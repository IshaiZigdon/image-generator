package renderer;

import geometries.Sphere;
import lighting.AmbientLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * tests for SimpleRayTracer class
 */
class SimpleRayTracerTest {
    /**
     * Javadoc just for quiet life
     */
    SimpleRayTracerTest() {
    }

    /**
     * test method for
     * {@link SimpleRayTracer#traceRay(Ray)}
     */
    @Test
    void traceRay() {
        Scene scene = new Scene("testSimpleRayTracer");
        // ============ Equivalence Partitions Tests ==============
        //TC01: return the color of the sphere and the background color
        Color ambientColor = new Color(255, 191, 191);
        Color backgroundColor = new Color(75, 127, 90);
        Ray ray1 = new Ray(new Point(0, 1, -2), new Vector(0, 0, 1));
        scene.setAmbientLight(new AmbientLight(ambientColor, Double3.ONE))
                .setBackground(backgroundColor)
                .geometries.add(new Sphere(new Point(0, 0, 10), 2));
        SimpleRayTracer smpRayTracer = new SimpleRayTracer(scene);
        //sphere
        assertEquals(ambientColor, smpRayTracer.traceRay(ray1), "wrong color");
        Ray ray2 = new Ray(new Point(0, 1, -2), new Vector(0, 0, -1));
        //background
        assertEquals(backgroundColor, smpRayTracer.traceRay(ray2), "wrong color");

    }
}