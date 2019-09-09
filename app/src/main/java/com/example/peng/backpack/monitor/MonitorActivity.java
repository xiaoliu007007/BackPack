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

import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.peng.backpack.R;
import com.example.peng.backpack.data_mng.TestMsg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MonitorActivity extends AppCompatActivity
    implements MonitorFragment.StatusListener,TraceFragment.LocationListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monitor);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        if(TestMsg.findById(TestMsg.class,1)==null){
            new ReadTxtTask().execute();
            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //find();
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
    /**
     * @description: 测试数据录入
     * @author: lyj
     * @create: 2019/09/09
     **/
    private class ReadTxtTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            AssetManager manager = getResources().getAssets();
            for(int file_name=0;file_name<16;file_name++){
                ArrayList<TestMsg> list=new ArrayList<>();
                for(int i=0;i<20;i++){
                    TestMsg msg=new TestMsg();
                    list.add(msg);
                }
                try {
                    InputStream inputStream = manager.open(String.valueOf(file_name));
                    InputStreamReader isr = new InputStreamReader(inputStream,
                            "UTF-8");
                    BufferedReader br = new BufferedReader(isr);
                    StringBuilder sb = new StringBuilder();
                    String s;
                    int pos=1;
                    int latitude_pos=0;
                    int value_pos=0;
                    while ((s = br.readLine()) != null) {
                        String[] str=s.trim().split("\\s+");
                        if(pos>=100&&pos<=119){
                            Integer latitude=Integer.parseInt(str[3])+39900000;
                            Integer longitude=Integer.parseInt(str[2])+116300000;
                            TestMsg msg=list.get(latitude_pos++);
                            msg.setLatitude(latitude);
                            msg.setLongitude(longitude);
                        }
                        pos++;
                        if(str.length==16&&str[0].equals("10000000")){
                            TestMsg msg1=list.get(value_pos++);
                            msg1.setValue((int)(Double.valueOf(str[1])*Math.pow(10, 10)));
                            TestMsg msg2=list.get(value_pos++);
                            msg2.setValue((int)(Double.valueOf(str[6])*Math.pow(10, 10)));
                            TestMsg msg3=list.get(value_pos++);
                            msg3.setValue((int)(Double.valueOf(str[11])*Math.pow(10, 10)));
                        }
                        if(str.length==11&&str[0].equals("10000000")){
                            TestMsg msg1=list.get(value_pos++);
                            msg1.setValue((int)(Double.valueOf(str[1])*Math.pow(10, 10)));
                            TestMsg msg2=list.get(value_pos++);
                            msg2.setValue((int)(Double.valueOf(str[6])*Math.pow(10, 10)));
                        }
                    }
                    for(TestMsg msg:list){
                        msg.save();
                    }
                    //关流
                    br.close();
                    isr.close();
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return "";
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }
    /**
     * @description: 测试数据是否录入
     * @author: lyj
     * @create: 2019/09/09
     **/
    private void find(){
        TestMsg msg=TestMsg.findById(TestMsg.class,319);
        Toast.makeText(getApplicationContext(), msg.toString(), Toast.LENGTH_SHORT).show();
    }
}
