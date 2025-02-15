package org.dromara.common.unipush.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UniPushResponse {

    /**
     * 推送结果
     */
    @JsonProperty("data")
    Object data;
    /**
     * 错误码
     */
    @JsonProperty("errCode")
    String errCode;

    /**
     * 错误信息
     */
    @JsonProperty("errMsg")
    String errMsg;

    public UniPushResponse() {
    }
    public UniPushResponse(Object data, String errCode, String errMsg) {
        this.data = data;
        this.errCode = errCode;
        this.errMsg = errMsg;
    }
    @Override
    public String toString() {
        return "UniPushResponse{" +
            "data=" + data +
            ", errCode=" + errCode +
            ", errMsg='" + errMsg + '\'' +
            '}';
    }
}
