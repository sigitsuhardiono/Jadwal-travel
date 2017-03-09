package sigit.jadwal.view;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import sigit.jadwal.R;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ForgotActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/segoeui.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        setContentView(R.layout.activity_forgot);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}


