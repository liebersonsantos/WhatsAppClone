package com.example.lieberson.whatsappclone.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lieberson.whatsappclone.R;
import com.example.lieberson.whatsappclone.config.ConfiguracaoFirebase;
import com.example.lieberson.whatsappclone.helper.Permissao;
import com.example.lieberson.whatsappclone.helper.Preferencias;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Random;

public class LoginActivity extends AppCompatActivity {

    private DatabaseReference referenciaFirebase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        referenciaFirebase = ConfiguracaoFirebase.getFirebase();
        referenciaFirebase.child("pontos").setValue(800);


    }

    public void abrirCadastroUsuario(View view){

        Intent intent = new Intent(LoginActivity.this, CadastroUsuarioActivity.class);
        startActivity(intent);



    }


}
