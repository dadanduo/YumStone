package com.android.yummobilestone.yumstone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.print.PrintManager;
import android.support.annotation.Nullable;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.yummobilestone.yumstone.Activity.IcActivity;
import com.android.yummobilestone.yumstone.Activity.PayMoneyActivity;
import com.android.yummobilestone.yumstone.Activity.printActivity;
import com.android.yummobilestone.yumstone.App.AppApplication;
import com.android.yummobilestone.yumstone.App.BaseMainActivity;
import com.android.yummobilestone.yumstone.InterfaceCallBack.CallBackQrCodeScan;
import com.android.yummobilestone.yumstone.InterfaceCallBack.QrCode;
import com.android.yummobilestone.yumstone.InterfaceCallBack.SharedPreferencesClass;
import com.android.yummobilestone.yumstone.Model.Pay;
import com.android.yummobilestone.yumstone.Model.Print;
import com.android.yummobilestone.yumstone.QrCodeScan.MipcaActivityCapture;
import com.android.yummobilestone.yumstone.utils.T;
import com.lidroid.xutils.util.LogUtils;
import com.lkl.cloudpos.aidl.AidlDeviceService;
import com.lkl.cloudpos.aidl.printer.AidlPrinter;
import com.lkl.cloudpos.aidl.printer.AidlPrinterListener;
import com.lkl.cloudpos.aidl.printer.PrintItemObj;

import java.util.ArrayList;

/**
 * Created by chenda on 2017/6/7.
 */


public class WebH5Activity extends BaseMainActivity {
    private WebView web;
    SharedPreferencesClass spc;
    //二维码回调
    private final static int SCANNIN_GREQUEST_CODE = 1;
    //支付回调
    public final static int PAYRETRNE=2;
    //打印回调
    public final static int PRINTVALUE=3;
    private CallBackQrCodeScan callqr;
    //二维码回调函数
    private QrCode qr;
    //拉卡拉打印
    private AidlPrinter printerDev = null;
    //box打印
    private PrintManager mPrintManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        spc=new SharedPreferencesClass();
        spc.SharedPreferencesClass(WebH5Activity.this);

        web=(WebView)findViewById(R.id.web);
        //设置支持js
        web.getSettings().setJavaScriptEnabled(true);
        //支持alert
        web.setWebChromeClient(new WebChromeClient());
        //加载网页
        web.loadUrl(AppApplication.URL);
        /////////////////////////////////////////////让js页面用手机格式打开///////////////////////////////////////
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        web.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////
        //添加js调用接口类，通过Android这个字段 调用这个类的方法
        web.addJavascriptInterface(new JsPrint(), "android");

        mPrintManager = (PrintManager) this.getSystemService("iboxpay_print");
        //二维码的回调
        qr=new QrCode();
        qr.QrCode(new CallBackQrCodeScan() {
            @Override
            public void QrCodeScanRetrun(String value) {
                // TODO Auto-generated method stub
                //Android调用js自定义函数把二维码返回参数传递给js端
                web.loadUrl("javascript:RetrunQrCodeValue('"+value+"')");
                //android 调用js方法
//					web.loadUrl("javascript:alert(123)");
                //Android自己调自己的函数
//					web.loadUrl("javascript:window.Android.getPayMethods()");
            }
        });
        //----------------------------------------------------------------////

    }

    public class JsPrint
    {
        //供js调用的打印方法
        @JavascriptInterface
        public void printStart(String printJson)
        {
            Print print = AppApplication.gson.fromJson(printJson, Print.class);
            if(spc.getSharedValues("JUDGMENTMODEL"," ").equals(""))
            {
                printText(print);
           }
            else
            {
                T.showShort(WebH5Activity.this,"盒子打印");
            }

        }
        //供js调用的二维码方法  String printContent
        @JavascriptInterface
        public  void   getQrCodeValue(	)
        {
            Scanning();
        }
        //供js调用的支付方法
        @JavascriptInterface
        public void  getPayMethods(String payJson)
        {
            Pay pay=AppApplication.gson.fromJson(payJson, Pay.class);
            //拉卡拉支付
            if(spc.getSharedValues("JUDGMENTMODEL"," ").equals(""))
            {
                PayMoney(pay);
            }
            //盒子支付
            else
            {
                T.showShort(WebH5Activity.this,"盒子支付");
            }
        }
        //供js调用的非接触式ic卡
        @JavascriptInterface
        public void  getContactlessIcCard()
        {
            startActivity(new Intent(WebH5Activity.this,IcActivity.class));
        }
    }
    //调用二维码的方法
    public void Scanning()
    {
        Intent intent = new Intent();
        intent.setClass(WebH5Activity.this, MipcaActivityCapture.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
    }

    /*二维码扫描，支付刷卡返回的数据
     *
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SCANNIN_GREQUEST_CODE:
                if(resultCode == RESULT_OK){
                    Bundle bundle = data.getExtras();
                    //二维码扫描返回结果值调用回调方法得到值
                    qr.sendString(bundle.getString("result"));
                }
                //支付状况返回
            case PAYRETRNE:
                if(resultCode==1){//交易取消
                    LogUtils.d(data.getStringExtra("transactioncancelled"));
                    web.loadUrl("javascript:PayRetrunValue('"+data.getStringExtra("transactioncancelled")+"')");
                }
                if(resultCode ==2){
                    LogUtils.d(data.getStringExtra("transactionsuccess"));
                    web.loadUrl("javascript:PayRetrunValue('"+data.getStringExtra("transactionsuccess")+"')");
                }
                if(resultCode == 3){//交易失败
                    LogUtils.d(data.getStringExtra("transactionfaild"));
                    web.loadUrl("javascript:PayRetrunValue('"+data.getStringExtra("transactionfaild")+"')");
                }
                //打印的返回数据
            case  PRINTVALUE:
                if(resultCode==4)
                {
                    web.loadUrl("javascript:RetrunPrint('"+data.getStringExtra("retrun")+"')");
                }

                break;
        }
    }
    //调用支付的方法
    public void PayMoney(Pay pay)
    {
        Intent intent=new Intent(WebH5Activity.this,PayMoneyActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putSerializable("payValue",pay);
        intent.putExtras(mBundle);
        startActivityForResult(intent, PAYRETRNE);
    }
    //--------------------------------拉卡拉打印-------------------//
    /**
     * 打印文本的方法
     * @param v
     * @createtor：daduke
     * @date:2017/5/22下午2:19:28
     */
    public void printText(final Print print){
        try {
            printerDev.printText(new ArrayList<PrintItemObj>(){
                {
                    add(new PrintItemObj(print.getName()));
                    add(new PrintItemObj(print.getSex()));
                }
            }, new AidlPrinterListener.Stub() {
                @Override
                public void onPrintFinish() throws RemoteException {
                    //打印成功的回调
                    sendValue("打印成功");
                }
                @Override
                public void onError(int arg0) throws RemoteException {
                    //打印出错的回调
                    sendValue(arg0+"");
                }
            });
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    //______________________________________________________//
    //打印跳转方法
    public void sendValue(String value)
    {
        Intent intent=new Intent(WebH5Activity.this,printActivity.class);
        intent.putExtra("retrunvalue", value);
        startActivityForResult(intent, PRINTVALUE);
    }


    //拉卡拉打印服务回调
    @Override
    public void onDeviceConnected(AidlDeviceService serviceManager) {
        try {
            printerDev = AidlPrinter.Stub.asInterface(serviceManager.getPrinter());
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
