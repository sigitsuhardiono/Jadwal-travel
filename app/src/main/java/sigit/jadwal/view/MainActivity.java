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
import android.widget.ListView;
import android.widget.TextView;
import android.location.LocationManager;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;

import sigit.jadwal.R;
import sigit.jadwal.adapter.ChatArrayAdapter;
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
    private ArrayList<String> dataSet;
    ListView listViewChat;
    private ChatArrayAdapter chatArrayAdapter;

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

}
