package com.example.app1;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class GobangActivity extends AppCompatActivity {
    private GobangView gobangView;
    private Button btnRestart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gobang);

        gobangView = findViewById(R.id.gobangView);
        btnRestart = findViewById(R.id.btnRestart);

        btnRestart.setOnClickListener(v -> gobangView.restart());
    }
} 