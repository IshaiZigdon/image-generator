package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

/**
 * this interface will serve all geometry shapes in the program
 * 2D and 3D
 *
 * @author Ishai zigdon
 * @author Zaki zafrani
 */
public abstract class Geometry extends Intersectable {
    /**
     * emission light
     */
    protected Color emission = Color.BLACK;
    /**
     * the material
     */
    private Material material = new Material();

    /**
     * get function for emission
     *
     * @return emission
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * set function for emission
     *
     * @param emission the given emission
     * @return the updated Geometry
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * get function to material
     *
     * @return material
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * set function for material
     *
     * @param material the given material
     * @return the updated Geometry
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * this function calculates the
     * vertical (normal) vector for the various shapes at a point
     * on the surface of the shape
     *
     * @param p a point on the shape
     * @return a vertical vector for the shape
     */
    public abstract Vector getNormal(Point p);

}
