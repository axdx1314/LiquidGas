package com.suchengkeji.android.liquidgas.api.base.subscribers;

/**
 * @aboutContent:
 * @author： An
 * @crateTime: 2018/1/25 11:01
 * @mailBox: an.****.life@gmail.com
 * @company: 东莞速成科技有限公司
 */
public interface SubscriberOnNextListener<T> {
    void onNext(T t);
}
