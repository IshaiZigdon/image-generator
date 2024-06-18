package lighting;
import primitives.*;

/**
 *
 *
 * @author Ishai zigdon
 * @author Zaki zafrani
 */
public interface LightSource {
    /**
     *
     *
     * @param p
     * @return
     */
    public Color getIntensity(Point p);

    /**
     *
     * @param p
     * @return
     */
    public Vector getL(Point p);
}
