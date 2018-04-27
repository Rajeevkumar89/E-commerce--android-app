package sellit.android.cs4251.com.sellit;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class LocationActivity extends AppCompatActivity  {



    private FusedLocationProviderClient mFusedLocationClient;
    static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    TextView TV,TV2;
    String ad3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        TV = findViewById(R.id.currentlocation);
        TV2 = findViewById(R.id.newlocation);



        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            double longitude = location.getLongitude();
                            double lattitude = location.getLatitude();
                            Geocoder mylocation = new Geocoder(getApplicationContext(),Locale.getDefault());
                            List<Address> addresses = null;
                            try {
                                addresses = mylocation.getFromLocation(lattitude, longitude, 1);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Address ad =addresses.get(0);
                           String ad1= ad.getLocality();
                           String ad2=ad.getAdminArea();
                            TV.setText(""+ad1+","+ad2);
                        }
                    }
                });
        Button search = findViewById(R.id.locsearch);
        search.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                EditText newloc = findViewById(R.id.newlocation);
                String newLocation= newloc.getText().toString();
                if(newLocation!=null || !newLocation.equals("")) {
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    List<Address> addressList = null;
                    try {
                        addressList = geocoder.getFromLocationName(newLocation, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address newaddress = addressList.get(0);

                    ad3=newaddress.getAddressLine(0);
                    String ad1= newaddress.getLocality();
                    String ad2=newaddress.getAdminArea();
                    TV2.setText(""+ad3);
                }

            }
        });

        Button Update = findViewById(R.id.lcconfirm);
        Update.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                TV.setText(""+ad3);
            }
        });

    }
}