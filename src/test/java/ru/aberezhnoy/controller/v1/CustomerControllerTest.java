package ru.aberezhnoy.controller.v1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.aberezhnoy.controller.CustomerController;
import ru.aberezhnoy.dto.CustomerDto;
import ru.aberezhnoy.exception.CustomerNotFound;
import ru.aberezhnoy.service.CustomerService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CustomerControllerTest {
    @Autowired
    private CustomerController cc;
    @Autowired
    private CustomerService cs;

    @Test
    public void getClientByIdAndUpdateTest() {
        CustomerDto customerDto1 = cc.findById(1L);
        customerDto1.setFirstname("JUnitTestWithId1");
        cs.update(customerDto1);
        CustomerDto customerDto2 = cc.findById(2L);
        customerDto2.setLastname("JUnitTestWithId2");
        cs.update(customerDto2);
        assertEquals(1, cc.findById(1L).getId());
        assertEquals("JUnitTestWithId1", cc.findById(1L).getFirstname());
        assertEquals(2, cc.findById(2L).getId());
        assertEquals("JUnitTestWithId2", cc.findById(2L).getLastname());
    }

    @Test
    public void throwNotFoundException() {
        assertThrows(CustomerNotFound.class, () -> cc.findById(6L));
    }

    @Test
    public void getAllClientsTest() {
        assertNotNull(cc.findAll());
        List<String> expectedValues = new ArrayList<>();
        long size = cc.findAll().size();
        for (long i = 1; i <= size; i++) {
            expectedValues.add(cc.findById(i).getEmail());
        }
        assertIterableEquals(expectedValues, cc.findAll().stream().map(CustomerDto::getEmail).toList());
    }

    @Test
    public void deleteTest() {
        cc.deleteById(1L);
        assertEquals(4, cc.findAll().size());
        assertEquals(2L, cc.findById(2L).getId());
        assertEquals(3L, cc.findById(3L).getId());
        assertEquals(4L, cc.findById(4L).getId());
        assertEquals(5L, cc.findById(5L).getId());
        assertThrows(CustomerNotFound.class, () -> cc.findById(1L));
    }
}
