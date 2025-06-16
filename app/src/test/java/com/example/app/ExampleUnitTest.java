package com.example.app;

import static com.example.app1.common.UrlCommon.KEY_LOGIN_URL;

import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

import android.content.Intent;
import android.widget.Toast;

import com.example.app1.HomeActivity;
import com.example.app1.LoginActivity;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private final OkHttpClient client = new OkHttpClient();
    @Test
    public void addition_isCorrect() {
//        assertEquals(4, 2 + 2);
        String username = "admin";
        String password = "12354";
        try {
            // 构建请求体
            Map<String, String> map = Map.of("username", "admin", "password", "@Qa123456");

            // 创建请求
            Request request = new Request.Builder()
                    .url(KEY_LOGIN_URL) // 替换为实际的登录API地址
                    .post(RequestBody.create(
                            MediaType.parse("application/json; charset=utf-8"),
                            map.toString()
                    ))
                    .build();

            // 异步执行请求
            Response execute = client.newCall(request).execute();
            ResponseBody body = execute.body();
            System.out.println(body.string().toString());

            
        }catch (Exception e){

        }

    }
}