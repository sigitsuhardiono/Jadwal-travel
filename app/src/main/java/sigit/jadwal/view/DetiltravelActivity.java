package sigit.jadwal.view;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;

import sigit.jadwal.R;
import sigit.jadwal.preference.Preference;
import sigit.jadwal.presenter.detiltravel.DetiltravelImp;
import sigit.jadwal.presenter.detiltravel.DetiltravelPresenter;
import sigit.jadwal.presenter.detiltravel.DetiltravelView;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class DetiltravelActivity extends AppCompatActivity implements OnMapReadyCallback,DetiltravelView,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    Toolbar toolbarDetilJadwal;
    GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    ProgressDialog progressDialog;
    DetiltravelPresenter detiltravelPresenter;
    Preference dtpref;
    TextView dtlnamatrn,dtljamtrn,dtlalamatjemput,dtlalamattujuan,dtlharga;
    LinearLayout linearLayoutjemput,linearLayouttujuan;
    Button buttonTelp,buttonSms,buttonKonfAsal,buttonKonfTujuan;
    ImageButton imageButtonDirection;
    private static final int GPS_TIME_INTERVAL = 60000*5; // get gps location every 5 min
    private static final int GPS_DISTANCE= 1000; // set the distance value in meter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/segoeui.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        setContentView(R.layout.activity_detiltravel);
        toolbarDetilJadwal = (Toolbar)findViewById(R.id.toolbarDetiltravel);
        toolbarDetilJadwal.setTitle("Detail Penumpang");
        setSupportActionBar(toolbarDetilJadwal);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_travel);
        detiltravelPresenter = new DetiltravelImp(this);
        dtpref = new Preference(getApplicationContext());
        Intent i = getIntent();
        detiltravelPresenter.getDetailPenumpang(dtpref.getUserDetails().get("id"),i.getExtras().getString("id_penumpang"));
        mapFragment.getMapAsync(this);
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            } else {
                checkLocationPermission();
            }
        }
        else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void showDetailPenumpang(HashMap<String, String> detail_penumpang) {
        dtljamtrn = (TextView)findViewById(R.id.Dtljamtrn);
        dtlnamatrn = (TextView)findViewById(R.id.Dtlnamatrn);
        dtlalamatjemput = (TextView)findViewById(R.id.Dtlalamatjemput);
        dtlalamattujuan = (TextView)findViewById(R.id.Dtlalamattujuan);
        dtlharga = (TextView)findViewById(R.id.Dtlhargatrn);
        dtljamtrn.setText(detail_penumpang.get("jam"));
        dtlnamatrn.setText(detail_penumpang.get("nama"));
        dtlalamatjemput.setText(detail_penumpang.get("alamat_jemput"));
        dtlalamattujuan.setText(detail_penumpang.get("alamat_tujuan"));
        if(!detail_penumpang.get("harga").equals("0")){
            dtlharga.setText(detail_penumpang.get("harga"));
        }

        final String latjemput = detail_penumpang.get("lat_jemput");
        final String lonjemput = detail_penumpang.get("lon_jemput");
        imageButtonDirection = (ImageButton)findViewById(R.id.imgDirection);
        linearLayoutjemput = (LinearLayout)findViewById(R.id.ln_jemput) ;
        linearLayoutjemput.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LatLng posisi = new LatLng(Double.parseDouble(latjemput), Double.parseDouble(lonjemput));
                mMap.addMarker(new MarkerOptions().position(posisi).title("Jemput"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posisi, 16));
                imageButtonDirection.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), MapfullActivity.class);
                        intent.putExtra("lat",latjemput);
                        intent.putExtra("lon", lonjemput);
                        v.getContext().startActivity(intent);
                    }
                });
            }
        });

        final String lattujuan = detail_penumpang.get("lat_tujuan");
        final String lontujuan = detail_penumpang.get("lon_tujuan");

        linearLayouttujuan = (LinearLayout)findViewById(R.id.ln_tujuan) ;
        linearLayouttujuan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LatLng posisi = new LatLng(Double.parseDouble(lattujuan), Double.parseDouble(lontujuan));
                mMap.addMarker(new MarkerOptions().position(posisi).title("Tujuan"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posisi, 16));
                imageButtonDirection.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentMap = new Intent(DetiltravelActivity.this,MapfullActivity.class);
                        intentMap.putExtra("lat", lattujuan);
                        intentMap.putExtra("lon", lontujuan);
                        startActivity(intentMap);
                    }
                });
            }
        });

        final String telp = "tel:"+detail_penumpang.get("telp");
        buttonTelp = (Button) findViewById(R.id.btn_telp);
        buttonTelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent =new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse(telp));
                startActivity(callIntent);
            }
        });

        final String id_driver = dtpref.getUserDetails().get("id");
        final String id_penumpang = detail_penumpang.get("id");
        buttonSms = (Button) findViewById(R.id.btn_sms);
        buttonSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detiltravelPresenter.kirimSms(id_driver,id_penumpang);
            }
        });
        String is_jemput = detail_penumpang.get("is_antarjemput");
        buttonKonfAsal = (Button) findViewById(R.id.btn_konf_asal);
        buttonKonfTujuan = (Button) findViewById(R.id.btn_konf_tujuan);
        if(is_jemput.equals("1")){
            buttonKonfAsal.setBackgroundResource(R.drawable.button_bg_rounded_corners_disable);
        }
        else if(is_jemput.equals("2")){
            buttonKonfAsal.setBackgroundResource(R.drawable.button_bg_rounded_corners_disable);
            buttonKonfTujuan.setBackgroundResource(R.drawable.button_bg_rounded_corners_disable);
        }
        buttonKonfAsal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detiltravelPresenter.konfAntarjemput(id_driver,id_penumpang,"jemput");
            }
        });
        buttonKonfTujuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detiltravelPresenter.konfAntarjemput(id_driver,id_penumpang,"antar");
            }
        });

    }

    @Override
    public void hideLoading() {
        progressDialog.dismiss();
    }

    @Override
    public void showLoading() {
        progressDialog = ProgressDialog.show(this, "Mengambil Data","Silakan tunggu..",false,false);
    }

    @Override
    public void viewMessage(String pesan) {
        new AlertDialog.Builder(this).setMessage(pesan).setNeutralButton("YA untuk lanjutkan", null).show();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
//        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
//        if (mLastLocation != null) {
//            mMap.clear();
//            LatLng latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
//            MarkerOptions markerOptions = new MarkerOptions();
//            markerOptions.position(latLng);
//            markerOptions.title("Current Position");
//            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
//            mCurrLocationMarker = mMap.addMarker(markerOptions);
//        }
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this,"onConnectionSuspended",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        //Toast.makeText(this,mCurrLocationMarker+"->"+String.valueOf(location.getLatitude())+"-"+String.valueOf(location.getLongitude()),Toast.LENGTH_SHORT).show();
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,18));
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this,"onConnectionFailed",Toast.LENGTH_SHORT).show();
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(DetiltravelActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION );
                            }
                        })
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION );
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        CekGPS();
    }

    public void onWindowFocusChanged(boolean hasFocus)
    {
        try
        {
            if(hasFocus)
            {
                CekGPS();
            }
            else{
            }
        }
        catch(Exception ex)
        {
        }
    }

    private void CekGPS(){
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("GPS tidak jalan.")
                    .setCancelable(false)
                    .setPositiveButton("Silakan setting GPS anda",
                            new DialogInterface.OnClickListener(){
                                public void onClick(DialogInterface dialog, int id){
                                    Intent exit = new Intent(Intent.ACTION_MAIN);
                                    exit.addCategory(Intent.CATEGORY_HOME);
                                    exit.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(exit);
                                }
                            });
            AlertDialog alert = alertDialogBuilder.create();
            alert.show();
        }
    }

}
