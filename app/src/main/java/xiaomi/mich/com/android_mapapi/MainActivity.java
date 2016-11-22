package xiaomi.mich.com.android_mapapi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.amap.api.maps.model.LatLng;
import com.amap.api.navi.model.NaviLatLng;

public class MainActivity extends AppCompatActivity {

    public Activity mContext = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        findViewById(R.id.titletext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 判断是否安装了百度和高德地图
                boolean isBaiduOk = Config.isAvilible(mContext, Config.BAIDU_PACKAGE);
                boolean isGaodeOk = Config.isAvilible(mContext, Config.GAODE_PACKAGE);
                if (isBaiduOk || isGaodeOk) {
                    AddInfo start = new AddInfo();
                    start.setAddress("顺事嘉业创业园");
                    start.setLatLng(new LatLng(116.340532,40.033389));
                    AddInfo end = new AddInfo();
                    end.setAddress("人民大学");
                    end.setLatLng(new LatLng(116.318894,39.976446));
                    Config.showNavSelectDialog(mContext, start, end, "北京", "选择导航", isBaiduOk, isGaodeOk, Config.TYPE_DRIVE);
                    return;
                }
            }
        });
    }

}
