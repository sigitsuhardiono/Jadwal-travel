package sigit.jadwal.presenter.lokasi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sigit.jadwal.model.lokasi.Lokasi;
import sigit.jadwal.service.APIinterface;
import sigit.jadwal.service.APIservice;

/**
 * Created by sigit on 09/03/2017.
 */

public class LokasiImp implements LokasiPresenter{
    LokasiView lokasiView;
    public LokasiImp(LokasiView lokasiView){
        this.lokasiView = lokasiView;
    }

    @Override
    public void sendLokasi(String id_driver,String latitude,String longitude) {
        APIinterface apIinterface = APIservice.getClient().create(APIinterface.class);
        Call<Lokasi> lokasi = apIinterface.sendlokasi(id_driver,latitude,longitude);
        lokasi.enqueue(new Callback<Lokasi>() {
            @Override
            public void onResponse(Call<Lokasi> call, Response<Lokasi> response) {
            }

            @Override
            public void onFailure(Call<Lokasi> call, Throwable t) {

            }
        });
    }

}
