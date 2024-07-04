package renderer;

import geometries.*;
import lighting.AmbientLight;
import lighting.DirectionalLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

import static java.awt.Color.*;
public class FinalPicture {
    /**
     * Scene for the picture
     */
    private final Scene scene = new Scene("Final Picture");
    /**
     * Camera builder for the tests with triangles
     */
    private final Camera.Builder cameraBuilder = Camera.getBuilder()
            .setDirection(Point.ZERO, Vector.Y)
            .setRayTracer(new SimpleRayTracer(scene));
    @Test
    public void FinalPictureTest() {
        scene.geometries.add(
                new Cylinder(new Ray(new Point(0, -70, 0),new Vector(0,1,1)), 50,110)
                        .setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),
                new Sphere(new Point(0, -70, 0),10)
                        .setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)));
        scene.lights.add(
                new SpotLight(new Color(1000, 600, 0), new Point(0, 0, 500), Vector.MINUS_Z)
                        .setKl(0.0004).setKq(0.0000006));

        cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000)
                .setVpSize(150, 150)
                .setImageWriter(new ImageWriter("FinalPicture", 500, 500))
                .build()
                .renderImage()
                .writeToImage();
    }
}
