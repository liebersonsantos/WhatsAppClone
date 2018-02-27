package com.example.lieberson.whatsappclone.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by lieberson on 27/02/18.
 * Essa classe(final) nao pode ser extendida pois será utilizada exclusivamente para configuraçao do firebase.
 */

public final class ConfiguracaoFirebase {

    private static DatabaseReference referenciaFirebase;
    private static FirebaseAuth autenticacao;

    public static DatabaseReference getFirebase(){ /*Ao criar o metodo como static, nao precisamos criar uma instancia da classe. Esse metodo será responsavel por recuperar a instancia do firebase*/

        if (referenciaFirebase == null) {
            referenciaFirebase = FirebaseDatabase.getInstance().getReference();
        }
        return referenciaFirebase;
    }

    public static FirebaseAuth getFirebaseAutenticacao(){ /* Esse metodo ele vai retornar o objeto do firebase que é responsavel pela autenticacao */

        if (autenticacao == null){

            autenticacao = FirebaseAuth.getInstance(); //recupera a instancia do firebase responsavel pela autenticacao

        }
        return autenticacao;

    }




}
