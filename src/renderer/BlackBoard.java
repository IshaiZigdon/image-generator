package renderer;


import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
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
    private int amountOfRays = 9;

    private static final Random RANDOM = new Random();

    /**
     * constructor with grid size and amount of rays
     *
     * @param sizeOfGrid the size of the grid
     */
    public BlackBoard(int sizeOfGrid) {
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
    public BlackBoard setAmountOfRays(int amountOfRays) {
        this.amountOfRays = amountOfRays;
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
    public List<Point> beamOfRays(Vector v, Point center, double size) {
        if (isZero(size)) return List.of(center);

        Vector right = calculateVerticalVector(v);
        Vector up = v.crossProduct(right).normalize();

        List<Ray> rayBeam = new LinkedList<>();

        double cellSize = (2 * size) / amountOfRays;
        Point topLeft = center.add(right.scale(-size).add(up.scale(size)));
        List<Point> points = new ArrayList<>(amountOfRays * amountOfRays);
        for (int i = 0; i < amountOfRays; i++) {
            for (int j = 0; j < amountOfRays; j++) {
                // Calculate the position within the grid
                double x = (i + RANDOM.nextDouble()) * cellSize;
                double y = -(j + RANDOM.nextDouble()) * cellSize;


                // Only add the ray if it is within the radius
                points.add(topLeft
                        .add(right.scale(x))
                        .add(up.scale(y))
                );
            }
        }
        return points;
    }

    private static Vector calculateVerticalVector(Vector n) {
        try {
            return n.crossProduct(Vector.X).normalize();
        } catch (IllegalArgumentException e) {
            return n.crossProduct(Vector.Y).normalize();
        }
    }
}
