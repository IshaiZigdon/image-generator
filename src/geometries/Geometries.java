package geometries;

import primitives.Point;
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
     * get function for intersectables
     *
     * @return intersectables
     */
    public List<Intersectable> getIntersectables() {
        return intersectables;
    }


    @Override
    public void setMinMax() {
        for (Intersectable i : intersectables) {
            i.setMinMax();

            //checking if i is an infinite shape
            if (i.max == null || i.min == null) {
                max = null;
                min = null;
                return;
            }

            if (i.max.getX() > max.getX())
                max = new Point(i.max.getX(), max.getY(), max.getZ());
            if (i.min.getX() < min.getX())
                min = new Point(i.min.getX(), min.getY(), min.getZ());

            if (i.max.getY() > max.getY())
                max = new Point(max.getX(), i.max.getY(), max.getZ());
            if (i.min.getY() < min.getY())
                min = new Point(min.getX(), i.min.getY(), min.getZ());

            if (i.max.getZ() > max.getZ())
                max = new Point(max.getX(), max.getY(), i.max.getZ());
            if (i.min.getZ() < min.getZ())
                min = new Point(min.getX(), min.getY(), i.min.getZ());
        }
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
