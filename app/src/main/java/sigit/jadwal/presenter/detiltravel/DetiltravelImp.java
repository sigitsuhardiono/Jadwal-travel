package sigit.jadwal.presenter.detiltravel;

import android.util.Log;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sigit.jadwal.model.getpenumpang.Getpenumpang;
import sigit.jadwal.model.konfirmasi.Konfirmasi;
import sigit.jadwal.model.sms.Sms;
import sigit.jadwal.service.APIinterface;
import sigit.jadwal.service.APIservice;

/**
 * Created by sigit on 18/02/2017.
 */

public class DetiltravelImp implements DetiltravelPresenter{
    DetiltravelView detiltravelView;
    public static final String KEY_ID = "id";
    public static final String KEY_NAMA = "nama";
    public static final String KEY_JAM = "jam";
    public static final String KEY_TELP1 = "telp";
    public static final String KEY_TELP2 = "telp2";
    public static final String KEY_ALAMAT_JEMPUT = "alamat_jemput";
    public static final String KEY_ALAMAT_TUJUAN = "alamat_tujuan";
    public static final String KEY_LAT_JEMPUT = "lat_jemput";
    public static final String KEY_LON_JEMPUT = "lon_jemput";
    public static final String KEY_LAT_TUJUAN = "lat_tujuan";
    public static final String KEY_LON_TUJUAN = "lon_tujuan";
    public static final String KEY_HARGA = "harga";
    NumberFormat rupiahFormat;
    public  DetiltravelImp(DetiltravelView detiltravelView){
        this.detiltravelView = detiltravelView;
    }
    @Override
    public void getDetailPenumpang(String id_driver, String id_penumpang) {
        detiltravelView.showLoading();
        APIinterface api = APIservice.getClient().create(APIinterface.class);
        Call<Getpenumpang> call= api.getPenumpang(id_driver,id_penumpang);
        call.enqueue(new Callback<Getpenumpang>() {
            @Override
            public void onResponse(Call<Getpenumpang> call, Response<Getpenumpang> response) {
                HashMap<String, String> detail_penumpang = new HashMap<String, String>();
                detail_penumpang.put(KEY_ID, response.body().getData().getId());
                detail_penumpang.put(KEY_JAM, response.body().getData().getJam());
                detail_penumpang.put(KEY_NAMA, response.body().getData().getNama());
                detail_penumpang.put(KEY_TELP1, response.body().getData().getTelp1());
                detail_penumpang.put(KEY_TELP2, response.body().getData().getTelp2());
                detail_penumpang.put(KEY_ALAMAT_JEMPUT, response.body().getData().getAlamatJemput());
                detail_penumpang.put(KEY_ALAMAT_TUJUAN, response.body().getData().getAlamatTujuan());
                detail_penumpang.put(KEY_LAT_JEMPUT, response.body().getData().getLatJemput());
                detail_penumpang.put(KEY_LON_JEMPUT, response.body().getData().getLngJemput());
                detail_penumpang.put(KEY_LAT_TUJUAN, response.body().getData().getLatTujuan());
                detail_penumpang.put(KEY_LON_TUJUAN, response.body().getData().getLngTujuan());
                rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);
                String rupiah = rupiahFormat.format(Double.parseDouble(response.body().getData().getHarga()));
                detail_penumpang.put(KEY_HARGA, rupiah);
                detiltravelView.showDetailPenumpang(detail_penumpang);
                detiltravelView.hideLoading();
            }

            @Override
            public void onFailure(Call<Getpenumpang> call, Throwable t) {

            }
        });
    }

    @Override
    public void kirimSms(String id_driver, String id_penumpang) {
        detiltravelView.showLoading();
        APIinterface apIinterface = APIservice.getClient().create(APIinterface.class);
        Call<Sms> sms = apIinterface.smsPenumpang(id_driver,id_penumpang);
        sms.enqueue(new Callback<Sms>() {
            @Override
            public void onResponse(Call<Sms> call, Response<Sms> response) {
                detiltravelView.viewMessage(response.body().getData().getStatus());
                detiltravelView.hideLoading();
            }

            @Override
            public void onFailure(Call<Sms> call, Throwable t) {

            }
        });
    }

    @Override
    public void konfAntarjemput(String id_driver, String id_penumpang, String status) {
        detiltravelView.showLoading();
        APIinterface apIinterface = APIservice.getClient().create(APIinterface.class);
        Call<Konfirmasi> sms = apIinterface.setAntarjemput(id_driver,id_penumpang,status);
        sms.enqueue(new Callback<Konfirmasi>() {
            @Override
            public void onResponse(Call<Konfirmasi> call, Response<Konfirmasi> response) {
                detiltravelView.viewMessage(response.body().getData().getStatus());
                detiltravelView.hideLoading();
            }

            @Override
            public void onFailure(Call<Konfirmasi> call, Throwable t) {

            }
        });
    }
}
