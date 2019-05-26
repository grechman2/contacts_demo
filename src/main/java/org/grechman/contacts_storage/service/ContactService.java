package org.grechman.contacts_storage.service;

import org.grechman.contacts_storage.dao.JdbcContactDao;
import org.grechman.contacts_storage.entity.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {
    private final JdbcContactDao jdbcContactDao;

    @Autowired
    public ContactService(JdbcContactDao jdbcContactDao) {
        this.jdbcContactDao = jdbcContactDao;
    }

    /**
     * Get contacts ignoring items which names match to filter
     * */
    public List<Contact> getContactsIgnoringMatchedByFilter(String nameFilterPattern){
        return jdbcContactDao.getContactsIgnoringMatchedByFilter(nameFilterPattern);
    }

    public void addContacts(List<Contact> contacts){
        jdbcContactDao.addContacts(contacts);
    }
}
