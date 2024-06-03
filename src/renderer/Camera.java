package renderer;

import primitives.*;

/**
 * this class represent the camera
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
    private double viewPlaneHeight =0.0;
    /**
     * the distance of the view plane
     */
    private double viewPlaneDistance = 0.0;
    /**
     * class for builder
     */
    public static class Builder{
        final Camera camera;
        /**
         * constructor that initialize camera with given Camera object
         * @param c given camera
         */
        public Builder(Camera c){
            camera = c;
        }
    }
    /**
     * get function for po
     * @return po
     */
    Point getP0(){
        return p0;
    }
    /**
     * get function for vTo
     * @return vTo
     */
    Vector getVTo(){
        return vTo;
    }
    /**
     * get function for vUp
     * @return vUp
     */
    Vector getVUp(){
        return vUp;
    }
    /**
     * get function for vRight
     * @return vRight
     */
    Vector getVRight(){
        return vRight;
    }
    /**
     * get function for viewPlaneHeight
     * @return viewPlaneHeight
     */
    double getViewPlaneHeight(){
        return viewPlaneHeight;
    }
    /**
     * get function for viewPlaneWidth
     * @return viewPlaneWidth
     */
    double getViewPlaneWidth(){
        return viewPlaneWidth;
    }
    /**
     * get function for viewPlaneDistance
     * @return viewPlaneDistance
     */
    double getViewPlaneDistance(){
        return viewPlaneDistance;
    }
    /**
     * default constructor
     */
    private Camera(){
    }
    /**
     * todo
     */
    public static Builder getBuilder(){
        return null;
    }
    /**
     * todo
     */
    public Ray constructRay(int nX, int nY, int j, int i){
        return null;
    }
}
