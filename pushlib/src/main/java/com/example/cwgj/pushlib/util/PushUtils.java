package com.example.cwgj.pushlib.util;

import android.content.Context;
import android.util.Log;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.example.cwgj.pushlib.receiver.MyMessageIntentService;

import rxbus.ecaray.com.rxbuslib.rxbus.RxBus;

/**
 * +----------------------------------------------------------------------
 * |  说明     ：
 * +----------------------------------------------------------------------
 * | 创建者   :  ldw
 * +----------------------------------------------------------------------
 * | 时　　间 ：2018/3/23 14:51
 * +----------------------------------------------------------------------
 * | 版权所有: 北京市车位管家科技有限公司
 * +----------------------------------------------------------------------
 **/
public class PushUtils {

    private static final String TAG = "PushUtils";
    //接收通知
    public static final String TAG_NOTIFICATION_REC = "notification_rec";
    //通知打开
    public static final String TAG_NOTIFICATION_OPENED = "notification_opened";
    //通知移除
    public static final String TAG_NOTIFICATION_REMOVED = "notification_removed";
    //rxbus 事件bus
    public static RxBus sRxBus;

    /**
     * 初始化云推送通道
     * @param applicationContext
     */
    public static void initCloudChannel(RxBus rxBus, Context applicationContext, String appKey, String appSecret) {
        sRxBus = rxBus;
        PushServiceFactory.init(applicationContext);
        CloudPushService pushService = PushServiceFactory.getCloudPushService();
        //设置接受通知通道为： AliyunMessageIntentService(官方建议)
        pushService.setPushIntentService(MyMessageIntentService.class);
        //注册channel
        pushService.register(applicationContext,appKey, appSecret, new CommonCallback() {
            @Override
            public void onSuccess(String response) {
                Log.d(TAG, "init cloudchannel success");
            }
            @Override
            public void onFailed(String errorCode, String errorMessage) {
                Log.d(TAG, "init cloudchannel failed -- errorcode:" + errorCode + " -- errorMessage:" + errorMessage);
            }
        });

        // 注册方法会自动判断是否支持小米系统推送，如不支持会跳过注册。
//        MiPushRegister.register(applicationContext, "小米AppID", "小米AppKey");
        // 注册方法会自动判断是否支持华为系统推送，如不支持会跳过注册。
//        HuaWeiRegister.register(applicationContext);
    }
}