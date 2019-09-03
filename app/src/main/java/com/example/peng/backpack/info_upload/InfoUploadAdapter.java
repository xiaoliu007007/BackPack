package com.example.peng.backpack.info_upload;

/**
 * Create by:Zhang Yunpeng
 * Date:2017/06/01
 * Modify by:
 * Date:
 * Modify by:
 * Date:
 * describe:数据呈现时，用于向Listview中添加数据的adapter
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.peng.backpack.R;
import com.example.peng.backpack.data_mng.DataMngItem;

import java.util.List;

public class InfoUploadAdapter extends BaseAdapter {

    private Context ctx;
    private List<DataMngItem> list;

    public interface CheckedChange {
        void onCheckedChange(boolean isChecked, DataMngItem item);
    }

    private CheckedChange listener;

    public InfoUploadAdapter(Context ctx, List<DataMngItem> list) {
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
    public View getView(final int arg0, View arg1, ViewGroup arg2) {
        PlaceHolder ph = null;
        if (arg0 <= list.size()) {
            DataMngItem item = list.get(arg0);

            //将每组数据添加到每一栏
            if (null == arg1) {
                arg1 = createListItemView(arg2);
                ph = new PlaceHolder();
                ph.idTxt = (TextView) arg1.findViewById(R.id.data_id);
                ph.startTimeTxt = (TextView) arg1.findViewById(R.id.start_time);
                ph.endTimeTxt = (TextView) arg1.findViewById(R.id.end_time);
                ph.status = (TextView) arg1.findViewById(R.id.status);
                ph.item = (LinearLayout) arg1.findViewById(R.id.data_mng_item);
                ph.mCheckedCb = (CheckBox) arg1.findViewById(R.id.info_checked);
                arg1.setTag(ph);
            } else {
                ph = (PlaceHolder) arg1.getTag();
            }

            ph.idTxt.setText(item.getId());
            ph.startTimeTxt.setText(item.getStartTime());
            ph.endTimeTxt.setText(item.getEndTime());
            ph.status.setText(item.getStatus());
            ph.mCheckedCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (listener != null) {
                        listener.onCheckedChange(isChecked, list.get(arg0));
                    }
                }
            });
        }
        return arg1;
    }

    private View createListItemView(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        return inflater.inflate(R.layout.info_upload_item, parent, false);
    }


    public void setListener(CheckedChange listener) {
        this.listener = listener;
    }

    private class PlaceHolder {
        public LinearLayout item;
        public CheckBox mCheckedCb;
        public TextView idTxt;       //通道
        public TextView startTimeTxt;   //测量值
        public TextView endTimeTxt;   //测量值
        public TextView status;        //探头状态
    }
}
