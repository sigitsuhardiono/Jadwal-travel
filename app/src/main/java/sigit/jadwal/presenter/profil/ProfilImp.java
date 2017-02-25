package sigit.jadwal.presenter.profil;

import android.content.Context;

import sigit.jadwal.preference.Preference;
import sigit.jadwal.presenter.profil.ProfilView;

/**
 * Created by sigit on 16/02/2017.
 */

public class ProfilImp implements ProfilPresenter{
    ProfilView profilView;
    Preference dtpref;
    public ProfilImp(ProfilView profilView){
        this.profilView = profilView;
    }
    @Override
    public void showProfil(Context context) {
        profilView.showLoading();
        dtpref = new Preference(context);
        profilView.showProfil(dtpref.getUserDetails().get("nama"),dtpref.getUserDetails().get("telp"));
        profilView.hideLoading();
    }
}
