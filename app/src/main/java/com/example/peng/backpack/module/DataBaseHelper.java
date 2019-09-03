package com.example.peng.backpack.module;

/**
 *Create by:Zhang Yunpeng
 *Date:2017/06/12
 *Modify by:
 *Date:
 *Modify by:
 *Date:
 *describe:创建数据库，创建表
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DataBaseHelper";
    private Context mContext;
    /** 创建背包表 */
    public static String CREATE_PACK = "create table PACK ("
            + "PackID integer primary key autoincrement," //自增ID
            + "PackName text,"                            //背包名称
            + "MACAddress text unique)";                          //MAC地址
    /** 创建事件表 */
    public static String CREATE_EVENT = "create table EVENT ("
            + "EventID integer primary key autoincrement,"//自增ID
            + "PackID integer,"                           //背包ID
            + "StartTime text,"                           //开始时间
            + "EndTime text)";                             //终止时间
    //创建数据表
    public static String CREATE_DATA = "create table DATA ("
            + "DataID integer primary key autoincrement," //自增ID
            + "EventID integer,"                          //事件ID
            + "Time text,"                                //时间
            + "RSP1 integer,"                                //探头1测量值
            + "RSP2 integer,"                                //探头2测量值
            + "RSP3 integer,"                                //探头3测量值
            + "RSP4 integer,"                                //探头4测量值
            + "State text,"                               //背包状态
            + "LongiTude real,"                           //经度
            + "LatiTude real)";                            //维度

    //创建测试表
    public static String CREATE_TEST = "create table TEST ("
            + "TestID integer primary key autoincrement," //自增ID
            + "Time text,"                                //时间
            + "LongiTude text,"                           //经度
            + "LatiTude text)";                            //维度

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                          int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public  void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PACK);
        Log.i(TAG, "onCreate: " + "背包表已创建");
        db.execSQL(CREATE_EVENT);
        Log.i(TAG, "onCreate: " + "事件表已创建");
        db.execSQL(CREATE_DATA);
        Log.i(TAG, "onCreate: " + "数据表已创建");
        db.execSQL(CREATE_TEST);
        Log.i(TAG, "onCreate: " + "测试表已创建");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
