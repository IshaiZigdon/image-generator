package primitives;

/**
 * class for material for kd ,ks and shininess
 *
 * @author Ishai zigdon
 * @author Zaki zafrani
 */
public class Material {
    /**
     * the diffuse attenuation coefficient
     */
    public Double3 kD = Double3.ZERO;
    /**
     * the specular attenuation coefficient
     */
    public Double3 kS = Double3.ZERO;
    /**
     * the shininess
     */
    public int nShininess = 0;

    /**
     * set function for kD with Double3 value
     *
     * @param kD the given kD
     * @return the updated Material
     */
    public Material setKd(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     * set function for kD with double value
     *
     * @param kD the given kD
     * @return the updated Material
     */
    public Material setKd(double kD) {
        this.kD = new Double3(kD);
        return this;
    }

    /**
     * set function for kS with Double3 value
     *
     * @param kS the given kS
     * @return the updated Material
     */
    public Material setKs(Double3 kS) {
        this.kS = kS;
        return this;
    }

    /**
     * set function for kD with double value
     *
     * @param kS the given kS
     * @return the updated Material
     */
    public Material setKs(double kS) {
        this.kS = new Double3(kS);
        return this;
    }

    /**
     * set function for nShininess
     *
     * @param nShininess the given nShininess
     * @return the updated Material
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
}
