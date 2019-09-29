package com.example.peng.backpack.data_mng;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

/**
 * @description: 辐射值数据信息
 * @author: lyj
 * @create: 2019/09/02
 **/
public class DataMsg extends SugarRecord {
    @Unique
    String time;
    String value;
    double longitude;
    double latitude;
    int status;

    public DataMsg() {

    }

    public DataMsg(String time, String value, double longitude, double latitude, int status) {
        this.time = time;
        this.value = value;
        this.longitude = longitude;
        this.latitude = latitude;
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "DataMsg{" +
                "time='" + time + '\'' +
                ", value='" + value + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", status=" + status +
                '}';
    }
}
