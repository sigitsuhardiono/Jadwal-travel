package sigit.jadwal.presenter.login;

/**
 * Created by sigit on 13/02/2017.
 */

public interface LoginView {
    void showValidationError();
    void loginSucces(String id_driver);
    void loginError(String pesan);
    void showLoading();
    void hideLoading();
}
