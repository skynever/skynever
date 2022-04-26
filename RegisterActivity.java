package com.example.resgister;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.resgister.request.RegisterRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private EditText et_id, et_pass, et_name, et_phone;
    private Button btn_register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_id = findViewById(R.id.et_id);
        et_name = findViewById(R.id.et_name);
        et_pass = findViewById(R.id.et_pass);
        et_phone = findViewById(R.id.et_phone);
        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String USERID = et_id.getText().toString();
                String USERPass = et_pass.getText().toString();
                String USERCompany = et_name.getText().toString();
                String USERPhone = et_phone.getText().toString();

                if (USERID.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "ID를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (USERPass.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (USERCompany.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "병원상호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (USERPhone.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "전화번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                signUp(USERID, USERPass, USERCompany, USERPhone);
            }
        });
    }

    private void signUp(String id, String password, String company, String phone) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("RegisterActivity", response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");

                    if (success) {
                        Toast.makeText(getApplicationContext(), "회원가입 성공하셨습니다", Toast.LENGTH_SHORT).show();
                        finish();

                    } else {
                        Toast.makeText(getApplicationContext(), "회원등록에 실패하셨습니다", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        RegisterRequest registerRequest = new RegisterRequest(id, password, company, phone, responseListener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("Register", error.toString());
            }
        });

        RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
        queue.add(registerRequest);
    }
}