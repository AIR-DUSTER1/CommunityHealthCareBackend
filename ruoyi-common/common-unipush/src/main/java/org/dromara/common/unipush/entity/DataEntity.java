package org.dromara.common.unipush.entity;

import lombok.Data;

@Data
public class DataEntity {
    /**
     * 推送的cid
     */
    private String cids;
    /**
     * 推送的标题
     */
    private String title;
    /**
     * 推送的内容
     */
    private String content;

    public DataEntity(String cids, String title, String content) {
        this.cids = cids;
        this.title = title;
        this.content = content;
    }
}
