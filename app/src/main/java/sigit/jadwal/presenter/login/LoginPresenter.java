package sigit.jadwal.presenter.login;

import android.content.Context;

/**
 * Created by sigit on 13/02/2017.
 */

public interface LoginPresenter {
    void login(String username,String password,Context context);
    void regtokenfcm(String id_driver,String token);
}
