package com.danieloliveira.login.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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


}
