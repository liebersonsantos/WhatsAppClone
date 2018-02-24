package com.example.lieberson.whatsappclone.helper;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lieberson on 24/02/18. *
 * Esta classe verificará as permissoes do app.
 */

public class Permissao {

    /*Esse metodo verifica as permissoes que o usuario
    tem e solicita as permissoes
    */
    public static boolean validaPermissoes(int requestCode, Activity activity, String[] permissoes){

        if (Build.VERSION.SDK_INT >= 23){
            List<String> listaPermissoes = new ArrayList<String>();

            /*Percorre as permissoes passadas verificando umas a uma se já tem a permissao liberada*/
            for (String permissao : permissoes){

               Boolean validaPermissao = ContextCompat.checkSelfPermission(activity, permissao) == PackageManager.PERMISSION_GRANTED;

               if (!validaPermissao){
                   listaPermissoes.add(permissao);
               }
            }

            /*Caso a lista esteja vazia, não é necessário solicitar permissão*/
            if (listaPermissoes.isEmpty()){

                return true;
            }

            String[] novasPermissoes = new String[listaPermissoes.size()]; //descobre o tamanho da nossa lista e criando um arrays de string do tamanho da lista
            listaPermissoes.toArray(novasPermissoes);

            /*Solicita permissao*/
            ActivityCompat.requestPermissions(activity, novasPermissoes, requestCode);

        }

    return true;
    }
}
