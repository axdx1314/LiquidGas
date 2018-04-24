package com.suchengkeji.android.liquidgas.view;

import com.suchengkeji.android.liquidgas.bean.SalesDataBean;

import java.util.Comparator;

/**
 * @aboutContent: RecyclerView - item 从高到低排序
 * @author： 安
 * @crateTime: 2018/1/3 09:10
 * @mailBox: an.****.life@gmail.com
 * @company: 东莞速成科技有限公司
 */
public class PriceHighToLowComparator implements Comparator<SalesDataBean.StoresBean> {

    @Override
    public int compare(SalesDataBean.StoresBean itemBean1, SalesDataBean.StoresBean itemBean2) {

        double price1 = 0;
        double price2 = 0;

        if (itemBean1.getTodayOrderNum() >= 0) {
            price1 = Double.parseDouble(String.valueOf(itemBean1.getTodayOrderNum()));
        }

        if (itemBean2.getTodayOrderNum() >= 0) {
            price2 = Double.parseDouble(String.valueOf(itemBean2.getTodayOrderNum()));
        }

        if (price1 > price2) {
            return -1;
        } else if (price1 < price2) {
            return 1;
        } else {
            return 0;
        }
    }

} 