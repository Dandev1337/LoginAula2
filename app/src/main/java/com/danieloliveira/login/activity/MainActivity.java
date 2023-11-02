package com.danieloliveira.login.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.danieloliveira.login.R;
import com.danieloliveira.login.dao.UserDAO;
import com.danieloliveira.login.model.User;

public class MainActivity extends AppCompatActivity {
    EditText edtEmail, edtsenha;
    Button btnEntra, btnCadastrar;

    UserDAO uDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        edtEmail = findViewById(R.id.edtEmail);
        edtsenha = findViewById(R.id.edtSenha);
        btnEntra = findViewById(R.id.btnEntrar);
        btnCadastrar = findViewById(R.id.btnCadastrar);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, Cadastro.class);
                startActivity(it);
            }
        });

        btnEntra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sp = getSharedPreferences("appLogin", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("email", edtEmail.getText().toString());
                editor.commit();

                uDAO = new UserDAO(getApplicationContext(),
                new User(edtEmail.getText().toString(),
                        edtsenha.getText().toString()));

                if(uDAO.verificarEmailSenha()){
                Intent redirecionar = new Intent(MainActivity.this, SplashActivity.class);
                startActivity(redirecionar);

                }else {
                    Toast.makeText(MainActivity.this, "Dados Incorretos", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

}