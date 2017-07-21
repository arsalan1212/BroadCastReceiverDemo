package com.example.arsalankhan.broadcastreceiverdemo.helper;

/**
 * Created by Arsalan khan on 7/21/2017.
 */

public class Contract {
    private int Id;
    private String Number;

    public Contract(int id, String number) {
       setId(id);
        setNumber(number);
    }
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

}
