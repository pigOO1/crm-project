package com.powernode.crm.workbench.mapper;

import com.powernode.crm.workbench.domain.Customer;

import java.util.List;

public interface CustomerMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_customer
     *
     * @mbg.generated Fri Sep 15 18:26:16 CST 2023
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_customer
     *
     * @mbg.generated Fri Sep 15 18:26:16 CST 2023
     */
    int insert(Customer row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_customer
     *
     * @mbg.generated Fri Sep 15 18:26:16 CST 2023
     */
    int insertSelective(Customer row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_customer
     *
     * @mbg.generated Fri Sep 15 18:26:16 CST 2023
     */
    Customer selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_customer
     *
     * @mbg.generated Fri Sep 15 18:26:16 CST 2023
     */
    int updateByPrimaryKeySelective(Customer row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_customer
     *
     * @mbg.generated Fri Sep 15 18:26:16 CST 2023
     */
    int updateByPrimaryKey(Customer row);

    /**
     * 插入客户
     * @param customer
     * @return
     */
    int insertCustomer(Customer customer);

    /**
     * 根据name模糊查询
     * @param name
     * @return
     */
    List<String> selectCustomerNameByName(String name);

    /**
     * 根据name精确查询客户全部信息
     * @param name
     * @return
     */
    Customer selectCustomerByName(String name);
}