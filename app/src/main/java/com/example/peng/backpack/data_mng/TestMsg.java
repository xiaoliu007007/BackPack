package com.example.peng.backpack.data_mng;

import com.orm.SugarRecord;
/**
 * @description: 测试内容
 * @author: lyj
 * @create: 2019/09/09
 **/
public class TestMsg extends SugarRecord {
    int value;
    int longitude;
    int latitude;

    public TestMsg() {
    }

    public TestMsg(int value, int longitude, int latitude) {
        this.value = value;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "TestMsg{" +
                "value=" + value +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
