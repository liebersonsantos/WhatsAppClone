package com.example.lieberson.whatsappclone.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.lieberson.whatsappclone.fragment.ContatosFragment;
import com.example.lieberson.whatsappclone.fragment.ConversasFragment;

/**
 * Created by lieberson on 13/03/18.
 * Esta classe trata os elementos da viewPage como fragmentos
 */

public class TabAdapter extends FragmentStatePagerAdapter {

   private String[] tituloAbas = {"CONVERSAS", "CONTATOS"};

    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) { //Esse metodo retorna os fragmentos para a pagina

        Fragment fragment = null;

        switch (position){
            case 0 :
                fragment = new ConversasFragment();
                break;
            case 1 :
                fragment = new ContatosFragment();
                break;
        }


        return fragment;
    }

    @Override
    public int getCount() { //Esse metodo retorna a quantidade de abas que nos queremos

        return tituloAbas.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tituloAbas[position];
    }
}
