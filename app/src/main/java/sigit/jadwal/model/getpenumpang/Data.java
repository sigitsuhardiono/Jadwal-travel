
package sigit.jadwal.model.getpenumpang;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("is_drop")
    @Expose
    private String isDrop;
    @SerializedName("jam")
    @Expose
    private String jam;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("telp1")
    @Expose
    private String telp1;
    @SerializedName("telp2")
    @Expose
    private String telp2;
    @SerializedName("jml")
    @Expose
    private String jml;
    @SerializedName("alamat_jemput")
    @Expose
    private String alamatJemput;
    @SerializedName("oa_jemput")
    @Expose
    private String oaJemput;
    @SerializedName("lat_jemput")
    @Expose
    private String latJemput;
    @SerializedName("lng_jemput")
    @Expose
    private String lngJemput;
    @SerializedName("alamat_tujuan")
    @Expose
    private String alamatTujuan;
    @SerializedName("oa_tujuan")
    @Expose
    private String oaTujuan;
    @SerializedName("lat_tujuan")
    @Expose
    private String latTujuan;
    @SerializedName("lng_tujuan")
    @Expose
    private String lngTujuan;
    @SerializedName("harga")
    @Expose
    private String harga;
    @SerializedName("metode_bayar")
    @Expose
    private String metodeBayar;
    @SerializedName("nama_agen")
    @Expose
    private String namaAgen;
    @SerializedName("is_sms")
    @Expose
    private String isSms;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsDrop() {
        return isDrop;
    }

    public void setIsDrop(String isDrop) {
        this.isDrop = isDrop;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTelp1() {
        return telp1;
    }

    public void setTelp1(String telp1) {
        this.telp1 = telp1;
    }

    public String getTelp2() {
        return telp2;
    }

    public void setTelp2(String telp2) {
        this.telp2 = telp2;
    }

    public String getJml() {
        return jml;
    }

    public void setJml(String jml) {
        this.jml = jml;
    }

    public String getAlamatJemput() {
        return alamatJemput;
    }

    public void setAlamatJemput(String alamatJemput) {
        this.alamatJemput = alamatJemput;
    }

    public String getOaJemput() {
        return oaJemput;
    }

    public void setOaJemput(String oaJemput) {
        this.oaJemput = oaJemput;
    }

    public String getLatJemput() {
        return latJemput;
    }

    public void setLatJemput(String latJemput) {
        this.latJemput = latJemput;
    }

    public String getLngJemput() {
        return lngJemput;
    }

    public void setLngJemput(String lngJemput) {
        this.lngJemput = lngJemput;
    }

    public String getAlamatTujuan() {
        return alamatTujuan;
    }

    public void setAlamatTujuan(String alamatTujuan) {
        this.alamatTujuan = alamatTujuan;
    }

    public String getOaTujuan() {
        return oaTujuan;
    }

    public void setOaTujuan(String oaTujuan) {
        this.oaTujuan = oaTujuan;
    }

    public String getLatTujuan() {
        return latTujuan;
    }

    public void setLatTujuan(String latTujuan) {
        this.latTujuan = latTujuan;
    }

    public String getLngTujuan() {
        return lngTujuan;
    }

    public void setLngTujuan(String lngTujuan) {
        this.lngTujuan = lngTujuan;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getMetodeBayar() {
        return metodeBayar;
    }

    public void setMetodeBayar(String metodeBayar) {
        this.metodeBayar = metodeBayar;
    }

    public String getNamaAgen() {
        return namaAgen;
    }

    public void setNamaAgen(String namaAgen) {
        this.namaAgen = namaAgen;
    }

    public String getIsSms() {
        return isSms;
    }

    public void setIsSms(String isSms) {
        this.isSms = isSms;
    }

}
