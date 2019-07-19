package com.example.minymart.apihelper;

import com.example.minymart.model.Cart;
import com.example.minymart.model.respons.ResponsBanner;
import com.example.minymart.model.respons.ResponsCart;
import com.example.minymart.model.respons.ResponsCategories;
import com.example.minymart.model.respons.ResponsProduct;
import com.example.minymart.model.respons.ResponsUser;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface BaseApiService {

    @FormUrlEncoded
    @POST("auth/register")
    Call<ResponseBody> createUser(@Field("first_name") String first_name,
                                  @Field("last_name") String last_name,
                                  @Field("email") String email,
                                  @Field("birthdate") String birthdate,
                                  @Field("gender") String gender,
                                  @Field("height") String height,
                                  @Field("weight") String weight,
                                  @Field("comment") String comment);

    @FormUrlEncoded
    @POST("/auth/login")
    Call<ResponseBody> requestLogin(@Field("email") String email,
                                    @Field("password") String password);

    @FormUrlEncoded
    @POST("purchase/checkout")
    Call<Cart> requestAddCart(@Header("Authorization") String tokenBearer
    );

    @GET("purchase/checkout")
    Call<ResponsCart> getCart(@Header("Authorization") String tokenBearer);

    @GET("purchase/payment")
    Call<ResponsCart> getPay(@Header("Authorization") String tokenBearer);

    @GET("profile")
    Call<ResponseBody> getProfile(@Header("Authorization") String tokenBearer);

    @GET("promo/banner")
    Call<ResponsBanner> getBanner();

    @GET("product/categories")
    Call<ResponsCategories> getCategories();

    @GET("product/products")
    Call<ResponsProduct> getProductResult(@Query("category") String categoryid,
                                          @Query("page") String page);
}
