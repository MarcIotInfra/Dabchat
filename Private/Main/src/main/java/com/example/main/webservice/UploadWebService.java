package com.example.main.webservice;

import com.example.main.model.Message;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UploadWebService {
    @POST("send/upload")
    Call<Message> sendMessage (@Body Message message);


}
