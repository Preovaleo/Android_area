package eu.theobouge.area_calculator;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;
import java.util.List;

public class AreaService {

    public AreaService() {

    }

    public double getArea(List<GeoPoint> coordinates) {
        double area = 0.0;
        if (coordinates.size() > 2) {

            ArrayList<GeoPoint> better = new ArrayList<GeoPoint>();
            for (GeoPoint g : coordinates) {
                better.add(g);
            }
            better.add(coordinates.get(0));

            GeoPoint p1, p2;
            for (int i = 0; i < better.size() - 1; i++) {
                p1 = better.get(i);
                p2 = better.get(i + 1);
                area += ConvertToRadian(p2.getLongitude() - p1.getLongitude()) * (2 + Math.sin(ConvertToRadian(p1.getLatitude())) + Math.sin(ConvertToRadian(p2.getLatitude())));
            }
            area = area * 6378137.0 * 6378137.0 / 2.0;
        }
        return area;
    }

    private static double ConvertToRadian(double input) {
        return input * Math.PI / 180;
    }
}
