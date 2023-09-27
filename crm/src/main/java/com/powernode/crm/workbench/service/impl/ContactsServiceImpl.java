package com.powernode.crm.workbench.service.impl;

import com.powernode.crm.workbench.domain.Contacts;
import com.powernode.crm.workbench.mapper.ContactsMapper;
import com.powernode.crm.workbench.service.ContactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("contactsService")
public class ContactsServiceImpl implements ContactsService {
    @Autowired
    private ContactsMapper contactsMapper;
    @Override
    public List<Contacts> queryContactsByName(String name) {
        return contactsMapper.selectContactsByName(name);
    }
}
