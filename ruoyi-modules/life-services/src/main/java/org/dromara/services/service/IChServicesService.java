package org.dromara.services.service;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.dromara.services.domain.bo.ChServicesStatusBo;
import org.dromara.services.domain.vo.ChServicesVo;
import org.dromara.services.domain.bo.ChServicesBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 生活服务Service接口
 *
 * @author AIR-DUSTER1
 * @date 2024-12-13
 */
public interface IChServicesService {

    /**
     * 查询生活服务
     *
     * @param servicesId 主键
     * @return 生活服务
     */
    ChServicesVo queryById(Long servicesId);

    /**
     * 分页查询生活服务列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 生活服务分页列表
     */
    TableDataInfo<ChServicesVo> queryPageList(ChServicesBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的生活服务列表
     *
     * @param bo 查询条件
     * @return 生活服务列表
     */
    List<ChServicesVo> queryList(ChServicesBo bo);

    /**
     * 新增生活服务
     *
     * @param bo 生活服务
     * @return 是否新增成功
     */
    Boolean insertByBo(ChServicesBo bo);

    /**
     * 修改生活服务
     *
     * @param bo 生活服务
     * @return 是否修改成功
     */
    Boolean updateByBo(ChServicesBo bo);

    /**
     * 校验并批量删除生活服务信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 更新服务状态
     * @param bo 业务对象
     * @return
     */
    int updateStatus(ChServicesStatusBo bo);

    Boolean publishNews(@NotNull(message = "状态不能为空") String status, @NotBlank(message = "服务名称不能为空") String name);
}
