package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

/**
 * this class represent the scene
 *
 * @author Ishai zigdon
 * @author Zaki zafrani
 */
public class Scene {
    /**
     * the name of the scene
     */
    public final String name;
    /**
     * the background color
     */
    public Color background = Color.BLACK;
    /**
     * the ambient light of the scene
     */
    public AmbientLight ambientLight = AmbientLight.NONE;
    /**
     * the shapes in the scene
     */
    public Geometries geometries = new Geometries();
    /**
     * list of all the lights on the scene
     */
    public List<LightSource> lights = new LinkedList<>();

    //------------------------functions---------------------------------

    /**
     * ctor with given name
     *
     * @param str the name
     */
    public Scene(String str) {
        name = str;
    }

    /**
     * set function for background
     *
     * @param bg the given background color
     * @return the updated scene
     */
    public Scene setBackground(Color bg) {
        background = bg;
        return this;
    }

    /**
     * set function for ambientLight
     *
     * @param amb the given ambient light
     * @return the updated scene
     */
    public Scene setAmbientLight(AmbientLight amb) {
        ambientLight = amb;
        return this;
    }

    /**
     * set function for geometries
     *
     * @param g the given geometries in the scene
     * @return the updated scene
     */
    public Scene setGeometries(Geometries g) {
        geometries = g;
        return this;
    }

    /**
     * set function for lights
     *
     * @param lights the given lights in the scene
     * @return the updated scene
     */
    public Scene setGeometries(LightSource lights) {
        this.lights.add(lights);
        return this;
    }
}
