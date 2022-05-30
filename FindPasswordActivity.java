package com.example.resgister;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.toolbox.Volley;
import com.example.resgister.request.FindPasswordRequest;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;


public class FindPasswordActivity extends AppCompatActivity implements TextWatcher {

    private TextInputLayout idTextField, nameTextField;
    private MaterialButton nextButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);

        initUi();
    }

    private void initUi() {
        ((MaterialToolbar) findViewById(R.id.toolbar)).setNavigationOnClickListener(v -> onBackPressed());

        idTextField = findViewById(R.id.id_text_field);
        nameTextField = findViewById(R.id.name_text_field);
        nextButton = findViewById(R.id.next_button);

        idTextField.getEditText().addTextChangedListener(this);
        nameTextField.getEditText().addTextChangedListener(this);

        nextButton.setOnClickListener(v -> {
            String id = idTextField.getEditText().getText().toString().trim();
            String name = nameTextField.getEditText().getText().toString().trim();

            findPassword(id, name);
        });
    }

    private boolean getTextFieldValidation() {
        String id = idTextField.getEditText().getText().toString().trim();
        String name = nameTextField.getEditText().getText().toString().trim();

        return !id.isEmpty() && !name.isEmpty();
    }

    private void findPassword(String id, String name) {
        FindPasswordRequest request = new FindPasswordRequest(id, name, response -> {
            Log.d("FindPasswordActivity", response);

            try {
                JSONObject object = new JSONObject(response);
                boolean success = object.getBoolean("success");

                if (success) {
                    String password = object.getString("result");

                    new AlertDialog.Builder(this)
                            .setMessage("회원님의 비밀번호는 " + password + " 입니다.")
                            .setPositiveButton("확인", null)
                            .create()
                            .show();

                } else {
                    Toast.makeText(getApplicationContext(),
                            "사용자를 찾을 수 없습니다. 아이디와 이름을 다시 확인해 주세요.",
                            Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        Volley.newRequestQueue(this).add(request);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        nextButton.setEnabled(getTextFieldValidation());
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }
}