package sigit.jadwal.service;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import sigit.jadwal.model.fcmtoken.Fcmtoken;
import sigit.jadwal.model.forgot.Forgot;
import sigit.jadwal.model.getpenumpang.Getpenumpang;
import sigit.jadwal.model.konfirmasi.Konfirmasi;
import sigit.jadwal.model.listtravel.Listtravel;
import sigit.jadwal.model.login.Login;
import sigit.jadwal.model.sms.Sms;

/**
 * Created by sigit on 15/02/2017.
 */

public interface APIinterface {
    @FormUrlEncoded
    @POST("travel/auth_driver")
    Call<Login> postLogin(@FieldMap HashMap<String, String> params);

    @FormUrlEncoded
    @POST("travel/update_token")
    Call<Fcmtoken> updateToken(@Field("id_driver") String id_driver, @Field("token") String token);

    @FormUrlEncoded
    @POST("travel/get_list_penumpang")
    Call<Listtravel> getListPenumpang(@Field("id_driver") String id_driver, @Field("kota") String kota);

    @FormUrlEncoded
    @POST("travel/get_penumpang")
    Call<Getpenumpang> getPenumpang(@Field("id_driver") String id_driver, @Field("id_penumpang") String id_penumpang);

    @FormUrlEncoded
    @POST("travel/send_sms")
    Call<Sms> smsPenumpang(@Field("id_driver") String id_driver, @Field("id_penumpang") String id_penumpang);

    @FormUrlEncoded
    @POST("travel/set_antar_jemput")
    Call<Konfirmasi> setAntarjemput(@Field("id_driver") String id_driver, @Field("id_penumpang") String id_penumpang, @Field("status") String status);

    @FormUrlEncoded
    @POST("travel/send_reset_code")
    Call<Forgot> sendResetcode(@Field("username") String username);

}
