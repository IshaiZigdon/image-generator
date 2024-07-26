package renderer;

import geometries.Geometries;
import geometries.Geometry;
import geometries.Intersectable;
import geometries.Polygon;
import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;

import static java.lang.Math.floor;

public class RegularGrid extends SimpleRayTracer {

    class Voxel {
        private Geometries geometries;
        private double[] max = new double[3];
        private double[] min = new double[3];

        private boolean inside(double[] value) {
            return value[0] <= max[0] && value[0] >= min[0]
                    && value[1] <= max[1] && value[1] >= min[1]
                    && value[2] <= max[2] && value[2] >= min[2];
        }

        private boolean between(double[] maxValue, double[] minValue) {
            return ((maxValue[0] > max[0] && minValue[0] < min[0]) || (maxValue[0] <= max[0] && maxValue[0] >= min[0])
                    || (minValue[0] <= max[0] && minValue[0] >= min[0])) &&
                    ((maxValue[1] > max[1] && minValue[1] < min[1]) || (maxValue[1] <= max[1] && maxValue[1] >= min[1])
                            || (minValue[1] <= max[1] && minValue[1] >= min[1])) &&
                    ((maxValue[2] > max[2] && minValue[2] < min[2]) || (maxValue[2] <= max[2] && maxValue[2] >= min[2])
                            || (minValue[2] <= max[2] && minValue[2] >= min[2]));
        }
    }

    private Voxel[][][] cells;

    private final double[] gridMax = {Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY};
    private final double[] gridMin = {Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY};

    private int nX;
    private int nY;
    private int nZ;

    private double[] cellSize = new double[3];

    private Geometries gridLimits;

    public RegularGrid(Scene s) {
        super(s);

        //max min of grid
        for (Intersectable i : s.geometries.getIntersectables()) {
            if (((Geometry) i).max[0] > gridMax[0])
                gridMax[0] = ((Geometry) i).max[0];
            if (((Geometry) i).min[0] < gridMin[0])
                gridMin[0] = ((Geometry) i).min[0];

            if (((Geometry) i).max[1] > gridMax[1])
                gridMax[1] = ((Geometry) i).max[1];
            if (((Geometry) i).min[1] < gridMin[1])
                gridMin[1] = ((Geometry) i).min[1];

            if (((Geometry) i).max[2] > gridMax[2])
                gridMax[2] = ((Geometry) i).max[2];
            if (((Geometry) i).min[2] < gridMin[2])
                gridMin[2] = ((Geometry) i).min[2];
        }

        double lambda = 3; // example value, replace with your actual value
        int n = s.geometries.getSize();

        //grid size
        double dx = gridMax[0] - gridMin[0];
        double dy = gridMax[1] - gridMin[1];
        double dz = gridMax[2] - gridMin[2];

        double v = dx * dy * dz;

        //grid resolution
        nX = (int) (dx * Math.cbrt((lambda * n) / v));
        nY = (int) (dy * Math.cbrt((lambda * n) / v));
        nZ = (int) (dz * Math.cbrt((lambda * n) / v));

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
        //if rey intersect the grid
        var intersection = gridLimits.findIntersections(ray);
        if (intersection == null) return scene.background;

        //todo: problem
        //if intersection.size() == 2 it means the ray is outside the grid,else ray head is inside
        Point p = intersection.size() == 2 ? intersection.getFirst() : ray.getHead();
        Vector v = ray.getDirection();
        Vector o = p.subtract(Point.ZERO);

        //we want to separate the coordinates
        double rX = v.dotProduct(Vector.X);
        double rY = v.dotProduct(Vector.Y);
        double rZ = v.dotProduct(Vector.Z);
        double oX = o.dotProduct(Vector.X);
        double oY = o.dotProduct(Vector.Y);
        double oZ = o.dotProduct(Vector.Z);

        double tNextCrossing = 0;
        //the first voxel that the ray intersects
        int[] cellIndex = findVoxel(oX, oY, oZ);
        if (cellIndex == null) return scene.background;


        double[] rayOrigGrid = {
                oX - gridMin[0],
                oY - gridMin[1],
                oZ - gridMin[2],
        };
        double[] deltaT = {
                cellSize[0] / (rX < 0 ? -rX : rX),
                cellSize[1] / (rY < 0 ? -rY : rY),
                cellSize[2] / (rZ < 0 ? -rZ : rZ)
        };

        double t_x = ((floor(rayOrigGrid[0] / cellSize[0]) + rX < 0 ? 0 : 1)
                * cellSize[0] - rayOrigGrid[0]) / rX;
        double t_y = ((floor(rayOrigGrid[1] / cellSize[1]) + rY < 0 ? 0 : 1)
                * cellSize[1] - rayOrigGrid[1]) / rY;
        double t_z = ((floor(rayOrigGrid[2] / cellSize[2]) + rZ < 0 ? 0 : 1)
                * cellSize[2] - rayOrigGrid[2]) / rZ;

        while (true) {
            if (cells[cellIndex[0]][cellIndex[1]][cellIndex[2]].geometries != null) {
                Intersectable.GeoPoint closestPoint =
                        findClosestIntersection(ray, cells[cellIndex[0]][cellIndex[1]][cellIndex[2]]);

                if (closestPoint != null) {
                    //todo
                    Vector nextCrossing = closestPoint.point.subtract(Point.ZERO);
                    double cX = nextCrossing.dotProduct(Vector.X);
                    double cY = nextCrossing.dotProduct(Vector.Y);
                    double cZ = nextCrossing.dotProduct(Vector.Z);
                    if (cells[cellIndex[0]][cellIndex[1]][cellIndex[2]].inside(new double[]{cX, cY, cZ}))
                        return calcColor(closestPoint, ray);
                }
            }

            if (t_x <= t_y && t_x <= t_z) {
                tNextCrossing = t_x;
                t_x += deltaT[0];
                if (rX < 0)
                    cellIndex[0]--;
                else
                    cellIndex[0]++;
            } else if (t_y <= t_z) {
                tNextCrossing = t_y;
                t_y += deltaT[1];
                if (rY < 0)
                    cellIndex[1]--;
                else
                    cellIndex[1]++;
            } else {
                tNextCrossing = t_z;
                t_z += deltaT[2];
                if (rZ < 0)
                    cellIndex[2]--;
                else
                    cellIndex[2]++;
            }

            if (cellIndex[0] < 0 || cellIndex[1] < 0 || cellIndex[2] < 0
                    || cellIndex[0] > nX - 1 || cellIndex[1] > nY - 1 || cellIndex[2] > nZ - 1)
                return scene.background;
        }
    }

    private Intersectable.GeoPoint findClosestIntersection(Ray ray, Voxel cell) {
        var gp = cell.geometries.findGeoIntersections(ray);
        return ray.findClosestGeoPoint(gp);
    }

    private void insert(Intersectable shape) {
        for (int i = 0; i < nX; i++) {
            for (int j = 0; j < nY; j++) {
                for (int k = 0; k < nZ; k++) {
                    if (cells[i][j][k].between(((Geometry) shape).max, ((Geometry) shape).min)) {
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

    private int[] findVoxel(double x, double y, double z) {
        double[] point = {x, y, z};
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
}
