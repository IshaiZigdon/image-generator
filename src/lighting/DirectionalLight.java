package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;


/**
 * class for directional light
 *
 * @author Ishai zigdon
 * @author Zaki zafrani
 */
public class DirectionalLight extends Light implements LightSource {
    /**
     * the direction vector
     */
    private final Vector direction;

    /**
     * ctor with given intensity and direction
     *
     * @param intensity the intensity
     * @param direction the direction
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction.normalize();
    }

    @Override
    public double getDistance(Point p) {
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public Color getIntensity(Point p) {
        return intensity;
    }

    @Override
    public Vector getL(Point p) {
        return direction; //not normalized because we did it before
    }

    @Override
    public double reachingLight(Ray ray){
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public List<Ray> beamOfRays(Point p, Vector v, Vector n) {
        return List.of(new Ray(p,v,n));
    }
}
