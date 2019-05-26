package org.grechman.contacts_storage.dao;

import org.grechman.contacts_storage.entity.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class JdbcContactDao {
    private final int FETCH_SIZE = 300;

    private final static String QUERY_FOR_ALL_CONTACTS = "SELECT * FROM CONTACTS.CONTACT";
    private final static String QUERY_FOR_INSERTING_INTO_CUSTOMER = "INSERT INTO CONTACTS.CONTACT (NAME) VALUES (?)";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcContactDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        jdbcTemplate.setFetchSize(FETCH_SIZE);
    }

    public List<Contact> getContactsIgnoringMatchedByFilter(String nameFilter) {
        List<Contact> contacts = jdbcTemplate.query(QUERY_FOR_ALL_CONTACTS, new ContactRegexResultSetExtractor(nameFilter));
        return contacts;
    }

    public void addContacts(List<Contact> contacts) {
        jdbcTemplate.batchUpdate(QUERY_FOR_INSERTING_INTO_CUSTOMER, new ContactBatchPreparedStatementSetter(contacts));
    }
}
