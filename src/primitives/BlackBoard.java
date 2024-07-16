package primitives;


import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;
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
    private double sizeOfGrid = 17;
    /**
     * the amount of rays we are sending to the grid
     */
    private int amountOfRays = 81;

    /**
     * constructor with grid size and amount of rays
     *
     * @param sizeOfGrid   the size of the grid
     * @param amountOfRays the amount of the rays
     */
    public BlackBoard(double sizeOfGrid, int amountOfRays) {
        this.sizeOfGrid = sizeOfGrid;
        this.amountOfRays = amountOfRays;
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
     * set function for the amount of the rays
     *
     * @param amountOfRays the amount of the rays
     * @return this object
     */
    public BlackBoard setAmountOfRays(int amountOfRays) {
        this.amountOfRays = amountOfRays;
        return this;
    }

    /**
     * returns beam of rays from a given point to a given target in the given direction
     *
     * @param p        the given point
     * @param distance the distance between the point and the target point
     * @param v        the given direction
     * @param normal   the normal vector form the shape for DELTA moving
     * @return list of rays
     */
    public List<Ray> beamOfRays(Point p, double distance, Vector v, Vector normal) {
        List<Ray> rayBeam = new LinkedList<>();

        Point gridCenter = p.add(v.scale(distance / 2));

        Vector up = v.verticalVector();///

        Vector right = v.crossProduct(up).normalize();

        double d1 = alignZero(Math.sqrt(sizeOfGrid * sizeOfGrid / amountOfRays));
        int distance1 = (int) (sizeOfGrid / d1);
        double r = sizeOfGrid / distance1;

        for (int i = 0; i < distance1; i++) {
            for (int j = 0; j < distance1; j++) {
                double yI = -(i - (distance1 - 1) / 2.0) * r;
                double xJ = (j - (distance1 - 1) / 2.0) * r;

                Point pIJ = gridCenter;
                if (!isZero(xJ))
                    pIJ = pIJ.add(right.scale(xJ));
                if (!isZero(yI))
                    pIJ = pIJ.add(up.scale(yI));

                rayBeam.add(new Ray(p, pIJ.subtract(p), normal));
            }
        }
        return rayBeam;
    }
}
