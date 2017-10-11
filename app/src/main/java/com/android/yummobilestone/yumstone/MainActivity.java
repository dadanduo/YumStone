package com.android.yummobilestone.yumstone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.yummobilestone.yumstone.App.AppApplication;
import com.android.yummobilestone.yumstone.utils.T;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class MainActivity extends Activity {

    @ViewInject(R.id.end_btn)
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);
        //是否为调试模式true:是，false:运营模式
        isOutPut(true);

        //直接判断是否为定制机是istrue 方行使用 isfalse直接拒绝退出系统
        if(AppApplication.JUDGAMENT.endsWith(android.os.Build.MODEL))
        {
            T.showShort(MainActivity.this, AppApplication.CUSTOMMODELS);
            finish();
        }
        //切换盒子与拉卡拉默认为空，系统为拉卡拉，代码注释，1为盒子系统代码启动
//        SharedPreferencesClass spc=new SharedPreferencesClass();
//        spc.SharedPreferencesClass(MainActivity.this);
//        spc.sendSharedValues("JUDGMENTMODEL", "1");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,WebH5Activity.class));
            }
        });

    }

    //是否输出日志
    public void isOutPut(boolean isor) {
        LogUtils.allowD = isor;
        LogUtils.allowE = isor;
        LogUtils.allowI = isor;
        LogUtils.allowV = isor;
        LogUtils.allowW = isor;
        LogUtils.allowWtf = isor;
    }
}
