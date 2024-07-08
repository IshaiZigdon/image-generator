package lighting;

import geometries.Sphere;
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
public class PointLight extends Light implements LightSource {
    /**
     * the position of the light
     */
    protected final Point position;
    /**
     * the radius
     */
    protected double radius = 30;
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

    private final double SIZE_OF_GRID = 17;
    private final int SIZE_OF_RAYS = 10;

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
     * @param radius the radius of the light
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

    @Override
    public boolean reachingLight(Ray ray){
        Sphere sphere = new Sphere(position,radius);
        return sphere.findIntersections(ray) != null;
    }
}
