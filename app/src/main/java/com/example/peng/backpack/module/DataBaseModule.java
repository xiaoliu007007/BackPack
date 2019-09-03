package com.example.peng.backpack.module;

/**
 *Create by:Zhang Yunpeng
 *Date:2017/06/12
 *Modify by:
 *Date:
 *Modify by:
 *Date:
 *describe:数据库操作模块
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DataBaseModule {

    private static final String TAG = "DataBaseModule";
    private DataBaseHelper dbHelper;

    /** 初始化创建数据库和表格 */
    public void init(Context context) {
        dbHelper = new DataBaseHelper(context, "BACKPACK.db", null, 1);
        Log.i(TAG, "init: " + "数据库已创建");
        dbHelper.getWritableDatabase();
        Log.i(TAG, "init: " + "判断性创建表格");
    }

    /** 在PACK表中添加数据 */
    public void addPack(String PackName, String MACAddress) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("PackName", PackName);
        values.put("MACAddress", MACAddress);
        db.insert("PACK", null, values);
    }

    /** 在PACK表中删除数据 */
    public void deletePack(String PackName, String MACAddress) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
    }

    /** 在PACK表中更新数据 */
    public void updatePack(String PackName, String MACAddress) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
    }

    /** 在PACK表中查询数据 */
    public void queryPack() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("PACK", null, null, null, null, null, null);
        if(cursor.moveToFirst()) {
            do{
                String PackName = cursor.getString(cursor.getColumnIndex("PackName"));
                String MACAddress = cursor.getString(cursor.getColumnIndex("MACAddress"));
                Log.i(TAG, "queryPack: " + PackName);
                Log.i(TAG, "queryPack: " + MACAddress);
            }while(cursor.moveToNext());
        }
        cursor.close();
    }

    /** 根据MAC地址查询PACKID */
    public int queryPackID(String MACAddress) {
        int id = -1;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM PACK WHERE MACAddress = ?",new String[]{MACAddress});
        while(cursor.moveToNext()) {
            id = cursor.getInt(cursor.getColumnIndex("PackID"));
        }
        cursor.close();

        return id;
    }

    /** 在EVENT表中添加数据 */
    public void addEvent(int PackID, String StartTime) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("PackID", PackID);
        values.put("StartTime", StartTime);
        db.insert("EVENT", null, values);
    }

    /** 在EVENT表中删除数据 */
    public void deleteEvent() {

    }

    /** 在EVENT表中更新数据 */
    public void updateEvent(int EventID, String EndTime) {
        ContentValues values = new ContentValues();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        values.put("EndTime", EndTime);
        db.update("EVENT", values, "EventID = ?", new String[]{String.valueOf(EventID)});
    }

    /** 在EVENT表中查询数据 */
    public void queryEvent() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("EVENT", null, null, null, null, null, null);
        if(cursor.moveToFirst()) {
            do{
                int EventID = cursor.getInt(cursor.getColumnIndex("EventID"));
                int PackID = cursor.getInt(cursor.getColumnIndex("PackID"));
                String StartTime = cursor.getString(cursor.getColumnIndex("StartTime"));
                String EndTime = cursor.getString(cursor.getColumnIndex("EndTime"));
                Log.i(TAG, "queryEventED: " + EventID);
                Log.i(TAG, "queryEventPD: " + PackID);
                Log.i(TAG, "queryEventST: " + StartTime);
                Log.i(TAG, "queryEventET: " + EndTime);
            }while(cursor.moveToNext());
        }
    }

    public int queryEventID() {
        int id = -1;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM EVENT WHERE EndTime is NULL",new String[]{});
        while(cursor.moveToNext()) {
            id = cursor.getInt(cursor.getColumnIndex("EventID"));
        }
        cursor.close();

        return id;
    }

    /** 在Data中添加数据 */
    public void addData(int EventID, String Time, int RSP1, int RSP2,
                        int RSP3, int RSP4, String State,
                        double LongiTude, double LatiTude) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("EventID", EventID);
        values.put("Time", Time);
        values.put("RSP1", RSP1);
        values.put("RSP2", RSP2);
        values.put("RSP3", RSP3);
        values.put("RSP4", RSP4);
        values.put("State", State);
        values.put("LongiTude", LongiTude);
        values.put("LatiTude", LatiTude);
        db.insert("DATA", null, values);
    }

    /** 在DATA表中删除数据 */
    public void deleteData() {

    }

    /** 在EVENT表中更新数据 */
    public void updateData() {

    }

    /** 在EVENT表中查询数据 */
    public void queryData() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("DATA", null, null, null, null, null, null);
        if(cursor.moveToFirst()) {
            do{
                int PackID = cursor.getInt(cursor.getColumnIndex("DataID"));
                int EventID = cursor.getInt(cursor.getColumnIndex("EventID"));
                String Time = cursor.getString(cursor.getColumnIndex("Time"));
                String RSP1 = cursor.getString(cursor.getColumnIndex("RSP1"));
                String RSP2 = cursor.getString(cursor.getColumnIndex("RSP2"));
                String RSP3 = cursor.getString(cursor.getColumnIndex("RSP3"));
                String RSP4 = cursor.getString(cursor.getColumnIndex("RSP4"));
                String State = cursor.getString(cursor.getColumnIndex("State"));
                double LongiTude = cursor.getDouble(cursor.getColumnIndex("LongiTude"));
                double LatiTude = cursor.getDouble(cursor.getColumnIndex("LatiTude"));

                Log.i(TAG, "queryData: " + PackID);
                Log.i(TAG, "queryData: " + EventID);
                Log.i(TAG, "queryData: " + Time);
                Log.i(TAG, "queryData: " + RSP1);
                Log.i(TAG, "queryData: " + RSP2);
                Log.i(TAG, "queryData: " + RSP3);
                Log.i(TAG, "queryData: " + RSP4);
                Log.i(TAG, "queryData: " + State);
                Log.i(TAG, "queryData: " + LongiTude);
                Log.i(TAG, "queryData: " + LatiTude);
            }while(cursor.moveToNext());
        }
    }

    /** 在TEST中添加数据 */
    public void addTest(String Time, String LongiTude, String LatiTude) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Time", Time);
        values.put("LongiTude", LongiTude);
        values.put("LatiTude", LatiTude);
        db.insert("TEST", null, values);
    }
}
