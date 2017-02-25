package sigit.jadwal.presenter.detiltravel;

import java.util.HashMap;

/**
 * Created by sigit on 18/02/2017.
 */

public interface DetiltravelView {
    void showLoading();
    void hideLoading();
    void showDetailPenumpang(HashMap<String, String> detail_penumpang);
    void viewMessage(String pesan);
}
