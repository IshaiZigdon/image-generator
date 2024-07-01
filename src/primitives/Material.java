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
     * the transparency attenuation coefficient
     */
    public Double3 kT = Double3.ZERO;
    /**
     * the reflection attenuation coefficient
     */
    public Double3 kR = Double3.ZERO;
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
        if (kD.add(kS.add(kT)).greaterThan(Double3.ONE))
            throw new IllegalArgumentException("kD out of range");
        if (kD.add(kR.add(kT)).greaterThan(Double3.ONE))
            throw new IllegalArgumentException("kD out of range");
        this.kD = kD;
        return this;
    }

    /**
     * set function for kD with double value
     *
     * @param kD2 the given kD
     * @return the updated Material
     */
    public Material setKd(double kD2) {
        Double3 kD = new Double3(kD2);
        if (kD.add(kS.add(kT)).greaterThan(Double3.ONE))
            throw new IllegalArgumentException("kD out of range");
        if (kD.add(kR.add(kT)).greaterThan(Double3.ONE))
            throw new IllegalArgumentException("kD out of range");
        this.kD = kD;
        return this;
    }

    /**
     * set function for kS with Double3 value
     *
     * @param kS the given kS
     * @return the updated Material
     */
    public Material setKs(Double3 kS) {
        if (kD.add(kS.add(kT)).greaterThan(Double3.ONE))
            throw new IllegalArgumentException("kS out of range");
        this.kS = kS;
        return this;
    }

    /**
     * set function for kD with double value
     *
     * @param kS2 the given kS
     * @return the updated Material
     */
    public Material setKs(double kS2) {
        Double3 kS = new Double3(kS2);
        if (kD.add(kS.add(kT)).greaterThan(Double3.ONE))
            throw new IllegalArgumentException("kS out of range");
        this.kS = kS;
        return this;
    }

    /**
     * set function for kT
     *
     * @param kT the given kT
     * @return the updated material
     */
    public Material setKt(Double3 kT) {
        if (kD.add(kS.add(kT)).greaterThan(Double3.ONE))
            throw new IllegalArgumentException("kT out of range");
        if (kD.add(kR.add(kT)).greaterThan(Double3.ONE))
            throw new IllegalArgumentException("kT out of range");
        this.kT = kT;
        return this;
    }

    /**
     * set function for kD with double value
     *
     * @param kT2 the given kT
     * @return the updated material
     */
    public Material setKt(double kT2) {
        Double3 kT = new Double3(kT2);
        if (kD.add(kS.add(kT)).greaterThan(Double3.ONE))
            throw new IllegalArgumentException("kT out of range");
        if (kD.add(kR.add(kT)).greaterThan(Double3.ONE))
            throw new IllegalArgumentException("kT out of range");
        this.kT = kT;
        return this;
    }

    /**
     * set function for kR
     *
     * @param kR the given kR
     * @return the updated material
     */
    public Material setKr(Double3 kR) {
        if (kD.add(kR.add(kT)).greaterThan(Double3.ONE))
            throw new IllegalArgumentException("kR out of range");
        this.kR = kR;
        return this;
    }

    /**
     * set function for kD with double value
     *
     * @param kR2 the given kR
     * @return the updated material
     */
    public Material setKr(double kR2) {
        Double3 kR = new Double3(kR2);
        if (kD.add(kR.add(kT)).greaterThan(Double3.ONE))
            throw new IllegalArgumentException("kR out of range");
        this.kR = kR;
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
