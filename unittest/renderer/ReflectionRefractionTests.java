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
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(5).setKt(0.7)),
                //eyes
                new Sphere(new Point(-10, 50, 100), 5)
                        .setEmission(new Color(BLACK))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(50)),

                new Sphere(new Point(10, 50, 100), 5)
                        .setEmission(new Color(BLACK))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(50)),
                //pupils
                new Sphere(new Point(-10, 50, 120), 2)
                        .setEmission(new Color(WHITE))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(50)),

                new Sphere(new Point(10, 50, 120), 2)
                        .setEmission(new Color(WHITE))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(50)),

                //nose
                new Triangle(new Point(-5, 45, 100), new Point(5, 45, 100), new Point(0, 40, 100))
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
                new Sphere(new Point(0, 0, -50), 35)
                        .setEmission(new Color(WHITE))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(5)),

                new Sphere(new Point(0, 0, -100), 50)
                        .setEmission(new Color(BLACK))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(5)));
        for (int i = -150; i < 500; i += 12) {
            for (int j = -200; j < 150; j += 10) {
                double randomHeight = -50 + Math.random() * 15;
                double randomShiftX = Math.random() * 8 - 4;
                double randomShiftZ = Math.random() * 5 - 2.5;

                // Create the points with the random shifts
                Point p1 = new Point(i + randomShiftX, -25, j + randomShiftZ);
                Point p2 = new Point(i - 5 + randomShiftX, randomHeight, j + 5 + randomShiftZ);
                Point p3 = new Point(i + 5 + randomShiftX, randomHeight, j - 5 + randomShiftZ);

                Geometry triangle = new Triangle(p1, p2, p3)
                        .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(1))
                        .setEmission(new Color(34, 139, 34));
                scene.geometries.add(triangle);
            }
        }



        scene.setBackground(new Color(64, 128, 128));
        scene.lights.add(
                new SpotLight(new Color(1000, 700, 700), new Point(-150, 200, -100), new Vector(1, -1, 0))
                        .setKl(0.0004).setKq(0.0000006)
        );

        cameraBuilder.setLocation(new Point(0, 0, 1000))
                .setVpDistance(1000)
                .setVpSize(150, 150)
                .setImageWriter(new ImageWriter("panda", 500, 500))
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
