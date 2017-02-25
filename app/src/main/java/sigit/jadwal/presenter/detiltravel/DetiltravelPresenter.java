package sigit.jadwal.presenter.detiltravel;

/**
 * Created by sigit on 18/02/2017.
 */

public interface DetiltravelPresenter {
    void getDetailPenumpang(String id_driver,String id_penumpang);
    void kirimSms(String id_driver,String id_penumpang);
    void konfAntarjemput(String id_driver,String id_penumpang,String status);
}
