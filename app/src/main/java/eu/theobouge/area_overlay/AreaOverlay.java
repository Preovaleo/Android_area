package eu.theobouge.area_overlay;

import android.content.Context;
import android.graphics.Color;
import android.view.MotionEvent;
import android.widget.Toast;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.Projection;
import org.osmdroid.views.overlay.Polygon;

import eu.theobouge.area_calculator.AreaService;

public class AreaOverlay extends Polygon {

    private Context ctx;
    private AreaService area;

    public AreaOverlay(Context ctx, AreaService area) {
        super();
        this.setFillColor(Color.GRAY);
        this.ctx = ctx;
        this.area = area;
    }

    @Override
    public boolean onLongPress(MotionEvent e, MapView mapView) {
        Projection proj = mapView.getProjection();
        GeoPoint p = (GeoPoint) proj.fromPixels((int) e.getX(), (int) e.getY());
        this.addPoint(p);
        Toast.makeText(ctx, Double.toString(this.area.getArea(this.getPoints())), Toast.LENGTH_SHORT).show();
        return false;
    }
}
