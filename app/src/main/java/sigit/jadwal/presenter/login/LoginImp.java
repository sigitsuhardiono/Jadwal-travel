package sigit.jadwal.presenter.login;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sigit.jadwal.model.fcmtoken.Fcmtoken;
import sigit.jadwal.model.login.Login;
import sigit.jadwal.service.APIinterface;
import sigit.jadwal.service.APIservice;
import sigit.jadwal.preference.Preference;

/**
 * Created by sigit on 13/02/2017.
 */

public class LoginImp implements LoginPresenter{
    LoginView loginView;
    Preference dtpref;
    Context ctx;

    public LoginImp(LoginView loginView){
        this.loginView = loginView;
    }
    @Override
    public void login(String username, String password, Context context) {
        if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
            loginView.showValidationError();
        }
        else{
            loginView.showLoading();
            APIinterface api = APIservice.getClient().create(APIinterface.class);
            HashMap<String, String> params = new HashMap<>();
            params.put("username", username);
            params.put("password", password);
            ctx = context;
            Call<Login> call = api.postLogin(params);
            call.enqueue(new Callback<Login>() {
                @Override
                public void onResponse(Call<Login> call, Response<Login> response) {
                    Integer code = response.body().getMeta().getCode();
                    if(code == 400){
                        String pesan = response.body().getMeta().getMessage();
                        loginView.loginError(pesan);
                        loginView.hideLoading();
                    }
                    else{
                        String data_id = response.body().getData().getId();
                        String data_nama = response.body().getData().getNama();
                        String data_telp = response.body().getData().getTelp();
                        String data_username = response.body().getData().getUsername();
                        String data_password = response.body().getData().getPassword();
                        dtpref = new Preference(ctx);
                        dtpref.createLoginSession(data_id,data_nama,data_telp,data_username,data_password);
                        loginView.loginSucces(response.body().getData().getId());
                        loginView.hideLoading();
                    }
                }
                @Override
                public void onFailure(Call<Login> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }

    @Override
    public void regtokenfcm(String id_driver, String token) {
        APIinterface api = APIservice.getClient().create(APIinterface.class);
        Call<Fcmtoken> call= api.updateToken(id_driver,token);
        call.enqueue(new Callback<Fcmtoken>() {
            @Override
            public void onResponse(Call<Fcmtoken> call, Response<Fcmtoken> response) {
                String pesan = response.body().getMeta().getMessage();
                Log.d("TAG", pesan);
            }
            @Override
            public void onFailure(Call<Fcmtoken> call, Throwable t) {

            }
        });
    }
}
