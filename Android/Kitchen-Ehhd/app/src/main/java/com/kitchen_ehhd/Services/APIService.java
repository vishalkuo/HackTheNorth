package com.kitchen_ehhd.Services;

import com.kitchen_ehhd.Models.Drawer;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by vishalkuo on 15-09-19.
 */
public interface APIService {
    @POST("/api/open/")
    void openDrawerTask(@Body Drawer request, Callback<String> taskCallback);

    @GET("/api/test/open/")
    List<String> resultList();
}


