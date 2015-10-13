package com.flyeek.dev.demo.network.http.retrofit;

import retrofit.RestAdapter;
import retrofit.converter.Converter;

/**
 * Created by flyeek on 9/17/15.
 */
public class RetrofitService {

    // No need to instantiate this class.
    private RetrofitService() {
    }


    public static <T> T createRequest(String endPoint, Class<T> serviceClass, Converter converter) {
        RestAdapter.Builder builder = new RestAdapter.Builder();
        builder.setEndpoint(endPoint);
        if (converter != null) {
            builder.setConverter(converter);
        }

        return builder.build().create(serviceClass);
    }
}
