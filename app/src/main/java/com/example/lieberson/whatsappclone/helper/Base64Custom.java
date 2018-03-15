package com.example.lieberson.whatsappclone.helper;

import android.util.Base64;

/**
 * Created by lieberson on 15/03/18.
 */

public class Base64Custom {

    public static String codificarBase64(String texto){

        return Base64.encodeToString(texto.getBytes(), Base64.DEFAULT).replaceAll("(\\n|\\r)", " ");
    }

    public static String decodificarBase64 (String textoCodificado){

        return new String (Base64.decode(textoCodificado, Base64.DEFAULT));
    }

}
