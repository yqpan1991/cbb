package com.apollo.cbb.biz.net.model;

import com.alibaba.fastjson.annotation.JSONField;


/**
 * Created by Panda on 2016/3/14.
 */
public class CommentInfo {


    /**
     * storeId : 1
     * userId : 1
     * userStoreId : 2
     * storeName : 小六汤包
     * type : 1
     * shortString : 3382729
     * latitude : 32.6
     * longtitude : 56.78
     * date : 3423432343
     * description : sdfsdkjflskjdf
     * commentId : 1
     * comment : 妹子,约不?
     * commentUserId : 1
     * commentDate : 345345243234235
     */

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
    public long date;
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
