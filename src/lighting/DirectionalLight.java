package lighting;

import primitives.*;


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
    public Point reachingLight(Ray ray) {
        return new Point(new Double3(Double.POSITIVE_INFINITY));
    }

}
