package org.grechman.contacts_storage.dao;

import org.grechman.contacts_storage.entity.Contact;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ContactBatchPreparedStatementSetterTest {

    ContactBatchPreparedStatementSetter setter;

    @Mock
    ResultSet resultSet;

    @Mock
    PreparedStatement preparedStatement;

    @Test
    public void setValues() throws SQLException {

        Contact contact1 = new Contact(1, "test1");
        Contact contact2 = new Contact(2, "test2");
        List<Contact> contacts = Arrays.asList(contact1, contact2);

        setter = new ContactBatchPreparedStatementSetter(contacts);

        // for first contact
        setter.setValues(preparedStatement, 0);
        verify(preparedStatement, times(1)).setString(eq(1), eq(contact1.getName()));

        // for second contact
        setter.setValues(preparedStatement, 1);
        verify(preparedStatement, times(1)).setString(eq(1), eq(contact2.getName()));

    }

    @Test
    public void getBatchSize() {
        Contact contact1 = new Contact(1, "test1");
        Contact contact2 = new Contact(2, "test2");
        List<Contact> contacts = Arrays.asList(contact1, contact2);

        setter = new ContactBatchPreparedStatementSetter(contacts);

        assertThat(setter.getBatchSize()).isEqualTo(contacts.size());
    }
}