package com.android.yummobilestone.yumstone.Activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.yummobilestone.yumstone.Model.Pay;

/**
 * Created by chenda on 2017/6/7.
 * 支付页面
 */


public class PayMoneyActivity extends Activity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Pay pay = (Pay)getIntent().getSerializableExtra("payValue");
        payMoney(pay);
    }


    public void payMoney(Pay pay)
    {
        Intent intent = new Intent();
        ComponentName componet = new ComponentName("com.lkl.cloudpos.payment", "com.lkl.cloudpos.payment.activity.MainMenuActivity");
        Bundle b = new Bundle();
        b.putString("proc_tp", pay.getProc_tp());
        b.putString("pay_tp", pay.getPay_tp());
        b.putString("amt", pay.getAmt());
        b.putString("msg_tp", pay.getMsg_tp());
        b.putString("proc_cd", pay.getProc_cd());
        b.putString("order_no", pay.getOrder_no());
        b.putString("appid", pay.getAppid());
        b.putString("time_stamp", pay.getTime_stamp());
        b.putString("order_info", pay.getOrder_info());
        intent.setComponent(componet);
        intent.putExtras(b);
        startActivityForResult(intent, 1);

    }

    /*支付回调数据*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_CANCELED){//交易取消
            Intent  mIntent =  new  Intent();
            mIntent.putExtra("transactioncancelled",  data.getExtras().getString("reason"));
            PayMoneyActivity.this.setResult(1,mIntent);
            finish();
        }
        if(resultCode == Activity.RESULT_OK){
            Intent  mIntent =  new  Intent();
            mIntent.putExtra("transactionsuccess",  "返回交易成功");
            PayMoneyActivity.this.setResult(2,mIntent);
            finish();
        }
        if(resultCode == -2){//交易失败
            Intent  mIntent =  new  Intent();
            mIntent.putExtra("transactionfaild",  data.getExtras().getString("reason"));
            PayMoneyActivity.this.setResult(3,mIntent);
            finish();
        }

    }
}
