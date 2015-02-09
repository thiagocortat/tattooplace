package infnet.tattooplace;

import android.app.Application;
import android.location.Location;

import com.parse.Parse;
import com.parse.ParseCrashReporting;
import com.parse.ParseInstallation;
import com.parse.ParseObject;

import infnet.tattooplace.models.Tatto;
import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;


public class ParseApplication extends Application {

  private static Location location;

  @Override
  public void onCreate() {
    super.onCreate();

//    ReactiveLocationProvider locationProvider = new ReactiveLocationProvider(this);
//    locationProvider.getLastKnownLocation()
//            .subscribe(new Action1<Location>() {
//                @Override
//                public void call(Location location) {
//                    ParseApplication.location = location;
//                }
//            });

    // Initialize Crash Reporting.
    ParseCrashReporting.enable(this);

    // Enable Local Datastore.
    Parse.enableLocalDatastore(this);

      ParseObject.registerSubclass(Tatto.class);

    // Add your initialization code here
      Parse.initialize(this, "BRngIIZDSL6AYVOHADLC2ncFbaZYCkNx5zR86CvB", "MDwf4nJgCYKivOxHFsmE3FFzQgTXIOKyHD3cgUL7");


      // Save the current Installation to Parse.
      ParseInstallation.getCurrentInstallation().saveInBackground();

      SmartLocation.with(this).location()
              .start(new OnLocationUpdatedListener() {
                  @Override
                  public void onLocationUpdated(Location location) {
                      ParseApplication.location = location;
                  }
              });
  }

    public static Location getLocation() {
        return location;
    }
}
