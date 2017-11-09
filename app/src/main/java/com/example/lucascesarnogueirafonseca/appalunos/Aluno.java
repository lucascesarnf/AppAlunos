package com.example.lucascesarnogueirafonseca.appalunos;

import java.io.Serializable;

/**
 * Created by lucascesarnogueirafonseca on 08/11/17.
 */

public class Aluno implements Serializable{

    private  Long id;
    private  String nome;
    private  String telefone;
    private  String endereco;
    private  String email;
    private  String foto;
    private  Double nota;

    public String toString(){
        return nome;
    }

    //Métodos Get e Set

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }
}
