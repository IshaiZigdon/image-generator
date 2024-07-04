package lighting;

import primitives.Color;
import primitives.*;

import java.util.List;

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

    /**
     * returns beam of rays from a given point in the given direction
     * @param p the given point
     * @param v the given direction
     * @param n the given normal for ray ctor
     * @return list of rays
     */
    public abstract List<Ray> beamOfRays( Point p, Vector v, Vector n);
}
