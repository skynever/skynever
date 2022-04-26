package com.example.resgister.request;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {
    //서버 URL
    final static private String URL = "http://skynever.dothome.co.kr/login.php";
    private Map<String, String> map;

    public LoginRequest(String USERID, String USERPassword, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("USERID", USERID);
        map.put("USERPassword", USERPassword);
    }

    @Nullable
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}

