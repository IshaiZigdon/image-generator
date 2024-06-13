package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.MissingResourceException;

import static primitives.Util.isZero;

/**
 * this class represent the camera
 * and view plane
 *
 * @author Ishai zigdon
 * @author Zaki zafrani
 */
public class Camera implements Cloneable {
    /**
     * point
     */
    private Point p0;
    /**
     * vector representing the vector to the view plane from the camera position
     */
    private Vector vTo;
    /**
     * vector representing the up vector from the camera position
     */
    private Vector vUp;
    /**
     * vector representing the right vector from the camera position
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
     * the image writer with the resolution
     */
    private ImageWriter imageWriter;
    /**
     * the ray tracer with the scene
     */
    private RayTracerBase rayTracer;

    /**
     * making the default constructor private
     */
    private Camera() {
    }

    /**
     * creating new camera builder
     *
     * @return new camera builder
     */
    public static Builder getBuilder() {
        return new Builder(new Camera());
    }

    /**
     * get function for p0
     *
     * @return p0
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
     * constructing a ray through given pixel
     *
     * @param nX width of pixel
     * @param nY height of pixel
     * @param j  column
     * @param i  line
     * @return the ray
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        double rY = viewPlaneHeight / nY;
        double rX = viewPlaneWidth / nX;

        double yI = -(i - (nY - 1) / 2.0) * rY;
        double xJ = (j - (nX - 1) / 2.0) * rX;

        Point pIJ = p0.add(vTo.scale(viewPlaneDistance));
        if (!isZero(xJ))
            pIJ = pIJ.add(vRight.scale(xJ));
        if (!isZero(yI))
            pIJ = pIJ.add(vUp.scale(yI));

        return new Ray(p0, pIJ.subtract(p0));
    }

    /**
     * render the image and color each pixel
     *
     * @return the updated camera
     */
    public Camera renderImage() {
        for (int i = 0; i < imageWriter.getNy(); i++)
            for (int j = 0; j < imageWriter.getNx(); j++)
                castRay(imageWriter.getNx(), imageWriter.getNy(), j, i);
        return this;
    }

    /**
     * print a grid with given width and height with given color
     *
     * @param interval the width and height
     * @param color    the color
     * @return the updated camera
     */
    public Camera printGrid(int interval, Color color) {
        for (int i = 0; i < imageWriter.getNy(); i++)
            for (int j = 0; j < imageWriter.getNx(); j++)
                if (isZero(i % interval) || isZero(j % interval))
                    imageWriter.writePixel(j, i, color);
        imageWriter.writeToImage();
        return this;
    }

    /**
     * cast a ray through a given pixel and colors it
     *
     * @param nX the width of the pixel
     * @param nY the height of the pixel
     * @param j  the x parameter
     * @param i  the y parameter
     */
    private void castRay(int nX, int nY, int j, int i) {
        Ray ray = constructRay(nX, nY, j, i);
        Color color = rayTracer.traceRay(ray);
        imageWriter.writePixel(j, i, color);
    }

    /**
     * class for builder
     */
    public static class Builder {
        /**
         * camera
         */
        final private Camera camera;

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
            if (!isZero(vTo.dotProduct(vUp)))
                throw new IllegalArgumentException("camera vectors must be vertical to each other");
            camera.vRight = vTo.crossProduct(vUp).normalize();
            camera.vTo = vTo.normalize();
            camera.vUp = vUp.normalize();
            return this;
        }

        /**
         * function for set size of plane
         *
         * @param width  the width
         * @param height the height
         * @return builder with plane with the given size
         */
        public Builder setVpSize(double width, double height) {
            if (width * height <= 0 || width <= 0)
                throw new IllegalArgumentException("view plane width and height values must be greater than 0");
            camera.viewPlaneWidth = width;
            camera.viewPlaneHeight = height;
            return this;
        }

        /**
         * function for set distance
         *
         * @param distance the distance
         * @return builder with given distance
         */
        public Builder setVpDistance(double distance) {
            if (distance <= 0)
                throw new IllegalArgumentException("view plane distance value must be greater than 0");
            camera.viewPlaneDistance = distance;
            return this;
        }

        /**
         * function for set imageWriter
         *
         * @param imageWriter the imageWriter
         * @return builder with given imageWriter
         */
        public Builder setImageWriter(ImageWriter imageWriter) {
            camera.imageWriter = imageWriter;
            return this;
        }

        /**
         * function for set rayTracer
         *
         * @param rayTracer the rayTracer
         * @return builder with given rayTracer
         */
        public Builder setRayTracer(RayTracerBase rayTracer) {
            camera.rayTracer = rayTracer;
            return this;
        }

        /**
         * function to check and build camera with valid values
         *
         * @return the camera
         */
        public Camera build() {
            final String message = "Missing render resource. ";
            String fields = "";
            if (isZero(camera.viewPlaneWidth))
                fields += "viewPlaneWidth ";
            if (isZero(camera.viewPlaneHeight))
                fields += "viewPlaneHeight ";
            if (isZero(camera.viewPlaneDistance))
                fields += "viewPlaneDistance ";
            if (camera.vTo == null)
                fields += "vTo ";
            if (camera.vUp == null)
                fields += "vUp ";
            if (camera.imageWriter == null)
                fields += "imageWriter ";
            if (camera.rayTracer == null)
                fields += "rayTracer ";
            if (!isZero(fields.length()))
                throw new MissingResourceException(message + fields.trim(), camera.getClass().getName(), "");
            if (!isZero(camera.vTo.dotProduct(camera.vUp)))
                throw new IllegalArgumentException("camera vectors must be vertical to each other");
            camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();
            try {
                return (Camera) camera.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
