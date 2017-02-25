
package sigit.jadwal.model.listtravel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("tgl_malang")
    @Expose
    private String tglMalang;
    @SerializedName("tgl_surabaya")
    @Expose
    private String tglSurabaya;
    @SerializedName("jam_malang")
    @Expose
    private String jamMalang;
    @SerializedName("jam_surabaya")
    @Expose
    private String jamSurabaya;
    @SerializedName("jenis_mobil")
    @Expose
    private String jenisMobil;
    @SerializedName("nopol_mobil")
    @Expose
    private String nopolMobil;
    @SerializedName("ext")
    @Expose
    private String ext;
    @SerializedName("nama_vendor")
    @Expose
    private String namaVendor;
    @SerializedName("jenis_mobil_vendor")
    @Expose
    private String jenisMobilVendor;
    @SerializedName("nopol_mobil_vendor")
    @Expose
    private String nopolMobilVendor;
    @SerializedName("penumpang")
    @Expose
    private List<Penumpang> penumpang = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTglMalang() {
        return tglMalang;
    }

    public void setTglMalang(String tglMalang) {
        this.tglMalang = tglMalang;
    }

    public String getTglSurabaya() {
        return tglSurabaya;
    }

    public void setTglSurabaya(String tglSurabaya) {
        this.tglSurabaya = tglSurabaya;
    }

    public String getJamMalang() {
        return jamMalang;
    }

    public void setJamMalang(String jamMalang) {
        this.jamMalang = jamMalang;
    }

    public String getJamSurabaya() {
        return jamSurabaya;
    }

    public void setJamSurabaya(String jamSurabaya) {
        this.jamSurabaya = jamSurabaya;
    }

    public String getJenisMobil() {
        return jenisMobil;
    }

    public void setJenisMobil(String jenisMobil) {
        this.jenisMobil = jenisMobil;
    }

    public String getNopolMobil() {
        return nopolMobil;
    }

    public void setNopolMobil(String nopolMobil) {
        this.nopolMobil = nopolMobil;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getNamaVendor() {
        return namaVendor;
    }

    public void setNamaVendor(String namaVendor) {
        this.namaVendor = namaVendor;
    }

    public String getJenisMobilVendor() {
        return jenisMobilVendor;
    }

    public void setJenisMobilVendor(String jenisMobilVendor) {
        this.jenisMobilVendor = jenisMobilVendor;
    }

    public String getNopolMobilVendor() {
        return nopolMobilVendor;
    }

    public void setNopolMobilVendor(String nopolMobilVendor) {
        this.nopolMobilVendor = nopolMobilVendor;
    }

    public List<Penumpang> getPenumpang() {
        return penumpang;
    }

    public void setPenumpang(List<Penumpang> penumpang) {
        this.penumpang = penumpang;
    }

}
