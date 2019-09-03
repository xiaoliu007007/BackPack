package com.example.peng.backpack.monitor;

/**
 *Create by:Zhang Yunpeng
 *Date:2017/06/01
 *Modify by:
 *Date:
 *Modify by:
 *Date:
 *describe:数据呈现时，用于向Listview中添加数据的adapter
 */

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.peng.backpack.R;

public class MonitorAdapter extends BaseAdapter{

    private Context ctx;
    private List<MonitorItem> list;

    public MonitorAdapter(Context ctx, List<MonitorItem> list) {
        this.ctx = ctx;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int arg0) {
        return list.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        PlaceHolder ph = null;
        if (arg0 <= list.size()) {
            MonitorItem item = list.get(arg0);

            //将每组数据添加到每一栏
            if (null == arg1) {
                arg1 = createListItemView(arg2);
                ph = new PlaceHolder();
                ph.channel = (TextView) arg1.findViewById(R.id.monitor_channel);
                ph.measure_val = (TextView) arg1.findViewById(R.id.monitor_measure_val);
                ph.status = (TextView) arg1.findViewById(R.id.monitor_status);
                arg1.setTag(ph);
            } else {
                ph = (PlaceHolder)arg1.getTag();
            }

            ph.channel.setText(item.getChannel());
            ph.measure_val.setText(item.getMesure_val());
            setStatus(ph, item);
        }
        return arg1;
    }

    /** 将以字符标志的探头状态转换成文字呈现在屏幕上 */
    private void setStatus(PlaceHolder ph, MonitorItem item) {
        String temp = item.getStatus();
        if(temp.equals("F")) {
            ph.status.setText("故障");
        }else if(temp.equals("N")) {
            ph.status.setText("正常");
        }else if(temp.equals("A")) {
            ph.status.setText("报警");
        }else if(temp.equals("S")) {
            ph.status.setText("严重报警");
        }else {

        }
    }

    private View createListItemView(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        return inflater.inflate(R.layout.monitor_itemlist, parent, false);
    }

    private class PlaceHolder{
        public TextView channel;       //通道
        public TextView measure_val;   //测量值
        public TextView status;        //探头状态
    }
}
