package primitives;


import lighting.LightSource;
import lighting.PointLight;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.isZero;

/**
 * class for target area for beam of rays
 *
 * @author Ishai zigdon
 * @author Zaki zafrani
 */
public class BlackBoard {

    /**
     * size of the grid
     */
    private double sizeOfGrid = 9;

    /**
     * constructor with grid size and amount of rays
     *
     * @param sizeOfGrid the size of the grid
     */
    public BlackBoard(double sizeOfGrid) {
        this.sizeOfGrid = sizeOfGrid;
    }

    /**
     * empty constructor
     */
    public BlackBoard() {
    }

    /**
     * set function for the size of the grid
     *
     * @param sizeOfGrid the size of the grid
     * @return this object
     */
    public BlackBoard setSizeOfGrid(double sizeOfGrid) {
        this.sizeOfGrid = sizeOfGrid;
        return this;
    }

    /**
     * returns beam of rays from a given point to a given target in the given direction
     *
     * @param p      the given point
     * @param light  the given light
     * @param v      the given direction
     * @param normal the normal vector form the shape for DELTA moving
     * @return list of rays
     */
    public List<Ray> beamOfRays(Point p, LightSource light, Vector v, Vector normal) {
        double radius = ((PointLight) light).getRadius();
        if (isZero(radius)) return List.of(new Ray(p, v, normal));

        Vector up = v.verticalVector();

        Vector right = v.crossProduct(up).normalize();

        List<Ray> rayBeam = new LinkedList<>();
        Point gridCenter = p.add(v.scale(light.getDistance(p)));

        // Calculate the grid size based on the number of rays
        // int sizeOfGrid = (int) Math.ceil(Math.sqrt(81));
        double cellSize = (2 * radius) / sizeOfGrid;

        for (int i = 0; i < sizeOfGrid; i++) {
            for (int j = 0; j < sizeOfGrid; j++) {
                // Calculate the position within the grid
                double x = (j - sizeOfGrid / 2.0) * cellSize;
                double y = (i - sizeOfGrid / 2.0) * cellSize;

                Point pIJ = gridCenter;
                if (!isZero(x)) pIJ = pIJ.add(right.scale(x));
                if (!isZero(y)) pIJ = pIJ.add(up.scale(y));

                // Only add the ray if it is within the radius
                if (light.getDistance(pIJ) <= radius) {
                    rayBeam.add(new Ray(p, pIJ.subtract(p), normal));
                }
            }
        }
        return rayBeam;
    }
}
