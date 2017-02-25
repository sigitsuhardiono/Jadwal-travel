package sigit.jadwal.view;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import sigit.jadwal.R;
import sigit.jadwal.adapter.Vwpagertrvadapter;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class JadwalActivity extends AppCompatActivity {
    Toolbar toolbarJadwal;
    private TabLayout tabLayout;
    private ViewPager viewPagerTrv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/segoeui.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        setContentView(R.layout.activity_jadwal);
        toolbarJadwal = (Toolbar)findViewById(R.id.toolbarJadwal);
        toolbarJadwal.setTitle("Jadwal Travel");
        setSupportActionBar(toolbarJadwal);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tabLayout = (TabLayout) findViewById(R.id.tabLayoutTrv);
        viewPagerTrv = (ViewPager) findViewById(R.id.viewPagerTrv);
        initTab();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void initTab(){
        setupViewPager(viewPagerTrv);
        tabLayout.setupWithViewPager(viewPagerTrv);
    }

    private void setupViewPager(final ViewPager viewPagerTrv){
        Vwpagertrvadapter vpgAdapter =  new Vwpagertrvadapter(getSupportFragmentManager(),this);
        viewPagerTrv.setAdapter(vpgAdapter);
        viewPagerTrv.setOffscreenPageLimit(2);
        viewPagerTrv.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setTabsFromPagerAdapter(vpgAdapter);
    }
}
