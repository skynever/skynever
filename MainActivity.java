package com.example.resgister;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.resgister.data.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView typeTextView = findViewById(R.id.type_text_view);
        TextView idTextView = findViewById(R.id.id_text_view);
        TextView passwordTextView = findViewById(R.id.password_text_view);
        TextView nameTextView = findViewById(R.id.name_text_view);
        TextView phoneTextView = findViewById(R.id.phone_text_view);

        Intent intent = getIntent();
        User user = intent.getParcelableExtra("ARG_USER");

        typeTextView.setText(user.isPharmacy() ? "약국" : "소비자");
        idTextView.setText(user.getId());
        passwordTextView.setText(user.getPassword());
        nameTextView.setText(user.getName());
        phoneTextView.setText(user.getPhone());
    }
}