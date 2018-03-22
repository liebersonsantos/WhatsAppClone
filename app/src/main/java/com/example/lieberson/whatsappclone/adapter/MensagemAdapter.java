package com.example.lieberson.whatsappclone.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lieberson.whatsappclone.Model.Mensagem;
import com.example.lieberson.whatsappclone.R;
import com.example.lieberson.whatsappclone.helper.Preferencias;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lieberson on 22/03/18.
 */

public class MensagemAdapter extends ArrayAdapter<Mensagem>{

    private Context context;
    private ArrayList<Mensagem> mensagens;


    public MensagemAdapter(@NonNull Context c, @NonNull ArrayList<Mensagem> objects) {
        super(c, 0, objects);
        this.context = c;
        this.mensagens = objects;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = null;

        //Verificando se a lista esta vazia
        if (mensagens != null){

            //Recupera dados do usuario remetente
            Preferencias preferencias = new Preferencias(context);
            String idUsuarioRemetente = preferencias.getIdentificador();


            //Inicializa objeto para montagem do layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            //Recupera mensagem
            Mensagem mensagem = mensagens.get(position);

            //Monta a view a partir do xml
            if (idUsuarioRemetente.equals(mensagem.getIdUsuario())){

                view =inflater.inflate(R.layout.item_mensagem_direita, parent, false);

            }else {

                view =inflater.inflate(R.layout.item_mensagem_esquerda, parent, false);
            }



            //Recuperando elemento para exibiçao
            TextView textoMensagem = view.findViewById(R.id.tv_mensagem);
            textoMensagem.setText(mensagem.getMensagem());

        }


        return view;
    }
}
