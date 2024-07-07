package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import static primitives.Util.alignZero;


/**
 * class for spotlight
 *
 * @author Ishai zigdon
 * @author Zaki zafrani
 */
public class SpotLight extends PointLight {
    /**
     * the direction of the spotLight
     */
    private final Vector direction;
    /**
     * the width of the beam
     */
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
    public SpotLight(Color intensity, Point position, Vector direction, double radius) {
        super(intensity, position, radius);
        this.direction = direction.normalize();
    }

    /**
     * set function for kC
     *
     * @param kC the given kC
     * @return the updated PointLight
     */
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

    /**
     * set function for narrowBeam
     *
     * @param narrowBeam the given narrowBeam
     * @return the updated spotLight
     */
    public SpotLight setNarrowBeam(int narrowBeam) {
        this.narrowBeam = narrowBeam;
        return this;
    }

    @Override
    public Color getIntensity(Point p) {
        double angle = alignZero(direction.dotProduct(getL(p)));
        return angle <= 0 ? Color.BLACK : super.getIntensity(p).scale(Math.pow(angle, narrowBeam));
    }
}
