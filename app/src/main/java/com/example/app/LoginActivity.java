package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {


    private EditText etUsername, etPassword;
    private Button btnLogin;
    private TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 初始化控件
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);

        // 登录按钮点击事件
        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();
            // 实现登录逻辑
            if (validateInput(username, password)) {
                login(username, password);
            }
        });

        // 注册文本点击事件
        tvRegister.setOnClickListener(v -> {
            // 跳转到注册页面
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    // 验证输入
    private boolean validateInput(String username, String password) {
        if (username.isEmpty()) {
            etUsername.setError("用户名不能为空");
            return false;
        }
        if (password.isEmpty()) {
            etPassword.setError("密码不能为空");
            return false;
        }
        return true;
    }

    // 登录逻辑
    private void login(String username, String password) {
        // 实现登录逻辑，例如调用 API
        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
    }

}
