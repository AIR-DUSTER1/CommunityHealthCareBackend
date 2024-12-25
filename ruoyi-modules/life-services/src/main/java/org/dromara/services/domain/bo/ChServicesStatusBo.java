package org.dromara.services.domain.bo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.dromara.common.core.validate.EditGroup;

import java.io.Serializable;
@Data
public class ChServicesStatusBo implements Serializable {
    /**
     * 服务ID
     */
    @NotNull(message = "服务ID不能为空")
    private Long servicesId;
    /**
     * 服务名称
     */
    @NotBlank(message = "服务名称不能为空")
    private String name;
    /**
     * 状态
     */
    @NotNull(message = "状态不能为空")
    private String status;
}
