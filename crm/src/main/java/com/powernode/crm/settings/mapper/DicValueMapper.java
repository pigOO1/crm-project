package com.powernode.crm.settings.mapper;

import com.powernode.crm.settings.domain.DicValue;

import java.util.List;

public interface DicValueMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_dic_value
     *
     * @mbg.generated Tue Sep 12 21:24:29 CST 2023
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_dic_value
     *
     * @mbg.generated Tue Sep 12 21:24:29 CST 2023
     */
    int insert(DicValue row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_dic_value
     *
     * @mbg.generated Tue Sep 12 21:24:29 CST 2023
     */
    int insertSelective(DicValue row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_dic_value
     *
     * @mbg.generated Tue Sep 12 21:24:29 CST 2023
     */
    DicValue selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_dic_value
     *
     * @mbg.generated Tue Sep 12 21:24:29 CST 2023
     */
    int updateByPrimaryKeySelective(DicValue row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_dic_value
     *
     * @mbg.generated Tue Sep 12 21:24:29 CST 2023
     */
    int updateByPrimaryKey(DicValue row);

    /**
     * 根据字典类型查询字典数据列表
     * @param typeCode
     * @return
     */
    List<DicValue> selectDicValueByTypeCode(String typeCode);


}