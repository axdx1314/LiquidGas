package com.suchengkeji.android.liquidgas.api.base.cookies;

import android.content.Context;
import com.suchengkeji.android.liquidgas.api.base.cookies.cach.PersistentCookieStore;
import com.suchengkeji.android.liquidgas.utils.LogUtils;

import java.util.List;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * @aboutContent: cookie 管理
 * @author： 安
 * @crateTime: 2017/12/22 16:58
 * @mailBox: an.****.life@gmail.com
 * @company: 东莞速成科技有限公司
 */

public class CookieManger implements CookieJar {
    private final String TAG = "===->>>" + this.getClass();
    private static Context mContext;

    private static PersistentCookieStore cookieStore;

    public CookieManger(Context context) {
        mContext = context;
        if (cookieStore == null) {
            cookieStore = new PersistentCookieStore(mContext);
        }
    }


    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        LogUtils.d(TAG,"=========cookie"+ "要报存cookie了");
        if (cookies != null && cookies.size() > 0) {
            for (Cookie item : cookies) {
                LogUtils.d(TAG,"=========cookie"+ url + "== " + cookies);
                cookieStore.add(url, item);
            }
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies = cookieStore.get(url);
        return cookies;
    }

}