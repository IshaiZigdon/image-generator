package primitives;


import java.util.LinkedList;
import java.util.List;
import java.util.Random;

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
    private double amountOfRays = 9;

    private static final Random RANDOM = new Random();

    /**
     * constructor with grid size and amount of rays
     *
     * @param sizeOfGrid the size of the grid
     */
    public BlackBoard(double sizeOfGrid) {
        this.amountOfRays = sizeOfGrid;
    }

    /**
     * empty constructor
     */
    public BlackBoard() {
    }

    /**
     * set function for the size of the grid
     *
     * @param amountOfRays the size of the grid
     * @return this object
     */
    public BlackBoard setAmountOfRays(double amountOfRays) {
        this.amountOfRays = amountOfRays;
        return this;
    }

    /**
     * returns beam of rays from a given point to a given target in the given direction
     *
     * @param p        the given point
     * @param distance the given distance of the black board
     * @param v        the given direction
     * @return list of rays
     */
    public List<Vector> beamOfRays(Point p, double distance, double size, Vector v) {
        if (isZero(size)) return List.of(v.scale(-1));

        Vector right = calculateVerticalVector(v);
        Vector up = v.crossProduct(right).normalize();

        List<Vector> rayBeam = new LinkedList<>();
        Point gridCenter = p.add(v.scale(-distance));

        double cellSize = (2 * size) / amountOfRays;
        Point topLeft = gridCenter.add(right.scale(-size).add(up.scale(size)));

        for (int i = 0; i < amountOfRays; i++) {
            for (int j = 0; j < amountOfRays; j++) {
                // Calculate the position within the grid
                double x = (i + RANDOM.nextDouble()) * cellSize;
                double y = -(j + RANDOM.nextDouble()) * cellSize;

                Point pIJ = topLeft;
                if (!isZero(x)) pIJ = pIJ.add(right.scale(x));
                if (!isZero(y)) pIJ = pIJ.add(up.scale(y));

                // Only add the ray if it is within the radius
                if (gridCenter.distance(pIJ) <= size) {
                    rayBeam.add(pIJ.subtract(p).normalize());
                }
            }
        }
        return rayBeam;
    }

    private static Vector calculateVerticalVector(Vector n) {
        try {
            return n.crossProduct(Vector.X).normalize();
        } catch (IllegalArgumentException e) {
            return n.crossProduct(Vector.Y).normalize();
        }
    }
}
