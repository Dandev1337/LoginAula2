package com.danieloliveira.login.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.danieloliveira.login.activity.Cadastro;
import com.danieloliveira.login.helper.DBhelper;
import com.danieloliveira.login.model.User;

public class UserDAO {
    private User user;

    private DBhelper db;

    public UserDAO (Context ctx, User user){
        this.db = new DBhelper(ctx);
        this.user = user;


    }

    public Boolean verificarEmailSenha(){
        SQLiteDatabase dbLite = this.db.getReadableDatabase();
        String sql = "SELECT * FROM user WHERE email =? AND senha = ?";
        Cursor cursor = dbLite.rawQuery(sql, new String[]{
                this.user.getEmail(),
                this.user.getSenha(),

        });

        if(cursor.getCount()>0){
            return true;
        }

        return false;
    }
    public User obterUserByEmail(){
        SQLiteDatabase dbLite = this.db.getReadableDatabase();
        String sql = "SELECT * FROM user where email = ?;";

        Cursor c = dbLite.rawQuery(sql, new String[]{this.user.getEmail()});

        if(c != null){
            c.moveToFirst();
        }

        this.user.setEmail(c.getString(0));
        this.user.setSenha(c.getString(1));
        this.user.setNome(c.getString(2));

        return this.user;
    }

    public boolean userExists(String email) {
        SQLiteDatabase dbLite = this.db.getReadableDatabase();
        Cursor cursor = null;

        try {
            String query = "SELECT * FROM user WHERE email = ?";
            cursor = dbLite.rawQuery(query, new String[]{email});
            return cursor.getCount() > 0;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
    }

    public boolean insertUser(User user) {
        SQLiteDatabase dbLite = this.db.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("email", user.getEmail());
        values.put("senha", user.getSenha());
        values.put("nome", user.getNome());

        long result = dbLite.insert("user", null, values);

        return result != -1;
    }


}
