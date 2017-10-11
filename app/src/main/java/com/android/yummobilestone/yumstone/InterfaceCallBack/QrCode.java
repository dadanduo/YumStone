package com.android.yummobilestone.yumstone.InterfaceCallBack;

//二维码类
public class QrCode {
	private CallBackQrCodeScan callqr;
	public  void QrCode( CallBackQrCodeScan callqr) {
        this.callqr = callqr;
    }
	public void sendString(String name)
	{
		callqr.QrCodeScanRetrun(name);
	}
}
