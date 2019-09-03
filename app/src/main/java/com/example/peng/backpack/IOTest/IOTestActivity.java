package com.example.peng.backpack.IOTest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.peng.backpack.R;

import java.util.HashMap;

public class IOTestActivity extends AppCompatActivity {

    private CheckBox mFrontRadio;
    private CheckBox mBehindRadio;
    private CheckBox mhwLoc1;
    private CheckBox mhwLoc2;
    private CheckBox mAlarmConf;
    private CheckBox mAcInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_io_test);

        initView();
//        resetStatus(new HashMap<String, Boolean>());
    }


    private void initView() {
        mFrontRadio = (CheckBox) findViewById(R.id.front_radio);
        mBehindRadio = (CheckBox) findViewById(R.id.behind_radio);
        mhwLoc1 = (CheckBox) findViewById(R.id.hongwai_1);
        mhwLoc2 = (CheckBox) findViewById(R.id.hongwai_2);
        mAlarmConf = (CheckBox) findViewById(R.id.alarm_confirm);
        mAcInput = (CheckBox) findViewById(R.id.ac_input);
    }


    private void resetStatus(HashMap<String, Boolean> statuses) {
        mFrontRadio.setChecked(statuses.get("front_radio"));
        mBehindRadio.setChecked(statuses.get("behind_radio"));
        mhwLoc1.setChecked(statuses.get("hongwai_1"));
        mhwLoc2.setChecked(statuses.get("hongwai_2"));
        mAlarmConf.setChecked(statuses.get("alarm_confirm"));
        mAcInput.setChecked(statuses.get("ac_input"));

//        mFrontRadio.setChecked(statuses.getOrDefault("front_radio", false));
//        mBehindRadio.setChecked(statuses.getOrDefault("behind_radio", false));
//        mhwLoc1.setChecked(statuses.getOrDefault("hongwai_1", false));
//        mhwLoc2.setChecked(statuses.getOrDefault("hongwai_2", false));
//        mAlarmConf.setChecked(statuses.getOrDefault("alarm_confirm", false));
//        mAcInput.setChecked(statuses.getOrDefault("ac_input", false));
//        mBatteryLess.setChecked(statuses.getOrDefault("battery_less", false));
    }

    public void normal(View view) {
        Toast.makeText(this, "打开‘正常’指示灯", Toast.LENGTH_LONG).show();
    }

    public void error(View view) {
        Toast.makeText(this, "打开‘故障’指示灯", Toast.LENGTH_LONG).show();


    }

    public void startFmq(View view) {
        Toast.makeText(this, "开启蜂鸣器", Toast.LENGTH_LONG).show();

    }

    public void ac(View view) {
        Toast.makeText(this, "打开‘市电’指示灯", Toast.LENGTH_LONG).show();

    }

    public void alarm(View view) {
        Toast.makeText(this, "打开‘报警’指示灯", Toast.LENGTH_LONG).show();

    }

    public void stopFmq(View view) {
        Toast.makeText(this, "关闭蜂鸣器", Toast.LENGTH_LONG).show();

    }


}
