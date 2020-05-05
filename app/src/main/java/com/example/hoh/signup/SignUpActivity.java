package com.example.hoh.signup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hoh.MainActivity;
import com.example.hoh.R;
import com.example.hoh.bean.BaseResponseBean;
import com.example.hoh.model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;

import static com.example.hoh.constant.AppConfig.SIGN_IN;
import static com.example.hoh.constant.AppConfig.SIGN_UP;

public class SignUpActivity extends AppCompatActivity {
    private static final String ACTIVITY_TAG="SignUpLog";
    private EditText text_username;
    private EditText text_password;
    private final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        text_username = (EditText) findViewById(R.id.editText_username2);
        text_password = (EditText) findViewById(R.id.editText_password2);
        Button back = (Button) findViewById(R.id.back_Button);
        String usernameKey = "username";

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

//        if(!sharedPreferences.getString(usernameKey,"").equals("")){
//            String str = sharedPreferences.getString(usernameKey,"");
//            goToMainActivity(str);
//        }else {
//            setContentView(R.layout.activity_signin);
//        }
    }

    public void register(View view){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String responseData = null;
                String username = text_username.getText().toString();
                String password = text_password.getText().toString();
//                //build the URL for login
//                HttpUrl.Builder urlBuilder = HttpUrl.parse(SIGN_IN).newBuilder();
//                urlBuilder.addQueryParameter("username", username);
//                urlBuilder.addQueryParameter("password", password);
                String url = SIGN_UP;
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
                        .url(SIGN_UP)
                        .build();
                Log.i(ACTIVITY_TAG, request.toString());
                try {
                    Response response = client.newCall(request).execute();//发送请求
                    String result = response.body().string();

                    //get the type of data
                    Type jsonType = new TypeToken<BaseResponseBean<Integer>>(){}.getType();
                    BaseResponseBean<Integer> bean = new Gson().fromJson(result, jsonType);
                    if (bean.getData() == null) {
                        Log.d(ACTIVITY_TAG, "result: Sign up failed");

                        //show Toast
                        Looper.prepare();
                        Toast.makeText(SignUpActivity.this, "Username has been used!", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    } else{
                        Log.d(ACTIVITY_TAG, "result: Sign in successfully"+result);
                        Log.d(ACTIVITY_TAG, "username: " + username);
                        SharedPreferences sharedPreferences = getSharedPreferences("com.example.hoh", Context.MODE_PRIVATE);
                        sharedPreferences.edit().putString("username",username).apply();
                        sharedPreferences.edit().putInt("id",bean.getData()).apply();
                        goToMainActivity(username, bean.getData());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }


    private void goToMainActivity(String username, int id) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("username",username);
        intent.putExtra("id", id);
        startActivity(intent);
    }



}
