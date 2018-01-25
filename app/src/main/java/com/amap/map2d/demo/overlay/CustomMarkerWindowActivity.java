package com.amap.map2d.demo.overlay;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.map2d.demo.R;
import com.amap.map2d.demo.util.WindowDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * @author azheng
 * @date 2018/1/24.
 * GitHub：https://git.oschina.net/weiazheng
 * email：wei.azheng@foxmail.com
 * description：自定义 Marker 、Window
 */


public class CustomMarkerWindowActivity extends Activity implements AMap.OnMarkerClickListener {

    private MapView mMapView;
    /**
     * 初始化地图控制器对象
     */
    private AMap aMap;

    private MarkerOptions markerOptionsDog;

    private MarkerOptions markerOptionsCat;

    private Marker mMarkerDog;
    private Marker mMarkerCat;

    private List<MarkerBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_marker_window_activity);

        mMapView = (MapView) findViewById(R.id.mapView_custom_marker_window);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);

        initView();
        initData();

    }

    private void initView() {
        if (aMap == null) {
            aMap = mMapView.getMap();
            markerOptionsCat = new MarkerOptions();
            markerOptionsDog = new MarkerOptions();
        }
        aMap.setOnMarkerClickListener(this);
    }

    private void initData() {
        list = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            MarkerBean markerBean = new MarkerBean();
            if (i % 2 == 0) {
                markerBean.setType(1);
                markerBean.setDog("🐶" + i);
                markerBean.setAge(i);
                markerBean.setGender("公");
            } else {
                markerBean.setType(2);
                markerBean.setCat("o(=•ェ•=)" + i);
                markerBean.setAge(i);
                markerBean.setGender("母");
            }
            markerBean.setLon((36 + i) + .635875);
            markerBean.setLat((117 - i) + .115943);

            list.add(markerBean);

        }
        initData(list);
    }

    /**
     * 获取到的数据进行赋值 添加到地图上
     *
     * @param list
     */
    private void initData(List<MarkerBean> list) {

        for (int i = 0; i < list.size(); i++) {
            //根据数据 判断是哪一种身份
            switch (list.get(i).getType()) {
                case 1:
                    //小狗
                    View viewDog = LayoutInflater.from(this).inflate(R.layout.marker_window_dog, null);
                    TextView textViewDog = (TextView) viewDog.findViewById(R.id.text_marker_window_dog);
                    textViewDog.setText(list.get(i).getDog());

                    /**
                     * 经度和纬度
                     */
                    LatLng mLatlngDog = new LatLng(list.get(i).getLon(), list.get(i).getLat());
                    //将年龄和性别拼接起来 点击Marker后的窗口取出
                    String groupDog = list.get(i).getAge() + "#" + list.get(i).getGender();

                    markerOptionsDog.position(mLatlngDog)
                            .title(list.get(i).getDog())
                            .snippet(groupDog)
                            .draggable(false)
                            .icon(BitmapDescriptorFactory.fromView(viewDog));
                    //将数据添加到地图上
                    mMarkerDog = aMap.addMarker(markerOptionsDog);
                    //设置类型为1  为了区分 点击 哪一个类型的Marker
                    mMarkerDog.setObject(1);
                    break;
                case 2:
                    //小猫
                    View viewCat = LayoutInflater.from(this).inflate(R.layout.marker_window_cat, null);
                    TextView textViewCat = (TextView) viewCat.findViewById(R.id.text_marker_window_cat);
                    textViewCat.setText(list.get(i).getCat());
                    /**
                     * 经度和纬度
                     */
                    LatLng mLatlngCat = new LatLng(list.get(i).getLon(), list.get(i).getLat());
                    //将年龄和性别拼接起来
                    String groupCat = list.get(i).getAge() + "#" + list.get(i).getGender();

                    markerOptionsCat.position(mLatlngCat)
                            .title(list.get(i).getCat())
                            .snippet(groupCat)
                            .draggable(false)
                            .icon(BitmapDescriptorFactory.fromView(viewCat));
                    //将数据添加到地图上
                    mMarkerCat = aMap.addMarker(markerOptionsCat);
                    //设置类型为2  为了区分 点击 哪一个类型的Marker
                    mMarkerCat.setObject(2);

                    break;
                default:

                    break;
            }

        }
        setPosition();
    }

    /**
     * 根据动画按钮状态，调用函数animateCamera或moveCamera来改变可视区域
     */
    private void changeCamera(CameraUpdate update) {

        aMap.moveCamera(update);

    }

    /**
     * 设置初始化地图位置
     * CameraPosition() 方法
     * var1 target 目标
     * var2 zoom  变焦
     * var3 tilt 倾斜
     * var4 bearing 方位
     */
    private void setPosition() {
        LatLng mLatlng = new LatLng(36.635875, 117.115943);
        changeCamera(
                CameraUpdateFactory.newCameraPosition(new CameraPosition(
                        mLatlng, 7, 30, 30)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    /**
     * 对marker标注点 点击响应事件
     *
     * @param marker
     * @return
     */
    @Override
    public boolean onMarkerClick(Marker marker) {
        if (aMap != null & marker.getTitle() != null) {
            // 点击地图Marker 打开Window
            WindowDialog.getInstance().showDialog(marker, CustomMarkerWindowActivity.this);
        }
        return true;
    }
}
