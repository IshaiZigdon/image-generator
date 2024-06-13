package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * this class represent the ambient light
 *
 * @author Ishai zigdon
 * @author Zaki zafrani
 */
public class AmbientLight {
    /**
     * black light
     */
    public static AmbientLight NONE = new AmbientLight(Color.BLACK, 0);
    /**
     * the intensity
     */
    final private Color intensity;
    /**
     * color of the light
     */
    private Color ia;
    /**
     * the attenuation coefficient
     */
    private Double3 ka;

    //------------------------------functions---------------------------------

    /**
     * ctor with 3 attenuation coefficient values for Ka
     *
     * @param ia the color
     * @param ka 3 attenuation coefficient values
     */
    public AmbientLight(Color ia, Double3 ka) {
        this.ia = ia;
        this.ka = ka;
        intensity = ia.scale(ka);
    }

    /**
     * ctor with 1 attenuation coefficient value for Ka
     *
     * @param ia the color
     * @param ka 1 attenuation coefficient value
     */
    public AmbientLight(Color ia, double ka) {
        this.ia = ia;
        this.ka = new Double3(ka);
        intensity = ia.scale(ka);
    }

    /**
     * get function for intensity
     *
     * @return intensity
     */
    public Color getIntensity() {
        return intensity;
    }
}
