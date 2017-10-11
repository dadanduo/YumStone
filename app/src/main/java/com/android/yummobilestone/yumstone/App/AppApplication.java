package com.android.yummobilestone.yumstone.App;
import android.app.Activity;
import android.app.Application;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chend on 2017/4/24.
 */
public class AppApplication extends Application {
    public static HttpUtils http;//网络请求对象
    private List<Activity> list = new ArrayList<Activity>();// 存放Activity的集合
    public static AppApplication application;
    public static  int Language = 0; //0:中文 1:英文
    //创建一个字符串常量，作为android与js通信的接口，即字符串映射对象
    public static final String JAVAINTERFACE = "javaInterface";
    //设置机型的名称用于判断是否为该机型OPPO R9st
    public static final String JUDGAMENT="V8";
    public static final String CUSTOMMODELS="你的机型不是定制机型";
    //调用前端入口urlfile:///android_asset/demo2.html
    public static final String  URL="http://14e96168b6.51mypc.cn/plc/callscreen/index.htm#/area/192.168.1.191";
    public static Gson gson;
    @Override
    public void onCreate() {
        super.onCreate();
        //程序首次加载只需要加载一次
       http = new HttpUtils();
       gson = new Gson();
    }
    //--------------------------------------
    //中英文切换
    public int getLanguage()
    {
    	return Language;
    }
    public void setLanguage(int Language)
    {
    	this.Language=Language;
    }
    //------------------------------------
    /** 单例模式 */
    public static AppApplication getInstance() {
        return application;
    }
    /** 存放Activity的集合 */
    public void addActivity(Activity activity) {
        list.add(activity);
    }
    /** 退出登录 */
    public void logout() {
        for (Activity activity : list) {
            activity.finish();
        }
    }

}
