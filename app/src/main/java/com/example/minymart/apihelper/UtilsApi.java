package com.example.minymart.apihelper;

public class UtilsApi {

    static final String BASE_URL_API = "http://private-66c556-trialappapi.apiary-mock.com/";

    public static BaseApiService getApiService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);

    }
}
