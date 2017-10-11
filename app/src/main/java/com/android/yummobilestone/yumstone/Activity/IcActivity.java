package com.android.yummobilestone.yumstone.Activity;

import android.os.Bundle;
import android.os.RemoteException;
import android.view.View;

import com.android.yummobilestone.yumstone.App.BaseMainActivity;
import com.android.yummobilestone.yumstone.R;
import com.lidroid.xutils.util.LogUtils;
import com.lkl.cloudpos.aidl.AidlDeviceService;
import com.lkl.cloudpos.aidl.rfcard.AidlRFCard;
import com.lkl.cloudpos.util.HexUtil;

/**
 * Created by chenda on 2017/6/7.
 */

public class IcActivity extends BaseMainActivity {
    public AidlRFCard rfcard = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rfcard);
    }

    // 打开设备
    public void open(View v) {
        try {
            boolean flag = rfcard.open();
            if (flag) {
                LogUtils.d("打开非接卡设备成功");
            } else {
                LogUtils.d("打开非接卡设备失败");
            }
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // 关闭设备
    public void close(View v) {
        try {
            boolean flag = rfcard.close();
            if (flag) {
                LogUtils.d("关闭非接卡设备成功");
            } else {
                LogUtils.d("关闭非接卡设备失败");
            }
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // 检测是否为在位
    public void isExists(View v) {
        try {
            boolean flag = rfcard.isExist();
            if (flag) {
                LogUtils.d("检测到卡片");
            } else {
                LogUtils.d("未检测到卡片");
            }
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // 上电复位
    public void reset(View v) {
        try {
            byte[] data = rfcard.reset(0x00);
            if (null != data) {
                LogUtils.d("非接卡复位结果" + HexUtil.bcd2str(data));
            } else {
                LogUtils.d("非接卡复位失败");
            }
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // 断开
    public void halt(View v) {
        try {
            int ret = rfcard.halt();
            if (ret == 0x00) {
                LogUtils.d("非接卡下电操作成功");
            } else {
                LogUtils.d("非接卡下电操作失败");
            }
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // 读取卡类型
    public void readCardType(View v) {
        try {
            int type = rfcard.getCardType();
            LogUtils.d("卡类型读取成功" + type);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            LogUtils.d("卡类型读取失败");
        }

    }

    // apdu通讯
    public void apduComm(View v) {
        try {
            byte[] apdu = HexUtil
                    .hexStringToByte("00A404000E315041592E5359532E4444463031");
            byte[] data = rfcard.apduComm(apdu);
            if (data != null) {
                LogUtils.d("APDU通讯成功" + HexUtil.bcd2str(data));
            } else {
                LogUtils.d("APDU通讯失败");
            }
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // 认证
    public void auth(View v) {
        try {
            byte[] resetData = rfcard.reset(0x00);
            @SuppressWarnings("unused")
            int cardType = rfcard.getCardType();
            int retCode = rfcard.auth((byte) 0x00, (byte) 0x01, new byte[] {
                    0x01, 0x02, 0x03, 0x04, 0x05, 0x06 }, resetData);
            LogUtils.d("认证返回结果为" + retCode);
            if (0x00 == retCode) {
                LogUtils.d("认证成功");
            } else {
                LogUtils.d("认证失败");
            }
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // 加值
    public void addValue(View v) {
        try {
            int retCode = rfcard.addValue((byte) 0x02, new byte[] { 0x01, 0x02,
                    0x03, 0x04, 0x05, 0x06, 0x07, 0x08 });
            LogUtils.d("加值返回操作结果为" + retCode);
            if (retCode == 0x00) {
                LogUtils.d("加值成功");
            } else {
                LogUtils.d("加值失败");
            }
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // 减值
    public void reduceValue(View v) {
        try {
            int retCode = rfcard.reduceValue((byte) 0x02, new byte[] { 0x01,
                    0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08 });
            LogUtils.d("减值返回操作结果为" + retCode);
            if (retCode == 0x00) {
                LogUtils.d("减值成功");
            } else {
                LogUtils.d("减值失败");
            }
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // 读取块数据
    public void readBlockData(View v) {
        try {
            byte[] data = new byte[2048];
            int length = rfcard.readBlock((byte) 0x01, data);
            LogUtils.d("读取块数据返回长度为" + length);
            if (length > 0x00) {
                LogUtils.d("读取块数据成功" + HexUtil.bcd2str(data));
            } else {
                LogUtils.d("读取块数据失败");
            }

        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // 写块数据
    public void writeBlock(View v) {
        try {
            int retCode = rfcard.writeBlock((byte) 0x01, new byte[] { 0x01, 0x02,
                    0x03, 0x04, 0x05, 0x06, 0x07, 0x08 });
            LogUtils.d("写块数据返回结果" + retCode);
            if (retCode == 0x00) {
                LogUtils.d("写入块数据成功");
            } else {
                LogUtils.d("写入快数据失败");
            }
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    @Override
    public void onDeviceConnected(AidlDeviceService serviceManager) {
        try {
            rfcard = AidlRFCard.Stub
                    .asInterface(serviceManager.getRFIDReader());
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
