package com.example.resgister;

import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.resgister.request.RegisterRequest;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity implements TextWatcher {

    private RadioGroup userTypeGroup;
    private TextInputLayout idTextField, passwordTextField, nameTextField, phoneTextField;
    private MaterialButton registerButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initUi();
    }

    private void initUi() {
        ((MaterialToolbar) findViewById(R.id.toolbar)).setNavigationOnClickListener(v -> onBackPressed());

        userTypeGroup = findViewById(R.id.user_type_group);
        userTypeGroup.setOnCheckedChangeListener((radioGroup, i) -> {
            Log.d("RegisterActivity", "onCheckedChange");

            if (i == R.id.pharmacy_button) {
                nameTextField.getEditText().setHint("병원상호");

            } else {
                nameTextField.getEditText().setHint("이름");
            }
        });

        idTextField = findViewById(R.id.id_text_field);
        passwordTextField = findViewById(R.id.password_text_field);
        nameTextField = findViewById(R.id.name_text_field);
        phoneTextField = findViewById(R.id.phone_text_field);
        registerButton = findViewById(R.id.register_button);

        idTextField.getEditText().addTextChangedListener(this);
        passwordTextField.getEditText().addTextChangedListener(this);
        nameTextField.getEditText().addTextChangedListener(this);
        phoneTextField.getEditText().addTextChangedListener(this);
        phoneTextField.getEditText().addTextChangedListener(new PhoneNumberFormattingTextWatcher("KR"));

        registerButton.setOnClickListener(v -> {
            String userType = (String) userTypeGroup.findViewById(userTypeGroup.getCheckedRadioButtonId()).getTag();
            String id = idTextField.getEditText().getText().toString().trim();
            String password = passwordTextField.getEditText().getText().toString().trim();
            String name = nameTextField.getEditText().getText().toString().trim();
            String phone = phoneTextField.getEditText().getText().toString().trim();

            signUp(userType, id, password, name, phone);
        });
    }

    private boolean getTextFieldValidation() {
        String id = idTextField.getEditText().getText().toString().trim();
        String password = passwordTextField.getEditText().getText().toString().trim();
        String name = nameTextField.getEditText().getText().toString().trim();
        String phone = phoneTextField.getEditText().getText().toString().trim();

        return !id.isEmpty() && !password.isEmpty() && !name.isEmpty() && !phone.isEmpty();
    }

    private void signUp(String userType, String id, String password, String company, String phone) {
        Response.Listener<String> responseListener = response -> {
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
        };

        RegisterRequest registerRequest = new RegisterRequest(userType, id, password, company, phone, responseListener,
                error -> Log.v("Register", error.toString()));

        RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
        queue.add(registerRequest);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        registerButton.setEnabled(getTextFieldValidation());
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }
}