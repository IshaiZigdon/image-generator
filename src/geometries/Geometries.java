package geometries;

import primitives.Ray;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * this class is for all the geometries that can intersect with a ray ,altogether
 */
public class Geometries extends Intersectable {
    /**
     * the list that will hold what geometries are inside
     */
    private final List<Intersectable> intersectables = new LinkedList<>();

    /**
     * empty constructor for now
     */
    public Geometries() {
    }

    /**
     * a constructor that get some geometries that are on the field and adds them
     *
     * @param geometries a group of unknown size geometries
     */
    public Geometries(Intersectable... geometries) {
        if (geometries != null)
            this.add(geometries);
    }

    /**
     * the function that adds the geometries given
     *
     * @param geometries a group of unknown size geometries
     */
    public void add(Intersectable... geometries) {
        intersectables.addAll(Arrays.asList(geometries));
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> result = null;
        for (Intersectable i : intersectables) {
            List<GeoPoint> geoPoints = i.findGeoIntersections(ray, maxDistance);
            if (geoPoints != null) {
                if (result == null)
                    result = new LinkedList<>(geoPoints);
                else
                    result.addAll(geoPoints);
            }
        }
        return result;
    }
}
