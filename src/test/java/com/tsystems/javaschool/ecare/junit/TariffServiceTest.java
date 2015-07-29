package com.tsystems.javaschool.ecare.junit;

import com.tsystems.javaschool.ecare.entities.Tariff;
import com.tsystems.javaschool.ecare.util.AppException;
import org.junit.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;


public class TariffServiceTest
{

    private static ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"spring/spring-datasource-test.xml"});
    private static TariffService tariffService = (TariffService) context.getBean("tariffService");

    private static Tariff TR1, TR2, TR3;

    /*@BeforeClass
    public static void beforeClass() {
        TR1 = new Tariff("tTariff1", 200);
        TR2 = new Tariff("tTariff2", 300);
        TR3 = new Tariff("tTariff3", 100);
    }

    @Before
    public void before() {
        TR2 = tariffService.saveOrUpdateTariff(TR2);
        TR3 = tariffService.saveOrUpdateTariff(TR3);
    }

    @Test
    @Transactional
    public void saveTariffTest() {
        Tariff tr = tariffService.saveOrUpdateTariff(TR1);
        Assert.assertEquals(TR1, tr);
    }

    @Test
    @Transactional
    public void loadTariffTest() {
        Assert.assertEquals(TR2, tariffService.loadTariff(TR2.getId()));
        Assert.assertEquals(TR3, tariffService.loadTariff(TR3.getId()));
    }

    @Test(expected = AppException.class)
    @Transactional
    public void loadMissedTariffTest() {
        tariffService.loadTariff(-12l);
    }

    @Test
    @Transactional
    public void deleteTariffTest() {
        long tariffsNumber = tariffService.getNumberOfTariffs();
        tariffService.deleteTariff(TR3.getId());
        Assert.assertEquals(tariffsNumber - 1l, tariffService.getNumberOfTariffs());
        TR3 = tariffService.saveOrUpdateTariff(TR3);
    }

    @Test(expected = AppException.class)
    @Transactional
    public void deleteMissedTariff() {
        tariffService.deleteTariff(-12l);
    }

    @Test
    @Transactional
    public void getAllTariffsTest() {
        List<Tariff> loadedTariffs = tariffService.getAllTariffs();
        Tariff tariffs[] = {TR2, TR3};
        Assert.assertArrayEquals(tariffs, loadedTariffs.toArray());
    }

    @After
    public void after() {
        tariffService.deleteAllTariffs();
    }*/
}
