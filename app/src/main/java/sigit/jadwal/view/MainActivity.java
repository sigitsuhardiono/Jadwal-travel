package sigit.jadwal.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.NavigationView;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.firebase.iid.FirebaseInstanceId;

import sigit.jadwal.R;
import sigit.jadwal.preference.Preference;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity{
    Intent intentJadwal,intentProfil;
    Toolbar toolbarMain;
    ActionBarDrawerToggle actionBarDrawerToggle;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Preference dtpref;
    String TAG = "MainActivity";
    String response;

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
            alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                    .setCancelable(false)
                    .setPositiveButton("Goto Settings Page To Enable GPS",
                            new DialogInterface.OnClickListener(){
                                public void onClick(DialogInterface dialog, int id){
                                    Intent callGPSSettingIntent = new Intent(
                                            android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                    callGPSSettingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    callGPSSettingIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                    callGPSSettingIntent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                    startActivity(callGPSSettingIntent);
                                }
                            });
            alertDialogBuilder.setNegativeButton("Cancel",
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
