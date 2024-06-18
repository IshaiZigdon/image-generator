package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * @author Ishai zigdon
 * @author Zaki zafrani
 */
public class PointLight extends Light implements LightSource {
    protected final Point position;
    private double kC = 1, kL = 0, kQ = 0;

    /**
     * ctor with given intensity and position
     *
     * @param intensity the intensity
     * @param position  the position
     */
    protected PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    /**
     * set function for kC
     *
     * @param kC the given kC
     * @return the updated PointLight
     */
    public PointLight setKc(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * set function for kL
     *
     * @param kL the given kL
     * @return the updated PointLight
     */
    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * set function for kQ
     *
     * @param kQ the given kQ
     * @return the updated PointLight
     */
    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }

    /**
     * set function for kC, kL,kQ
     *
     * @param kC the given kC
     * @param kL the given kL
     * @param kQ the given kQ
     * @return the updated PointLight
     */
    public PointLight setCoefficients(double kC, double kL, double kQ) {
        this.kC = kC;
        this.kL = kL;
        this.kQ = kQ;
        return this;
    }

    /**
     * calculates the intensity color in the point
     *
     * @param p the point on the shape
     * @return the color of the point
     */
    @Override
    public Color getIntensity(Point p) {
        double distanceSquared = position.distanceSquared(p);
        double distance = position.distance(p);
        double nominator = kC + kL * distance + kQ * distanceSquared;
        return getIntensity().scale(1 / nominator);
    }

    /**
     * creating a vector of the direction of the shape
     *
     * @param p the point on the shape
     * @return the direction vector
     */
    @Override
    public Vector getL(Point p) {
        return p.subtract(position).normalize();
    }
}
