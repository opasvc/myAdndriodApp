package com.example.app;

import static com.example.app.common.UrlString.KEY_IP;
import static com.example.app.common.UrlString.KEY_PORT;
import static com.example.app.common.UrlString.KEY_URL;
import static com.example.app.common.UrlString.KEY_XIEYI;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        textView = findViewById(R.id.response_text);

//        Button loginButton = findViewById(R.id.login_button);
//        textView.setText("正在发起请求");
//        loginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sendNetworkRequest();
//            }
//        });
    }


    private void sendNetworkRequest() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(KEY_XIEYI + KEY_IP + ":" + KEY_PORT + "//" + KEY_URL)
                .build();
        //使用新线程执行网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                try (Response response = client.newCall(request).execute()) {
                    if (response.isSuccessful()) {
                        String responseData = response.body().string();
                        runOnUiThread(() -> textView.setText(responseData));
                    }
                } catch (IOException e) {
                    runOnUiThread(() -> textView.setText("Request failed"));
                    throw new RuntimeException(e);
                }

            }
        }).start();
    }
}