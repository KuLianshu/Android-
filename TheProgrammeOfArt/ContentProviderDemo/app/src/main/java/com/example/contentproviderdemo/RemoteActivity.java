package com.example.contentproviderdemo;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.contentproviderdemo.utils.LogUtils;


public class RemoteActivity extends AppCompatActivity implements View.OnClickListener {

    private String newId;

    private static final String AUTHOR = "com.example.database.provider";
    private static final String url1= "content://"+AUTHOR+"/book";
//    private static final String url2= "content://"+AUTHOR+"/book";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote);

        findViewById(R.id.add_data).setOnClickListener(this);
        findViewById(R.id.query_data).setOnClickListener(this);
        findViewById(R.id.update_data).setOnClickListener(this);
        findViewById(R.id.delete_data).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_data:
                addData();
                break;
            case R.id.query_data:
                queryData();
                break;
            case R.id.update_data:
                updateData();
                break;
            case R.id.delete_data:
                deleteData();
                break;
        }
    }

    private void deleteData() {
        Uri uri = Uri.parse(url1+"/"+newId);
        getContentResolver().delete(uri,null,null);
    }

    private void updateData() {
        Uri uri = Uri.parse(url1+"/"+newId);
        ContentValues values = new ContentValues();
        values.put("name","A Storm of Swords");
        values.put("pages",1234);;
        values.put("price",24.5);
        getContentResolver().update(uri,values,null,null);

    }

    private void queryData() {
        Uri uri = Uri.parse(url1);
        Cursor cursor = getContentResolver().query(uri,null,null,null,null);
        if (cursor!=null){
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String author = cursor.getString(cursor.getColumnIndex("author"));
            int pages = cursor.getInt(cursor.getColumnIndex("page"));
            double price = cursor.getDouble(cursor.getColumnIndex("price"));
            LogUtils.i("name = "+name+", author = "+author+", page = "+pages+", price = "+price);



            cursor.close();
        }

    }

    private void addData() {
        Uri uri = Uri.parse(url1);
        ContentValues values = new ContentValues();
        values.put("name","A Clash of Kings");
        values.put("author","George Martin");
        values.put("pages",1040);
        values.put("price",22.4);
        Uri newUri = getContentResolver().insert(uri,values);
        if (newUri!=null){
            newId = newUri.getPathSegments().get(1);
        }
    }
}
