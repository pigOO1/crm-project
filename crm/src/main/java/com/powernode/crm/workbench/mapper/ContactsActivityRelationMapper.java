package com.powernode.crm.workbench.mapper;

import com.powernode.crm.workbench.domain.ContactsActivityRelation;

import java.util.List;

public interface ContactsActivityRelationMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_contacts_activity_relation
     *
     * @mbg.generated Sat Sep 16 20:54:50 CST 2023
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_contacts_activity_relation
     *
     * @mbg.generated Sat Sep 16 20:54:50 CST 2023
     */
    int insert(ContactsActivityRelation row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_contacts_activity_relation
     *
     * @mbg.generated Sat Sep 16 20:54:50 CST 2023
     */
    int insertSelective(ContactsActivityRelation row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_contacts_activity_relation
     *
     * @mbg.generated Sat Sep 16 20:54:50 CST 2023
     */
    ContactsActivityRelation selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_contacts_activity_relation
     *
     * @mbg.generated Sat Sep 16 20:54:50 CST 2023
     */
    int updateByPrimaryKeySelective(ContactsActivityRelation row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_contacts_activity_relation
     *
     * @mbg.generated Sat Sep 16 20:54:50 CST 2023
     */
    int updateByPrimaryKey(ContactsActivityRelation row);

    /**
     * 添加联系人市场活动关系记录
     * @param list
     * @return
     */
    int insertContactsActivityRelationByList(List<ContactsActivityRelation> list);
}