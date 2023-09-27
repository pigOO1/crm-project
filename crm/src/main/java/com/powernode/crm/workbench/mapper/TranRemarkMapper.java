package com.powernode.crm.workbench.mapper;

import com.powernode.crm.workbench.domain.TranRemark;

import java.util.List;

public interface TranRemarkMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran_remark
     *
     * @mbg.generated Sat Sep 16 21:37:04 CST 2023
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran_remark
     *
     * @mbg.generated Sat Sep 16 21:37:04 CST 2023
     */
    int insert(TranRemark row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran_remark
     *
     * @mbg.generated Sat Sep 16 21:37:04 CST 2023
     */
    int insertSelective(TranRemark row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran_remark
     *
     * @mbg.generated Sat Sep 16 21:37:04 CST 2023
     */
    TranRemark selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran_remark
     *
     * @mbg.generated Sat Sep 16 21:37:04 CST 2023
     */
    int updateByPrimaryKeySelective(TranRemark row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran_remark
     *
     * @mbg.generated Sat Sep 16 21:37:04 CST 2023
     */
    int updateByPrimaryKey(TranRemark row);

    /**
     * 添加交易备注信息
     * @param list
     * @return
     */
    int insertTranRemarkByList(List<TranRemark> list);

    /**
     * 根据tranId查询交易备注
     * @param tranId
     * @return
     */
    List<TranRemark> selectTranRemarkForDetailByTranId(String tranId);
}