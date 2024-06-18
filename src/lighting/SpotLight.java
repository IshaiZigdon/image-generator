package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import static java.lang.Math.max;

/**
 * @author Ishai zigdon
 * @author Zaki zafrani
 */
public class SpotLight extends PointLight {
    private final Vector direction;

    /**
     * ctor with given intensity and position and direction
     *
     * @param intensity the intensity
     * @param position  the position
     * @param direction the direction
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    /**
     * set function for kC
     *
     * @param kC the given kC
     * @return the updated PointLight
     */
    @Override
    public PointLight setKc(double kC) {
        return (SpotLight) super.setKc(kC);
    }

    /**
     * set function for kL
     *
     * @param kL the given kL
     * @return the updated PointLight
     */
    public SpotLight setKl(double kL) {
        return (SpotLight) super.setKc(kL);
    }

    /**
     * set function for kQ
     *
     * @param kQ the given kQ
     * @return the updated PointLight
     */
    public SpotLight setKq(double kQ) {
        return (SpotLight) super.setKc(kQ);
    }

    /**
     * set function for kC, kL,kQ
     *
     * @param kC the given kC
     * @param kL the given kL
     * @param kQ the given kQ
     * @return the updated PointLight
     */
    public SpotLight setCoefficients(double kC, double kL, double kQ) {
        return (SpotLight) super.setCoefficients(kC, kL, kQ);
    }

    /**
     * @param p
     * @return
     */
    @Override
    public Color getIntensity(Point p) {
        return super.getIntensity(p).scale(max(0, direction.dotProduct(getL(p))));
    }
}
