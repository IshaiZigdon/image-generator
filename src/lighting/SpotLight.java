package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Util;
import primitives.Vector;

import static java.lang.Double.max;
import static primitives.Util.alignZero;


/**
 * @author Ishai zigdon
 * @author Zaki zafrani
 */
public class SpotLight extends PointLight {
    private final Vector direction;
    private int narrowBeam = 1;

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
    public SpotLight setKc(double kC) {
        return (SpotLight) super.setKc(kC);
    }

    /**
     * set function for kL
     *
     * @param kL the given kL
     * @return the updated PointLight
     */
    public SpotLight setKl(double kL) {
        return (SpotLight) super.setKl(kL);
    }

    /**
     * set function for kQ
     *
     * @param kQ the given kQ
     * @return the updated PointLight
     */
    public SpotLight setKq(double kQ) {
        return (SpotLight) super.setKq(kQ);
    }

    public SpotLight setNarrowBeam(int narrowBeam) {
        this.narrowBeam = narrowBeam;
        return this;
    }

    /**
     * @param p
     * @return
     */
    @Override
    public Color getIntensity(Point p) {
        double angle = alignZero(direction.dotProduct(getL(p)));
        double factor = alignZero(Math.pow(max(0, angle), narrowBeam));
        return super.getIntensity(p).scale(factor);
    }
}
