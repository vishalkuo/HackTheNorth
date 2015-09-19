package com.kitchen_ehhd.Services;

import com.kitchen_ehhd.Models.Drawer;
import com.kitchen_ehhd.Models.ResponseObject;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by vishalkuo on 15-09-19.
 */
public interface APIService {
    @POST("/api/open/{drawer}")
    void openDrawerTask(@Body String request, Callback<String> taskCallback);

}


