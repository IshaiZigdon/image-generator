package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * class for calculating lights on given point
 *
 * @author Ishai zigdon
 * @author Zaki zafrani
 */
public interface LightSource {
    /**
     * calculates the intensity of the light in a given point
     *
     * @param p the given point
     * @return the result
     */
    public Color getIntensity(Point p);

    /**
     * calculates the direction vector from the light to a given point
     *
     * @param p the given point
     * @return the result
     */
    public Vector getL(Point p);
}
