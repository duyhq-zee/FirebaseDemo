package com.duyhq.firebasedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class UpdateActivity extends AppCompatActivity {

    EditText bookNameET;
    Button updateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        String key = intent.getStringExtra("key");

        bookNameET = findViewById(R.id.et_book_name);

        updateBtn = findViewById(R.id.btn_update);

        updateBtn.setOnClickListener(v -> {
            MainActivity.getInstance().updateBookByKey(key,
                    bookNameET.getText().toString());

            bookNameET.setText("");

            finish();
        });

    }


}