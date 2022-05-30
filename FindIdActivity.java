package com.example.resgister;

import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.toolbox.Volley;
import com.example.resgister.request.FindIdRequest;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;


public class FindIdActivity extends AppCompatActivity implements TextWatcher {

    private TextInputLayout nameTextField, phoneTextField;
    private MaterialButton nextButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_id);

        initUi();
    }

    private void initUi() {
        ((MaterialToolbar) findViewById(R.id.toolbar)).setNavigationOnClickListener(v -> onBackPressed());

        nameTextField = findViewById(R.id.name_text_field);
        phoneTextField = findViewById(R.id.phone_text_field);
        nextButton = findViewById(R.id.next_button);

        nameTextField.getEditText().addTextChangedListener(this);
        phoneTextField.getEditText().addTextChangedListener(this);
        phoneTextField.getEditText().addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        nextButton.setOnClickListener(v -> {
            String name = nameTextField.getEditText().getText().toString().trim();
            String phone = phoneTextField.getEditText().getText().toString().trim();

            findId(name, phone);
        });
    }

    private boolean getTextFieldValidation() {
        String name = nameTextField.getEditText().getText().toString().trim();
        String phone = phoneTextField.getEditText().getText().toString().trim();

        return !name.isEmpty() && !phone.isEmpty();
    }

    private void findId(String name, String phone) {
        FindIdRequest request = new FindIdRequest(name, phone, response -> {
            Log.d("FindIdActivity", response);

            try {
                JSONObject object = new JSONObject(response);
                boolean success = object.getBoolean("success");

                if (success) {
                    String id = object.getString("result");

                    new AlertDialog.Builder(this)
                            .setMessage("회원님의 아이디는 " + id + " 입니다.")
                            .setPositiveButton("확인", null)
                            .create()
                            .show();

                } else {



                    Toast.makeText(getApplicationContext(),
                            "사용자를 찾을 수 없습니다. 이름과 전화번호를 다시 확인해 주세요.",
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