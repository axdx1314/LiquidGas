package com.suchengkeji.android.liquidgas.commontadapter.bean;

/**
 * @aboutContent:
 * @author： 安
 * @crateTime: 2018/1/2 16:04
 * @mailBox: an.****.life@gmail.com
 * @company: 东莞速成科技有限公司
 */

public class StoreData {
    private String name;//门店名字
    private String totalSales;//总销量
    //类型
    private String type5;
    private String type15;
    private String type50;


    public StoreData(String name, String totalSales, String type5, String type15, String type50) {
        this.name = name;
        this.totalSales = totalSales;
        this.type5 = type5;
        this.type15 = type15;
        this.type50 = type50;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(String totalSales) {
        this.totalSales = totalSales;
    }

    public String getType5() {
        return type5;
    }

    public void setType5(String type5) {
        this.type5 = type5;
    }

    public String getType15() {
        return type15;
    }

    public void setType15(String type15) {
        this.type15 = type15;
    }

    public String getType50() {
        return type50;
    }

    public void setType50(String type50) {
        this.type50 = type50;
    }
}
