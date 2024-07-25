package finalPicture;

import geometries.Cylinder;
import geometries.Polygon;
import lighting.PointLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.*;
import scene.Scene;

import static java.awt.Color.*;

/**
 * test for the final picture
 */
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
            .setMultithreading(0)
            .setDebugPrint(0.1);

    /**
     * test for the final picture
     */
    @Test
    public void FinalPictureTest() {
        //computer
        scene.geometries.add(
                //body
                new Polygon(new Point(-500, 0, 0), new Point(500, 0, 0),
                        new Point(500, 0, 1000), new Point(-500, 0, 1000))
                        .setEmission(new Color(0, 255, 255))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),
                new Polygon(new Point(-500, 0, 1000), new Point(500, 0, 1000),
                        new Point(500, -20, 1000), new Point(-500, -20, 1000))
                        .setEmission(new Color(0, 255, 255))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),

                //screen
                new Polygon(new Point(-500, 0, 0), new Point(500, 0, 0),
                        new Point(500, 500, 0), new Point(-500, 500, 0))
                        .setEmission(new Color(30, 30, 30))
                        .setMaterial(new Material().setKt(0.7).setKd(0.05).setShininess(20)),
                //frame
                new Cylinder(new Ray(new Point(-500, 0, 0), new Vector(0, 1, 0)), 3, 500)
                        .setEmission(new Color(0, 255, 255))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(5)),
                new Cylinder(new Ray(new Point(500, 0, 0), new Vector(0, 1, 0)), 3, 500)
                        .setEmission(new Color(0, 255, 255))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(5)),
                new Cylinder(new Ray(new Point(-500, 0, 0), new Vector(1, 0, 0)), 12, 1000)
                        .setEmission(Color.BLACK)
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(5)),
                new Cylinder(new Ray(new Point(-500, 500, 0), new Vector(1, 0, 0)), 3, 1000)
                        .setEmission(new Color(0, 255, 255))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(5)),
                new Polygon(new Point(500, 0, 1), new Point(-500, 0, 1),
                        new Point(-500, 50, 1), new Point(500, 50, 1))
                        .setEmission(new Color(0, 255, 255))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(5)),

                //backround
                new Polygon(new Point(500, 500, 0), new Point(-500, 500, 0),
                        new Point(-500, 1000, 0), new Point(500, 1000, 0))
                        .setEmission(new Color(BLACK))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3)),
                new Polygon(new Point(-1000, -20, 0), new Point(1000, -20, 0),
                        new Point(1000, -20, 2000), new Point(-1000, -20, 2000))
                        .setEmission(new Color(BLACK))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3)),
                new Polygon(new Point(-500, -1000, 0), new Point(-1000, -1000, 0),
                        new Point(-1000, 1000, 0), new Point(-500, 1000, 0))
                        .setEmission(new Color(BLACK))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3)),
                new Polygon(new Point(500, -1000, 0), new Point(1000, -1000, 0),
                        new Point(1000, 1000, 0), new Point(500, 1000, 0))
                        .setEmission(new Color(BLACK))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3))

        );
        //beyond the screen
        scene.geometries.add(
                //floor
                new Polygon(new Point(-1000, -20, 0), new Point(1000, -20, 0),
                        new Point(1000, -20, -2000), new Point(-1000, -20, -2000))
                        .setEmission(new Color(GRAY))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3)),
                new Cylinder(new Ray(new Point(0, -20, -400), Vector.Y), 100, 200)
                        .setEmission(new Color(GREEN))
                        .setMaterial(new Material().setKd(0.6).setKs(0.3))
        );

        //keyboard
        for (int i = -420; i <= 400; i += 57) {
            for (int j = 50; j <= 500; j += 90) {
                scene.geometries.add(
                        new Polygon(new Point(i, 0.1, j), new Point(i + 50, 0.1, j),
                                new Point(i + 50, 0.1, j + 80), new Point(i, 0.1, j + 80))
                                .setEmission(new Color(BLACK))
                                .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100))
                );
            }
        }
        //mouse
        scene.geometries.add(
                new Polygon(new Point(-120, 0.1, 670), new Point(120, 0.1, 670),
                        new Point(120, 0.1, 930), new Point(-120, 0.1, 930))
                        .setEmission(new Color(BLACK))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100))
        );

        scene.setBackground(new Color(64, 128, 128));

        scene.lights.add(
                new PointLight(new Color(WHITE), new Point(0, 500, 700))
                        .setKl(0.0004).setKq(0.0000006)
        );

        scene.lights.add(
                new PointLight(new Color(WHITE), new Point(-200, 150, -400))
                        .setKl(0.0004).setKq(0.0000006)
        );

        cameraBuilder.setLocation(new Point(0, 1200, 4200))
                .setVpDistance(2200)
                .setVpSize(900, 500)
                .setDirection(new Point(0, 0, -160), new Vector(0, 1, -120 / 436d))
                .setImageWriter(new ImageWriter("FinalPicture", 900, 500))
                .setRayTracer(new RegularGrid(scene))
                .build()
                .renderImage()
                .writeToImage();
    }
}
