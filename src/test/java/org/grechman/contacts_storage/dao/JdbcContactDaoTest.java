package org.grechman.contacts_storage.dao;

import org.grechman.contacts_storage.entity.Contact;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class JdbcContactDaoTest {

    JdbcContactDao jdbcContactDao;

    private final static String QUERY_FOR_ALL_CONTACTS = "SELECT * FROM CONTACTS.CONTACT";

    private final static String QUERY_FOR_INSERTING_INTO_CUSTOMER = "INSERT INTO CONTACTS.CONTACT (NAME) VALUES (?)";

    @Mock
    JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() throws Exception {
        jdbcContactDao = new JdbcContactDao(jdbcTemplate);
    }

    @Test
    public void getContactsIgnoringMatchedByFilter() {

        // Act
        jdbcContactDao.getContactsIgnoringMatchedByFilter("test");

        //assert
        verify(jdbcTemplate, times(1)).query(eq(QUERY_FOR_ALL_CONTACTS), any(ContactRegexResultSetExtractor.class));
    }

    @Test
    public void addContacts() {

        Contact contact = new Contact(1,"Test name");

        List<Contact> contacts = Arrays.asList(contact);

        // Act
        jdbcContactDao.addContacts(contacts);

        // assert
        verify(jdbcTemplate, times(1)).batchUpdate(eq(QUERY_FOR_INSERTING_INTO_CUSTOMER), any(ContactBatchPreparedStatementSetter.class));
    }
}