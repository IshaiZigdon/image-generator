package renderer;

import geometries.Geometries;
import geometries.Geometry;
import geometries.Intersectable;
import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;

import static java.lang.Math.floor;

public class RegularGrid extends SimpleRayTracer {

    class Voxel {
        private Geometries geometries;
        private double[] max;
        private double[] min;
    }

    private Voxel[][][] cells;

    private final double[] gridMax = {-Double.POSITIVE_INFINITY, -Double.POSITIVE_INFINITY, -Double.POSITIVE_INFINITY};
    private final double[] gridMin = {Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY};

    private double nX;
    private double nY;
    private double nZ;

    private double[] cellSize;

    private RegularGrid(Scene s) {
        super(s);

        for (Intersectable i : s.geometries.getIntersectables()) {
            if (((Geometry) i).max[0] > gridMax[0])
                gridMax[0] = ((Geometry) i).max[0];
            else if (((Geometry) i).min[0] < gridMin[0])
                gridMin[0] = ((Geometry) i).min[0];

            if (((Geometry) i).max[1] > gridMax[1])
                gridMax[1] = ((Geometry) i).max[1];
            else if (((Geometry) i).min[1] < gridMin[1])
                gridMin[1] = ((Geometry) i).min[1];

            if (((Geometry) i).max[2] > gridMax[2])
                gridMax[2] = ((Geometry) i).max[2];
            else if (((Geometry) i).min[2] < gridMin[2])
                gridMin[2] = ((Geometry) i).min[2];
        }

        double lambda = 3; // example value, replace with your actual value
        int N = s.geometries.getSize(); // replace with size of geometries in the scene

        double dx = gridMax[0] - gridMin[0];
        double dy = gridMax[1] - gridMin[1];
        double dz = gridMax[2] - gridMin[2];

        double V = dx * dy * dz;

        nX = dx * Math.cbrt((lambda * N) / V);
        nY = dy * Math.cbrt((lambda * N) / V);
        nZ = dz * Math.cbrt((lambda * N) / V);

        cellSize[0] = dx / nX;
        cellSize[1] = dy / nY;
        cellSize[2] = dz / nZ;

        for (int i = 0; i <= nX; i++) {
            for (int j = 0; j <= nY; j++) {
                for (int k = 0; k <= nZ; k++) {
                    cells[i][j][k].min[0] = gridMin[0] + i * cellSize[0] + j * cellSize[1] + k * cellSize[2];
                    cells[i][j][k].min[1] = gridMin[1] + i * cellSize[0] + j * cellSize[1] + k * cellSize[2];
                    cells[i][j][k].min[2] = gridMin[2] + i * cellSize[0] + j * cellSize[1] + k * cellSize[2];

                    cells[i][j][k].max[0] = cells[i][j][k].min[0] + cellSize[0];
                    cells[i][j][k].max[1] = cells[i][j][k].min[1] + cellSize[1];
                    cells[i][j][k].max[2] = cells[i][j][k].min[2] + cellSize[2];
                }
            }
        }

        //todo: insert geometries to voxels
        for (Intersectable i : s.geometries.getIntersectables()) {
            insert(i);
        }
    }

    @Override
    public Color traceRay(Ray ray) {
        Vector v = ray.getDirection();
        Point p = ray.getHead();
        Vector o = p.subtract(Point.ZERO);
        double rX = v.dotProduct(Vector.X);
        double rY = v.dotProduct(Vector.Y);
        double rZ = v.dotProduct(Vector.Z);
        double oX = o.dotProduct(Vector.X);
        double oY = o.dotProduct(Vector.Y);
        double oZ = o.dotProduct(Vector.Z);

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

        double tNextCrossing = 0;
        //todo: the first voxel that the ray intersects
        int[] cellIndex = {0, 0, 0};

        while (true) {
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
            if (cells[cellIndex[0]][cellIndex[1]][cellIndex[2]].geometries != null) {
                Intersectable.GeoPoint closestPoint =
                        findClosestIntersection(ray, cells[cellIndex[0]][cellIndex[1]][cellIndex[2]]);
                if (closestPoint != null)  //todo : && closestPoint.point.distance())
                    return calcColor(closestPoint, ray);
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

    public void insert(Intersectable shape) {
        for (int i = 0; i <= nX; i++) {
            for (int j = 0; j <= nY; j++) {
                for (int k = 0; k <= nZ; k++) {
                    double start = cells[i][j][k].min[0];
                    double end = cells[i][j][k].max[0];
                    if (inside(((Geometry) shape).max[0], ((Geometry) shape).min[0], start, end)
                            && inside(((Geometry) shape).max[1], ((Geometry) shape).min[1], start, end)
                            && inside(((Geometry) shape).max[2], ((Geometry) shape).min[2], start, end))
                        cells[i][j][k].geometries.add(shape);
                }
            }
        }
    }

    private boolean inside(double value1, double value2, double start, double end) {
        return value1 <= end && value1 >= start || value2 <= end && value2 >= start;
    }
}
