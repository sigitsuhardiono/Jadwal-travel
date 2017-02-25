package sigit.jadwal.presenter.travel;

import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sigit.jadwal.model.listtravel.Listtravel;
import sigit.jadwal.model.listtravel.Penumpang;
import sigit.jadwal.service.APIinterface;
import sigit.jadwal.service.APIservice;

/**
 * Created by sigit on 16/02/2017.
 */

public class TravelImp implements TravelPresenter{
    TravelView travelView;
    public TravelImp(TravelView travelView){
        this.travelView = travelView;
    }
    @Override
    public void getPenumpang(String id_driver, String kota) {
        travelView.showLoading();
        Log.e("ID driver",id_driver);
        APIinterface api = APIservice.getClient().create(APIinterface.class);
        Call<Listtravel> call= api.getListPenumpang(id_driver,kota);
        call.enqueue(new Callback<Listtravel>() {
            @Override
            public void onResponse(Call<Listtravel> call, Response<Listtravel> response) {
                if(response.code() == 500){
                    travelView.errorMessage(response.message());
                    travelView.hideLoading();
                }
                else{
                    int code = response.body().getMeta().getCode();
                    String pesanError = response.body().getMeta().getMessage();
                    if(code == 400){
                        travelView.errorMessage(pesanError);
                        travelView.hideLoading();
                    }
                    else{
                        List<Penumpang> penumpang = response.body().getData().getPenumpang();
                        travelView.showPenumpang(penumpang);
                        travelView.hideLoading();
                    }
                }
            }

            @Override
            public void onFailure(Call<Listtravel> call, Throwable t) {

            }
        });
    }
}
