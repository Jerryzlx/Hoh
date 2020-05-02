package com.example.hoh.signup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
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

public class SignUpActivity extends AppCompatActivity {
    private static final String ACTIVITY_TAG="SignUpLog";
    private EditText text_username;
    private EditText text_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        text_username = (EditText) findViewById(R.id.editText_username2);
        text_password = (EditText) findViewById(R.id.editText_password2);
        String usernameKey = "username";

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.hoh",Context.MODE_PRIVATE);

//        if(!sharedPreferences.getString(usernameKey,"").equals("")){
//            String str = sharedPreferences.getString(usernameKey,"");
//            goToMainActivity(str);
//        }else {
//            setContentView(R.layout.activity_signin);
//        }
    }

    public void register(View view){
        //todo register funcition
    }


    private void goToMainActivity(String s) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("Welcome",s);
        startActivity(intent);
    }



}
