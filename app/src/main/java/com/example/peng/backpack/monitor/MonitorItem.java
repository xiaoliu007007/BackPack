package com.example.peng.backpack.monitor;

/**
 *Create by:Zhang Yunpeng
 *Date:2017/06/01
 *Modify by:
 *Date:
 *Modify by:
 *Date:
 *describe:包含三列数据信息 ，通道号、测量值、状态
 */

public class MonitorItem {

    private static final String TAG = "DataMngItem";
    private String channel;
    private String measure_val;
    private String status;

    //返回通道号
    public String getChannel() {
        return this.channel;
    }
    //设置通道号
    public void setChannel(String channel) {
        this.channel = channel;
    }

    //设置测量值
    public String getMesure_val() {
        return this.measure_val;
    }
   //获取测量值
    public void setMesure_val(String measure_val) {
        this.measure_val = measure_val;}

    //获取探头状态
    public String getStatus() {
        return this.status;
    }
    //设置探头状态
    public void setStatus(String status) {
        this.status = status;
    }
}
