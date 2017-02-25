package sigit.jadwal.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import sigit.jadwal.R;
import sigit.jadwal.presenter.profil.ProfilImp;
import sigit.jadwal.presenter.profil.ProfilPresenter;
import sigit.jadwal.presenter.profil.ProfilView;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ProfilActivity extends AppCompatActivity  implements ProfilView{
    Toolbar toolbarProfil;
    ProgressDialog progressDialog;
    ProfilPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/segoeui.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        setContentView(R.layout.activity_profil);
        toolbarProfil = (Toolbar)findViewById(R.id.toolbarProfil);
        toolbarProfil.setTitle("Profil");
        setSupportActionBar(toolbarProfil);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        presenter = new ProfilImp(this);
        presenter.showProfil(getApplicationContext());
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void showProfil(String nama, String telp) {
        TextView username_profil = (TextView)findViewById(R.id.user_profile_name);
        username_profil.setText(nama);
        TextView username_telp = (TextView)findViewById(R.id.user_profile_telp);
        username_telp.setText(telp);
    }

    @Override
    public void showLoading() {
        progressDialog = ProgressDialog.show(this, "Mengambil Data","Silakan tunggu..",false,false);
    }

    @Override
    public void hideLoading() {
        progressDialog.dismiss();
    }
}
