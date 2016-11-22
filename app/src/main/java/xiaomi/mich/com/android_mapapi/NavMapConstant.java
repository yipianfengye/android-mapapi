package xiaomi.mich.com.android_mapapi;

/**
 * Created by liuchao on 2015/12/30.
 */
public class NavMapConstant {

    // ####################################### 百度地图scheme定义start  #############################################
    /**
     * 百度地图scheme的构成，可参考百度地图URI
     *      API官方文档：http://developer.baidu.com/map/wiki/index.php?title=uri/api/android#.E5.85.AC.E4.BA.A4.E3.80.81.E9.A9.BE.E8.BD.A6.E3.80.81.E6.AD.A5.E8.A1.8C.E5.AF.BC.E8.88.AA
     */
    /**
     * scheme前缀
     * 必选
     */
    public static final String SCHEME_BAIDU_PRE = "intent://map/direction?";
    /**
     * 起点
     * 必选
     * 1、名称：天安门
     * 2、经纬度：39.98871 <纬度> ,116.43234 <经度> 。
     * 3、名称和经纬度：name:天安门|latlng:39.98871,116.43234
     */
    public static final String SCHEME_BAIDU_ORIGIN = "origin=";
    /**
     * 终点
     * 必选
     * 1、名称：天安门
     * 2、经纬度：39.98871 <纬度> ,116.43234 <经度> 。
     * 3、名称和经纬度：name:天安门|latlng:39.98871,116.43234
     */
    public static final String SCHEME_BAIDU_DESTINATION = "destination=";
    /**
     * 必选
     * 导航模式，固定为transit、driving、walking，分别表示公交、驾车和步行
     */
    public static final String SCHEME_BAIDU_MODE = "mode=";
    /**
     * 可选
     * 允许的值为bd09ll、bd09mc、gcj02、wgs84。
     * bd09ll表示百度经纬度坐标，bd09mc表示百度墨卡托坐标，gcj02表示经过国测局加密的坐标，wgs84表示gps获取的坐标。
     */
    public static final String SCHEME_BAIDU_TYPE = "coord_type=";
    /**
     * 城市名或县名
     * 当给定region时，认为起点和终点都在同一城市，除非单独给定起点或终点的城市。
     */
    public static final String SCHEME_BAIDU_REGION = "region=";
    /**
     * 必选
     * appName APP名称
     */
    public static final String SCHEME_BAIDU_SRC = "src=";
    /**
     * scheme后缀
     * 必选
     */
    public static final String SCHEME_BAIDU_LAST = "#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end";
    // ####################################### 百度地图scheme定义end  #############################################




    // ####################################### 高德地图scheme定义start  #############################################
    /**
     * 高德地图scheme的构成，可参考高德地图URI
     *      API官方文档：http://lbs.amap.com/api/uri-api/android-uri-explain/
     */
    /**
     * scheme前缀
     */
    public static final String SCHEME_GAODE_PRE = "androidamap://route?";
    /**
     * 必选
     * 第三方调用应用名称。如 amap
     */
    public static final String SCHEME_GAODE_SOURCEAPPLICATION = "sourceApplication=";
    /**
     * 必选
     * 起点纬度
     */
    public static final String SCHEME_GAODE_SLAT = "slat=";
    /**
     * 必选
     * 起点经度
     */
    public static final String SCHEME_GAODE_SLON = "slon=";
    /**
     * 必选
     * 起点名称
     */
    public static final String SCHEME_GAODE_SNAME = "sname=";
    /**
     * 必选
     * 终点纬度
     */
    public static final String SCHEME_GAODE_DLAT = "dlat=";
    /**
     * 必选
     * 终点经度
     */
    public static final String SCHEME_GAODE_DLON = "dlon=";
    /**
     * 必选
     * 终点名称
     */
    public static final String SCHEME_GAODE_DNAME = "dname=";
    /**
     * 必选
     * 是否偏移(0:lat 和 lon 是已经加密后的,不需要国测加密; 1:需要国测加密)
     */
    public static final String SCHEME_GAODE_DEV = "dev=";
    /**
     * 必选
     * 驾车方式 =0（速度快）=1（费用少） =2（路程短）=3 不走高速 =4（躲避拥堵）=5（不走高速且避免收费） =6（不走高速且躲避拥堵） =7（躲避收费和拥堵） =8（不走高速躲避收费和拥堵）。
     * 公交 =0（速度快）=1（费用少） =2（换乘较少）=3（步行少）=4（舒适）=5（不乘地铁）
     */
    public static final String SCHEME_GAODE_M = "m=";
    /**
     * 必选
     * t = 1(公交) =2（驾车） =4(步行)
     */
    public static final String SCHEME_GAODE_T = "t=";
    // ####################################### 高德地图scheme定义end  #############################################
}
