package com.tsystems.javaschool.ecare.junit;

import com.tsystems.javaschool.ecare.entities.User;
import com.tsystems.javaschool.ecare.util.AppException;
import org.junit.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;



public class UserServiceTest
{

    private static ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"spring/spring-datasource-test.xml"});
    private static UserService clientService = (UserService) context.getBean("clientService");

    private static User CL1, CL2, CL3;

    /*@BeforeClass
    public static void beforeClass() {
        //Util.setClientService(clientService);

        CL1 = new User("Ivan", null, null, 9234132135l, "SPB", "ivanov@mail.ru", "password", "ROLE_USER", 1000);
        CL2 = new User("Semen", "Semenov", new Date(), 98274560923l, "Moscow", "semenov@mail.ru", "Qwerty123", "ROLE_USER", 1000);
        CL3 = new User("Petr", "Petrov", new Date(), 9582450345l, "Sankt-Peterburg", "petrov@mail.ru", "petrov51spb", "ROLE_USER", 2000);
    }

    @Before
    public void before() {
        CL2 = clientService.saveOrUpdateClient(CL2);
        CL3 = clientService.saveOrUpdateClient(CL3);
    }

    @Test
    @Transactional
    public void saveClientTest() {
        User cl = clientService.saveOrUpdateClient(CL1);
        CL1.setId(cl.getId());
        assertEquals(CL1, cl);
    }

    @Test
    @Transactional
    public void loadClientTest() {
        assertEquals(CL2, clientService.loadClient(CL2.getId()));
        assertEquals(CL3, clientService.loadClient(CL3.getId()));
    }

    @Test(expected = AppException.class)
    @Transactional
    public void loadMissedClientTest() {
        clientService.loadClient(-12l);
    }

    @Test
    @Transactional
    public void findClientTest() {
        assertEquals(CL2, clientService.findClient(CL2.getEmail(), CL2.getPassword()));
        assertEquals(CL3, clientService.findClient(CL3.getEmail(), CL3.getPassword()));
    }

    @Test(expected = AppException.class)
    @Transactional
    public void findMissedClientTest() {
        clientService.findClient(CL1.getEmail(), CL1.getPassword());
    }

    @Test(expected = AppException.class)
    @Transactional
    public void findClientByNotExistedNumberTest() {
        clientService.findClientByNumber(-12345l);
    }

    @Test
    @Transactional
    public void deleteClientTest() {
        long clientsNumber = clientService.getNumberOfClients();
        clientService.deleteClient(CL2.getId());
        assertEquals(clientsNumber - 1l, clientService.getNumberOfClients());
        clientService.deleteClient(CL3.getId());
        assertEquals(clientsNumber - 2l, clientService.getNumberOfClients());
    }

    @Test(expected = AppException.class)
    public void deleteMissedClientTest() {
        clientService.deleteClient(-12l);
    }

    @Test
    @Transactional
    public void getAllClients() {
        List<User> loadedClients = clientService.getAllClients();
        User clients[] = {CL2, CL3};
        assertArrayEquals(clients, loadedClients.toArray());
    }

    @Test
    @Transactional
    public void existLoginTest() {
        assertEquals(false, clientService.existLogin(CL1.getEmail()));
        assertEquals(true, clientService.existLogin(CL2.getEmail()));
        assertEquals(true, clientService.existLogin(CL3.getEmail()));
    }

    @After
    public void after() {
        clientService.deleteAllClients();
    }*/
}
