package com.example.lieberson.whatsappclone.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lieberson.whatsappclone.Model.Mensagem;
import com.example.lieberson.whatsappclone.R;
import com.example.lieberson.whatsappclone.adapter.MensagemAdapter;
import com.example.lieberson.whatsappclone.config.ConfiguracaoFirebase;
import com.example.lieberson.whatsappclone.helper.Base64Custom;
import com.example.lieberson.whatsappclone.helper.Preferencias;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ConversaActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText editMensagem;
    private ImageButton btMensagem;
    private DatabaseReference fireBase;
    private ListView listView;
    private ArrayList<Mensagem> mensagens;
    private ArrayAdapter<Mensagem> adapter;

    private ValueEventListener valueEventListenerMensagem;

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
        listView = findViewById(R.id.lv_conversas);

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

        //Montando listView e adapter
        mensagens = new ArrayList<>();

        adapter = new MensagemAdapter(ConversaActivity.this, mensagens);



        listView.setAdapter(adapter);

        //Recuperando as mensagens do Firebase
        fireBase = ConfiguracaoFirebase.getFirebase()
                    .child("mensagens")
                    .child(idUsuarioRemetente)
                    .child(idUsuarioDestinatario);

        //Criando Listener para as mensagens
        valueEventListenerMensagem = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Limpando as mensagens
                mensagens.clear();

                //Recuperando mensagens
                for (DataSnapshot dados : dataSnapshot.getChildren()){

                    Mensagem mensagem = dados.getValue(Mensagem.class);
                    mensagens.add(mensagem);
                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        fireBase.addValueEventListener(valueEventListenerMensagem);


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

                    //Salvando mensagem para o remetente
                    salvarMensagem(idUsuarioRemetente, idUsuarioDestinatario, mensagem);

                    //Salvando mensagem para o destinatario
                    salvarMensagem(idUsuarioDestinatario, idUsuarioRemetente, mensagem);

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

    @Override
    protected void onStop() {
        super.onStop();

        fireBase.removeEventListener(valueEventListenerMensagem);

     }
}
