package com.example.peng.backpack.Test;

import android.os.AsyncTask;
import android.util.Log;

import com.example.peng.backpack.data_mng.TestMsg;
import com.example.peng.backpack.main.MainActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 测试类TestMsg相关操作函数
 * @author: lyj
 * @create: 2019/09/09
 **/
public class MsgTest {
    private final String TAG="MsgTest";
    /**
     * @description: 测试数据录入
     * @author: lyj
     * @create: 2019/09/09
     **/
    public class ReadTxtTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            String base="/assets/";
            for(int file_name=0;file_name<16;file_name++){
                ArrayList<TestMsg> list=new ArrayList<>();
                for(int i=0;i<20;i++){
                    TestMsg msg=new TestMsg();
                    list.add(msg);
                }
                try {
                    String file=base+file_name;
                    InputStream inputStream =MsgTest.class.getResourceAsStream(file);
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
                            Integer latitude=Integer.parseInt(str[3])+39900050;
                            Integer longitude=Integer.parseInt(str[2])+116299930;
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
    public String find(){
        TestMsg msg=TestMsg.findById(TestMsg.class,1);
        Log.i(TAG, ":"+msg.toString());
        return msg.toString();
    }

    /**
     * @description: 查找数据库中当前坐标的测试数据
     * @author: lyj
     * @create: 2019/09/10
     **/
    public int findData() {
        int[] start={116364350,39970250};
        int[] incrBy={20,-15};
        int[] now={(int)(MainActivity.Longitude*1000000),(int)(MainActivity.Latitude*1000000)};
        int[] convertLoc=findNearerLocation(start,incrBy,now);
        Log.i(TAG, ":"+convertLoc[0]+":"+convertLoc[1]);
        List<TestMsg> list=TestMsg.find(TestMsg.class,"latitude = ? and longitude = ?",String.valueOf(convertLoc[1]),String.valueOf(convertLoc[0]));
        if(list==null){
            return 0;
        }
        Log.i(TAG, ":"+list.get(0).getValue());
        return list.get(0).getValue();
    }

    /**
     * @description: 坐标转换算法
     * @author: lyj
     * @create: 2019/09/10
     **/
    public int[] findNearerLocation(int[] start,int[] incrBy,int[] now) {
        if(now[0]<start[0]||now[1]>start[1]){
            return now;
        }
        int[] ans= {0,0};
        for(int i=0;i<2;i++) {
            while(start[i]+incrBy[i]<now[i]) {
                start[i]+=incrBy[i];
            }
            ans[i]=now[i]-start[i]<=start[i]+incrBy[i]-now[i]?start[i]:start[i]+incrBy[i];
        }
        return ans;
    }
}
