package com.example.peng.backpack.monitor;

/**
 *Create by:Zhang Yunpeng
 *Date:2017/06/05
 *Modify by:
 *Date:
 *Modify by:
 *Date:
 *describe:数据监测呈现模块，对采集的监测数据呈现，并判断背包的状态
 */

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.peng.backpack.R;
import com.example.peng.backpack.data_mng.DataMsg;
import com.example.peng.backpack.main.MainActivity;
import com.example.peng.backpack.utils.DBScanUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;

public class MonitorFragment extends Fragment {

    private static final String TAG = "DataMngFragment";
    private static final int RspSize = 4;

    /**
     * 用于存储各个通道数据的列表，MonitorItem中包含了三列数据信息 ，通道号、测量值、状态
     */
    private List<MonitorItem> list;
    private MonitorAdapter mAdapter;
    private ListView listView;
    private StatusListener mCallback;
    private Button status_button;
    private Timer timer = null;
    private TimerTask task = null;
    private boolean isStop = true;
    private String[] BackPackStatus = {"禁用","正常","污染","严重污染","故障"};
    private double latitude;
    private double longitude;

    //用于Fragment和Activity通信的接口
    public interface StatusListener {
        public void onStatus(int status);
    }
    /**
     * @description: 坐标
     * @author: lyj
     * @create: 2019/09/02
     **/
    public void setLocation(double latitude,double longitude){
        this.latitude=latitude;
        this.longitude=longitude;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (StatusListener)activity;
        }catch (ClassCastException e) {
            throw new ClassCastException(activity.toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_monitor, container, false);
        init(view);
        return view;
    }

    /** 界面初始化 */
    private void init(View view){
        list = new ArrayList<MonitorItem>();
        mAdapter = new MonitorAdapter(this.getActivity(), list);
        final Activity activity = this.getActivity();
        status_button = (Button)view.findViewById(R.id.bt_status);
        listView = (ListView) view.findViewById(R.id.datalist);

        listView.setAdapter(mAdapter);

        final Button start = (Button) view.findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    if(isStop) {
                        MainActivity.dataBaseModule.addEvent(MainActivity.PackID, getTime());
                        MainActivity.EventID = MainActivity.dataBaseModule.queryEventID();
                        Log.i(TAG, "onClick: " + MainActivity.EventID);
                        startTimer();
                        start.setText("停止");
                    }else{
                        stopTimer();
                        MainActivity.dataBaseModule.updateEvent(MainActivity.EventID, getTime());
                        MainActivity.dataBaseModule.queryEvent();
                        MainActivity.dataBaseModule.queryData();
                        start.setText("开始");
                    }
                    isStop = !isStop;

                }catch (Exception e) {
                    Log.i(TAG, "onClick: " + e.toString());
                }
            }
        });

        /**
         * @description: DBSCAN测试按钮
         * @author: lyj
         * @create: 2019/09/23
         **/
        status_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                double[] ans= DBScanUtils.getTestMsg();
                Toast.makeText(getActivity().getApplicationContext(), "预测坐标为"+ans[0]+":"+ans[1], Toast.LENGTH_LONG).show();
            }
        });

        MainActivity.bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
            public void onDataReceived(byte[] data, String message) {
                String res = new String(data);
                UpdateUI(res);
            }
        });
    }

    /** 使用timer定时的向背包发送请求获取数据
     * 格式timer.schedule(task, m, n) m s后执行task，经过n s再次执行
     * */
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                String to_send = "GMA";
                //Log.i(TAG, "handleMessage: " + to_send);
                MainActivity.bt.send(to_send.getBytes(), true);
            }
            super.handleMessage(msg);
        }
    };

    /** 解析数据，更新界面 */
    private void UpdateUI(String data) {
        list.clear();
        String[] rsp_status = new String[RspSize];
        int[] rsp_val = new int[RspSize];
        int index = -1;

        for (int i = 3; i <= 21; i += 6) {
            String channel = data.substring(i, i + 1);
            String status = data.substring(i + 1, i + 2);
            String val = data.substring(i + 2, i + 6);

            MonitorItem item = new MonitorItem();
            item.setChannel(channel);
            item.setMesure_val(val);
            item.setStatus(status);
            list.add(item);

            index++;
            rsp_status[index] = status;
            rsp_val[index] = Integer.parseInt(val);

        }
        add(list.get(0).getMesure_val());
        int cnt = SetStatus(rsp_status);
        MainActivity.dataBaseModule.addData(MainActivity.EventID,getTime(),rsp_val[0],rsp_val[1],rsp_val[2],
                rsp_val[3],BackPackStatus[cnt],MainActivity.Longitude,MainActivity.Latitude);
        mAdapter.notifyDataSetChanged();
    }

    private int SetStatus(String[] rsp_status) {
        /**
         * 以数值型代表探头状态，并判断背包状态
         * 0：禁用状态；1：正常；2：报警；3：严重报警；4：故障；
         * */
        int BackStatus = 0;
        int status = 0;
        for(int i = 0; i < RspSize; i++) {
            if(rsp_status[i].equals("F")) {
                status = 4;
            }else if(rsp_status[i].equals("N")) {
                status = 1;
            }else if(rsp_status[i].equals("A")) {
                status = 2;
            }else if(rsp_status[i].equals("S")) {
                status = 3;
            }else {

            }
            BackStatus = Math.max(BackStatus, status);
        }
        setButtonStatus(BackStatus);
        mCallback.onStatus(BackStatus);
        return BackStatus;
    }

    //将背包状态呈现在界面上
    private void setButtonStatus(int status) {
        if(status == 0) {
            status_button.setText("禁用");
            status_button.setBackgroundColor(Color.GRAY);
        }else if(status == 1) {
            status_button.setText("正常");
            status_button.setBackgroundColor(Color.GREEN);
        }else if(status == 2) {
            status_button.setText("污染");
            status_button.setBackgroundColor(Color.RED);
        }else if(status == 3) {
            status_button.setText("严重污染");
            status_button.setBackgroundColor(Color.MAGENTA);
        }else if(status == 4) {
            status_button.setText("故障");
            status_button.setBackgroundColor(Color.YELLOW);
        }else {

        }
    }

    /** 启动计时器 */
    private void startTimer() {
        if(timer == null) {
            timer = new Timer();
        }

        if(task == null) {
            task = new TimerTask() {
                @Override
                public void run() {
                    // 需要做的事:发送消息
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                }
            };
        }
        if(timer != null && task != null) {
            timer.schedule(task, 0, 2000); // 0s后执行task,经过2s再次执行
        }
    }

    /** 停止计时器 */
    private void stopTimer() {
        if(timer != null) {
            timer.cancel();
            timer = null;
        }

        if(task != null) {
            task.cancel();
            task = null;
        }
    }

    @Override
    public void onStop() {
        stopTimer();
        super.onStop();
    }

    /** 获取当前时间 */
    private String getTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String date = dateFormat.format(new java.util.Date());

        return date;
    }
    /**
     * @description: 存储数据
     * @author: lyj
     * @create: 2019/09/02
     **/
    private void add(String val){
        if(longitude!=0.0&&latitude!=0.0){
            DataMsg msg=new DataMsg(getTime(),val,longitude,latitude);
            msg.save();
        }
    }
}
