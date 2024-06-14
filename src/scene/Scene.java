package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import primitives.Color;

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

    //------------------------functions---------------------------------

    /**
     * ctor with given name
     *
     * @param n the name
     */
    public Scene(String n) {
        name = n;
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
}
