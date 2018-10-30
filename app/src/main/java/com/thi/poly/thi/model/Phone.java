package com.thi.poly.thi.model;

public class Phone {


    String ID,Name;


    public Phone(String ID, String name) {
        this.ID = ID;
        Name = name;
    }
    public Phone() {

    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

}
