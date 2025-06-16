package com.example.app1.ui.auth;

import com.example.app1.common.UrlCommon;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app1.R;
import com.example.app1.ui.HomeActivity;
import com.google.android.material.textfield.TextInputEditText;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText etUsername;
    private TextInputEditText etPassword;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);

        findViewById(R.id.btnLogin).setOnClickListener(v -> login());
        findViewById(R.id.tvRegister).setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }

    private void login() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "请输入用户名和密码", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = UrlCommon.KEY_LOGIN_URL;
        RequestQueue queue = Volley.newRequestQueue(this);

        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("username", username);
            jsonBody.put("password", password);
            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    response -> {
                        Log.d(TAG, "收到响应: " + response);
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            if (jsonResponse.getInt("code") == 200) {
                                String token = jsonResponse.getString("data");
                                
                                // 保存token
                                SharedPreferences sp = getSharedPreferences("user_info", MODE_PRIVATE);
                                sp.edit().putString("token", token).apply();
                                
                                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                finish();
                            } else {
                                String message = jsonResponse.getString("msg");
                                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Log.e(TAG, "解析响应数据失败", e);
                            Toast.makeText(LoginActivity.this, "解析响应数据失败", Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        Log.e(TAG, "请求失败", error);
                        String errorMessage;
                        if (error.networkResponse == null) {
                            errorMessage = "无法连接到服务器，请检查网络连接";
                        } else {
                            errorMessage = "登录失败: " + error.getMessage();
                        }
                        Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() {
                    return requestBody.getBytes();
                }
            };

            queue.add(stringRequest);
        } catch (JSONException e) {
            Log.e(TAG, "创建请求体失败", e);
            Toast.makeText(this, "创建请求体失败", Toast.LENGTH_SHORT).show();
        }
    }
}