package lighting;

import primitives.Color;
import renderer.BlackBoard;

/**
 * class for all the lights
 *
 * @author Ishai zigdon
 * @author Zaki zafrani
 */
public abstract class Light {
    /**
     * the intensity of the light
     */
    protected final Color intensity;

    /**
     * ctor with given intensity
     *
     * @param intensity the intensity
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * get function for intensity
     *
     * @return the intensity
     */
    public Color getIntensity() {
        return intensity;
    }

}
