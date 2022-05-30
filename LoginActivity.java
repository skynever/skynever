package com.example.resgister;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.resgister.data.User;
import com.example.resgister.request.LoginRequest;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements TextWatcher {

    private TextInputLayout idTextField, passwordTextField;
    private TextView errorMessageTextView;
    private MaterialButton signInButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initUi();
    }

    private void initUi() {
        idTextField = findViewById(R.id.id_text_field);
        passwordTextField = findViewById(R.id.password_text_field);
        errorMessageTextView = findViewById(R.id.error_message_text_view);
        signInButton = findViewById(R.id.sign_in_button);
        View findIdButton = findViewById(R.id.find_id_button);
        View findPasswordButton = findViewById(R.id.find_password_button);
        View signUpButton = findViewById(R.id.sign_up_button);

        idTextField.getEditText().addTextChangedListener(this);
        passwordTextField.getEditText().addTextChangedListener(this);

        signInButton.setOnClickListener(v -> {
            String id = idTextField.getEditText().getText().toString().trim();
            String password = passwordTextField.getEditText().getText().toString().trim();

            signIn(id, password);
        });

        findIdButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, FindIdActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });

        findPasswordButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, FindPasswordActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });

        signUpButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });
    }

    private boolean getTextFieldValidation() {
        String id = idTextField.getEditText().getText().toString().trim();
        String password = passwordTextField.getEditText().getText().toString().trim();

        return !id.isEmpty() && !password.isEmpty();
    }

    private void signIn(String id, String password) {
        errorMessageTextView.setVisibility(View.GONE);

        Response.Listener<String> responseListener = response -> {
            Log.d("LoginActivity", response);

            try {
                JSONObject jsonObject = new JSONObject(response);
                boolean success = jsonObject.getBoolean("success");

                if (success) {
                    User user = new User(jsonObject.getJSONObject("result"));
                    Toast.makeText(getApplicationContext(), "로그인 성공하셨습니다", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("ARG_USER", user);
                    startActivity(intent);
                    finish();

                } else {
                    String cause = jsonObject.getString("result");

                    switch (cause) {
                        case "USER_NOT_FOUND":
                            errorMessageTextView.setText("사용자를 찾을 수 없습니다.");
                            errorMessageTextView.setVisibility(View.VISIBLE);
                            break;

                        case "INCORRECT_PASSWORD":
                            errorMessageTextView.setText("비밀번호가 틀렸습니다.");
                            errorMessageTextView.setVisibility(View.VISIBLE);
                            break;

                        default:
                            errorMessageTextView.setText("로그인 실패하셨습니다. 잠시 후 다시 시도해 주세요.");
                            errorMessageTextView.setVisibility(View.VISIBLE);
                            break;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        };

        LoginRequest loginRequest = new LoginRequest(id, password, responseListener);
        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        queue.add(loginRequest);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        signInButton.setEnabled(getTextFieldValidation());
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }
}