package com.powernode.crm.workbench.service.impl;

import com.powernode.crm.commons.constants.Constant;
import com.powernode.crm.commons.utils.DateUtils;
import com.powernode.crm.commons.utils.UUIDUtils;
import com.powernode.crm.settings.domain.User;
import com.powernode.crm.workbench.domain.Customer;
import com.powernode.crm.workbench.domain.FunnelVo;
import com.powernode.crm.workbench.domain.Tran;
import com.powernode.crm.workbench.domain.TranHistory;
import com.powernode.crm.workbench.mapper.CustomerMapper;
import com.powernode.crm.workbench.mapper.TranHistoryMapper;
import com.powernode.crm.workbench.mapper.TranMapper;
import com.powernode.crm.workbench.service.CustomerService;
import com.powernode.crm.workbench.service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service("tranService")
public class TranServiceImpl implements TranService {
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private TranMapper tranMapper;
    @Autowired
    private TranHistoryMapper tranHistoryMapper;
    @Transactional()
    @Override
    public void saveCreateTran(Map<String, Object> map) {
        User user = (User) map.get(Constant.SESSION_USER);
        String customerName = (String) map.get("customerName");
        //查询当前客户信息
        Customer customer = customerMapper.selectCustomerByName(customerName);
        //判断是否存在当前客户
        if(customer== null){
            //不存在，新建
            customer = new Customer();
            customer.setName(customerName);
            customer.setCreateBy(user.getId());
            customer.setOwner(user.getId());
            customer.setId(UUIDUtils.getUUID());
            customer.setCreateTime(DateUtils.forMateDateTime(new Date()));
            //保存新客户信息
            customerMapper.insertCustomer(customer);
        }
        //创建交易
        Tran tran = new Tran();
        tran.setOwner((String) map.get("owner"));
        tran.setId(UUIDUtils.getUUID());
        tran.setCreateBy(user.getId());
        tran.setCreateTime(DateUtils.forMateDateTime(new Date()));
        tran.setCustomerId(customer.getId());
        tran.setStage((String) map.get("stage"));
        tran.setMoney((String) map.get("money"));
        tran.setName((String) map.get("name"));
        tran.setExpectedDate((String) map.get("expectedDate"));
        tran.setType((String) map.get("type"));
        tran.setSource((String)map.get("source"));
        tran.setActivityId((String) map.get("activityId"));
        tran.setContactsId((String) map.get("contactsId"));
        tran.setDescription((String) map.get("description"));
        tran.setContactSummary((String) map.get("contactSummary"));
        tran.setNextContactTime((String) map.get("nextContactTime"));
        //保存交易
        tranMapper.insertTran(tran);
        //创建交易历史
        TranHistory tranHistory = new TranHistory();
        tranHistory.setCreateBy(user.getId());
        tranHistory.setCreateTime(DateUtils.forMateDateTime(new Date()));
        tranHistory.setId(UUIDUtils.getUUID());
        tranHistory.setMoney((String) map.get("money"));
        tranHistory.setStage((String) map.get("stage"));
        tranHistory.setExpectedDate((String) map.get("expectedDate"));
        tranHistory.setTranId(tran.getId());
        //保存交易历史
        tranHistoryMapper.insertTranHistory(tranHistory);
    }

    @Override
    public Tran queryTranForDetailById(String id) {
        return tranMapper.selectTranForDetailById(id);
    }

    @Override
    public List<FunnelVo> queryCountOfTranGroupByStage() {
        return tranMapper.selectCountOfTranGroupByStage();
    }
}
