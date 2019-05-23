package com.example.main.webservice;

import com.example.main.model.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface DownloadWebService {

    @POST("user/messages")
    Call<List<Message>> receiveMessages (@Body String id);

}