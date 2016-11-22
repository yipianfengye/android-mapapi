package xiaomi.mich.com.android_mapapi;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by aaron on 2016/11/15.
 */

public class Config {

    public static Dialog dialog = null;

    /**
     * 百度地图和高德地图安装包包名
     */
    public static final String BAIDU_PACKAGE = "com.baidu.BaiduMap";
    public static final String GAODE_PACKAGE = "com.autonavi.minimap";

    /**
     * 导航类型:1:百度导航，2：高德导航
     */
    public static final int NAV_TYPE_BAIDU = 1;
    public static final int NAV_TYPE_GAODE = 2;
    /**
     * 导航方式：0：步行导航， 1：驾车导航
     */
    public static final int TYPE_WALK = 0;
    public static final int TYPE_DRIVE = 1;
    public static final int TYPE_BUS = 2;

    /**
     * 显示选择导航弹出框
     * @param mContext
     * @param title 弹窗标题
     * @param isBaiduOk 是否安装百度地图应用
     * @param isGaodeOk 是否安装高德地图应用
     * @param navType 导航类型： 步行导航：TYPE_WALK， 驾车导航：TYPE_DRIVE， 公交导航： TYPE_BUS
     */
    public static void showNavSelectDialog(final Activity mContext, AddInfo start, AddInfo end, String city, String title, boolean isBaiduOk, boolean isGaodeOk, int navType) {
        if (mContext == null) {
            return;
        }
        if (!isBaiduOk && !isGaodeOk) {
            return;
        }

        if (TextUtils.isEmpty(title)) {
            title = "选择导航";
        }

        // 直接跳转百度导航
        if (isBaiduOk && !isGaodeOk) {
            buildIntentFromScheme(mContext, NAV_TYPE_BAIDU, start, end, city, navType);
            return;
        }
        // 直接跳转高德导航
        if (!isBaiduOk && isGaodeOk) {
            buildIntentFromScheme(mContext, NAV_TYPE_GAODE, start, end, city, navType);
            return;
        }

        View view = inflateView(mContext, title, isBaiduOk, isGaodeOk, start, end, city, navType);
        dialog = DialogUtil.getInstance(mContext).showMaterialCustomDialog(view, false);
    }


    /**
     * 加载布局文件
     * @return
     */
    public static View inflateView(final Activity mContext, String title, boolean isBaiduOk, boolean isGaodeOk, AddInfo start, AddInfo end, String city, int navType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.nav_select, null);

        TextView textTitle = (TextView) view.findViewById(R.id.navselect_text_title);
        textTitle.setText(title);

        RelativeLayout baiduRela = (RelativeLayout) view.findViewById(R.id.navselect_rela_baidu);
        ImageView baiduIcon = (ImageView) view.findViewById(R.id.navselect_icon_baidu);
        TextView baiduName = (TextView) view.findViewById(R.id.navselect_name_baidu);

        RelativeLayout gaodeRela = (RelativeLayout) view.findViewById(R.id.navselect_rela_gaode);
        ImageView gaodeIcon = (ImageView) view.findViewById(R.id.navselect_icon_gaode);
        TextView gaodeName = (TextView) view.findViewById(R.id.navselect_name_gaode);

        // 数组中[0]为百度地图，[1]为高德地图
        PackageInfo[] mPackageInfo = getAppInfoByPackage(mContext);
        if (isBaiduOk) {
            baiduRela.setVisibility(View.VISIBLE);
            if (mPackageInfo != null && mPackageInfo[0] != null) {
                String appName = mPackageInfo[0].applicationInfo.loadLabel(mContext.getPackageManager()).toString();
                Drawable appIcon = mPackageInfo[0].applicationInfo.loadIcon(mContext.getPackageManager());
                if (!TextUtils.isEmpty(appName)) {
                    // 若存在特殊空格字符，则替换掉
                    baiduName.setText(appName.trim().replaceAll("\\s*", ""));
                }
                baiduIcon.setBackgroundDrawable(appIcon);
            }
        } else {
            baiduRela.setVisibility(View.GONE);
        }
        if (isGaodeOk) {
            gaodeRela.setVisibility(View.VISIBLE);
            if (mPackageInfo != null && mPackageInfo[1] != null) {
                String appName = mPackageInfo[1].applicationInfo.loadLabel(mContext.getPackageManager()).toString();
                Drawable appIcon = mPackageInfo[1].applicationInfo.loadIcon(mContext.getPackageManager());
                if (!TextUtils.isEmpty(appName)) {
                    // 若存在特殊空格字符，则替换掉
                    gaodeName.setText(appName.trim().replaceAll("\\s*", ""));
                }
                gaodeIcon.setBackgroundDrawable(appIcon);
            }
        } else {
            gaodeRela.setVisibility(View.GONE);
        }
        // 初始化按钮监听
        initRippleListener(mContext, baiduRela, gaodeRela, start, end, city, navType);
        return view;
    }


    /**
     * 初始化按钮监听
     * @param mContext
     * @param baiduRela
     * @param gaodeRela
     */
    public static void initRippleListener(final Activity mContext, RelativeLayout baiduRela, RelativeLayout gaodeRela, final AddInfo start, final AddInfo end, final String city, final int navType) {
        baiduRela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtil.getInstance(mContext).closeDialog();
                buildIntentFromScheme(mContext, NAV_TYPE_BAIDU, start, end, city, navType);
            }
        });
        gaodeRela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtil.getInstance(mContext).closeDialog();
                buildIntentFromScheme(mContext, NAV_TYPE_GAODE, start, end, city, navType);
            }
        });
    }


    /**
     * 解析百度地图和高德地图的sheme，返回Intent对象
     */
    public static void buildIntentFromScheme(final Activity mContext, final int navKind, final AddInfo start, final AddInfo end, final String city, final int navType) {
        if (navKind == NAV_TYPE_BAIDU) {
            StringBuffer uriBuffer = new StringBuffer();
            uriBuffer.append(NavMapConstant.SCHEME_BAIDU_PRE)
                    .append(NavMapConstant.SCHEME_BAIDU_ORIGIN).append("latlng:").append(start.getLatLng().latitude).append(",").append(start.getLatLng().longitude).append("|name:").append(start.getAddress())
                    .append("&").append(NavMapConstant.SCHEME_BAIDU_DESTINATION).append("latlng:").append(end.getLatLng().latitude).append(",").append(end.getLatLng().longitude).append("|name:").append(end.getAddress())
                    .append("&").append(NavMapConstant.SCHEME_BAIDU_MODE).append(changeBaiMode(navType))
                    .append("&").append(NavMapConstant.SCHEME_BAIDU_REGION).append(city)
                    .append("&").append(NavMapConstant.SCHEME_BAIDU_TYPE).append("gcj02")
                    .append("&").append(NavMapConstant.SCHEME_BAIDU_SRC).append(getApplicationName(mContext))
                    .append(NavMapConstant.SCHEME_BAIDU_LAST);
            Intent intent = null;
            try {
                intent = Intent.parseUri(uriBuffer.toString(), Intent.URI_INTENT_SCHEME);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            mContext.startActivity(intent);
        } else if (navKind == NAV_TYPE_GAODE) {
            StringBuffer uriBuffer = new StringBuffer();
            uriBuffer.append(NavMapConstant.SCHEME_GAODE_PRE)
                    .append(NavMapConstant.SCHEME_GAODE_SOURCEAPPLICATION).append(getApplicationName(mContext))
                    .append("&").append(NavMapConstant.SCHEME_GAODE_SLAT).append(start.getLatLng().latitude)
                    .append("&").append(NavMapConstant.SCHEME_GAODE_SLON).append(start.getLatLng().longitude)
                    .append("&").append(NavMapConstant.SCHEME_GAODE_SNAME).append(start.getAddress())
                    .append("&").append(NavMapConstant.SCHEME_GAODE_DLAT).append(end.getLatLng().latitude)
                    .append("&").append(NavMapConstant.SCHEME_GAODE_DLON).append(end.getLatLng().longitude)
                    .append("&").append(NavMapConstant.SCHEME_GAODE_DNAME).append(end.getAddress())
                    .append("&").append(NavMapConstant.SCHEME_GAODE_DEV).append("0")
                    .append("&").append(NavMapConstant.SCHEME_GAODE_M).append("3")
                    .append("&").append(NavMapConstant.SCHEME_GAODE_T).append(changeGaoMode(navType));
            Intent intent = new Intent();
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setData(Uri.parse(uriBuffer.toString()));
            intent.setPackage(GAODE_PACKAGE);
            mContext.startActivity(intent);
        }
    }


    /**
     * 获取高德地图的导航方式
     * @param navType
     * @return
     */
    public static int changeGaoMode(int navType) {
        int result = 4;
        if (navType == TYPE_WALK) {
            result = 4;
        } else if (navType == TYPE_DRIVE) {
            result = 2;
        } else if (navType == TYPE_BUS) {
            result = 1;
        }

        return result;
    }

    /**
     * 获取百度地图的导航方式
     * @param navType
     * @return
     */
    public static String changeBaiMode(int navType) {
        String result = "";
        if (navType == TYPE_WALK) {
            result = "walking";
        } else if (navType == TYPE_DRIVE) {
            result = "driving";
        } else if (navType == TYPE_BUS) {
            result = "transit";
        }

        return result;
    }

    /**
     * 获取当前应用名称
     * @param mContext
     * @return
     */
    public static String getApplicationName(final Activity mContext) {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = mContext.getApplicationContext().getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(mContext.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        String applicationName = (String) packageManager.getApplicationLabel(applicationInfo);
        return applicationName;
    }


    /**
     * 获取packageInfo数组，第一个元素为
     * @param mContext
     * @return
     */
    public static PackageInfo[] getAppInfoByPackage(Activity mContext) {
        PackageInfo[] mPackageInfo = new PackageInfo[2];
        List<PackageInfo> packages = mContext.getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packages.size(); i ++) {
            // L.i("执行：" + i);
            if (packages.get(i).packageName.equals(BAIDU_PACKAGE)) {
                mPackageInfo[0] = packages.get(i);
            } else if (packages.get(i).packageName.equals(GAODE_PACKAGE)) {
                mPackageInfo[1] = packages.get(i);
            }

            if (mPackageInfo[0] != null && mPackageInfo[1] != null) {
                return mPackageInfo;
            }
        }

        return mPackageInfo;
    }

    /**
     * 判断某个应用是否安装
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isAvilible(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        // 获取所有已安装程序的包信息
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        for (int i = 0; i < pinfo.size(); i++) {
            if (pinfo.get(i).packageName.equalsIgnoreCase(packageName)) {
                return true;
            }
        }
        return false;
    }
}
