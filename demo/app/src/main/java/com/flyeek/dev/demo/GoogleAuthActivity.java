package com.flyeek.dev.demo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.flyeek.dev.demo.network.http.retrofit.RetrofitService;
import com.flyeek.dev.demo.network.http.retrofit.converter.JsonObjectConverter;
import com.flyeek.dev.demo.network.http.retrofit.request.GoogleAuthRequest;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class GoogleAuthActivity extends Activity {

    final String client_id = "106762758820-033oki27k6otff4u61mihplp2t4ec2c1.apps.googleusercontent.com";
    final String client_secret = "S-xfAqi-OH5yIZppjFYLe8nQ";
    final String redirect_url = "http://localhost";

    private Button mBtnGetEmail;
    private TextView mTxtEmail;
    private WebView mWebView;
    private Handler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler();

    }

    @Override
    protected void onResume() {
        super.onResume();

        final String url = "https://accounts.google.com/o/oauth2/auth?client_id=106762758820-033oki27k6otff4u61mihplp2t4ec2c1.apps.googleusercontent.com&redirect_uri=http://localhost&scope=email&response_type=code&access_type=online&prompt=select_account";

        WebView webview = new WebView(this);
        webview.setVisibility(View.VISIBLE);
        webview.getSettings().setJavaScriptEnabled(true);
        setContentView(webview);

        webview.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.i("fly", "get auth url = " + url);

                if (url.startsWith("http://localhost")) {
                    if (url.contains("code=")) {
                        final String code = url.substring("http://localhost".length() + 7,
                                url.length());
                        Log.i("fly", "code = " + code);

                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                // use code to get token.
                                getToken(code);
                            }
                        });
                    } else {
                        Toast.makeText(GoogleAuthActivity.this, "user not auth", Toast
                                .LENGTH_SHORT).show();
                    }
                }
            }
        });

        webview.loadUrl(url);
    }

    private void getToken(String code) {
        final GoogleAuthRequest tokenRequest = RetrofitService.createRequest(GoogleAuthRequest.END_POINT,
                GoogleAuthRequest.class, new JsonObjectConverter());
        tokenRequest.getToken(code, client_id, client_secret, redirect_url, GoogleAuthRequest
                .GRANT_TYPE, new Callback<JSONObject>() {
            @Override
            public void success(JSONObject jsonObject, Response response) {
                Log.i("fly", "token json = " + jsonObject.toString());

                String token;
                try {
                    token = jsonObject.getString("access_token");
                } catch (JSONException e) {
                    token = "";
                }

                if (!TextUtils.isEmpty(token)) {
                    Log.i("fly", "token = " + token);

                    getEmail(token);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.i("fly", "get token failed = " + error.getMessage());
            }
        });
    }

    private void getEmail(String accessToken) {
        final GoogleAuthRequest tokenRequest = RetrofitService.createRequest(GoogleAuthRequest.END_POINT,
                GoogleAuthRequest.class, new JsonObjectConverter());
        tokenRequest.getEmail("Bearer " + accessToken, new Callback<JSONObject>() {
            @Override
            public void success(JSONObject jsonObject, Response response) {
                Log.i("fly", "user info json = " + jsonObject.toString());

                String email;
                try {
                    email = jsonObject.getString("email");
                } catch (JSONException e) {
                    email = "";
                }

                if (!TextUtils.isEmpty(email)) {
                    Log.i("fly", "email = " + email);
                    CookieManager.getInstance().removeAllCookie();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.i("fly", "get email failed = " + error.getMessage());
            }
        });
    }
}
