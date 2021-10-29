package com.example.wellnesmeter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;


public class ResultActivity extends AppCompatActivity {

    TextView result_score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        result_score = findViewById(R.id.rescore);

        Bundle b = getIntent().getExtras();
        String receivingdata = b.getString("key");
        result_score.setText(receivingdata);
    }
}