package lighting;

import primitives.*;

/**
 *
 */
public class AmbientLight {
    /***/
    private Color ia;
    /***/
    private Double3 ka;
    /***/
    final private Color intensity;
    /***/
    public static AmbientLight NONE = new AmbientLight(Color.BLACK,0);

    //------------------------------functions---------------------------------
    /**
     *
     * @param ia
     * @param ka
     */
    public AmbientLight(Color ia, Double3 ka) {
        this.ia = ia;
        this.ka = ka;
        intensity = ia.scale(ka);
    }

    /**
     *
     * @param ia
     * @param ka
     */
    public AmbientLight(Color ia,double ka)
    {
        this.ia = ia;
        this.ka = new Double3(ka);
        intensity = ia.scale(ka);
    }

    /**
     *
     * @return
     */
    public Color getIntensity() { return intensity; }




}
