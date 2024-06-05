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
        public Builder setDirection(Vector vUp, Vector vTo) {
            if (isZero(vUp.dotProduct(vTo))) {
                camera.vRight = vUp.crossProduct(vTo).normalize();
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
                camera.viewPlaneWidth = distance;
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
            if(isZero(fields.length()))
                throw new MissingResourceException(message, camera.getClass().getName(), fields);
            if (isZero(camera.vUp .dotProduct(camera.vTo))) {
                camera.vRight = camera.vUp.crossProduct(camera.vTo).normalize();
                return camera;
            }
            throw new IllegalArgumentException("camera vectors are vertical to each other");
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
     * @return po
     */
    Point getP0() {
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
     * todo
     */
    public static Builder getBuilder() {
        return new Builder(new Camera());
    }

    /**
     * todo
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        return null;
    }
}
