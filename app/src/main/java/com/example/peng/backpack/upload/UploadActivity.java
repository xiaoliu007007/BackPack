package com.example.peng.backpack.upload;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import com.example.peng.backpack.R;
import com.example.peng.backpack.data_mng.DataMsg;

import java.util.List;

/**
 * @description: 上传Activity
 * @author: lyj
 * @create: 2019/09/02
 **/
public class UploadActivity extends AppCompatActivity {
    private ListView infoList;
    private UploadAdapter mAdapter;
    private List<DataMsg> mdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_upload);
        initView();
    }

    private void initView() {
        infoList = (ListView) findViewById(R.id.info);
        mdata = find();
        mAdapter = new UploadAdapter(this, mdata);
        infoList.setAdapter(mAdapter);
    }

    private List<DataMsg> find(){
        List<DataMsg > msg = DataMsg.listAll(DataMsg .class);
        return msg;
    }
}
