package sigit.jadwal.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import sigit.jadwal.R;
import sigit.jadwal.view.TrvmalangFragment;
import sigit.jadwal.view.TrvsurabayaFragment;

/**
 * Created by sigit on 09/02/2017.
 */

public class Vwpagertrvadapter extends FragmentPagerAdapter {
    final int page = 2;
    final Context context;

    public Vwpagertrvadapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = TrvmalangFragment.newInstance("tes","malang");
                break;
            case 1:
                fragment = TrvsurabayaFragment.newInstance("tes","surabaya");
                break;
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return context.getResources().getString(R.string.tabtrv_1);
            case 1:
                return context.getResources().getString(R.string.tabtrv_2);
        }

        return null;
    }
    @Override
    public int getCount() {
        return page;
    }
}
