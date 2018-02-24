package com.example.lieberson.whatsappclone.activity;

import android.Manifest;
import android.content.DialogInterface;
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

import com.example.lieberson.whatsappclone.R;
import com.example.lieberson.whatsappclone.helper.Permissao;
import com.example.lieberson.whatsappclone.helper.Preferencias;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.HashMap;
import java.util.Random;

public class LoginActivity extends AppCompatActivity {

    private EditText nome;
    private EditText telefone;
    private EditText codPais;
    private EditText codArea;
    private Button cadastrar;
    private String[] permissoesNecessarias = new String[]{
            Manifest.permission.SEND_SMS,
            Manifest.permission.BATTERY_STATS,
            Manifest.permission.INTERNET
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Permissao.validaPermissoes(1,this, permissoesNecessarias);

        nome = findViewById(R.id.edit_nome);
        telefone = findViewById(R.id.edit_telefone);
        codPais = findViewById(R.id.edit_cod_pais);
        codArea = findViewById(R.id.edit_cod_area);
        cadastrar = findViewById(R.id.botaoCadastrarId);

        /*Definindo as máscaras*/
        SimpleMaskFormatter simpleMaskTelefone = new SimpleMaskFormatter("NNNNN-NNNN");
        MaskTextWatcher maskTelefone = new MaskTextWatcher(telefone, simpleMaskTelefone);   //o primeiro parametro é o edit_text, o segundo é a mascara
        telefone.addTextChangedListener(maskTelefone);

        SimpleMaskFormatter simpleMaskCodPais = new SimpleMaskFormatter("+NN");
        MaskTextWatcher maskCodPais = new MaskTextWatcher(codPais, simpleMaskCodPais);
        codPais.addTextChangedListener(maskCodPais);

        SimpleMaskFormatter simpleMaskCodArea =  new SimpleMaskFormatter("NN");
        MaskTextWatcher maskCodArea = new MaskTextWatcher(codArea, simpleMaskCodArea);
        codArea.addTextChangedListener(maskCodArea);

        /*Utilizando TextWatcher() para contar os caracteres e passar o cursos para o proximo editText*/
        codPais.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (codPais.length() == 3){
                    codArea.requestFocus();
                }

            }
        });

        codArea.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (codArea.length() == 2){
                    telefone.requestFocus();
                }

            }
        });

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nomeUsuario = nome.getText().toString();
                String telefoneCompleto =
                        codPais.getText().toString() +
                        codArea.getText().toString() +
                        telefone.getText().toString();

                String telefoneSemFormatacao = telefoneCompleto.replace("+", "");
                telefoneSemFormatacao = telefoneSemFormatacao.replace("-", "");

                /*Gerando token de segurança*/
                Random randomico = new Random();
                int numeroRandomico = randomico.nextInt(9999 - 1000) + 1000;

                String token = String.valueOf(numeroRandomico);

                String mensagemEnvio = "WhatsApp Clone Código de Confirmação" + token;


                /*Salvando os dados para validaçao*/
                Preferencias preferencias = new Preferencias(LoginActivity.this);
                preferencias.salvarUsuarioPreferencias(nomeUsuario, telefoneSemFormatacao, token);

                /*Envio do SMS*/
                boolean enviadoSMS = enviaSMS("+" +telefoneSemFormatacao,mensagemEnvio );

                /*
                HashMap<String, String> usuario = preferencias.getDadosUsuario();
                //Log.i("TOKEN", "T: " + token);
                //Log.i("TELEFONE", "T: " + telefoneSemFormatacao);
                Log.i("TOKEN", "NOME: " + usuario.get("nome") + " FONE: " + usuario.get("telefone"));
                */


            }
        });

    }

    /*Envio do SMS*/
    private boolean enviaSMS(String telefone, String mensagem){

        try{

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(telefone, null, mensagem, null,null);

            return true;
        }catch (Exception e){

            e.printStackTrace();
            return false;
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[]permissions, int[] grantResults){     //Esse metodo é chamado para que trate toda vez que a pessoas negar uma permissao, dando para ver a permissao negada e mostrar para o usuario que ele precisa autorizar a permissao.

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int resultado : grantResults){

            if (resultado == PackageManager.PERMISSION_DENIED){

                alertaValidacaoPermissao();
            }
        }
    }

    private void alertaValidacaoPermissao() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissões negadas");
        builder.setMessage("Para utilizar esse app é necessário aceitar as permissões");

    builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {

            finish();

        }
    });

    AlertDialog dialog = builder.create();
    dialog.show();

    }


}
