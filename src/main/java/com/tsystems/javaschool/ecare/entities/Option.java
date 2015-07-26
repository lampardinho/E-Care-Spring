package com.tsystems.javaschool.ecare.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by Kolia on 01.07.2015.
 */
@Entity
@Table(name = "options", schema = "", catalog = "ecare")
@NamedQueries(
        {
                @NamedQuery(name = "Option.getAllOptions", query = "SELECT o FROM Option o"),
                @NamedQuery(name = "Option.findOptionByTitleAndTariffId", query = "SELECT o FROM Option o WHERE o.name = :title"),
                @NamedQuery(name = "Option.getAllOptionsForTariff", query = "SELECT o FROM Option o"),
                @NamedQuery(name = "Option.deleteAllOptions", query = "DELETE FROM Option"),
                @NamedQuery(name = "Option.deleteAllOptionsForTariff", query = "DELETE FROM Option"),
                @NamedQuery(name = "Option.size", query = "SELECT count(o) FROM Option o")
        })
public class Option implements Serializable
{
    @Id
    @Column(name = "option_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int optionId;

    @Column(name = "name")
    private String name;

    @Column(name = "connection_price")
    private int connectionPrice;

    @Column(name = "monthly_price")
    private int monthlyPrice;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "option_locking",
            joinColumns = @JoinColumn(name = "selected_option_id"),
            inverseJoinColumns = @JoinColumn(name = "locked_option_id"))
    private Set<Option> lockedOptions;


    public Option()
    {
    }

    public Option(String name, int connectionPrice, int monthlyPrice)
    {
        this.name = name;
        this.connectionPrice = connectionPrice;
        this.monthlyPrice = monthlyPrice;
    }

    public int getOptionId()
    {
        return optionId;
    }

    public void setOptionId(int optionId)
    {
        this.optionId = optionId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getConnectionPrice()
    {
        return connectionPrice;
    }

    public void setConnectionPrice(int connectionPrice)
    {
        this.connectionPrice = connectionPrice;
    }

    public int getMonthlyPrice()
    {
        return monthlyPrice;
    }

    public void setMonthlyPrice(int monthlyPrice)
    {
        this.monthlyPrice = monthlyPrice;
    }

    public Set<Option> getLockedOptions()
    {
        return lockedOptions;
    }

    public void setLockedOptions(Set<Option> lockedOptions)
    {
        this.lockedOptions = lockedOptions;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Option that = (Option) o;

        if (connectionPrice != that.connectionPrice) return false;
        if (monthlyPrice != that.monthlyPrice) return false;
        if (optionId != that.optionId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = optionId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + connectionPrice;
        result = 31 * result + monthlyPrice;
        return result;
    }


}
