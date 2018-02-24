package com.example.lieberson.whatsappclone.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.lieberson.whatsappclone.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference referenciaFirebase = FirebaseDatabase.getInstance().getReference(); //.getInstance(); = manipula o BD e .getReference referencia o Bd



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        referenciaFirebase.child("pontos").setValue(100);














    }
}
