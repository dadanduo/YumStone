package com.android.yummobilestone.yumstone.utils;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

public class InteractionJsUtil {

	
	private Context mContext;
	private WebView mWebView;
	public InteractionJsUtil(Context context, WebView webView) {
	    mContext = context;
	    mWebView = webView;
	}
	@JavascriptInterface
	//andorid4.2（包括android4.2）以上，如果不写该注解，js无法调用android方法
	public void showToast(String json){
	    T.showShort(mContext, json);
	}
}
