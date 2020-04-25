package com.example.hoh.signin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;

import com.example.hoh.MainActivity;
import com.example.hoh.R;
import com.example.hoh.model.User;
import com.google.gson.Gson;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import static com.example.hoh.constant.AppConfig.SIGN_IN;

public class SignInActivity extends AppCompatActivity {
    private static final String ACTIVITY_TAG="SignInLog";
    private EditText text_username;
    private EditText text_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        text_username = (EditText) findViewById(R.id.editText_username);
        text_password = (EditText) findViewById(R.id.editText_password);
        String usernameKey = "username";

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.hoh",Context.MODE_PRIVATE);

//        if(!sharedPreferences.getString(usernameKey,"").equals("")){
//            String str = sharedPreferences.getString(usernameKey,"");
//            goToMainActivity(str);
//        }else {
//            setContentView(R.layout.activity_signin);
//        }
    }

    public void login(View view){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                String responseData = null;
                String username = text_username.getText().toString();
                String password = text_password.getText().toString();
//                //build the URL for login
//                HttpUrl.Builder urlBuilder = HttpUrl.parse(SIGN_IN).newBuilder();
//                urlBuilder.addQueryParameter("username", username);
//                urlBuilder.addQueryParameter("password", password);
                String url = SIGN_IN;
                //设置媒体类型。application/json表示传递的是一个json格式的对象
                MediaType mediaType = MediaType.parse("application/json");
                //使用JSONObject封装参数
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("username",username);
                    jsonObject.put("password", password);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //创建RequestBody对象，将参数按照指定的MediaType封装
                RequestBody requestBody = RequestBody.create(mediaType,jsonObject.toString());
                Request request = new Request.Builder()
                .post(requestBody)//Post请求的参数传递
                .url(SIGN_IN)
                .build();
                Log.i(ACTIVITY_TAG, request.toString());
                try {
                    Response response = client.newCall(request).execute();//发送请求
                    String result = response.body().string();

                    Gson gson = new Gson();
                    User user = gson.fromJson(result, User.class);
                    Log.d(ACTIVITY_TAG, "result: "+result);
                    Log.d(ACTIVITY_TAG, "data" + user.getUsername());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                SharedPreferences sharedPreferences = getSharedPreferences("com.example.hoh", Context.MODE_PRIVATE);
                sharedPreferences.edit().putString("username",username).apply();
//        goToMainActivity(username);
            }
        }).start();
    }

    private void goToMainActivity(String s) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("Welcome",s);
        startActivity(intent);
    }



}
