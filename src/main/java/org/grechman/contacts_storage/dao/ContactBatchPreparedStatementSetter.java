package org.grechman.contacts_storage.dao;

import org.grechman.contacts_storage.entity.Contact;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ContactBatchPreparedStatementSetter implements BatchPreparedStatementSetter {
    private final List<Contact> contacts;

    public ContactBatchPreparedStatementSetter(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public void setValues(PreparedStatement ps, int i) throws SQLException {
        Contact contact = contacts.get(i);
        ps.setString(1, contact.getName());
    }

    @Override
    public int getBatchSize() {
        return contacts.size();
    }
}
