package sigit.jadwal.view;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.location.LocationManager;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;

import sigit.jadwal.R;
import sigit.jadwal.adapter.ChatArrayAdapter;
import sigit.jadwal.preference.Preference;
import sigit.jadwal.presenter.lokasi.LokasiImp;
import sigit.jadwal.presenter.lokasi.LokasiPresenter;
import sigit.jadwal.presenter.lokasi.LokasiView;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener,LokasiView {
    Intent intentJadwal,intentProfil;
    Toolbar toolbarMain;
    ActionBarDrawerToggle actionBarDrawerToggle;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Preference dtpref;
    String TAG = "MainActivity";
    String response;
    private ArrayList<String> dataSet;
    ListView listViewChat;
    private ChatArrayAdapter chatArrayAdapter;
    /*load lokasi preference*/
    LokasiPresenter lokasiPresenter;
    /*for update location*/
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    GoogleMap mMap;
    Location mLastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/segoeui.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        setContentView(R.layout.activity_main);
        dtpref = new Preference(getApplicationContext());
        dtpref.checkLogin();
        lokasiPresenter = new LokasiImp(this);
        toolbarMain = (Toolbar)findViewById(R.id.toolbarMain);
        toolbarMain.setTitle("Beranda");
        setSupportActionBar(toolbarMain);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbarMain,R.string.drawerBuka, R.string.drawerTutup){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView = (NavigationView)findViewById(R.id.nav_layout);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                drawerLayout.closeDrawer(GravityCompat.START);
                selectDrawerItem(item);
                return true;
            }
        });
        View headerView = navigationView.getHeaderView(0);
        TextView username_nav = (TextView)headerView.findViewById(R.id.nama_user_nav);
        username_nav.setText(dtpref.getUserDetails().get("nama"));
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.i(TAG, "FCM Registration Token: " + token);
        dataSet = new ArrayList<>();
        initDataset();
        listViewChat = (ListView) findViewById(R.id.listView1);

        chatArrayAdapter = new ChatArrayAdapter(getApplicationContext(), R.layout.chat_message,dataSet);
        listViewChat.setAdapter(chatArrayAdapter);

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void selectDrawerItem(MenuItem menuItem) {
        if(menuItem.isChecked()) {
            menuItem.setChecked(false);
        }
        else{
            menuItem.setChecked(true);
        }
        switch (menuItem.getItemId()) {
            case R.id.jdw_travel:
                intentJadwal = new Intent(MainActivity.this,JadwalActivity.class);
                startActivity(intentJadwal);
                break;
            case R.id.menu_profil:
                intentProfil = new Intent(MainActivity.this,ProfilActivity.class);
                startActivity(intentProfil);
                break;
            case R.id.menu_logout:
                dtpref = new Preference(getApplicationContext());
                dtpref.logoutUser();
                finish();
                break;
            default:
                intentJadwal = new Intent(MainActivity.this,JadwalActivity.class);
                startActivity(intentJadwal);
                break;
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

    private void initDataset(){
        dataSet.add("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin dapibus.");
        dataSet.add("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur sagittis.");
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
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
                                ActivityCompat.requestPermissions(MainActivity.this,
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
    public void onConnected(@Nullable Bundle bundle) {
        Toast.makeText(this,"onConnected",Toast.LENGTH_SHORT).show();
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(60000*5);
        mLocationRequest.setFastestInterval(60000*5);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(this,"onLocationChanged",Toast.LENGTH_SHORT).show();
        lokasiPresenter.sendLokasi(dtpref.getUserDetails().get("id"),String.valueOf(location.getLatitude()),String.valueOf(location.getLongitude()));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this,"onMapReady",Toast.LENGTH_SHORT).show();
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

    @Override
    public void viewMessage(String pesan) {
        Toast.makeText(this,pesan,Toast.LENGTH_SHORT).show();
    }
}
