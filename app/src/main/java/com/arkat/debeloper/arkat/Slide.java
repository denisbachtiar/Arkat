package com.arkat.debeloper.arkat;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Created by Denis Bachtiar on 10/11/2017.
 */


public class Slide {

    @SerializedName("id_slide")
    @Expose
    private String id_slide;

    @SerializedName("judul")
    @Expose
    private String judul;

    @SerializedName("gambar")
    @Expose
    private String gambar;

    public Slide() {
    }

    public String getId_slide() {
        return id_slide;
    }

    public void setId_slide(String id_slide) {
        this.id_slide = id_slide;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }
}
