package sigit.jadwal.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import sigit.jadwal.R;
import sigit.jadwal.preference.Preference;
import sigit.jadwal.presenter.forgot.ForgotImp;
import sigit.jadwal.presenter.forgot.ForgotPresenter;
import sigit.jadwal.presenter.forgot.ForgotView;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ForgotActivity extends AppCompatActivity implements ForgotView{
    Button buttonKirim;
    EditText editTextuser;
    ProgressDialog progressDialog;
    ForgotPresenter forgotPresenter;
    Preference dtpref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/segoeui.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        setContentView(R.layout.activity_forgot);
        forgotPresenter = new ForgotImp(this);
        buttonKirim = (Button)findViewById(R.id.btn_forgot);
        editTextuser = (EditText)findViewById(R.id.forgotusername);
        dtpref = new Preference(getApplicationContext());
        buttonKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPresenter.sendReset(editTextuser.getText().toString());
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void viewMessage(String pesan) {
        new AlertDialog.Builder(this).setMessage(pesan).setNeutralButton("Close", null).show();
    }

    @Override
    public void hideLoading() {
        progressDialog.dismiss();
    }

    @Override
    public void showLoading() {
        progressDialog = ProgressDialog.show(this, "Loading","Silakan tunggu..",false,false);
    }
}


