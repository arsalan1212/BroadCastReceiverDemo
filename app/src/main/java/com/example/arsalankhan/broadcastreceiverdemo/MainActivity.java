package com.example.arsalankhan.broadcastreceiverdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.arsalankhan.broadcastreceiverdemo.Database.databaseAdapter;
import com.example.arsalankhan.broadcastreceiverdemo.helper.Contract;
import com.example.arsalankhan.broadcastreceiverdemo.helper.MyAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
public static final int REQUEST_CODE=100;
    private RecyclerView recyclerView;
    ArrayList<Contract> contractArrayList=new ArrayList<>();
    TextView tv_display;
    MyAdapter adapter;

    private BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            setAdapaterData();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_display= (TextView) findViewById(R.id.tv_display);

        recyclerView= (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new MyAdapter(this,contractArrayList);
        recyclerView.setAdapter(adapter);
        setAdapaterData();

        //Allow the Permission Manually

       /* if(Build.VERSION.SDK_INT <= 23){

        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)!=
                PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_PHONE_STATE,Manifest.permission.MODIFY_PHONE_STATE},
                    REQUEST_CODE);

        }else{

        }*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver,new IntentFilter(MyReceiver.INTENT_FILTER));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcastReceiver);
    }

    public void setAdapaterData(){

        contractArrayList.clear();
        contractArrayList=readNumberFromDatabase();


        if(contractArrayList.size()>0){
            tv_display.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            adapter=new MyAdapter(this,contractArrayList);
            recyclerView.setAdapter(adapter);
        }
        else{
            tv_display.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    private ArrayList<Contract> readNumberFromDatabase(){
        databaseAdapter databaseAdapter=new databaseAdapter(this);
        ArrayList<Contract> arrayList=databaseAdapter.getNumbers();
        return arrayList;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==REQUEST_CODE && grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED &&
                grantResults[1]==PackageManager.PERMISSION_GRANTED){

        }
    }
}
