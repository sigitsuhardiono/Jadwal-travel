package sigit.jadwal.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import sigit.jadwal.R;
import sigit.jadwal.preference.Preference;
import sigit.jadwal.presenter.login.LoginImp;
import sigit.jadwal.presenter.login.LoginPresenter;
import sigit.jadwal.presenter.login.LoginView;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
public class LoginActivity extends AppCompatActivity implements LoginView{
    LoginPresenter presenter;
    Preference dtpref;
    Intent intentMain;
    Button buttonLogin;
    EditText user,pass;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/segoeui.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        setContentView(R.layout.activity_login);
        dtpref = new Preference(getApplicationContext());
        buttonLogin = (Button)findViewById(R.id.btn_login);
        user = (EditText)findViewById(R.id.username);
        pass = (EditText)findViewById(R.id.password);
        presenter = new LoginImp(this);
        buttonLogin.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                presenter.login(user.getText().toString(), pass.getText().toString(),getApplicationContext());
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void showValidationError() {
        new AlertDialog.Builder(this).setMessage("Please enter valid username and password!").setNeutralButton("Close", null).show();
    }

    @Override
    public void loginSucces(String id_driver) {
        presenter = new LoginImp(this);
        presenter.regtokenfcm(id_driver, FirebaseInstanceId.getInstance().getToken());
        Log.d("TAG", "InstanceID Token : " + FirebaseInstanceId.getInstance().getToken());
        Intent main = new Intent(this,MainActivity.class);
        startActivity(main);
        finish();
    }

    @Override
    public void loginError(String pesan) {
        new AlertDialog.Builder(this).setMessage(pesan).setNeutralButton("Close", null).show();
    }

    @Override
    public void showLoading() {
        progressDialog = ProgressDialog.show(this, "Mengambil Data","Silakan tunggu..",false,false);
    }

    @Override
    public void hideLoading() {
        progressDialog.dismiss();
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
