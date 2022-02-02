package com.example.optic.utilities;

public class ImportDAO {
    //Volevo metterle public ma le lascio con get per la proprieta
    private String user;
    private String passWord;
    private String dbUrl;
    private String driverClassName;

    public ImportDAO(){
        this.user = "root";
        this.passWord = "17moneC*";
        this.dbUrl = "jdbc:mysql://localhost:3306/optic";
        this.driverClassName = "com.mysql.jdbc.Driver";
    }

    public String getUser() {
        return user;
    }

    public String getPassWord() {
        return passWord;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public String getDriverClassName() {
        return driverClassName;
    }
}
