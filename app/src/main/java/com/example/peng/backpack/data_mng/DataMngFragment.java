package com.example.peng.backpack.data_mng;

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
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.peng.backpack.R;
import com.example.peng.backpack.main.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DataMngFragment extends Fragment {

    private static final String TAG = "DataMngFragment";
    private static final int RspSize = 4;

    /**
     * 用于存储各个通道数据的列表，MonitorItem中包含了三列数据信息 ，通道号、测量值、状态
     */
    private List<DataMngItem> list;
    private DataMngAdapter mAdapter;
    private ListView listView;

    //用于Fragment和Activity通信的接口
    public interface StatusListener {
        public void onStatus(int status);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_data_mng, container, false);
        init(view);
        return view;
    }

    /** 界面初始化 */
    private void init(View view){
        list = new ArrayList<DataMngItem>();
        mAdapter = new DataMngAdapter(this.getActivity(), list);
        final Activity activity = this.getActivity();
        listView = (ListView) view.findViewById(R.id.datalist);

        listView.setAdapter(mAdapter);

        mAdapter.setListener(new DataMngAdapter.ClickListener() {
            @Override
            public void onClick(DataMngItem item) {
                //点击事件
            }
        });

    }


    /** 解析数据，更新界面 */
    private void UpdateUI(ArrayList<DataMngItem> items) {
        list.clear();
        list.addAll(items);
        mAdapter.notifyDataSetChanged();
    }

 }
