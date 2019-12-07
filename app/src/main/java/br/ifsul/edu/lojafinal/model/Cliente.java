package br.ifsul.edu.lojafinal.model;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.ArrayList;

public class Cliente implements Serializable {
    private Long codigoDeBarras;
    private String cpf;
    private String nome;
    private String sobrenome;
    private String url_foto = "";
    private boolean situacao;
    private ArrayList<String> pedidos_keys = new ArrayList<>();
    private String key; //atributo apenas local
    private Integer index; //atributo apenas local (as anotações "exclude no get e no set determina isso)

    public Cliente() {
    }

    public Long getCodigoDeBarras() {
        return codigoDeBarras;
    }

    public void setCodigoDeBarras(Long codigoDeBarras) {
        this.codigoDeBarras = codigoDeBarras;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getUrl_foto() {
        return url_foto;
    }

    public void setUrl_foto(String url_foto) {
        this.url_foto = url_foto;
    }

    public boolean isSituacao() {
        return situacao;
    }

    public void setSituacao(boolean situacao) {
        this.situacao = situacao;
    }

    public void setPedidos(ArrayList<String> keys_pedidos) {
        this.pedidos_keys = keys_pedidos;
    }

    @Exclude
    public String getKey() {
        return key;
    }

    @Exclude
    public void setKey(String key) {
        this.key = key;
    }

    @Exclude
    public Integer getIndex() {
        return index;
    }

    @Exclude
    public void setIndex(Integer index) {
        this.index = index;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public String toString() {
        return "Cliente{" +
                "codigoDeBarras=" + codigoDeBarras +
                ", cpf='" + cpf + '\'' +
                ", nome='" + nome + '\'' +
                ", sobrenome='" + sobrenome + '\'' +
                ", url_foto='" + url_foto + '\'' +
                ", situacao=" + situacao +
                ", key='" + key + '\'' +
                '}';
    }
}