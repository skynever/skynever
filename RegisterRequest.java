package com.example.resgister.request;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    //서버 URL
    final static private String URL = "http://skynever.dothome.co.kr/insert.php";
    private final Map<String, String> map;

    public RegisterRequest(String type,
                           String id,
                           String password,
                           String name,
                           String phone,
                           Response.Listener<String> listener,
                           Response.ErrorListener errorListener) {
        super(Method.POST, URL, listener, errorListener);

        map = new HashMap<>();
        map.put("type", type);
        map.put("id", id);
        map.put("password", password);
        map.put("name", name);
        map.put("phone", phone);
    }

    @Nullable
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}

