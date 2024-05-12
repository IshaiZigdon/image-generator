package geometries;

import primitives.*;

/***
 * this interface will serve all geometry shapes in the program
 * 2D and 3D
 */
public interface Geometry {
     /***
      * all the classes will implement this function, calculates the
      * vertical vector for the current shape
      * @param p a point on the shape
      * @return a vertical vector for the shape
      */
     public Vector getNormal(Point p);

}
