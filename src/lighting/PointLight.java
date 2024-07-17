package lighting;

import geometries.Plane;
import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.alignZero;


/**
 * class for point light
 *
 * @author Ishai zigdon
 * @author Zaki zafrani
 */
public class PointLight extends Light implements LightSource { /**
     * the position of the light
     */
    protected final Point position;
    /**
     * the radius
     */
    protected double radius = 5;
    /**
     * the constant attenuation factor
     */
    private double kC = 1;
    /**
     * the constant linear attenuation factor
     */
    private double kL = 0;
    /**
     * the constant squared attenuation factor
     */
    private double kQ = 0;

    /**
     * ctor with given intensity and position
     *
     * @param intensity the intensity
     * @param position  the position
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }


    /**
     * ctor with given intensity and position
     *
     * @param intensity the intensity
     * @param position  the position
     * @param radius    the radius of the light
     */
    public PointLight(Color intensity, Point position, double radius) {
        super(intensity);
        this.position = position;
        this.radius = radius;
    }

    /**
     * set function for kC
     *
     * @param kC the given kC
     * @return the updated PointLight
     */
    public PointLight setKc(double kC) {
        if (alignZero(kC) <= 0)
            throw new IllegalArgumentException("kC must be greater than zero");
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
     * set function for radius
     *
     * @param radius the given radius
     * @return the updated PointLight
     */
    public PointLight setRadius(double radius) {
        this.radius = radius;
        return this;
    }

    public double getRadius(){
        return radius;
    }

    @Override
    public double getDistance(Point p) {
        return p.distance(position);
    }


    @Override
    public Color getIntensity(Point p) {
        double distanceSquared = position.distanceSquared(p);
        double distance = Math.sqrt(distanceSquared);
        return intensity.scale(1 / (kC + kL * distance + kQ * distanceSquared));
    }

    @Override
    public Vector getL(Point p) {
        return p.subtract(position).normalize();
    }
}
