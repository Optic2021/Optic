package com.example.optic.bean;

public class AdminBean implements UserBean{
    private String username;
    private String password;
    private String insta;
    private String faceb;
    private String whats;
    private String descrizione;
    private String nomeCampo;
    private String via;
    private String provincia;
    private String referee;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getInsta() {
        return insta;
    }

    public void setInsta(String insta) {
        this.insta = insta;
    }

    public String getFaceb() {
        return faceb;
    }

    public void setFaceb(String faceb) {
        this.faceb = faceb;
    }

    public String getWhats() {
        return whats;
    }

    public void setWhats(String whats) {
        this.whats = whats;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getNomeCampo() {
        return nomeCampo;
    }

    public void setNomeCampo(String nomeCampo) {
        this.nomeCampo = nomeCampo;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    @Override
    public String getNomeC() {
        return null;
    }

    @Override
    public void setNomeC(String nomeC) {

    }

    @Override
    public String getProv() {
        return null;
    }

    @Override
    public void setProv(String prov) {

    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getReferee() {
        return referee;
    }

    public void setReferee(String referee) {
        this.referee = referee;
    }
}
