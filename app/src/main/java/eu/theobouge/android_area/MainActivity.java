package eu.theobouge.android_area;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import eu.theobouge.area_calculator.AreaService;
import eu.theobouge.area_overlay.AreaOverlay;

public class MainActivity extends Activity {

    /**
     * the map to use it on all the activity
     */
    MapView map = null;

    /**
     * The main activity for the app
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        //Do standard things on create activity
        super.onCreate(savedInstanceState);
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        //set the layout to the current activity
        setContentView(R.layout.activity_main);

        //get the map from the layout
        this.map = (MapView) findViewById(R.id.map);

        // set default config
        this.map.setTileSource(TileSourceFactory.MAPNIK);
        this.map.setMultiTouchControls(true);
        IMapController mapController = this.map.getController();
        mapController.setZoom(15.0);
        mapController.setCenter(new GeoPoint(48.1066747, -1.6946996000000354));

        // check if the app has the right to get geolocation
        if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            // if not ask for it
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 99);
        }

        // Instanciate the layout with the current phone location
        MyLocationNewOverlay myLocationoverlay = new MyLocationNewOverlay(this.map);
        myLocationoverlay.enableFollowLocation();
        myLocationoverlay.enableMyLocation();

        // add it to the map
        this.map.getOverlays().add(myLocationoverlay);

        //instanciate the areaService
        AreaService area = new AreaService();

        // instanciate my custom layout and add it to the map
        this.map.getOverlays().add(new AreaOverlay(this, area));


    }

    /**
     * Do standard things
     */
    public void onResume() {
        super.onResume();
        this.map.onResume();
    }

    /**
     * Do standard things
     */
    public void onPause() {
        super.onPause();
        this.map.onPause();
    }
}