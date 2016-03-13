package com.apollo.cbb.biz.net.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by Panda on 2016/3/13.
 */
public class CommentsFromCandate {

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
    public int date;

    @JSONField(name = "description")
    public String description;

    @JSONField(name = "commentId")
    public int commentId;

    @JSONField(name = "comment")
    public String comment;

    @JSONField(name = "commentUserId")
    public int commentUserId;

    @JSONField(name = "commentDate")
    public long commentDate;
}
