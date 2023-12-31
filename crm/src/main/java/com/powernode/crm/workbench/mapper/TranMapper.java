package com.powernode.crm.workbench.mapper;

import com.powernode.crm.workbench.domain.FunnelVo;
import com.powernode.crm.workbench.domain.Tran;

import java.util.List;

public interface TranMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran
     *
     * @mbg.generated Sat Sep 16 21:14:42 CST 2023
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran
     *
     * @mbg.generated Sat Sep 16 21:14:42 CST 2023
     */
    int insert(Tran row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran
     *
     * @mbg.generated Sat Sep 16 21:14:42 CST 2023
     */
    int insertSelective(Tran row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran
     *
     * @mbg.generated Sat Sep 16 21:14:42 CST 2023
     */
    Tran selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran
     *
     * @mbg.generated Sat Sep 16 21:14:42 CST 2023
     */
    int updateByPrimaryKeySelective(Tran row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran
     *
     * @mbg.generated Sat Sep 16 21:14:42 CST 2023
     */
    int updateByPrimaryKey(Tran row);

    /**
     * 添加交易
     *
     * @param tran
     * @return
     */
    int insertTran(Tran tran);

    /**
     * 根据id查询交易详情
     *
     * @param id
     * @return
     */
    Tran selectTranForDetailById(String id);

    /**
     * 根据阶段分组查询交易的数据量
     * @return
     */
    List<FunnelVo> selectCountOfTranGroupByStage();

}