package com.tsystems.javaschool.ecare.junit;

import com.tsystems.javaschool.ecare.entities.Contract;
import com.tsystems.javaschool.ecare.entities.Option;
import com.tsystems.javaschool.ecare.entities.Tariff;
import com.tsystems.javaschool.ecare.entities.User;
import com.tsystems.javaschool.ecare.services.ContractService;
import com.tsystems.javaschool.ecare.services.OptionService;
import com.tsystems.javaschool.ecare.services.TariffService;
import com.tsystems.javaschool.ecare.services.UserService;
import com.tsystems.javaschool.ecare.util.AppException;
import org.junit.*;


import java.util.Date;
import java.util.HashSet;


public class ClientServicesTest
{
    private static User CL1, CL2, CL3;
    private static Contract CN21, CN31;
    private static Tariff TR1;
    private static Option OP11, OP12, OP13, OP14, OP15;

    private static long clientsNumber;
    private static long contractsNumber;

    @Before
    public void before() {
        clientsNumber = UserService.getInstance().getNumberOfClients();
        contractsNumber = ContractService.getInstance().getNumberOfContracts();

        TR1 = new Tariff("tTariff1", 300, new HashSet<Option>());
        TR1 = TariffService.getInstance().saveOrUpdateTariff(TR1);

        OP11 = new Option("tOption11", 5, 120);
        OP11 = OptionService.getInstance().saveOrUpdateOption(OP11);

        OP12 = new Option("tOption12", 4, 100);
        OP12 = OptionService.getInstance().saveOrUpdateOption(OP12);

        OP13 = new Option("tOption13", 5, 50);
        OP13 = OptionService.getInstance().saveOrUpdateOption(OP13);

        OP14 = new Option("tOption14", 10, 100);
        OP14 = OptionService.getInstance().saveOrUpdateOption(OP14);

        OP15 = new Option("tOption15", 2, 110);
        OP15 = OptionService.getInstance().saveOrUpdateOption(OP15);

        TR1.getAvailableOptions().add(OP11);
        TR1.getAvailableOptions().add(OP12);
        TR1.getAvailableOptions().add(OP13);
        TR1.getAvailableOptions().add(OP14);
        TR1.getAvailableOptions().add(OP15);

        TR1 = TariffService.getInstance().saveOrUpdateTariff(TR1);

        CL1 = new User("Ivan", "Semenov",  new Date(), "9234132", "SPB", "ivanov@mail.ru", "password", (byte)1);
        CL1 = UserService.getInstance().saveOrUpdateClient(CL1);

        CL2 = new User("Semen", "Semenov", new Date(), "98274560923l", "Moscow", "semenov@mail.ru", "Qwerty123", (byte)1);
        CL2 = UserService.getInstance().saveOrUpdateClient(CL2);

        CN21 = new Contract(CL2, null, 14557643, 300);

        CL3 = new User("Petr", "Petrov", new Date(), "9582450345l", "Sankt-Peterburg", "petrov@mail.ru", "petrov51spb", (byte)0);
        CL3 = UserService.getInstance().saveOrUpdateClient(CL3);

        CN31 = new Contract(CL3, TR1, 896523450, 234);
        CN31 = ContractService.getInstance().saveOrUpdateContract(CN31);
    }

    @Test
    public void testLoginUser() throws Exception {
        return;
        //User client = UserService.getInstance().findClient(CL2.getEmail(), CL2.getPassword());
        //Assert.assertEquals(CL2, client);
    }

    @Test
    public void testFindUserByNumber() throws Exception {
        return;
        //User client = UserService.getInstance().findClientByNumber(CN31.getPhoneNumber());
        //Assert.assertEquals(CL3, client);
    }

    @Test
    public void testLoadUser()  throws Exception  {
        return;
        //Assert.assertEquals(CL1, UserService.getInstance().loadClient(CL1.getUserId()));
        //Assert.assertEquals(CL2, UserService.getInstance().loadClient(CL2.getUserId()));
        //Assert.assertEquals(CL3, UserService.getInstance().loadClient(CL3.getUserId()));
    }

    @Test
    public void testUpdateUser() throws Exception {
        throw new Exception();
        //CL1 = UserService.getInstance().loadClient(CL1.getUserId());
        //CL1.setSurname("Ivanov");
        //CL1.setBirthDate(new Date());
        //CL1 = UserService.getInstance().saveOrUpdateClient(CL1);
        //Assert.assertEquals(CL1, UserService.getInstance().loadClient(CL1.getUserId()));
    }

    @Test
    public void testDeleteUser() throws Exception {
        throw new Exception();
        /*UserService.getInstance().deleteClient(CL1.getUserId());
        Assert.assertEquals(clientsNumber + 2l, UserService.getInstance().getNumberOfClients());
        UserService.getInstance().deleteClient(CL2.getUserId());
        Assert.assertEquals(clientsNumber + 1l, UserService.getInstance().getNumberOfClients());
        UserService.getInstance().deleteClient(CL3.getUserId());
        Assert.assertEquals(clientsNumber, UserService.getInstance().getNumberOfClients());
        TariffService.getInstance().deleteTariff(TR1.getTariffId());*/
    }

    @Test(expected = AppException.class)
    public void testLoadMissedUser() throws Exception {
        UserService.getInstance().loadClient(-12);
    }

    @Ignore
    @Test(expected = AppException.class)
    public void testDeleteMissedUser() throws Exception {
        UserService.getInstance().deleteClient(-12);
    }



    @Test
    public void testLoadContract() throws Exception {
        Assert.assertEquals(CN31, ContractService.getInstance().loadContract(CN31.getContractId()));
    }

    @Test
    public void testDeleteContract() throws Exception {
        ContractService.getInstance().deleteContract(CN31.getContractId());
        Assert.assertEquals(contractsNumber + 1l, ContractService.getInstance().getNumberOfContracts());
    }

    @Test(expected = AppException.class)
    public void testLoadMissedContract() throws Exception {
        ContractService.getInstance().loadContract(-12);
    }

    @Test(expected = AppException.class)
    public void testDeleteMissedContract() throws Exception {
        ContractService.getInstance().deleteContract(-12);
    }

    /*@After
    public void after() {
        if(UserService.getInstance().getNumberOfClients() > clientsNumber) {
            if(UserService.getInstance().existLogin(CL1.getEmail())) UserService.getInstance().deleteClient(CL1.getUserId());
            if(UserService.getInstance().existLogin(CL2.getEmail())) UserService.getInstance().deleteClient(CL2.getUserId());
            if(UserService.getInstance().existLogin(CL3.getEmail())) UserService.getInstance().deleteClient(CL3.getUserId());
            TariffService.getInstance().deleteTariff(TR1.getTariffId());
        }
    }*/
}
