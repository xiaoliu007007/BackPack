package com.example.peng.backpack.monitor;

/**
 *Create by:Zhang Yunpeng
 *Date:2017/03/30
 *Modify by:
 *Date:
 *Modify by:
 *Date:
 *describe:智能监测页面，实时采集监测数据，采用Fragment，左边绘制变色轨迹，右边进行数据呈现
 */

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.peng.backpack.R;

public class MonitorActivity extends AppCompatActivity
    implements MonitorFragment.StatusListener,TraceFragment.LocationListener{

    private static final String TAG = "MonitorActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monitor);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    /** 使用接口实现Fragment间通信，通信过程Fragment-Activity-Fragment*/
    @Override
    public void onStatus(int status) {
        //在activity中查找fragment，并调用Fragment函数
        TraceFragment traceFragment =
                (TraceFragment)getSupportFragmentManager().findFragmentById(R.id.left);
        if(traceFragment != null) {
            traceFragment.setColorStatus(status);
        }else {

        }
    }


    @Override
    public void setLocation(double latitude, double longitude) {
        MonitorFragment monitorFragment=(MonitorFragment)getSupportFragmentManager().findFragmentById(R.id.right);
        if(monitorFragment!=null){
            monitorFragment.setLocation(latitude,longitude);
        }
    }
}
