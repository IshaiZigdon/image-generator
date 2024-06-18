package lighting;

import primitives.Color;

/**
 *
 *
 * @author Ishai zigdon
 * @author Zaki zafrani
 */
public abstract class Light {
    protected Color intensity;

    /**
     * ctor with given intensity
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
