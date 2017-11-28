package com.arkat.debeloper.arkat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Denis Bachtiar on 10/11/2017.
 */

public class Response {

    @SerializedName("data_slider")
    @Expose
    private List <Slide> data_slider;

    public Response() {
    }

    public List<Slide> getData_slider() {
        return data_slider;
    }

    public void setData_slider(List<Slide> data_slider) {
        this.data_slider = data_slider;
    }
}
