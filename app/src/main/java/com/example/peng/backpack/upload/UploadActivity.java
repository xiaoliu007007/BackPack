package com.example.peng.backpack.upload;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.peng.backpack.R;
import com.example.peng.backpack.data_mng.DataMsg;
import com.example.peng.backpack.main.MainActivity;
import com.example.peng.backpack.route.BNaviMainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.HttpStatus;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.util.EntityUtils;

/**
 * @description: 上传Activity
 * @author: lyj
 * @create: 2019/09/02
 **/
public class UploadActivity extends AppCompatActivity {
    public static final int SHOW_ANS=0;
    private ListView infoList;
    private UploadAdapter mAdapter;
    private List<DataMsg> mdata;
    private double start_latitude;
    private double start_longitude;

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
        Button upload = (Button)findViewById(R.id.upload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMsg();
            }
        });
    }

    private List<DataMsg> find(){
        List<DataMsg > msg = DataMsg.listAll(DataMsg .class);
        return msg;
    }

    private void sendMsg(){
        new Thread(new Runnable() {
            @Override
            public void run(){
                HttpClient httpClient = new DefaultHttpClient();
                String url=MainActivity.IP+"getMsg/";
                HttpPost httpPost = new HttpPost(url);
                List<DataMsg> list=find();
                ArrayList<NameValuePair> datas = new ArrayList<NameValuePair>();
                for(DataMsg msg:list){
                    NameValuePair data = new BasicNameValuePair("name", msg.toString());
                    datas.add(data);
                }
                try {
                    HttpEntity requestEntity = new UrlEncodedFormEntity(datas);
                    httpPost.setEntity(requestEntity);
                    try {
                        HttpResponse response = httpClient.execute(httpPost);
                        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                            String result = EntityUtils.toString(response.getEntity());
                            Message message = new Message();
                            message.what = SHOW_ANS;
                            message.obj = result;
                            if(result!=null && result.length()!=0){
                                handler.sendMessage(message);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch (msg.what) {
                case SHOW_ANS:
                    JSONObject ans = null;
                    try {
                        ans = new JSONObject((String) msg.obj);
                        Intent intent = new Intent(UploadActivity.this, BNaviMainActivity.class);
                        intent.putExtra("end_longitude",ans.get("longitude").toString());
                        intent.putExtra("end_latitude",ans.get("latitude").toString());
                        intent.putExtra("start_longitude",MainActivity.Longitude);
                        intent.putExtra("start_latitude", MainActivity.Latitude);
                        startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;

                default:
                    break;

            }

        }
    };
}
