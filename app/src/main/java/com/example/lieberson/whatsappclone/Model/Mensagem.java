package com.example.lieberson.whatsappclone.Model;

/**
 * Created by lieberson on 21/03/18.
 */

public class Mensagem {

    private String idUsuario;
    private String mensagem;

    public Mensagem() {
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
