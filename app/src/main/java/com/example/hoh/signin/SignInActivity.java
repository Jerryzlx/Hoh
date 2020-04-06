package com.example.hoh.signin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.hoh.MainActivity;
import com.example.hoh.R;

public class SignInActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String usernameKey = "username";

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.hoh",Context.MODE_PRIVATE);

        if(!sharedPreferences.getString(usernameKey,"").equals("")){
            String str = sharedPreferences.getString(usernameKey,"");
            goToMainActivity(str);
        }else {
            setContentView(R.layout.activity_signin);
        }
    }

    public void login(View view){
        EditText myTextField = (EditText)findViewById(R.id.username);
        String str = myTextField.getText().toString();

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.hoh", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("username",str).apply();

        goToMainActivity(str);
    }

    private void goToMainActivity(String s) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("Welcome",s);
        startActivity(intent);
    }



}
