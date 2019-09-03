package com.example.peng.backpack.sys_setting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.peng.backpack.R;

import java.util.HashMap;

public class SysSettingActivity extends AppCompatActivity {

    private EditText mAlarmParamEdt;
    private EditText mAlarmCPSEdt;
    private EditText mSeverAlarmParamEdt;
    private EditText mSeverAlarmCPSEdt;
    private EditText mFuSheEdt;
    private EditText mRenYuanEdt;
    private EditText mAlarmTimeEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sys_setting);
        initView();
    }

    private void initView() {
        mAlarmParamEdt = (EditText) findViewById(R.id.alarm_param);
        mAlarmCPSEdt = (EditText) findViewById(R.id.alarm_cps);
        mSeverAlarmParamEdt = (EditText) findViewById(R.id.sever_alarm_param);
        mSeverAlarmCPSEdt = (EditText) findViewById(R.id.sever_alarm_cps);
        mFuSheEdt = (EditText) findViewById(R.id.fscsphyz);
        mRenYuanEdt = (EditText) findViewById(R.id.rypbbcyz);
        mAlarmTimeEdt = (EditText) findViewById(R.id.alarm_reset_time);
    }

    public void set(View view) {
        Toast.makeText(this, "设置参数", Toast.LENGTH_LONG).show();
    }

    public void get(View view) {

    }
}
