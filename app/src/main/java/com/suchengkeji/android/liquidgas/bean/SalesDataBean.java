package com.suchengkeji.android.liquidgas.bean;

import java.util.Comparator;
import java.util.List;

public class SalesDataBean {
    /**
     * company : {"storeName":null,"saleDate":"2018-01-11","todayOrderNum":6,"todayOrderBottleNum":45,"todayTotalAmount":5219,"yesterdayGrowthOrderNum":7,"yesterdayGrowthOrderBottleNum":24,"yesterdayGrowthTotalAmount":2803,"saleDetailInfos":[{"materialTypeName":"15KG","orderNum":2,"orderBottleNum":13,"totalAmount":1419},{"materialTypeName":"50KG","orderNum":2,"orderBottleNum":17,"totalAmount":3230},{"materialTypeName":"5KG","orderNum":2,"orderBottleNum":15,"totalAmount":570}]}
     * stores : [{"storeName":"充装站","saleDate":"2018-01-11","todayOrderNum":3,"todayOrderBottleNum":33,"todayTotalAmount":3748,"yesterdayGrowthOrderNum":0,"yesterdayGrowthOrderBottleNum":0,"yesterdayGrowthTotalAmount":0,"saleDetailInfos":[{"materialTypeName":"15KG","orderNum":1,"orderBottleNum":10,"totalAmount":1050},{"materialTypeName":"50KG","orderNum":1,"orderBottleNum":12,"totalAmount":2280},{"materialTypeName":"5KG","orderNum":1,"orderBottleNum":11,"totalAmount":418}]},{"storeName":"博美站","saleDate":"2018-01-11","todayOrderNum":3,"todayOrderBottleNum":12,"todayTotalAmount":1471,"yesterdayGrowthOrderNum":0,"yesterdayGrowthOrderBottleNum":0,"yesterdayGrowthTotalAmount":0,"saleDetailInfos":[{"materialTypeName":"15KG","orderNum":1,"orderBottleNum":3,"totalAmount":369},{"materialTypeName":"50KG","orderNum":1,"orderBottleNum":5,"totalAmount":950},{"materialTypeName":"5KG","orderNum":1,"orderBottleNum":4,"totalAmount":152}]}]
     */

    private CompanyBean company;
    private List<StoresBean> stores;

    public CompanyBean getCompany() {
        return company;
    }

    public void setCompany(CompanyBean company) {
        this.company = company;
    }

    public List<StoresBean> getStores() {
        return stores;
    }

    public void setStores(List<StoresBean> stores) {
        this.stores = stores;
    }

    public static class CompanyBean {
        /**
         * storeName : null
         * saleDate : 2018-01-11
         * todayOrderNum : 6
         * todayOrderBottleNum : 45
         * todayTotalAmount : 5219
         * yesterdayGrowthOrderNum : 7
         * yesterdayGrowthOrderBottleNum : 24
         * yesterdayGrowthTotalAmount : 2803
         * saleDetailInfos : [{"materialTypeName":"15KG","orderNum":2,"orderBottleNum":13,"totalAmount":1419},{"materialTypeName":"50KG","orderNum":2,"orderBottleNum":17,"totalAmount":3230},{"materialTypeName":"5KG","orderNum":2,"orderBottleNum":15,"totalAmount":570}]
         */

        private String storeName;
        private String saleDate;
        private int todayOrderNum;
        private int todayOrderBottleNum;
        private int todayTotalAmount;
        private int yesterdayGrowthOrderNum;
        private int yesterdayGrowthOrderBottleNum;
        private int yesterdayGrowthTotalAmount;
        private List<SaleDetailInfosBean> saleDetailInfos;

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public String getSaleDate() {
            return saleDate;
        }

        public void setSaleDate(String saleDate) {
            this.saleDate = saleDate;
        }

        public int getTodayOrderNum() {
            return todayOrderNum;
        }

        public void setTodayOrderNum(int todayOrderNum) {
            this.todayOrderNum = todayOrderNum;
        }

        public int getTodayOrderBottleNum() {
            return todayOrderBottleNum;
        }

        public void setTodayOrderBottleNum(int todayOrderBottleNum) {
            this.todayOrderBottleNum = todayOrderBottleNum;
        }

        public int getTodayTotalAmount() {
            return todayTotalAmount;
        }

        public void setTodayTotalAmount(int todayTotalAmount) {
            this.todayTotalAmount = todayTotalAmount;
        }

        public int getYesterdayGrowthOrderNum() {
            return yesterdayGrowthOrderNum;
        }

        public void setYesterdayGrowthOrderNum(int yesterdayGrowthOrderNum) {
            this.yesterdayGrowthOrderNum = yesterdayGrowthOrderNum;
        }

        public int getYesterdayGrowthOrderBottleNum() {
            return yesterdayGrowthOrderBottleNum;
        }

        public void setYesterdayGrowthOrderBottleNum(int yesterdayGrowthOrderBottleNum) {
            this.yesterdayGrowthOrderBottleNum = yesterdayGrowthOrderBottleNum;
        }

        public int getYesterdayGrowthTotalAmount() {
            return yesterdayGrowthTotalAmount;
        }

        public void setYesterdayGrowthTotalAmount(int yesterdayGrowthTotalAmount) {
            this.yesterdayGrowthTotalAmount = yesterdayGrowthTotalAmount;
        }

        public List<SaleDetailInfosBean> getSaleDetailInfos() {
            return saleDetailInfos;
        }

        public void setSaleDetailInfos(List<SaleDetailInfosBean> saleDetailInfos) {
            this.saleDetailInfos = saleDetailInfos;
        }

        public static class SaleDetailInfosBean {
            /**
             * materialTypeName : 15KG
             * orderNum : 2
             * orderBottleNum : 13
             * totalAmount : 1419
             */

            private String materialTypeName;
            private int orderNum;
            private int orderBottleNum;
            private int totalAmount;

            public String getMaterialTypeName() {
                return materialTypeName;
            }

            public void setMaterialTypeName(String materialTypeName) {
                this.materialTypeName = materialTypeName;
            }

            public int getOrderNum() {
                return orderNum;
            }

            public void setOrderNum(int orderNum) {
                this.orderNum = orderNum;
            }

            public int getOrderBottleNum() {
                return orderBottleNum;
            }

            public void setOrderBottleNum(int orderBottleNum) {
                this.orderBottleNum = orderBottleNum;
            }

            public int getTotalAmount() {
                return totalAmount;
            }

            public void setTotalAmount(int totalAmount) {
                this.totalAmount = totalAmount;
            }
        }
    }

    public static class StoresBean {
        /**
         * storeName : 充装站
         * saleDate : 2018-01-11
         * todayOrderNum : 3
         * todayOrderBottleNum : 33
         * todayTotalAmount : 3748
         * yesterdayGrowthOrderNum : 0
         * yesterdayGrowthOrderBottleNum : 0
         * yesterdayGrowthTotalAmount : 0
         * saleDetailInfos : [{"materialTypeName":"15KG","orderNum":1,"orderBottleNum":10,"totalAmount":1050},{"materialTypeName":"50KG","orderNum":1,"orderBottleNum":12,"totalAmount":2280},{"materialTypeName":"5KG","orderNum":1,"orderBottleNum":11,"totalAmount":418}]
         */

        private String storeName;
        private String storeCode;
        private String saleDate;
        private int todayOrderNum;
        private int todayOrderBottleNum;
        private int todayTotalAmount;
        private int yesterdayGrowthOrderNum;
        private int yesterdayGrowthOrderBottleNum;
        private int yesterdayGrowthTotalAmount;
        private List<SaleDetailInfosBeanX> saleDetailInfos;


        public String getStoreCode() {
            return storeCode;
        }

        public void setStoreCode(String storeCode) {
            this.storeCode = storeCode;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public String getSaleDate() {
            return saleDate;
        }

        public void setSaleDate(String saleDate) {
            this.saleDate = saleDate;
        }

        public int getTodayOrderNum() {
            return todayOrderNum;
        }

        public void setTodayOrderNum(int todayOrderNum) {
            this.todayOrderNum = todayOrderNum;
        }

        public int getTodayOrderBottleNum() {
            return todayOrderBottleNum;
        }

        public void setTodayOrderBottleNum(int todayOrderBottleNum) {
            this.todayOrderBottleNum = todayOrderBottleNum;
        }

        public int getTodayTotalAmount() {
            return todayTotalAmount;
        }

        public void setTodayTotalAmount(int todayTotalAmount) {
            this.todayTotalAmount = todayTotalAmount;
        }

        public int getYesterdayGrowthOrderNum() {
            return yesterdayGrowthOrderNum;
        }

        public void setYesterdayGrowthOrderNum(int yesterdayGrowthOrderNum) {
            this.yesterdayGrowthOrderNum = yesterdayGrowthOrderNum;
        }

        public int getYesterdayGrowthOrderBottleNum() {
            return yesterdayGrowthOrderBottleNum;
        }

        public void setYesterdayGrowthOrderBottleNum(int yesterdayGrowthOrderBottleNum) {
            this.yesterdayGrowthOrderBottleNum = yesterdayGrowthOrderBottleNum;
        }

        public int getYesterdayGrowthTotalAmount() {
            return yesterdayGrowthTotalAmount;
        }

        public void setYesterdayGrowthTotalAmount(int yesterdayGrowthTotalAmount) {
            this.yesterdayGrowthTotalAmount = yesterdayGrowthTotalAmount;
        }

        public List<SaleDetailInfosBeanX> getSaleDetailInfos() {
            return saleDetailInfos;
        }

        public void setSaleDetailInfos(List<SaleDetailInfosBeanX> saleDetailInfos) {
            this.saleDetailInfos = saleDetailInfos;
        }


        public static class SaleDetailInfosBeanX {
            /**
             * materialTypeName : 15KG
             * orderNum : 1
             * orderBottleNum : 10
             * totalAmount : 1050
             */

            private String materialTypeName;
            private int orderNum;
            private int orderBottleNum;
            private int totalAmount;

            public String getMaterialTypeName() {
                return materialTypeName;
            }

            public void setMaterialTypeName(String materialTypeName) {
                this.materialTypeName = materialTypeName;
            }

            public int getOrderNum() {
                return orderNum;
            }

            public void setOrderNum(int orderNum) {
                this.orderNum = orderNum;
            }

            public int getOrderBottleNum() {
                return orderBottleNum;
            }

            public void setOrderBottleNum(int orderBottleNum) {
                this.orderBottleNum = orderBottleNum;
            }

            public int getTotalAmount() {
                return totalAmount;
            }

            public void setTotalAmount(int totalAmount) {
                this.totalAmount = totalAmount;
            }
        }
    }
}