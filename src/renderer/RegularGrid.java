package renderer;

import geometries.Geometries;
import geometries.Intersectable;
import geometries.Intersectable.GeoPoint;
import geometries.Polygon;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.floor;
import static primitives.Util.isZero;

/**
 * class for ray tracing using regular grid
 *
 * @author Ishai zigdon
 * @author Zaki zafrani
 */
public class RegularGrid extends SimpleRayTracer {

    /**
     * Delta value for accuracy
     */
    private static final double DELTA = 0.1;

    /**
     * the maximum values of the grid
     */
    private int[] gridMax;
    /**
     * the minimum values of the grid
     */
    private int[] gridMin;

    /**
     * the grid faces
     */
    private Geometries gridLimits;

    /**
     * the size of the grid cells
     */
    private final int[] cellSize = new int[3];

    /**
     * the resolution of the grid on the x-axis
     */
    private int nX;
    /**
     * the resolution of the grid on the y-axis
     */
    private int nY;
    /**
     * the resolution of the grid on the z-axis
     */
    private int nZ;

    /**
     * the voxels of the grid
     */
    private Voxel[][][] cells;

    /**
     * class for representing a voxel
     */
    static class Voxel {
        /**
         * the geometries within the voxel
         */
        private Geometries geometries;
        /**
         * the maximum values of the voxel
         */
        private final double[] max = new double[3];
        /**
         * the minimum values of the voxel
         */
        private final double[] min = new double[3];

        /**
         * checking if a given point is within the voxel
         *
         * @param point the given point
         * @return true if it is, false if not
         */
        private boolean inside(Point point) {
            return point.getX() <= max[0] + DELTA && point.getX() >= min[0] - DELTA
                    && point.getY() <= max[1] + DELTA && point.getY() >= min[1] - DELTA
                    && point.getZ() <= max[2] + DELTA && point.getZ() >= min[2] - DELTA;
        }

        /**
         * checking if a box is between or overlapping the voxel
         *
         * @param maxValue the max values of the given box
         * @param minValue the min values of the given box
         * @return true if it is, false if not
         */
        private boolean between(Point maxValue, Point minValue) {
            return overlap(maxValue.getX(), minValue.getX(), max[0], min[0]) &&
                    overlap(maxValue.getY(), minValue.getY(), max[1], min[1]) &&
                    overlap(maxValue.getZ(), minValue.getZ(), max[2], min[2]);
        }

        /**
         * aid function for between. checking one dimension at a time
         *
         * @param maxX the max value of the given dimension
         * @param minX the min value of the given dimension
         * @param max  the max value of the voxel in this dimension
         * @param min  the min value of the voxel in this dimension
         * @return true if its overlapping, false if not
         */
        private boolean overlap(double maxX, double minX, double max, double min) {
            return (maxX > max && minX < min) ||
                    (maxX <= max && maxX >= min) ||
                    (minX <= max && minX >= min);
        }
    }

    /**
     * ctor with given scene
     *
     * @param s the given scene
     */
    public RegularGrid(Scene s) {
        super(s);
        initiateGrid();
    }

    /**
     * ctor with given scene and given blackboard
     *
     * @param s          the given scene
     * @param blackBoard the given blackBoard
     */
    public RegularGrid(Scene s, BlackBoard blackBoard) {
        super(s, blackBoard);
        initiateGrid();
    }

    /**
     * initiating the grid values
     */
    private void initiateGrid() {
        //set the min max of the geometries in the scene
        scene.geometries.setMinMax();

        //checking if the scene contain infinite shapes
        if (scene.geometries.max == null || scene.geometries.min == null)
            throw new IllegalArgumentException("cannot use regular grid on infinite shapes");

        gridMax = new int[]{
                customRound(scene.geometries.max.getX()),
                customRound(scene.geometries.max.getY()),
                customRound(scene.geometries.max.getZ())
        };

        gridMin = new int[]{
                customRound(scene.geometries.min.getX()),
                customRound(scene.geometries.min.getY()),
                customRound(scene.geometries.min.getZ())
        };

        double lambda = 4; // adjusted variable
        int n = scene.geometries.getIntersectables().size();

        //grid size
        int dx = gridMax[0] - gridMin[0];
        int dy = gridMax[1] - gridMin[1];
        int dz = gridMax[2] - gridMin[2];

        //formula for finding the best resolution
        //given the size of the grid and the amount of shapes
        long v = (long) dx * dy * dz;
        double formula = Math.cbrt((lambda * n) / v);

        //Calculate initial grid resolution
        int nXt = (int) Math.round(dx * formula);
        int nYt = (int) Math.round(dy * formula);
        int nZt = (int) Math.round(dz * formula);

        // Adjust nX, nY, nZ to closest value that dividable by dx, dy, dz
        nX = closestDivisor(dx, nXt);
        nY = closestDivisor(dy, nYt);
        nZ = closestDivisor(dz, nZt);

        cellSize[0] = dx / nX;
        cellSize[1] = dy / nY;
        cellSize[2] = dz / nZ;

        //initialize all the voxels max min values
        cells = new Voxel[nX][nY][nZ];
        for (int i = 0; i < nX; i++) {
            for (int j = 0; j < nY; j++) {
                for (int k = 0; k < nZ; k++) {
                    cells[i][j][k] = new Voxel();
                    cells[i][j][k].min[0] = gridMin[0] + i * cellSize[0];
                    cells[i][j][k].min[1] = gridMin[1] + j * cellSize[1];
                    cells[i][j][k].min[2] = gridMin[2] + k * cellSize[2];

                    cells[i][j][k].max[0] = cells[i][j][k].min[0] + cellSize[0];
                    cells[i][j][k].max[1] = cells[i][j][k].min[1] + cellSize[1];
                    cells[i][j][k].max[2] = cells[i][j][k].min[2] + cellSize[2];
                }
            }
        }

        //insert geometries to voxels
        for (Intersectable i : scene.geometries.getIntersectables()) {
            insert(i);
        }

        //build the faces of the grid
        buildBox();
    }

    /**
     * finding the largest closest value for positive number
     * and lowest closest value for negative number
     *
     * @param value the given value
     * @return the result
     */
    private static int customRound(double value) {
        return value >= 0 ? (int) Math.ceil(value) : (int) Math.floor(value);
    }

    /**
     * Function to find the closest integer that divides the size without a remainder
     *
     * @param size     the divider
     * @param estimate the given value
     * @return the closest value to estimate that is divisor of size
     */
    private static int closestDivisor(int size, int estimate) {
        if (estimate == 0) return 1;

        int lower = estimate;
        int upper = estimate;

        while (lower > 0 && size % lower != 0) {
            lower--;
        }

        while (size % upper != 0) {
            upper++;
        }

        if (lower == 0) return upper;
        return (estimate - lower <= upper - estimate) ? lower : upper;
    }

    /**
     * inserting the shape into the voxels
     *
     * @param shape the given shape
     */
    private void insert(Intersectable shape) {
        for (int i = 0; i < nX; i++) {
            for (int j = 0; j < nY; j++) {
                for (int k = 0; k < nZ; k++) {
                    if (cells[i][j][k].between(shape.max, shape.min)) {
                        if (cells[i][j][k].geometries == null) cells[i][j][k].geometries = new Geometries();
                        cells[i][j][k].geometries.add(shape);
                    }
                }
            }
        }
    }

    /**
     * building the grid faces using the grid min and max values
     */
    private void buildBox() {
        Point minminmin = new Point(gridMin[0], gridMin[1], gridMin[2]);
        Point minminmax = new Point(gridMin[0], gridMin[1], gridMax[2]);
        Point minmaxmin = new Point(gridMin[0], gridMax[1], gridMin[2]);
        Point minmaxmax = new Point(gridMin[0], gridMax[1], gridMax[2]);
        Point maxminmin = new Point(gridMax[0], gridMin[1], gridMin[2]);
        Point maxminmax = new Point(gridMax[0], gridMin[1], gridMax[2]);
        Point maxmaxmin = new Point(gridMax[0], gridMax[1], gridMin[2]);
        Point maxmaxmax = new Point(gridMax[0], gridMax[1], gridMax[2]);
        gridLimits = new Geometries();
        gridLimits.add(
                new Polygon(minminmin, minmaxmin, maxmaxmin, maxminmin),
                new Polygon(minminmin, minmaxmin, minmaxmax, minminmax),
                new Polygon(maxminmin, maxmaxmin, maxmaxmax, maxminmax),
                new Polygon(minmaxmax, maxmaxmax, maxminmax, minminmax),
                new Polygon(minmaxmax, maxmaxmax, maxmaxmin, minmaxmin),
                new Polygon(minminmin, minminmax, maxminmax, maxminmin)
        );
    }

    /**
     * finding the closest intersection with the geometries in the given voxel
     *
     * @param ray  the given ray
     * @param cell the given voxel
     * @return the closest intersection or null if there is no intersections
     */
    private GeoPoint findClosestIntersection(Ray ray, Voxel cell) {
        var gp = cell.geometries.findGeoIntersections(ray);
        return ray.findClosestGeoPoint(gp);
    }

    /**
     * traversing the grid with a given ray
     *
     * @param ray the given ray
     * @return the closest intersection point
     */
    private GeoPoint traversGrid(Ray ray) {
        //checking if the ray intersect the grid
        var intersection = gridLimits.findIntersections(ray);
        if (intersection == null) return null;

        //finding the first voxel that the ray intersects
        Point p = beginningPoint(ray.getHead(), intersection);
        int[] cellIndex = findVoxel(p);

        Vector v = ray.getDirection();

        //we want to separate the coordinates
        double[] r = {
                v.getX(),
                v.getY(),
                v.getZ()
        };
        double[] rayOrigGrid = {
                p.getX() - gridMin[0],
                p.getY() - gridMin[1],
                p.getZ() - gridMin[2]
        };
        double[] deltaT = {
                abs(cellSize[0] / r[0]),
                abs(cellSize[1] / r[1]),
                abs(cellSize[2] / r[2])
        };
        double[] t = {
                calculateT(rayOrigGrid[0], r[0], cellSize[0]),
                calculateT(rayOrigGrid[1], r[1], cellSize[1]),
                calculateT(rayOrigGrid[2], r[2], cellSize[2])
        };

        GeoPoint firstIntersection = null;
        //checking intersection with the voxel's geometries
        if (cells[cellIndex[0]][cellIndex[1]][cellIndex[2]].geometries != null) {
            GeoPoint closestPoint =
                    findClosestIntersection(ray, cells[cellIndex[0]][cellIndex[1]][cellIndex[2]]);

            if (closestPoint != null) {
                //checking if the point is inside the voxel
                if (cells[cellIndex[0]][cellIndex[1]][cellIndex[2]].inside(closestPoint.point))
                    return closestPoint;
                //saving the point
                firstIntersection = closestPoint;
            }
        }

        while (true) {
            //moving to the next voxel
            cellIndex = nextCell(t, deltaT, r, cellIndex);
            if (cellIndex == null)
                //it is the end of the grid
                return null;

            //checking intersection with the voxel's geometries
            if (cells[cellIndex[0]][cellIndex[1]][cellIndex[2]].geometries != null) {
                //we have a save point and its within the voxel
                if (firstIntersection != null && cells[cellIndex[0]][cellIndex[1]][cellIndex[2]].inside(firstIntersection.point)) {
                    GeoPoint closestPoint =
                            findClosestIntersection(ray, cells[cellIndex[0]][cellIndex[1]][cellIndex[2]]);

                    if (closestPoint != null) {
                        //checking if the point is inside the voxel
                        if (cells[cellIndex[0]][cellIndex[1]][cellIndex[2]].inside(closestPoint.point)) {
                            //if it is not closest to the ray head that the saved point, it is not the right point
                            if (ray.getHead().distance(closestPoint.point) <= ray.getHead().distance(firstIntersection.point))
                                return closestPoint;
                        }
                    }
                    return firstIntersection;

                } else {
                    GeoPoint closestPoint =
                            findClosestIntersection(ray, cells[cellIndex[0]][cellIndex[1]][cellIndex[2]]);
                    if (closestPoint != null) {
                        //checking if the point is inside the voxel
                        if (cells[cellIndex[0]][cellIndex[1]][cellIndex[2]].inside(closestPoint.point)) {
                            return closestPoint;
                        } else {
                            //if we don't have saved point or the new point is closer, we save the new point
                            if (firstIntersection == null ||
                                    ray.getHead().distance(closestPoint.point) <= ray.getHead().distance(firstIntersection.point))
                                firstIntersection = closestPoint;
                        }
                    }
                }
            }
        }
    }

    /**
     * finding the beginning point of the traversing
     * @param p the ray head
     * @param intersection the intersection with the grid
     * @return p if the ray head is inside, otherwise the first intersection with the grid
     */
    private Point beginningPoint(Point p, List<Point> intersection) {
        //checking if the point is inside the grid
        if (p.getX() >= gridMin[0] && p.getX() <= gridMax[0]
                && p.getY() >= gridMin[1] && p.getY() <= gridMax[1]
                && p.getZ() >= gridMin[2] && p.getZ() <= gridMax[2])
            return fixPoint(p);

        //finding the intersection point that is closest to the ray head
        if (intersection.size() == 1)
            return fixPoint(intersection.getFirst());

        //finding the intersection point that is closest to the ray head
        return intersection.getFirst().distance(p) < intersection.get(1).distance(p) ?
                fixPoint(intersection.getFirst()) : fixPoint(intersection.get(1));
    }

    /**
     * calculating the t value for given dimension
     *
     * @param oGrid    the point on te grid in relation of the grid min
     * @param r        the direction of the ray in the given dimension
     * @param cellSize the size of the cell in the given dimension
     * @return the result
     */
    private double calculateT(double oGrid, double r, int cellSize) {
        return r < 0 ? (floor(oGrid / cellSize) * cellSize - oGrid) / r
                : ((floor(oGrid / cellSize) + 1) * cellSize - oGrid) / r;
    }

    /**
     * calculating the next voxel the ray passes through
     *
     * @param t         the t values
     * @param deltaT    the delta values
     * @param r         the directions of the ray
     * @param cellIndex the current cell
     * @return the next cell if it is inside the grid, null otherwise
     */
    private int[] nextCell(double[] t, double[] deltaT, double[] r, int[] cellIndex) {
        if (t[0] <= t[1] && t[0] <= t[2]) {
            t[0] += deltaT[0];
            if (r[0] < 0)
                cellIndex[0]--;
            else
                cellIndex[0]++;
        } else if (t[1] <= t[2]) {
            t[1] += deltaT[1];
            if (r[1] < 0)
                cellIndex[1]--;
            else
                cellIndex[1]++;
        } else {
            t[2] += deltaT[2];
            if (r[2] < 0)
                cellIndex[2]--;
            else
                cellIndex[2]++;
        }

        //checking if the next cell is outside the grid
        if (cellIndex[0] < 0 || cellIndex[1] < 0 || cellIndex[2] < 0 ||
                cellIndex[0] >= nX || cellIndex[1] >= nY || cellIndex[2] >= nZ)
            return null;

        return cellIndex;
    }

    /**
     * finding all the geometries that are in the voxel in the path of the given ray
     *
     * @param ray the given ray
     * @return the geometries in the path
     */
    private Geometries geometriesInPath(Ray ray) {
        //if rey intersect the grid
        var intersection = gridLimits.findIntersections(ray);
        if (intersection == null) return null;

        //the first voxel that the ray intersects
        Point p = beginningPoint(ray.getHead(),intersection);
        int[] cellIndex = findVoxel(p);

        Vector v = ray.getDirection();

        //we want to separate the coordinates
        double[] r = {
                v.getX(),
                v.getY(),
                v.getZ()
        };
        double[] rayOrigGrid = {
                p.getX() - gridMin[0],
                p.getY() - gridMin[1],
                p.getZ() - gridMin[2]
        };
        double[] deltaT = {
                abs(cellSize[0] / r[0]),
                abs(cellSize[1] / r[1]),
                abs(cellSize[2] / r[2])
        };
        double[] t = {
                calculateT(rayOrigGrid[0], r[0], cellSize[0]),
                calculateT(rayOrigGrid[1], r[1], cellSize[1]),
                calculateT(rayOrigGrid[2], r[2], cellSize[2])
        };

        Geometries geometries = new Geometries();

        while (true) {
            //adding the geometries of the voxel
            if (cells[cellIndex[0]][cellIndex[1]][cellIndex[2]].geometries != null) {
                for (Intersectable i : cells[cellIndex[0]][cellIndex[1]][cellIndex[2]].geometries.getIntersectables())
                    geometries.add(i);
            }

            cellIndex = nextCell(t, deltaT, r, cellIndex);
            if (cellIndex == null)
                return geometries;
        }
    }

    /**
     * finding the voxel that contain the given point
     *
     * @param point the given point
     * @return the coordinates of the voxel
     */
    private int[] findVoxel(Point point) {
        int i = (int) ((point.getX() - gridMin[0]) / cellSize[0]);
        int j = (int) ((point.getY() - gridMin[1]) / cellSize[1]);
        int k = (int) ((point.getZ() - gridMin[2]) / cellSize[2]);

        return new int[]{i, j, k};
    }

    /**
     * moving the given point slightly inside the grid if it is on the edge
     *
     * @param p the given point
     * @return the fixed point
     */
    private Point fixPoint(Point p) {
        if (isZero((p.getX() - gridMin[0])))
            p = p.add(Vector.X.scale(DELTA));

        if (isZero((p.getX() - gridMax[0])))
            p = p.add(Vector.X.scale(-DELTA));

        if (isZero((p.getY() - gridMin[1])))
            p = p.add(Vector.Y.scale(DELTA));

        if (isZero((p.getY() - gridMax[1])))
            p = p.add(Vector.Y.scale(-DELTA));

        if (isZero((p.getZ() - gridMin[2])))
            p = p.add(Vector.Z.scale(DELTA));

        if (isZero((p.getZ() - gridMax[2])))
            p = p.add(Vector.Z.scale(-DELTA));

        return p;
    }

    @Override
    public Color traceRay(Ray ray) {
        GeoPoint closestPoint = traversGrid(ray);
        return closestPoint == null ? scene.background
                : calcColor(closestPoint, ray);
    }

    @Override
    protected Color calcGlobalEffect(Ray ray, Double3 kx, int level, Double3 k) {
        Double3 kkx = kx.product(k);
        if (kkx.lowerThan(MIN_CALC_COLOR_K)) return Color.BLACK;
        GeoPoint gp = traversGrid(ray);
        return gp == null ? scene.background
                : calcColor(gp, ray, level - 1, kkx).scale(kx);
    }

    @Override
    protected Double3 transparency(GeoPoint gp, LightSource light, Vector l, Vector n) {
        Ray ray = new Ray(gp.point, l.scale(-1), n);
        Geometries geometries = geometriesInPath(ray);
        if (geometries == null) return Double3.ONE;
        var intersections = geometries.findGeoIntersections(ray, light.getDistance(gp.point));
        if (intersections == null) return Double3.ONE;

        Double3 ktr = Double3.ONE;
        for (var intersection : intersections)
            ktr = ktr.product(intersection.geometry.getMaterial().kT);
        return ktr;
    }
}
