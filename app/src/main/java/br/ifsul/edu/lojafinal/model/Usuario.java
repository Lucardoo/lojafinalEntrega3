package br.ifsul.edu.lojafinal.model;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.Exclude;

public class Usuario {
    private Long codigoDeBarras;
    private FirebaseUser firebaseUser;
    private String nome;
    private String sobrenome;
    private String funcao;
    private String email;
    private String key; //apenas interno


    public Usuario(){
    }

    @Exclude
    public FirebaseUser getFirebaseUser(){
        return firebaseUser;
    }

    @Exclude
    public void setFirebaseUser (FirebaseUser firebaseUser){
        this.firebaseUser = firebaseUser;
    }

    public Long getCodigoDeBarras() {
        return codigoDeBarras;
    }

    public void setCodigoDeBarras(Long codigoDeBarras) {
        this.codigoDeBarras = codigoDeBarras;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao){
        this.funcao = funcao;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNome(){
        return nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getSobrenome(){
        return sobrenome;
    }

    public void setSobrenome(String sobrenome){
        this.sobrenome = sobrenome;
    }

    @Override
    public String toString() {
        return "User{" +
                "codigoDeBarras=" + codigoDeBarras +
                ", nome='" + nome + '\'' +
                ", sobrenome='" + sobrenome + '\'' +
                ", funcao='" + funcao + '\'' +
                ", email='" + email + '\'' +
                ", firebaseUser=" + firebaseUser +
                '}';
    }
}
