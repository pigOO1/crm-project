package com.powernode.crm.workbench.service;

import com.powernode.crm.workbench.domain.Contacts;

import java.util.List;

public interface ContactsService {
    List<Contacts> queryContactsByName(String name);
}
