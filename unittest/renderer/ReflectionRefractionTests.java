/**
 *
 */
package renderer;

import static java.awt.Color.*;

import org.junit.jupiter.api.Test;

import geometries.*;
import lighting.*;
import primitives.*;
import scene.Scene;



/**
 * Tests for reflection and transparency functionality, test for partial
 * shadows
 * (with transparency)
 *
 * @author dzilb
 */
public class ReflectionRefractionTests {
    /**
     * Scene for the tests
     */
    private final Scene scene = new Scene("Test scene");
    /**
     * Camera builder for the tests with triangles
     */
    private final Camera.Builder cameraBuilder = Camera.getBuilder()
            .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setRayTracer(new SimpleRayTracer(scene));

    /**
     * test of reflection, refraction and shadow on 4 different shapes
     */
    @Test
    public void Panda() {
        scene.geometries.add(
                //Large reflective floor
                new Plane(new Point(0, -40, 0), new Vector(0, 1, 0))
                        .setEmission(new Color(255, 218, 185))
                        .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(100)),
                //head
                new Sphere(new Point(0, 50, -100), 30)
                        .setEmission(new Color(WHITE))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(5)),
                //eyes
                new Sphere(new Point(-10, 60, -60), 8)
                        .setEmission(new Color(BLACK))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(50)),

                new Sphere(new Point(10, 60, -60), 8)
                        .setEmission(new Color(BLACK))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(50)),
                //pupils
                new Sphere(new Point(-10, 60, -50), 4)
                        .setEmission(new Color(WHITE))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(50)),

                new Sphere(new Point(10, 60, -50), 4)
                        .setEmission(new Color(WHITE))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(50)),

                //nose
                new Triangle(new Point(-5, 52, -50), new Point(5, 52, -50), new Point(0, 47, -50))
                        .setEmission(new Color(BLACK))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(50)),
                //ears
                new Sphere(new Point(-30, 65, -100), 10)
                        .setEmission(new Color(BLACK))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(50)),

                new Sphere(new Point(30, 65, -100), 10)
                        .setEmission(new Color(BLACK))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(50)),

                //body
                new Sphere(new Point(0, 0, -70), 35)
                        .setEmission(new Color(WHITE))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(5)),

                new Sphere(new Point(0, 0, -100), 50)
                        .setEmission(new Color(BLACK))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(5)),
                // Mirror polygon
                new Polygon(
                        new Point(50, 80, -250),  // Top-left
                        new Point(70, 80, 300),   // Top-right
                        new Point(70, -35, 300),  // Bottom-right
                        new Point(50, -35, -250)  // Bottom-left
                )
                        .setEmission(new Color(30,30,30))
                        .setMaterial(new Material().setKr(0.4).setKd(0.05).setShininess(20)));

        scene.setBackground(new Color(64, 128, 128));
        scene.lights.add(
                new SpotLight(new Color(1000, 800, 800), new Point(-150, 200, -100), new Vector(1, -1, 0))
                        .setKl(0.0004).setKq(0.0000006)
        );

        cameraBuilder.setLocation(new Point(-100, 0, 1000))
                .setVpDistance(2200)
                .setVpSize(500, 500)
                .setDirection(new Vector(0.15,0,-1),new Vector(0,1,0))
                .setImageWriter(new ImageWriter("panda", 2048, 2048))
                .build()
                .renderImage()
                .writeToImage();
    }
    @Test
    public void PandaHead() {
        scene.geometries.add(
                //Large reflective floor
                new Plane(new Point(0, -90, 0), new Vector(0, 1, 0))
                        .setEmission(new Color(255, 127, 80))
                        .setMaterial(new Material().setKd(0.6).setKs(0.4).setShininess(100)),
                //head
                new Sphere(new Point(0, 0, -100), 30)
                        .setEmission(new Color(WHITE))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(5)),
                //eyes
                new Sphere(new Point(-10, 10, -60), 8)
                        .setEmission(new Color(BLACK))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(50)),

                new Sphere(new Point(10, 10, -60), 8)
                        .setEmission(new Color(BLACK))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(50)),
                //pupils
                new Sphere(new Point(-10, 10, -50), 4)
                        .setEmission(new Color(WHITE))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(50)),

                new Sphere(new Point(10, 10, -50), 4)
                        .setEmission(new Color(WHITE))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(50)),

                //nose
                new Triangle(new Point(-5, 2, -50), new Point(5, 2, -50), new Point(0, -3, -50))
                        .setEmission(new Color(BLACK))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(50)),
                //ears
                new Sphere(new Point(-30, 15, -100), 10)
                        .setEmission(new Color(BLACK))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(50)),

                new Sphere(new Point(30, 15, -100), 10)
                        .setEmission(new Color(BLACK))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(50)));

        scene.setBackground(new Color(137, 207, 240));
        scene.lights.add(
                new SpotLight(new Color(600, 400, 400), new Point(-150, 200, -100), new Vector(1, -1, 0))
                        .setKl(0.0004).setKq(0.0000006)
        );

        cameraBuilder.setLocation(new Point(0, 0, 1000))
                .setVpDistance(2700)
                .setVpSize(500, 500)
                .setImageWriter(new ImageWriter("pandaHead", 2048, 2048))
                .build()
                .renderImage()
                .writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheres() {
        scene.geometries.add(
                new Sphere(new Point(0, 0, -50), 50d).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(new Point(0, 0, -50), 25d).setEmission(new Color(RED))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));
        scene.lights.add(
                new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2))
                        .setKl(0.0004).setKq(0.0000006));

        cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000)
                .setVpSize(150, 150)
                .setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500))
                .build()
                .renderImage()
                .writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheresOnMirrors() {
        scene.geometries.add(
                new Sphere(new Point(-950, -900, -1000), 400d).setEmission(new Color(0, 50, 100))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)
                                .setKt(new Double3(0.5, 0, 0))),
                new Sphere(new Point(-950, -900, -1000), 200d).setEmission(new Color(100, 50, 20))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
                new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                        new Point(670, 670, 3000))
                        .setEmission(new Color(20, 20, 20))
                        .setMaterial(new Material().setKr(1)),
                new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                        new Point(-1500, -1500, -2000))
                        .setEmission(new Color(20, 20, 20))
                        .setMaterial(new Material().setKr(new Double3(0.5, 0, 0.4))));
        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));
        scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4))
                .setKl(0.00001).setKq(0.000005));

        cameraBuilder.setLocation(new Point(0, 0, 10000)).setVpDistance(10000)
                .setVpSize(2500, 2500)
                .setImageWriter(new ImageWriter("reflectionTwoSpheresMirrored", 500, 500))
                .build()
                .renderImage()
                .writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a spot light with a
     * partially
     * transparent Sphere producing partial shadow
     */
    @Test
    public void trianglesTransparentSphere() {
        scene.geometries.add(
                new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135),
                        new Point(75, 75, -150))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
                new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
                new Sphere(new Point(60, 50, -50), 30d).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)));
        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
        scene.lights.add(
                new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1))
                        .setKl(4E-5).setKq(2E-7));

        cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000)
                .setVpSize(200, 200)
                .setImageWriter(new ImageWriter("refractionShadow", 600, 600))
                .build()
                .renderImage()
                .writeToImage();
    }
}
