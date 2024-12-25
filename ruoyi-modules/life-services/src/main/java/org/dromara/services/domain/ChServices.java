package org.dromara.services.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.math.BigDecimal;

/**
 * 生活服务对象 ch_services
 *
 * @author AIR-DUSTER1
 * @date 2024-12-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ch_services")
public class ChServices extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 服务ID
     */
    @TableId(value = "services_id")
    private Long servicesId;

    /**
     * 服务名称
     */
    private String name;

    /**
     * 图标路径
     */
    private String icon;

    /**
     * 服务描述
     */
    private String description;

    /**
     * 分类
     */
    private String classification;

    /**
     * 类型
     */
    private String type;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 状态
     */
    private String status;

    /**
     * 备注
     */
    private String remark;


}
