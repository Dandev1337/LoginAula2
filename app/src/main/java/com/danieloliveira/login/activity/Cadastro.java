package com.danieloliveira.login.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.danieloliveira.login.R;
import com.danieloliveira.login.dao.UserDAO;
import com.danieloliveira.login.model.User;


public class Cadastro extends AppCompatActivity {

    Button btnCad;
    EditText email, senha, nome;

    UserDAO uDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        email = findViewById(R.id.edtEmailCad);
        nome = findViewById(R.id.edtNomeCad);
        senha = findViewById(R.id.edtSenhaCad);
        btnCad = findViewById(R.id.btnCadastro);
        uDao = new UserDAO(getApplicationContext(), new User());

        btnCad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputEmail = email.getText().toString();
                String inputSenha = senha.getText().toString();
                String inputNome = nome.getText().toString();

                if (isValidInput(inputEmail, inputSenha, inputNome)) {
                    // Dados válidos, você pode continuar com o cadastro
                    // Crie um objeto User com os dados fornecidos pelo usuário
                    User newUser = new User(inputNome, inputEmail, inputSenha);

                    // Faça a verificação se o usuário já existe
                    if (!uDao.userExists(inputEmail)) {
                        // Se o usuário não existe, insira-o no banco de dados
                        uDao.insertUser(newUser);
                        Toast.makeText(Cadastro.this, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                        // Redirecione o usuário para a tela de login ou outra atividade apropriada
                    } else {
                        // Caso o usuário já exista, exiba uma mensagem de erro
                        Toast.makeText(Cadastro.this, "Usuário já cadastrado com esse email.", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });


    }

    private boolean isValidInput(String email, String senha, String nome) {
        if (email.isEmpty() || senha.isEmpty() || nome.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    public void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void composeEmail(String[] addresses, String subject) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        // intent.putExtra(Intent.EXTRA_STREAM, attachment);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}