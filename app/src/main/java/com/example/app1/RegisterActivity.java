package com.example.app1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;

/**
 * 注册界面Activity
 * 处理用户注册相关的逻辑
 */
public class RegisterActivity extends AppCompatActivity {
    // 用户名输入框
    private TextInputEditText etUsername;
    // 密码输入框
    private TextInputEditText etPassword;
    // 确认密码输入框
    private TextInputEditText etConfirmPassword;
    // 注册按钮
    private Button btnRegister;
    // 登录链接文本
    private TextView tvLogin;
    // 加载动画
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 调用父类的onCreate方法
        super.onCreate(savedInstanceState);
        // 设置页面布局
        setContentView(R.layout.activity_register);

        // 初始化视图
        initViews();
        // 设置监听器
        setupListeners();
    }

    /**
     * 初始化视图组件
     */
    private void initViews() {
        // 通过ID查找并绑定视图组件
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        tvLogin = findViewById(R.id.tvLogin);
        progressBar = findViewById(R.id.progressBar);
    }

    /**
     * 设置按钮点击等监听器
     */
    private void setupListeners() {
        // 设置注册按钮点击监听器
        btnRegister.setOnClickListener(v -> {
            // 获取输入的用户名、密码和确认密码
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();
            String confirmPassword = etConfirmPassword.getText().toString();

            // 验证输入
            if (username.isEmpty()) {
                Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
                return;
            }
            if (password.isEmpty()) {
                Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                return;
            }
            if (confirmPassword.isEmpty()) {
                Toast.makeText(this, "请确认密码", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!password.equals(confirmPassword)) {
                Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                return;
            }

            // 显示加载动画
            showLoading(true);
            
            // 模拟注册过程
            new android.os.Handler().postDelayed(() -> {
                // 隐藏加载动画
                showLoading(false);
                
                // TODO: 实现实际的注册逻辑
                Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
                // 关闭当前页面
                finish();
            }, 1500); // 延迟1.5秒，模拟网络请求
        });

        // 设置登录链接点击监听器
        tvLogin.setOnClickListener(v -> {
            // 返回登录页面，添加无动画效果
            finish();
            overridePendingTransition(0, 0);
        });
    }
    
    /**
     * 显示或隐藏加载动画
     * @param show 是否显示加载动画
     */
    private void showLoading(boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
            btnRegister.setEnabled(false);
        } else {
            progressBar.setVisibility(View.GONE);
            btnRegister.setEnabled(true);
        }
    }
} 