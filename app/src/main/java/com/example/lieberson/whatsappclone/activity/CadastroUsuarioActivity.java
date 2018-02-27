package com.example.lieberson.whatsappclone.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lieberson.whatsappclone.Model.Usuario;
import com.example.lieberson.whatsappclone.R;
import com.example.lieberson.whatsappclone.config.ConfiguracaoFirebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CadastroUsuarioActivity extends AppCompatActivity {

    private EditText nome;
    private EditText email;
    private EditText senha;
    private Button botaoCadastrar;

    private Usuario usuario;

    private FirebaseAuth autenticacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        nome  = findViewById(R.id.edit_cadastro_nome);
        email = findViewById(R.id.edit_cadastro_email);
        senha = findViewById(R.id.edit_cadastro_senha);
        botaoCadastrar = findViewById(R.id.bt_cadastrar);

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                usuario = new Usuario();

                usuario.setNome(nome.getText().toString());
                usuario.setEmail(email.getText().toString());
                usuario.setSenha(senha.getText().toString());

                cadastrarUsuario();

            }
        });

    }

    private void cadastrarUsuario(){

    autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao(); //esse é o objeto responsavel por fazer a autenticacao do firebase
    autenticacao.createUserWithEmailAndPassword(    //autenticacao por email e senha
            usuario.getEmail(),
            usuario.getSenha()
    ).addOnCompleteListener(CadastroUsuarioActivity.this, new OnCompleteListener<AuthResult>() { //recebe dois parametros. o primeiro é a classe atual, o segundo é o metodo responsavel por verificar se realmente foi feito o cadastro desse usuario
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {

            if (task.isSuccessful()){

                Toast.makeText(CadastroUsuarioActivity.this, "Sucesso ao Cadastrar Usuário", Toast.LENGTH_SHORT).show();

            }else {

                Toast.makeText(CadastroUsuarioActivity.this, "Erro ao Cadastrar Usuário", Toast.LENGTH_SHORT).show();

            }

        }
    });

    }


}
