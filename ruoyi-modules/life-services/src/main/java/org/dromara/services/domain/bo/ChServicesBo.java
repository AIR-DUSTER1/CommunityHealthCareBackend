package org.dromara.services.domain.bo;

import org.dromara.services.domain.ChServices;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * 生活服务业务对象 ch_services
 *
 * @author AIR-DUSTER1
 * @date 2024-12-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = ChServices.class, reverseConvertGenerate = false)
public class ChServicesBo extends BaseEntity {

    /**
     * 服务ID
     */
    @NotNull(message = "服务ID不能为空", groups = { EditGroup.class })
    private Long servicesId;

    /**
     * 服务名称
     */
    @NotBlank(message = "服务名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String name;

    /**
     * 图标路径
     */
    @NotBlank(message = "图标路径不能为空", groups = { AddGroup.class, EditGroup.class })
    private String icon;

    /**
     * 服务描述
     */
    private String description;

    /**
     * 分类
     */
    @NotNull(message = "分类不能为空", groups = { AddGroup.class, EditGroup.class })
    private String classification;

    /**
     * 类型
     */
    @NotNull(message = "类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String type;

    /**
     * 价格
     */
    @NotNull(message = "价格不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal price;

    /**
     * 状态
     */
    @NotNull(message = "状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private String status;


}
