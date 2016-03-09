package com.apollo.cbb.biz.net.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by Panda on 2016/3/6.
 */
public class RecommendInfo {

    public static final int RECOMMEND_TYPE_DAYLY = 1;
    public static final int RECOMMEND_TYPE_NORMAL = 2;
    public static final int RECOMMEND_TYPE_DATE = 3;

    @JSONField(name = "storeId")
    public int storeId;

    @JSONField(name = "userId")
    public int userId;

    @JSONField(name = "userStoreId")
    public int userStoreId;

    @JSONField(name = "storeName")
    public String storeName;

    @JSONField(name = "type")
    public int type;

    @JSONField(name = "shortString")
    public String shortString;

    @JSONField(name = "latitude")
    public double latitude;

    @JSONField(name = "longtitude")
    public double longtitude;

    @JSONField(name = "date")
    public double date;

    @JSONField(name = "description")
    public String description;
}
