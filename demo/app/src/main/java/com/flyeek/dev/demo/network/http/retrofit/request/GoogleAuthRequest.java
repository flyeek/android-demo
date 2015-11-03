package com.flyeek.dev.demo.network.http.retrofit.request;

import org.json.JSONObject;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;

/**
 * @author flyeek
 * @version created at 15/9/18.
 */
public interface GoogleAuthRequest {

    public static final String END_POINT = "https://www.googleapis.com/oauth2";
    public static final String END_POINT_USERINFO = "https://www.googleapis.com/oauth2/v3/userinfo";

    public static final String GRANT_TYPE = "authorization_code";

    @FormUrlEncoded
    @POST("/v4/token/")
    void getToken(@Field("code") String code, @Field("client_id") String clientId,
                  @Field("client_secret") String clientSecret, @Field("redirect_uri") String
                          redirectURI, @Field("grant_type") String grantType, Callback<JSONObject>
                          callback);

    @Headers("Accept: application/json")
    @GET("/v3/userinfo/")
    void getEmail(@Header("Authorization") String authorization, Callback<JSONObject> callback);
}
