package com.tsystems.javaschool.ecare.junit.services;

import com.tsystems.javaschool.ecare.entities.*;
import com.tsystems.javaschool.ecare.services.ContractService;
import com.tsystems.javaschool.ecare.services.OptionService;
import com.tsystems.javaschool.ecare.services.TariffService;
import com.tsystems.javaschool.ecare.services.UserService;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;


import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring-datasource-test.xml")
public class ContractServiceTest
{


    @Autowired
    private UserService clientService;
    @Autowired
    private ContractService contractService;
    @Autowired
    private TariffService tariffService;
    @Autowired
    private OptionService optionService;

    private static User CL1;
    private static Contract CN11, CN12;
    private static Tariff TR1;
    private static Option OP11, OP12, OP13, OP14, OP15;


    @Before
    public void before() {

        CL1 = clientService.saveOrUpdateClient(new User("test", "user", new Date(), "653456354", "spb", "zxc@mail.ru", "qwerty", "ROLE_USER"));
        OP11 = optionService.getOptionByName("Bit");

        Set<Option> options = new HashSet<>();
        options.add(OP11);
        TR1 = tariffService.saveOrUpdateTariff(new Tariff("tTariff1", 300, options));

        CN11 = new Contract(CL1, TR1, 123454, 1000);
        CN12 = new Contract(CL1, TR1, 765435, 500);

        CN12 = contractService.saveOrUpdateContract(CN12);
    }

    @Test
    @Transactional
    public void saveContractTest() {
        Contract cn = contractService.saveOrUpdateContract(CN12);
        //CN11.setContractId(cn.getContractId());
        assertEquals(CN12, cn);
    }

    @Test
    @Transactional
    public void loadContractTest() {
        assertNotSame(CN12, contractService.loadContract(CN11.getContractId()));
    }

    @Test
    @Transactional
    public void loadMissedContractTest() {
        Contract contract = contractService.loadContract(-12);
        assertNull(contract);
    }

    @Test
    @Transactional
    public void findContractByNumberTest() {
        assertEquals(CN12, contractService.getContractByPhoneNumber(CN12.getPhoneNumber()));
    }

    @Test
    @Transactional
    public void findContractByNotExistingNumberTest() {
        assertNull(contractService.getContractByPhoneNumber(-12));

    }



    @Test
    @Transactional
    public void deleteMissedContractTest() {
        assertNull(contractService.loadContract(-12));
    }

    @Test
    @Transactional
    public void getAllContractsTest() {
        Set<Contract> loadedContracts = contractService.getAllContracts();
        assertNotNull(loadedContracts);
    }


}
