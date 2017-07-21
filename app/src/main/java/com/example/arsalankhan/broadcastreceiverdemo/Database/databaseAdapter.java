package com.example.arsalankhan.broadcastreceiverdemo.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.arsalankhan.broadcastreceiverdemo.helper.Contract;

import java.util.ArrayList;

/**
 * Created by Arsalan khan on 7/21/2017.
 */

public class databaseAdapter {

    dbHelper helper;
    public databaseAdapter(Context context){
        helper=new dbHelper(context);
    }

    public long insertNumber(String number){
        SQLiteDatabase db=helper.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(DatabaseContract.schema.NUMBER,number);
        long id=db.insert(DatabaseContract.schema.TABLE_NAME,null,values);
        return id;
    }

    public ArrayList<Contract> getNumbers(){
        SQLiteDatabase db=helper.getWritableDatabase();
        String columns[]={DatabaseContract.schema.ID,DatabaseContract.schema.NUMBER};
        Cursor cursor=db.query(DatabaseContract.schema.TABLE_NAME,columns,null,null,null,null,null);

        ArrayList<Contract> contractArrayList=new ArrayList<>();
        while (cursor.moveToNext()){

            int column1=cursor.getColumnIndex(DatabaseContract.schema.ID);
            int column2=cursor.getColumnIndex(DatabaseContract.schema.NUMBER);

            int id=cursor.getInt(column1);
            String number=cursor.getString(column2);

            Contract contract=new Contract(id,number);
            contractArrayList.add(contract);
        }
        return contractArrayList ;
    }


    static class dbHelper extends SQLiteOpenHelper {

        private static final int DATABASE_VERSION = 1;
        private static final String CREATE = "CREATE TABLE " + DatabaseContract.schema.TABLE_NAME + "(" + DatabaseContract.schema.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + DatabaseContract.schema.NUMBER + " VARCHAR(255));";
        private static final String DROP_TABLE="DROP TABLE IF EXISTS "+DatabaseContract.schema.TABLE_NAME;
        private Context context;
        dbHelper(Context context) {
            super(context, DatabaseContract.DATABASE_NAME, null, DATABASE_VERSION);
            this.context=context;
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {

            try {
                sqLiteDatabase.execSQL(CREATE);
            }catch (Exception e){

                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            try{
                sqLiteDatabase.execSQL(DROP_TABLE);
                onCreate(sqLiteDatabase);

            }catch (Exception e){
                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
            }

        }
    }
}
