package com.flyeek.dev.demo.network.http.retrofit.request;

import org.json.JSONObject;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * @author flyeek
 * @version created at 15/9/18.
 */
public interface GithubRequest {

    public static final String END_POINT = "https://api.github.com";

    /**
     * for example, <href>https://api.github.com/users/octocat/repos</href>
     *
     * @param user the user id(name) to which the result repos belong.
     * @param callback asynchronized request result.
     */
    @GET("/users/{user}/repos")
    void listRepos(@Path("user") String user, Callback<JSONObject> callback);
}
