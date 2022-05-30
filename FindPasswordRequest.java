package com.example.resgister.request;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class FindPasswordRequest extends StringRequest {
    //서버 URL
    final static private String URL = "http://skynever.dothome.co.kr/find_password.php";
    private final Map<String, String> map;

    public FindPasswordRequest(String id, String name, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("id", id);
        map.put("name", name);
    }

    @Nullable
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}

