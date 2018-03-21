package com.example.lieberson.whatsappclone.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.lieberson.whatsappclone.Model.Mensagem;
import com.example.lieberson.whatsappclone.R;
import com.example.lieberson.whatsappclone.config.ConfiguracaoFirebase;
import com.example.lieberson.whatsappclone.helper.Base64Custom;
import com.example.lieberson.whatsappclone.helper.Preferencias;
import com.google.firebase.database.DatabaseReference;

public class ConversaActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText editMensagem;
    private ImageButton btMensagem;
    private DatabaseReference fireBase;

    //Dados do destinatario
    private String nomeUsuarioDestinatario;
    private String idUsuarioDestinatario;

    //Dados do remetente
    private String idUsuarioRemetente;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversa);

        toolbar = findViewById(R.id.tb_conversa);
        editMensagem = findViewById(R.id.edit_mensagem);
        btMensagem = findViewById(R.id.bt_enviar);

        //Dados do usuario logado
        Preferencias preferencias = new Preferencias(ConversaActivity.this);
        idUsuarioRemetente  = preferencias.getIdentificador();

        //Recuperando os dados da Intent
        Bundle extra = getIntent().getExtras();

        if(extra != null){
            nomeUsuarioDestinatario = extra.getString("nome");

            String emailDestinatario = extra.getString("email");
            idUsuarioDestinatario = Base64Custom.codificarBase64(emailDestinatario);

        }

        //Configurando conversa
        toolbar.setTitle(nomeUsuarioDestinatario);
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left);
        setSupportActionBar(toolbar);

        //Enviando as mensagens
        btMensagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String textoMensagem = editMensagem.getText().toString();

                if (textoMensagem.isEmpty()){

                    Toast.makeText(ConversaActivity.this, "Digite uma mensagem para enviar", Toast.LENGTH_SHORT).show();

                }else{

                    Mensagem mensagem = new Mensagem();
                    mensagem.setIdUsuario(idUsuarioRemetente);
                    mensagem.setMensagem(textoMensagem);

                    salvarMensagem(idUsuarioRemetente, idUsuarioDestinatario, mensagem);
                    editMensagem.setText(" ");

                }

            }
        });

    }

     private boolean salvarMensagem(String idRemetente, String idDestinatario, Mensagem mensagem){

        try {

            fireBase = ConfiguracaoFirebase.getFirebase().child("mensagem");

            fireBase.child(idRemetente)
                    .child(idDestinatario)
                    .push()
                    .setValue(mensagem);

            return true;
        }catch (Exception e){

            e.printStackTrace();
            return false;

        }

     }

}
