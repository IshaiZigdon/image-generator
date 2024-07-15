package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * this class represent the ambient light
 *
 * @author Ishai zigdon
 * @author Zaki zafrani
 */
public class AmbientLight extends Light {
    /**
     * black light
     */
    public static AmbientLight NONE = new AmbientLight(Color.BLACK, 0);

    //------------------------------functions---------------------------------

    /**
     * ctor with 3 attenuation coefficient values for Ka
     *
     * @param ia the color
     * @param ka 3 attenuation coefficient values
     */
    public AmbientLight(Color ia, Double3 ka) {
        super(ia.scale(ka));
    }

    /**
     * ctor with 1 attenuation coefficient value for Ka
     *
     * @param ia the color
     * @param ka 1 attenuation coefficient value
     */
    public AmbientLight(Color ia, double ka) {
        super(ia.scale(ka));
    }
}
