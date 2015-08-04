package com.tsystems.javaschool.ecare.junit;

import com.tsystems.javaschool.ecare.entities.Option;
import com.tsystems.javaschool.ecare.entities.Tariff;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class OptionServiceTest
{

    private static ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"WEB-INF/spring-datasource-test.xml"});
    private static TariffService tariffService = (TariffService) context.getBean("tariffService");
    private static OptionService optionService = (OptionService) context.getBean("optionService");

    private static Tariff TR1, TR2, TR3;
    private static Option OP11, OP12, OP21, OP22, OP23, OP31, OP32;

    /*@BeforeClass
    public static void beforeClass() {
        TR1 = tariffService.saveOrUpdateTariff(new Tariff("tTariff1", 200));
        OP11 = optionService.saveOrUpdateOption(new Option(TR1, "tOption11", 3, 200));
        OP12 = optionService.saveOrUpdateOption(new Option(TR1, "tOption12", 4, 250));
        TR1.addOption(OP11);
        TR1.addOption(OP12);
        TR1 = tariffService.saveOrUpdateTariff(TR1);

        TR2 = tariffService.saveOrUpdateTariff(new Tariff("tTariff2", 300));
        OP21 = optionService.saveOrUpdateOption(new Option(TR2, "tOption21", 3, 150));
        OP22 = optionService.saveOrUpdateOption(new Option(TR2, "tOption22", 1, 150));
        OP23 = optionService.saveOrUpdateOption(new Option(TR2, "tOption23", 2, 200));
        TR2.addOption(OP21);
        TR2.addOption(OP22);
        TR2.addOption(OP23);
        TR2 = tariffService.saveOrUpdateTariff(TR2);

        TR3 = tariffService.saveOrUpdateTariff(new Tariff("tTariff3", 200));
        OP31 = optionService.saveOrUpdateOption(new Option(TR3, "tOption31", 3, 200));
        OP32 = optionService.saveOrUpdateOption(new Option(TR3, "tOption32", 4, 250));
        TR3.addOption(OP31);
        TR3.addOption(OP32);
        TR3 = tariffService.saveOrUpdateTariff(TR3);
    }

    @Test
    @Transactional
    public void saveOptionTest() {
        Option op = optionService.saveOrUpdateOption(OP23);
        OP23.setId(op.getId());
        Assert.assertEquals(OP23, op);
        optionService.deleteOption(op.getId());
    }

    @Test
    @Transactional
    public void loadOptionTest() {
        Assert.assertEquals(OP21, optionService.loadOption(OP21.getId()));
        optionService.deleteOption(OP21.getId());
    }

    @Test(expected = AppException.class)
    @Transactional
    public void loadMissedOptionTest() {
        optionService.loadOption(-12l);
    }

    @Test
    @Transactional
    public void deleteOption() {
        long optionsNumber = optionService.getNumberOfOptions();
        TR2.getOptions().remove(OP22);
        tariffService.saveOrUpdateTariff(TR2);
        Assert.assertEquals(optionsNumber - 1l, optionService.getNumberOfOptions());
        OP22 = optionService.saveOrUpdateOption(OP22);
    }

    @Test(expected = ECareException.class)
    @Transactional
    public void deleteMissedOptionTest() {
        optionService.deleteOption(-12l);
    }

    @Test
    @Transactional
    public void getAllOptionsForTariffTest() {
        List<Option> loadedOptions = optionService.getAllOptionsForTariff(TR1.getId());
        Option options[] = {OP11, OP12};
        Assert.assertArrayEquals(options, loadedOptions.toArray());
    }

    @Test
    @Transactional
    public void deleteAllOptionsForTariffTest() {
        long optionsNumber = optionService.getNumberOfOptions();
        optionService.deleteAllOptionsForTariff(TR3.getId());
        Assert.assertEquals(optionsNumber - 2l, optionService.getNumberOfOptions());
    }

    @Test
    @Transactional
    public void setAndRemoveDependentOptionTest() {
        OP11 = optionService.setDependentOption(OP11, OP12);
        Assert.assertEquals(true, OP11.getDependentOptions().contains(OP12));
        OP11 = optionService.deleteDependentOption(OP11, OP12);
        Assert.assertEquals(false, OP11.getDependentOptions().contains(OP12));
    }

    @Test(expected = ECareException.class)
    @Transactional
    public void setIncompatibleOptionAsDependentTest() {
        OP11 = optionService.setIncompatibleOption(OP11, OP12);
        try{
            OP11 = optionService.setDependentOption(OP11, OP12);
        } catch (ECareException ecx) {
            OP11 = optionService.deleteIncompatibleOption(OP11, OP12);
            throw ecx;
        }
    }

    @Test
    @Transactional
    public void setAndRemoveIncompatibleOptionTest() {
        OP11 = optionService.setIncompatibleOption(OP11, OP12);
        Assert.assertEquals(true, OP11.getIncompatibleOptions().contains(OP12));
        OP11 = optionService.deleteIncompatibleOption(OP11, OP12);
        Assert.assertEquals(false, OP11.getIncompatibleOptions().contains(OP12));
    }

    @Test(expected = ECareException.class)
    @Transactional
    public void setDependentOptionAsIncompatibleTest() {
        OP11 = optionService.setDependentOption(OP11, OP12);
        try{
            OP11 = optionService.setIncompatibleOption(OP11, OP12);
        } catch (ECareException ecx) {
            OP11 = optionService.deleteDependentOption(OP11, OP12);
            throw ecx;
        }
    }

    @AfterClass
    public static void afterClass() {
        tariffService.deleteAllTariffs();
    }*/
}
