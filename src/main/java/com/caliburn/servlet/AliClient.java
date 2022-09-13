package com.caliburn.servlet;

public class AliClient {
    private String Id ="";
    private String Pass ="";
    private boolean is_empty = true;

    public AliClient(String id, String pass) {
        Id = id;
        Pass = pass;
        is_empty = false;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String pass) {
        Pass = pass;
    }
    public boolean isEmpty(){
        return is_empty;
    }
}
