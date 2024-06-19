package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

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
    private Vector direction;

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

    /**
     * ctor with given intensity
     *
     * @param intensity the intensity
     */
    @SuppressWarnings("unused")
    protected DirectionalLight(Color intensity) {
        super(intensity);
    }

    @Override
    public Color getIntensity(Point p) {
        return intensity;
    }

    @Override
    public Vector getL(Point p) {
        return direction; //not normalized because we did it before
    }
}
