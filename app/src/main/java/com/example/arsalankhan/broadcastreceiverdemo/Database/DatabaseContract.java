package com.example.arsalankhan.broadcastreceiverdemo.Database;

/**
 * Created by Arsalan khan on 7/21/2017.
 */

public class DatabaseContract {
    public static final String DATABASE_NAME="IncommingNumber";

    public abstract class schema{
        public static final String TABLE_NAME="NUMBER";
        public static final String ID="_id";
        public static final String NUMBER="number";
    }
}
