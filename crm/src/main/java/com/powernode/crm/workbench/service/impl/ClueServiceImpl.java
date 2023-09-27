package com.powernode.crm.workbench.service.impl;

import com.powernode.crm.commons.constants.Constant;
import com.powernode.crm.commons.utils.DateUtils;
import com.powernode.crm.commons.utils.UUIDUtils;
import com.powernode.crm.settings.domain.User;
import com.powernode.crm.workbench.domain.*;
import com.powernode.crm.workbench.mapper.*;
import com.powernode.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("clueService")
public class ClueServiceImpl implements ClueService {
    @Autowired
    private ClueMapper clueMapper;
    @Autowired
    private ClueRemarkMapper clueRemarkMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private ContactsMapper contactsMapper;
    @Autowired
    private ContactsRemarkMapper contactsRemarkMapper;
    @Autowired
    private CustomerRemarkMapper customerRemarkMapper;
    @Autowired
    private ClueActivityRelationMapper clueActivityRelationMapper;
    @Autowired
    private ContactsActivityRelationMapper contactsActivityRelationMapper;
    @Autowired
    private TranMapper tranMapper;
    @Autowired
    private TranRemarkMapper tranRemarkMapper;
    /**
     * 保存线索
     * @param clue
     * @return
     */
    @Override
    public int saveClue(Clue clue) {
        return clueMapper.insertClue(clue);
    }

    @Override
    public List<Clue> queryClueByConditionForPage(Map<String, Object> map) {
        return clueMapper.selectClueByConditionForPage(map);
    }

    @Override
    public int queryCountOfClueByCondition(Map<String, Object> map) {
        return clueMapper.selectCountOfClueByCondition(map);
    }

    /**
     * 查询线索明细
     * @param id
     * @return
     */
    @Override
    public Clue queryClueForDetailById(String id) {
        return clueMapper.selectClueForDetailById(id);
    }

    @Override
    @Transactional
    public void saveConvertClue(Map<String, Object> map) {
        User user =(User) map.get(Constant.SESSION_USER);
        String isCreateTran =(String) map.get("isCreateTran");
        //查询clue详细信息
        String clueId = (String) map.get("clueId");
        Clue clue = clueMapper.selectClueById(clueId);
        //建立客户对象
        Customer customer = new Customer();
        customer.setAddress(clue.getAddress());
        customer.setContactSummary(clue.getContactSummary());
        customer.setDescription(clue.getDescription());
        customer.setId(UUIDUtils.getUUID());
        customer.setCreateBy(user.getId());
        customer.setCreateTime(DateUtils.forMateDateTime(new Date()));
        customer.setName(clue.getCompany());
        customer.setNextContactTime(clue.getNextContactTime());
        customer.setOwner(user.getId());
        customer.setPhone(clue.getPhone());
        customer.setWebsite(clue.getWebsite());
        //保存客户信息
        customerMapper.insertCustomer(customer);

        //建立联系人对象
        Contacts contact = new Contacts();
        contact.setAddress(clue.getAddress());
        contact.setContactSummary(clue.getContactSummary());
        contact.setDescription(clue.getDescription());
        contact.setId(UUIDUtils.getUUID());
        contact.setCreateBy(user.getId());
        contact.setCreateTime(DateUtils.forMateDateTime(new Date()));
        contact.setFullname(clue.getCompany());
        contact.setNextContactTime(clue.getNextContactTime());
        contact.setOwner(user.getId());

        contact.setEmail(clue.getEmail());
        contact.setAppellation(clue.getAppellation());
        contact.setMphone(clue.getMphone());
        contact.setSource(clue.getSource());
        contact.setCustomerId(customer.getId());
        contact.setJob(clue.getJob());
        //保存联系人信息
        contactsMapper.insertContacts(contact);

        //查询该线索下的所有备注信息
        List<ClueRemark> remarkList = clueRemarkMapper.selectClueRemarkByClueId(clueId);
        if (remarkList!=null && remarkList.size() > 0) {
            //创建contactsRemark
            ContactsRemark contactsRemark = null;
            List<ContactsRemark> contactsRemarkList = new ArrayList<>();
            //创建customerRemark
            CustomerRemark customerRemark = new CustomerRemark();
            List<CustomerRemark> customerRemarkList = new ArrayList<>();
            for(ClueRemark clueRemark:remarkList){
                contactsRemark = new ContactsRemark();
                contactsRemark.setContactsId(contact.getId());
                contactsRemark.setCreateBy(clueRemark.getCreateBy());
                contactsRemark.setId(UUIDUtils.getUUID());
                contactsRemark.setCreateTime(clueRemark.getCreateTime());
                contactsRemark.setEditFlag(clueRemark.getEditFlag());
                contactsRemark.setEditTime(clueRemark.getEditTime());
                customerRemark.setEditBy(clueRemark.getEditBy());
                contactsRemarkList.add(contactsRemark);

                customerRemark = new CustomerRemark();
                customerRemark.setCustomerId(customerRemark.getId());
                customerRemark.setCreateBy(clueRemark.getCreateBy());
                customerRemark.setId(UUIDUtils.getUUID());
                customerRemark.setCreateTime(clueRemark.getCreateTime());
                customerRemark.setEditFlag(clueRemark.getEditFlag());
                customerRemark.setEditTime(clueRemark.getEditTime());
                customerRemark.setEditBy(clueRemark.getEditBy());
                customerRemarkList.add(customerRemark);

            }
            //把备注信息添加到联系人备注表
            contactsRemarkMapper.insertContactsRemarkByList(contactsRemarkList);
            //把备注信息添加到客户备注表
            customerRemarkMapper.insertCustomerRemarkByList(customerRemarkList);
        }
        //查询clue和activity关系信息
        List<ClueActivityRelation> clueActivityRelationList = clueActivityRelationMapper.selectClueActivityRelationByClueId(clueId);
        //将clue和activity关系转换为contacts和activity关系
        if(clueActivityRelationList != null && clueActivityRelationList.size() > 0){
            ContactsActivityRelation CORelation = null;
            List<ContactsActivityRelation> CORelationList = new ArrayList<>();
            for(ClueActivityRelation CLRelation: clueActivityRelationList){
                CORelation = new ContactsActivityRelation();
                CORelation.setId(UUIDUtils.getUUID());
                CORelation.setContactsId(contact.getId());
                CORelation.setActivityId(CLRelation.getActivityId());
                CORelationList.add(CORelation);
            }
            //调用service
            contactsActivityRelationMapper.insertContactsActivityRelationByList(CORelationList);
        }
        //判断是否需要添加交易
        if("true".equals(isCreateTran)){
            //创建交易
            Tran tran = new Tran();
            tran.setActivityId((String) map.get("activityId"));
            tran.setContactsId(contact.getId());
            tran.setId(UUIDUtils.getUUID());
            tran.setCreateBy(user.getId());
            tran.setCreateTime(DateUtils.forMateDateTime(new Date()));
            tran.setCustomerId(customer.getId());
            tran.setName((String) map.get("name"));
            tran.setMoney((String) map.get("money"));
            tran.setExpectedDate((String) map.get("expectedDate"));
            tran.setStage((String) map.get("stage"));
            tran.setOwner(user.getId());
            //调用service
            tranMapper.insertTran(tran);
            //将该线索下的备注转换为交易备注
            if (remarkList!=null && remarkList.size() > 0) {
                TranRemark tranRemark = null;
                List<TranRemark> tranRemarkList = new ArrayList<>();
                for(ClueRemark clueRemark:remarkList){
                    tranRemark.setId(UUIDUtils.getUUID());
                    tranRemark.setNoteContent(clueRemark.getNoteContent());
                    tranRemark.setCreateBy(clueRemark.getCreateBy());
                    tranRemark.setCreateTime(clueRemark.getCreateTime());
                    tranRemark.setEditBy(clueRemark.getEditBy());
                    tranRemark.setEditTime(clueRemark.getEditTime());
                    tranRemark.setEditFlag(clueRemark.getEditFlag());
                    tranRemark.setTranId(tran.getId());
                    tranRemarkList.add(tranRemark);
                }
                //调用service
                tranRemarkMapper.insertTranRemarkByList(tranRemarkList);
            }
        }
        //删除该线索下的所有的备注
        clueRemarkMapper.deleteAllClueMarkById(clueId);
        //删除与该线索的市场活动关联表
        clueActivityRelationMapper.deleteAllClueActivityRelationByClueId(clueId);
        //删除该线索
        clueMapper.deleteClueById(clueId);
    }
}
