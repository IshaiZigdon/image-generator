package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;


/**
 * class for calculating lights on given point
 *
 * @author Ishai zigdon
 * @author Zaki zafrani
 */
public interface LightSource {
    /**
     * calculates the intensity of the light on a given point
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

    /**
     * returning the distance from the light to the point
     *
     * @param p the given point
     * @return the result
     */
    public double getDistance(Point p);

    /**
     * method for checking if the light is reaching to the geometry
     *
     * @param ray the ray from the geometry to the light
     * @return the first intersecting point
     */
    public Point reachingLight(Ray ray);
}
