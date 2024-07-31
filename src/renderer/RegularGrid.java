package renderer;

import geometries.Geometries;
import geometries.Intersectable;
import geometries.Intersectable.GeoPoint;
import geometries.Polygon;
import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;

import static java.lang.Math.abs;
import static java.lang.Math.floor;

public class RegularGrid extends SimpleRayTracer {

    /**
     * Delta value for accuracy
     */
    private static final double DELTA = 0.1;

    class Voxel {
        private Geometries geometries;
        private double[] max = new double[3];
        private double[] min = new double[3];

        private boolean inside(Point value) {
            return value.getX() <= max[0] + DELTA && value.getX() >= min[0] - DELTA
                    && value.getY() <= max[1] + DELTA && value.getY() >= min[1] - DELTA
                    && value.getZ() <= max[2] + DELTA && value.getZ() >= min[2] - DELTA;
        }

        private boolean between(Point maxValue, Point minValue) {
            return overlap(maxValue.getX(), minValue.getX(), max[0], min[0]) &&
                    overlap(maxValue.getY(), minValue.getY(), max[1], min[1]) &&
                    overlap(maxValue.getZ(), minValue.getZ(), max[2], min[2]);
        }

        private boolean overlap(double maxX, double minX, double max, double min) {
            return (maxX > max && minX < min) ||
                    (maxX <= max && maxX >= min) ||
                    (minX <= max && minX >= min);
        }
    }

    private Voxel[][][] cells;

    private final int[] gridMax;
    private final int[] gridMin;

    private int nX;
    private int nY;
    private int nZ;

    private double[] cellSize = new double[3];

    private Geometries gridLimits;

    public RegularGrid(Scene s) {
        super(s);
        s.geometries.setMinMax();

        gridMax = new int[]{
                (int) s.geometries.max.getX(),
                (int) s.geometries.max.getY(),
                (int) s.geometries.max.getZ()
        };

        gridMin = new int[]{
                (int) s.geometries.min.getX(),
                (int) s.geometries.min.getY(),
                (int) s.geometries.min.getZ()
        };

        double lambda = 3; // example value, replace with your actual value
        int n = s.geometries.getSize();

        //grid size
        int dx = gridMax[0] - gridMin[0];
        int dy = gridMax[1] - gridMin[1];
        int dz = gridMax[2] - gridMin[2];

        int v = dx * dy * dz;
        double formula = Math.cbrt((lambda * n) / v);

        //Calculate initial grid resolution
        int nXt = (int) Math.round(dx * formula);
        int nYt = (int) Math.round(dy * formula);
        int nZt = (int) Math.round(dz * formula);

        // Adjust nX, nY, nZ to closest values
        nX = closestDivisor(dx, nXt);
        nY = closestDivisor(dy, nYt);
        nZ = closestDivisor(dz, nZt);

        cellSize[0] = dx / nX;
        cellSize[1] = dy / nY;
        cellSize[2] = dz / nZ;

        //initialize all the voxels max min
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
        for (Intersectable i : s.geometries.getIntersectables()) {
            insert(i);
        }

        buildBox();
    }

    @Override
    public Color traceRay(Ray ray) {
        GeoPoint closestPoint = traversGrid(ray);
        return closestPoint == null ? scene.background
                : calcColor(closestPoint, ray);
    }

    private GeoPoint traversGrid(Ray ray) {
        //if rey intersect the grid
        var intersection = gridLimits.findIntersections(ray);
        if (intersection == null) return null;

        //the first voxel that the ray intersects
        Point p = ray.getHead();
        int[] cellIndex = findVoxel(p);
        if (cellIndex == null) {
            if(intersection.size()==2){
                p = intersection.getFirst().distance(p) <  intersection.get(1).distance(p) ?
                        intersection.getFirst() : intersection.get(1);
            }
            else
                p = intersection.getFirst();
            cellIndex = findVoxel(p);
        }
        Vector v = ray.getDirection();

        //we want to separate the coordinates
        double rX = v.getX();
        double rY = v.getY();
        double rZ = v.getZ();
        double oX = p.getX();
        double oY = p.getY();
        double oZ = p.getZ();

        double[] rayOrigGrid = {
                oX - gridMin[0],
                oY - gridMin[1],
                oZ - gridMin[2],
        };
        double[] deltaT = {
                abs(cellSize[0] / rX),
                abs(cellSize[1] / rY),
                abs(cellSize[2] / rZ),
        };

        double t_x = rX < 0 ? abs((gridMin[0] + (floor(rayOrigGrid[0] / cellSize[0]) + 1) * cellSize[0] - oX) / rX)
                : abs((oX - (gridMin[0] + floor(rayOrigGrid[0] / cellSize[0]) * cellSize[0])) / rX);
        double t_y = rY < 0 ? abs((gridMin[1] + (floor(rayOrigGrid[1] / cellSize[1]) + 1) * cellSize[1] - oY) / rY)
                : abs((oY - (gridMin[1] + floor(rayOrigGrid[1] / cellSize[1]) * cellSize[1])) / rY);
        double t_z = rZ < 0 ? abs((gridMin[2] + (floor(rayOrigGrid[2] / cellSize[2]) + 1) * cellSize[2] - oZ) / rZ)
                : abs((oZ - (gridMin[2] + floor(rayOrigGrid[2] / cellSize[2]) * cellSize[0])) / rZ);

        GeoPoint firstIntersection = null;
        if (cells[cellIndex[0]][cellIndex[1]][cellIndex[2]].geometries != null) {
            GeoPoint closestPoint =
                    findClosestIntersection(ray, cells[cellIndex[0]][cellIndex[1]][cellIndex[2]]);

            if (closestPoint != null) {
                if (cells[cellIndex[0]][cellIndex[1]][cellIndex[2]].inside(closestPoint.point))
                    return closestPoint;
                firstIntersection = closestPoint;
            }
        }

        while (true) {

            if (t_x <= t_y && t_x <= t_z) {
                t_x += deltaT[0];
                if (rX < 0)
                    cellIndex[0]--;
                else
                    cellIndex[0]++;
            } else if (t_y <= t_z) {
                t_y += deltaT[1];
                if (rY < 0)
                    cellIndex[1]--;
                else
                    cellIndex[1]++;
            } else {
                t_z += deltaT[2];
                if (rZ < 0)
                    cellIndex[2]--;
                else
                    cellIndex[2]++;
            }

            if (cellIndex[0] < 0 || cellIndex[1] < 0 || cellIndex[2] < 0 ||
                    cellIndex[0] >= nX || cellIndex[1] >= nY || cellIndex[2] >= nZ)
                return null;

            if (cells[cellIndex[0]][cellIndex[1]][cellIndex[2]].geometries != null) {
                if (firstIntersection != null && cells[cellIndex[0]][cellIndex[1]][cellIndex[2]].inside(firstIntersection.point)) {
                    GeoPoint closestPoint =
                            findClosestIntersection(ray, cells[cellIndex[0]][cellIndex[1]][cellIndex[2]]);

                    if (closestPoint != null) {
                        if (cells[cellIndex[0]][cellIndex[1]][cellIndex[2]].inside(closestPoint.point)) {
                            if (ray.getHead().distance(closestPoint.point) <= ray.getHead().distance(firstIntersection.point))
                                return closestPoint;
                        }
                    }
                    return firstIntersection;

                } else {
                    GeoPoint closestPoint =
                            findClosestIntersection(ray, cells[cellIndex[0]][cellIndex[1]][cellIndex[2]]);
                    if (closestPoint != null) {
                        if (cells[cellIndex[0]][cellIndex[1]][cellIndex[2]].inside(closestPoint.point)) {
                            return closestPoint;
                        } else {
                            if (firstIntersection == null ||
                                    ray.getHead().distance(closestPoint.point) <= ray.getHead().distance(firstIntersection.point))
                                firstIntersection = closestPoint;
                        }
                    }
                }
            }
        }
    }

    private GeoPoint findClosestIntersection(Ray ray, Voxel cell) {
        var gp = cell.geometries.findGeoIntersections(ray);
        return ray.findClosestGeoPoint(gp);
    }

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

    private int[] findVoxel(Point point) {
        for (int i = 0; i < nX; i++) {
            for (int j = 0; j < nY; j++) {
                for (int k = 0; k < nZ; k++) {
                    if (cells[i][j][k].inside(point)) {
                        return new int[]{i, j, k};
                    }
                }
            }
        }
        return null;
    }

    // Function to find the closest integer that divides the size without a remainder
    private int closestDivisor(int size, int estimate) {
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
}
