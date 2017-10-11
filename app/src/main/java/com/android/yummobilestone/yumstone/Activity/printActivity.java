package com.android.yummobilestone.yumstone.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by chenda on 2017/6/7.
 */

public class printActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent=getIntent();
        String value=intent.getStringExtra("retrunvalue");
        Intent  mIntent =  new  Intent();
        mIntent.putExtra("retrun",  value);
        printActivity.this.setResult(4,mIntent);
        finish();
    }
}
