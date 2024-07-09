package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;


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
     * returns beam of rays from a given point in the given direction
     *
     * @param p the given point
     * @param v the given direction
     * @param n the normal vector form the shape for DELTA moving
     * @return list of rays
     */
    public List<Ray> beamOfRays(Point p, Vector v,Vector n);

    public double reachingLight(Ray ray);
}
