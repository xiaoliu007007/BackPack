package com.example.peng.backpack.info_upload;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.peng.backpack.R;
import com.example.peng.backpack.data_mng.DataMngItem;

import java.util.ArrayList;
import java.util.List;

public class InfoUploadActivity extends AppCompatActivity {

    private ListView infoList;
    private RadioButton neutronRb;
    private RadioButton leastSquareRb;
    private RadioButton splineInterpolationRb;
    private RadioButton neighborInterpolationRb;
    private RadioGroup mGroup;

    private InfoUploadAdapter mAdapter;
    private List<DataMngItem> mdata;
    private List<DataMngItem> mChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_info_upload);
        initView();
    }

    private void initView() {
        mGroup = (RadioGroup) findViewById(R.id.alg_group);
        neutronRb = (RadioButton) findViewById(R.id.neural);
        leastSquareRb = (RadioButton) findViewById(R.id.least_square);
        splineInterpolationRb = (RadioButton) findViewById(R.id.spline_interpolation);
        neighborInterpolationRb = (RadioButton) findViewById(R.id.neighbor_interpolation);
        infoList = (ListView) findViewById(R.id.info);
        mdata = new ArrayList<>();
        mAdapter = new InfoUploadAdapter(this, mdata);
        infoList.setAdapter(mAdapter);

        mAdapter.setListener(new InfoUploadAdapter.CheckedChange() {
            @Override
            public void onCheckedChange(boolean isChecked, DataMngItem item) {
                if (isChecked) {
                    mChecked.add(item);
                } else {
                    mChecked.remove(item);
                }
            }
        });
    }

    private void readData() {
        mdata.clear();
        //从数据库读取数据

        mAdapter.notifyDataSetChanged();
    }

    public void upload(View view) {
//只需要把mChecked上传即可
        switch (mGroup.getCheckedRadioButtonId()) {
            case R.id.neural:

                break;

            case R.id.neighbor_interpolation:

                break;

            case R.id.spline_interpolation:

                break;

            case R.id.least_square:

                break;
        }
    }
}
