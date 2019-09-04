package com.example.peng.backpack.monitor;

/**
 *Create by:Zhang Yunpeng
 *Date:2017/06/06
 *Modify by:
 *Date:
 *Modify by:
 *Date:
 *describe:轨迹绘制模块，在绘制工作人员轨迹同时根据背包状态将轨迹进行着色
 */

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.peng.backpack.LocationApplication;
import com.example.peng.backpack.R;
import com.example.peng.backpack.main.MainActivity;
import com.example.peng.backpack.monitor.trace.LocationService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TraceFragment extends Fragment {
    private static final String TAG = "TraceFragment";
    private MapView mMapView = null;    //地图视图
    private BaiduMap mBaiduMap = null;  //地图
    private Overlay last_track = null;  //路径
    private static int ColorStatus = 0; //当前所选用的颜色
    private static String[] statusName = {"禁用", "正常", "污染", "严重污染", "故障"};
    private LocationListener mCallback;

    /**
     * @description: 传递坐标
     * @author: lyj
     * @create: 2019/09/02
     **/
    public interface LocationListener{
        public void setLocation(double latitude,double longitude);
    }

    /** 设置与获取轨迹颜色 */
    public void setColorStatus(int status) {
        ColorStatus = status;
    }
    public int getColorStatus() {
        return ColorStatus;
    }


    LocationService locationService;
    LatLng lastPnt = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * @description: 创建通信接口
     * @author: lyj
     * @create: 2019/09/02
     **/
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        String name=activity.getLocalClassName();
        System.out.println("---------------------------------"+name);
        if(name.equals("monitor.MonitorActivity")){
            try {
                mCallback = (LocationListener)activity;
            }catch (ClassCastException e) {
                throw new ClassCastException(activity.toString());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_trace, container, false);
        init(view);
        return view;
    }

    private void init(View view){
        /**在使用SDK各组件之前初始化context信息，传入ApplicationContext
         *注意该方法要再setContentView方法之前实现
         **/
        Activity activity = getActivity();
        mMapView = (MapView) view.findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        //开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
    }

    @Override
    public void onStop() {
        locationService.unregisterListener(mListener); //注销掉监听
        locationService.stop(); //停止定位服务
        super.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
        /**获取locationservice实例，建议应用中只初始化1个location实例，然后使用
         *可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
         **/
        locationService = ((LocationApplication) getActivity().getApplication()).locationService;
        locationService.registerListener(mListener); //注册监听
        int type = getActivity().getIntent().getIntExtra("from", 0);
        if (type == 0) {
            locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        } else if (type == 1) {
            locationService.setLocationOption(locationService.getOption());
        }
        //启动定位服务,此时百度地图开始每隔一定时间(setScanSpan)就发起一次定位请求
        locationService.start();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        locationService.stop(); //停止定位服务
        mBaiduMap.setMyLocationEnabled(false);  // 关闭定位图层
        mMapView.onDestroy();
        mMapView = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume(); //在activity执行onResume时执行mMapView.onResume()
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause(); //在activity执行onPause时执行mMapView.onPause ()
    }

    private BDLocationListener mListener = new BDLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                MyLocationData locData = new MyLocationData.Builder()
                        .accuracy(location.getRadius())
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(100).latitude(location.getLatitude())
                        .longitude(location.getLongitude()).build();

                //获取经纬度
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                MainActivity.dataBaseModule.addTest(getTime(),Double.toString(longitude), Double.toString(latitude));
                MainActivity.Latitude = latitude;
                MainActivity.Longitude = longitude;
                Log.i(TAG, "onReceiveLocation: "+"纬度："+Double.toString(latitude)+"；经度：" +
                        Double.toString(longitude) + "；状态：" + statusName[ColorStatus] + "；精准度"
                        + location.getRadius());
                        //+ "；地址" + location.getAddrStr() + "；运营商" +
                        //location.getOperators());

                mBaiduMap.setMyLocationData(locData);
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

                /** 根据经纬度生成一个坐标点
                 * 判断如果是第一次定位则不发生连线
                 * 以后每次将本次定位点与上一次定位点相连接，产生一条轨迹
                 * 根据颜色状态，改变当前轨迹颜色
                 * 如果定位失败，告知用户
                 * */
                LatLng pnt = new LatLng(location.getLatitude(), location.getLongitude());
                if(lastPnt == null){
                    lastPnt = pnt;
                    return;
                }
                List<LatLng> pointList = new ArrayList<>();
                pointList.add(lastPnt);
                pointList.add(pnt);
                lastPnt = pnt;
                if(ColorStatus == 0) {
                    PolylineOptions polyline = new PolylineOptions().width(10).color(Color.GRAY).points(pointList);
                    Overlay track = mBaiduMap.addOverlay(polyline);
                }else if(ColorStatus == 1){
                    PolylineOptions polyline = new PolylineOptions().width(10).color(Color.GREEN).points(pointList);
                    Overlay track = mBaiduMap.addOverlay(polyline);
                }else if(ColorStatus == 2) {
                    PolylineOptions polyline = new PolylineOptions().width(10).color(Color.RED).points(pointList);
                    Overlay track = mBaiduMap.addOverlay(polyline);
                }else if(ColorStatus == 3) {
                    PolylineOptions polyline = new PolylineOptions().width(10).color(Color.MAGENTA).points(pointList);
                    Overlay track = mBaiduMap.addOverlay(polyline);
                }else if(ColorStatus == 4) {
                    PolylineOptions polyline = new PolylineOptions().width(10).color(Color.YELLOW).points(pointList);
                    Overlay track = mBaiduMap.addOverlay(polyline);
                }else {

                }

                if (location.getLocType() == BDLocation.TypeServerError) {
                    Toast.makeText(getActivity().getApplicationContext(), "服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因", Toast.LENGTH_LONG).show();
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    Toast.makeText(getActivity().getApplicationContext(), "网络不同导致定位失败，请检查网络是否通畅", Toast.LENGTH_LONG).show();
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    Toast.makeText(getActivity().getApplicationContext(), "无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机", Toast.LENGTH_LONG).show();
                }
                mCallback.setLocation(latitude,longitude);
            }
        }
    };

    /** 获取当前时间 */
    private String getTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String date = dateFormat.format(new java.util.Date());

        return date;
    }

}
