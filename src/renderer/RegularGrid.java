package renderer;

import geometries.*;
import primitives.*;
import scene.Scene;

import java.util.List;

import static java.lang.Math.floor;

public class RegularGrid extends SimpleRayTracer {

    class Cell{
        private Geometries geometries;
    }

    private final double[] gridMax;
    private final double[] gridMin;

    private Cell[][][] cells;

    private double nX;
    private double nY;
    private double nZ;


    private RegularGrid(Scene s) {


        super(s);
    }

    @Override
    public Color traceRay(Ray ray) {
        Vector v = ray.getDirection();
        Point p = ray.getHead();
        //todo: o should be the point of the first intersection with the grid
        Vector o = p.subtract(Point.ZERO);
        double rX = v.dotProduct(Vector.X);
        double rY = v.dotProduct(Vector.Y);
        double rZ = v.dotProduct(Vector.Z);
        double oX = o.dotProduct(Vector.X);
        double oY = o.dotProduct(Vector.Y);
        double oZ = o.dotProduct(Vector.Z);
        double[] cellDimension = {
                (gridMax[0] - gridMin[0]) / nX,
                (gridMax[1] - gridMin[1]) / nY,
                (gridMax[2] - gridMin[2]) / nZ
        };
        double[] rayOrigGrid = {
                oX - gridMin[0],
                oY - gridMin[1],
                oZ - gridMin[2],
        };
        double[] deltaT = {
                nX / (rX < 0 ? -rX : rX),
                nY / (rY < 0 ? -rY : rY),
                nZ / (rZ < 0 ? -rZ : rZ)
        };
        double t_x = ((floor(rayOrigGrid[0] / cellDimension[0]) + rX < 0 ? 0 : 1)
                * cellDimension[0] - rayOrigGrid[0]) / rX;
        double t_y = ((floor(rayOrigGrid[1] / cellDimension[1]) + rY < 0 ? 0 : 1)
                * cellDimension[1] - rayOrigGrid[1]) / rY;
        double t_z = ((floor(rayOrigGrid[2] / cellDimension[2]) + rZ < 0 ? 0 : 1)
                * cellDimension[2] - rayOrigGrid[2]) / rZ;
        double tNextCrossing = 0;
        int[] cellIndex = {(int)(oX), (int)(oY), (int)(oZ)};
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
            if(cells[cellIndex[0],cellIndex[1],cellIndex[2]].geometries != null){
                Intersectable.GeoPoint closestPoint =
                        findClosestIntersection(ray,cells[cellIndex[0],cellIndex[1],cellIndex[2]]);
                if(closestPoint != null )//todo : && closestPoint.point.distance())
                    return calcColor(closestPoint, ray);
            }
            if (cellIndex[0] < 0 || cellIndex[1] < 0 || cellIndex[2] < 0
                    || cellIndex[0] > nX - 1 || cellIndex[1] > nY - 1 || cellIndex[2] > nZ - 1)
                return scene.background;
        }
    }

    private Intersectable.GeoPoint findClosestIntersection(Ray ray, Cell cell) {
        var gp = cell.geometries.findGeoIntersections(ray);
        return ray.findClosestGeoPoint(gp);
    }
}
