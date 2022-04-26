package com.example.resgister;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.resgister.data.User;
import com.example.resgister.request.LoginRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private EditText et_id, et_pass;
    private Button btn_login, btn_register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et_id = findViewById(R.id.et_id);
        et_pass = findViewById(R.id.et_pass);
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String USERID = et_id.getText().toString().trim();
                String USERPass = et_pass.getText().toString().trim();

                if (USERID.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "ID를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (USERPass.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                login(USERID, USERPass);
            }
        });
    }

    private void login(String id, String password) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("LoginActivity", response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");

                    if (success) {
                        User user = new User(jsonObject.getJSONObject("result"));
                        Toast.makeText(getApplicationContext(), "로그인 성공하셨습니다", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("ARG_USER", user);
                        startActivity(intent);
                        finish();

                    } else {
                        String cause = jsonObject.getString("result");

                        switch (cause) {
                            case "USER_NOT_FOUND":
                                Toast.makeText(getApplicationContext(), "사용자를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();
                                break;

                            case "INCORRECT_PASSWORD":
                                Toast.makeText(getApplicationContext(), "비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                                break;

                            default:
                                Toast.makeText(getApplicationContext(), "로그인 실패하셨습니다", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        LoginRequest loginRequest = new LoginRequest(id, password, responseListener);
        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        queue.add(loginRequest);
    }
}