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

    private int size = 0;

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

    public List<Intersectable> getIntersectables() {
        return intersectables;
    }

    public int getSize(){
        return size;
    }

    /**
     * the function that adds the geometries given
     *
     * @param geometries a group of unknown size geometries
     */
    public void add(Intersectable... geometries) {
        intersectables.addAll(Arrays.asList(geometries));
        size += Arrays.asList(geometries).size();
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> result = null;
        for (Intersectable i : intersectables) {
            var geoPoints = i.findGeoIntersections(ray, maxDistance);
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
