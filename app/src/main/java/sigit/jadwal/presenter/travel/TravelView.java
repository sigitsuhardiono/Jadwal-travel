package sigit.jadwal.presenter.travel;

import java.util.List;

import sigit.jadwal.model.listtravel.Penumpang;

/**
 * Created by sigit on 16/02/2017.
 */

public interface TravelView {
    void showLoading();
    void hideLoading();
    void errorMessage(String pesan);
    void showPenumpang(List<Penumpang> penumpang);
}
