package com.example.app1;

import static com.example.app1.common.UrlCommon.KEY_LOGIN_URL;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import okhttp3.*;

import java.io.IOException;

import org.json.JSONObject;

/**
 * 登录界面Activity
 * 处理用户登录相关的逻辑
 */
public class LoginActivity extends AppCompatActivity {
    private static final String PREF_NAME = "user_pref";
    private static final String KEY_TOKEN = "token";
    
    // 用户名输入框
    private TextInputEditText etUsername;
    // 密码输入框
    private TextInputEditText etPassword;
    // 登录按钮
    private Button btnLogin;
    // 注册链接文本
    private TextView tvRegister;
    // 加载动画
    private ProgressBar progressBar;
    // OkHttpClient实例
    private final OkHttpClient client = new OkHttpClient();
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 调用父类的onCreate方法
        super.onCreate(savedInstanceState);
        // 设置页面布局
        setContentView(R.layout.activity_login);

        // 初始化SharedPreferences
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        
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
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);
        progressBar = findViewById(R.id.progressBar);
    }

    /**
     * 设置按钮点击等监听器
     */
    private void setupListeners() {
        // 设置登录按钮点击监听器
        btnLogin.setOnClickListener(v -> {
            // 获取输入的用户名和密码
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();

            // 验证输入是否为空
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "请输入账号和密码", Toast.LENGTH_SHORT).show();
                return;
            }

            // 显示加载动画
            showLoading(true);

            // 发起登录请求
            login(username, password);
        });

        // 设置注册链接点击监听器
        tvRegister.setOnClickListener(v -> {
            // 跳转到注册页面，添加无动画效果
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
        });
    }

    /**
     * 发起登录请求
     *
     * @param username 用户名
     * @param password 密码
     */
    private void login(String username, String password) {
        try {
            // 构建请求体
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("username", username);
            jsonBody.put("password", password);

            // 创建请求
            Request request = new Request.Builder()
                    .url("http://192.168.199.116:18080/user-server/userInfo/login")
                    .post(RequestBody.create(
                            MediaType.parse("application/json; charset=utf-8"),
                            jsonBody.toString()
                    ))
                    .build();

            // 异步执行请求
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    // 在主线程中处理失败情况
                    runOnUiThread(() -> {
                        showLoading(false);
                        Toast.makeText(LoginActivity.this,
                                "登录失败，请检查网络连接",
                                Toast.LENGTH_SHORT).show();
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    runOnUiThread(() -> {
                        showLoading(false);
                        if (response.isSuccessful()) {
                            try {
                                String responseData = response.body().string();
                                JSONObject jsonResponse = new JSONObject(responseData);
                                
                                int code = jsonResponse.optInt("code");
                                String msg = jsonResponse.optString("msg");
                                
                                if (code == 200) {
                                    // 登录成功，保存token
                                    String token = jsonResponse.optString("data");
                                    saveToken(token);
                                    
                                    Toast.makeText(LoginActivity.this, 
                                        msg, 
                                        Toast.LENGTH_SHORT).show();
                                    
                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // 登录失败，显示错误信息
                                    Toast.makeText(LoginActivity.this, 
                                        msg, 
                                        Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                Toast.makeText(LoginActivity.this, 
                                    "解析响应数据失败", 
                                    Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, 
                                "登录失败，请稍后重试", 
                                Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        } catch (Exception e) {
            showLoading(false);
            Toast.makeText(this, "创建请求失败", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 显示或隐藏加载动画
     *
     * @param show 是否显示加载动画
     */
    private void showLoading(boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
            btnLogin.setEnabled(false);
        } else {
            progressBar.setVisibility(View.GONE);
            btnLogin.setEnabled(true);
        }
    }

    private void saveToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_TOKEN, token);
        editor.apply();
    }
} 