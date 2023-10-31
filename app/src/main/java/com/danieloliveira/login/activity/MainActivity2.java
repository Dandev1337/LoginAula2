package com.danieloliveira.login.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.danieloliveira.login.R;
import com.danieloliveira.login.dao.UserDAO;
import com.danieloliveira.login.model.User;

public class MainActivity2 extends AppCompatActivity {
    TextView txtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        txtEmail = findViewById(R.id.txtEmail);

        SharedPreferences sp = getSharedPreferences("appLogin",Context.MODE_PRIVATE);
        String email = sp.getString("email", "abcd");
        User user = new User();
        user.setEmail(email);
        UserDAO udao = new UserDAO(getApplicationContext(), user);
        user = udao.obterUserByEmail();
        txtEmail.setText(email);


       // Intent it = getIntent();
       // String email = it.getStringExtra("email");

    }
}