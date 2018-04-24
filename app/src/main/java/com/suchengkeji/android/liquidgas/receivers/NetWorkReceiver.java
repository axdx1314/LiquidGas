package com.suchengkeji.android.liquidgas.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;

import com.suchengkeji.android.liquidgas.utils.NetworkUtils;
import com.suchengkeji.android.liquidgas.utils.PromptUtils;

/**
 * 网络状态改变监听
 */
public class NetWorkReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            //检查网络状态的类型
            int networkState = NetworkUtils.getNetworkState(context);
            Log.d("-----ss---", action + "==");

            //创建Intent
            Intent intents = new Intent();
            // 接口回传网络状态的类型
            onNetChange(context, networkState, intents);
        }
    }


    /**
     * 网络状态与网络类型
     *
     * @param context
     * @param networkState
     * @param intents
     */
    private void onNetChange(Context context, int networkState, Intent intents) {
        switch (networkState) {
            case 0:
                PromptUtils.showToast(context, "网络断开");
                break;
            case 1:
                //PromptUtils.showToast(context, "中国移动CMNET网络");
                intents.setAction("com.suchengkeji.android.liquidgas.receivers.NETCHANGE");
                context.sendBroadcast(intents);
                break;
            case 2:
                //PromptUtils.showToast(context, "中国移动CMWAP网络");
                intents.setAction("com.suchengkeji.android.liquidgas.receivers.NETCHANGE");
                context.sendBroadcast(intents);
                break;
            case 3:
                //PromptUtils.showToast(context, "中国联通UNIWAP网络");
                intents.setAction("com.suchengkeji.android.liquidgas.receivers.NETCHANGE");
                context.sendBroadcast(intents);
                break;
            case 4:
                //PromptUtils.showToast(context, "中国联通3GWAP网络");
                intents.setAction("com.suchengkeji.android.liquidgas.receivers.NETCHANGE");
                context.sendBroadcast(intents);
                break;
            case 5:
                //PromptUtils.showToast(context, "中国联通3HNET网络");
                intents.setAction("com.suchengkeji.android.liquidgas.receivers.NETCHANGE");
                context.sendBroadcast(intents);
                break;
            case 6:
                //PromptUtils.showToast(context, "中国联通UNINET网络");
                intents.setAction("com.suchengkeji.android.liquidgas.receivers.NETCHANGE");
                context.sendBroadcast(intents);
                break;
            case 7:
                //PromptUtils.showToast(context, "中国电信CTWAP网络");
                intents.setAction("com.suchengkeji.android.liquidgas.receivers.NETCHANGE");
                context.sendBroadcast(intents);
                break;
            case 8:
                //PromptUtils.showToast(context, "中国电信CTNET网络");
                intents.setAction("com.suchengkeji.android.liquidgas.receivers.NETCHANGE");
                context.sendBroadcast(intents);
                break;
            case 10:
                //PromptUtils.showToast(context, "WIFI网络");
                intents.setAction("com.suchengkeji.android.liquidgas.receivers.NETCHANGE");
                context.sendBroadcast(intents);
                break;
        }
    }


}
