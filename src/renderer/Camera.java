package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.MissingResourceException;

import static primitives.Util.isZero;

/**
 * this class represent the camera
 *
 * @author Ishai zigdon
 * @author Zaki zafrani
 */
public class Camera implements Cloneable {
    /**
     * class for builder
     */
    public static class Builder {
        final Camera camera;

        /**
         * constructor that initialize camera with given Camera object
         *
         * @param c given camera
         */
        public Builder(Camera c) {
            camera = c;
        }

        /**
         * function for set location
         *
         * @param p the location point
         * @return builder with given location
         */
        public Builder setLocation(Point p) {
            camera.p0 = p;
            return this;
        }

        /**
         * function for set direction
         *
         * @param vUp vertical vector
         * @param vTo vector to distance
         * @return builder with given direction
         */
        public Builder setDirection(Vector vTo, Vector vUp) {
            if (isZero(vTo.dotProduct(vUp))) {
                camera.vRight = vTo.crossProduct(vUp).normalize();
                camera.vTo = vTo.normalize();
                camera.vUp = vUp.normalize();
                return this;
            }
            throw new IllegalArgumentException("camera vectors must be vertical to each other");
        }

        /**
         * function for set size of plane
         *
         * @param width  the width
         * @param height the height
         * @return builder with plane with the given size
         */
        public Builder setVpSize(double width, double height) {
            if (width * height > 0 && width > 0) {
                camera.viewPlaneWidth = width;
                camera.viewPlaneHeight = height;
                return this;
            }
            throw new IllegalArgumentException("view plane width and height values must be greater than 0");
        }

        /**
         * function for set distance
         *
         * @param distance the distance
         * @return builder with given distance
         */
        public Builder setVpDistance(double distance) {
            if (distance > 0) {
                camera.viewPlaneDistance = distance;
                return this;
            }
            throw new IllegalArgumentException("view plane distance value must be greater than 0");
        }

        /**
         * function to build camera with valid values
         *
         * @return the camera
         */
        public Camera build() {
            final String message = "Missing render resource. ";
            String fields = "";
            if (isZero(camera.viewPlaneWidth)) {
                fields += "viewPlaneWidth ";
            }
            if (isZero(camera.viewPlaneHeight)) {
                fields += "viewPlaneHeight ";
            }
            if (isZero(camera.viewPlaneDistance)) {
                fields += "viewPlaneDistance ";
            }
            if (!isZero(fields.length()))
                throw new MissingResourceException(message, camera.getClass().getName(), fields);
            setDirection(camera.vTo, camera.vUp);
            return camera;
        }
    }

    /**
     * point
     */
    private Point p0;
    /**
     * vector representing the y-axis
     */
    private Vector vTo;
    /**
     * vector representing the z-axis
     */
    private Vector vUp;
    /**
     * vector representing the x-axis
     */
    private Vector vRight;
    /**
     * the width of the view plane
     */
    private double viewPlaneWidth = 0.0;
    /**
     * the height of the view plane
     */
    private double viewPlaneHeight = 0.0;
    /**
     * the distance of the view plane
     */
    private double viewPlaneDistance = 0.0;

    /**
     * get function for po
     *
     * @return p0
     */
    Point getCenter() {
        return p0;
    }

    /**
     * get function for vTo
     *
     * @return vTo
     */
    Vector getVTo() {
        return vTo;
    }

    /**
     * get function for vUp
     *
     * @return vUp
     */
    Vector getVUp() {
        return vUp;
    }

    /**
     * get function for vRight
     *
     * @return vRight
     */
    Vector getVRight() {
        return vRight;
    }

    /**
     * get function for viewPlaneHeight
     *
     * @return viewPlaneHeight
     */
    double getViewPlaneHeight() {
        return viewPlaneHeight;
    }

    /**
     * get function for viewPlaneWidth
     *
     * @return viewPlaneWidth
     */
    double getViewPlaneWidth() {
        return viewPlaneWidth;
    }

    /**
     * get function for viewPlaneDistance
     *
     * @return viewPlaneDistance
     */
    double getViewPlaneDistance() {
        return viewPlaneDistance;
    }

    /**
     * default constructor
     */
    private Camera() {
    }

    /**
     * creating new camera builder
     * @return new camera builder
     */
    public static Builder getBuilder() {
        return new Builder(new Camera());
    }

    /**
     * constructing a ray through given pixel
     * @param nX width of pixel
     * @param nY height of pixel
     * @param j  column
     * @param i  line
     * @return the ray
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        double Ry = viewPlaneHeight/nY;
        double Rx = viewPlaneWidth/nX;

        double yI = -(i - (nY-1)/2.0)*Ry;
        double xJ = (j - (nX-1)/2.0)*Rx;

        Point pIJ = p0.add(vTo.scale(viewPlaneDistance));
        if(!isZero(xJ))
            pIJ = pIJ.add(vRight.scale(xJ));
        if(!isZero(yI))
            pIJ = pIJ.add(vUp.scale(yI));

        return new Ray(p0, pIJ.subtract(p0));
    }
}
