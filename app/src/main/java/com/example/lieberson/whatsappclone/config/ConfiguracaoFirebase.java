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

    public static DatabaseReference getFirebase(){

        if( referenciaFirebase == null ){
            referenciaFirebase = FirebaseDatabase.getInstance().getReference();
        }

        return referenciaFirebase;
    }

    public static FirebaseAuth getFirebaseAutenticacao(){
        if( autenticacao == null ){
            autenticacao = FirebaseAuth.getInstance();
        }
        return autenticacao;
    }

}