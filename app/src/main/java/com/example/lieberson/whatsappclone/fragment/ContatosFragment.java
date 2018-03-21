package com.example.lieberson.whatsappclone.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.lieberson.whatsappclone.Model.Contato;
import com.example.lieberson.whatsappclone.R;
import com.example.lieberson.whatsappclone.activity.ConversaActivity;
import com.example.lieberson.whatsappclone.adapter.ContatoAdapter;
import com.example.lieberson.whatsappclone.config.ConfiguracaoFirebase;
import com.example.lieberson.whatsappclone.helper.Preferencias;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContatosFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter arrayAdapter;
    private ArrayList<Contato> contatos;

    private DatabaseReference databaseReference;

    private ValueEventListener valueEventListenerContatos;

    public ContatosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(valueEventListenerContatos);
        Log.i("ValueEventListener", "onStart");
    }

    @Override
    public void onStop() {
        super.onStop();

        databaseReference.removeEventListener(valueEventListenerContatos);
        Log.i("ValueEventListener", "onStop");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //instanciando os objetos da lista
        contatos = new ArrayList<>();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contatos, container, false);

        //Monta o listView e o adapter
        listView = view.findViewById(R.id.lv_contatos);
       /* arrayAdapter = new ArrayAdapter(
                        getActivity(),
                        R.layout.lista_contato,
                        contatos
        );*/
        arrayAdapter = new ContatoAdapter(getActivity(), contatos);

        listView.setAdapter(arrayAdapter);

        //Recuperando os contatos do Firebase
        Preferencias preferencias = new Preferencias(getActivity());
        String identificadorUsuarioLogado = preferencias.getIdentificador();

        databaseReference = ConfiguracaoFirebase.getFirebase()
                            .child("contatos")
                            .child(identificadorUsuarioLogado);

        //Listener para recuperar contatos
        valueEventListenerContatos = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //limpandoa lista para nao duplicar os contatos
                contatos.clear();

                //listanto os contatos
                for (DataSnapshot dados:dataSnapshot.getChildren()){

                    Contato contato = dados.getValue(Contato.class);
                    contatos.add(contato);
                }

                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getActivity(), ConversaActivity.class);

                //recuperando dados a seram passados
                Contato contato = contatos.get(i);



                //Enviando dados para Conversa_Activity
                intent.putExtra("nome", contato.getNome());
                intent.putExtra("email", contato.getEmail());

                startActivity(intent);

            }
        });

        return view;
    }

}
