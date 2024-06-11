package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import primitives.Color;

/**
 *
 */
public class Scene {
    /***/
    public String name;
    /***/
    public Color background = Color.BLACK;
    /***/
    public AmbientLight ambientLight = AmbientLight.NONE;
    /***/
    public Geometries geometries = new Geometries();

    //------------------------functions---------------------------------

    /**
     *
     * @param n
     */
    public Scene(String n){name= n;}

    /**
     *
     * @param bg
     * @return
     */
    public Scene setBackground(Color bg) {
        background = bg;
        return this;
    }

    /**
     *
     * @param amb
     * @return
     */
    public Scene setAmbientLight(AmbientLight amb) {
        ambientLight = amb;
        return this;
    }

    /**
     *
     * @param g
     * @return
     */
    public Scene setGeometries(Geometries g) {
        geometries = g;
        return this;
    }
}
