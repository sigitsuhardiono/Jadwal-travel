package sigit.jadwal.presenter.forgot;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sigit.jadwal.model.forgot.Forgot;
import sigit.jadwal.service.APIinterface;
import sigit.jadwal.service.APIservice;

/**
 * Created by sigit on 09/03/2017.
 */

public class ForgotImp implements ForgotPresenter{
    ForgotView forgotView;
    public ForgotImp(ForgotView forgotView){
        this.forgotView = forgotView;
    }

    @Override
    public void sendReset(String username) {
        forgotView.showLoading();
        APIinterface apIinterface = APIservice.getClient().create(APIinterface.class);
        Call<Forgot> forgot = apIinterface.sendResetcode(username);
        forgot.enqueue(new Callback<Forgot>() {
            @Override
            public void onResponse(Call<Forgot> call, Response<Forgot> response) {
                forgotView.viewMessage(response.body().getData().getStatus());
                forgotView.hideLoading();
            }

            @Override
            public void onFailure(Call<Forgot> call, Throwable t) {

            }
        });
    }

}
