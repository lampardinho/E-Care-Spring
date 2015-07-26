package com.tsystems.javaschool.ecare.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by Kolia on 01.07.2015.
 */
@Entity
@Table(name = "tariffs", schema = "", catalog = "ecare")
@NamedQueries(
        {
                @NamedQuery(name = "Tariff.getAllTariffs", query = "SELECT t FROM Tariff t"),
                @NamedQuery(name = "Tariff.deleteAllTariffs", query = "DELETE FROM Tariff"),
                @NamedQuery(name = "Tariff.size", query = "SELECT count(t) FROM Tariff t")
        })
public class Tariff implements Serializable
{
    @Id
    @Column(name = "tariff_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tariffId;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private int price;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "available_options",
            joinColumns = @JoinColumn(name = "tariff_id", referencedColumnName = "tariff_id"),
            inverseJoinColumns = @JoinColumn(name = "option_id", referencedColumnName = "option_id"))
    private Set<Option> availableOptions;

    public Tariff()
    {
    }

    public Tariff(String name, int price, Set<Option> availableOptions)
    {
        this.name = name;
        this.price = price;
        this.availableOptions = availableOptions;
    }

    public int getTariffId()
    {
        return tariffId;
    }

    public void setTariffId(int tariffId)
    {
        this.tariffId = tariffId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getPrice()
    {
        return price;
    }

    public void setPrice(int price)
    {
        this.price = price;
    }

    public Set<Option> getAvailableOptions()
    {
        return availableOptions;
    }

    public void setAvailableOptions(Set<Option> availableOptions)
    {
        this.availableOptions = availableOptions;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tariff that = (Tariff) o;

        if (price != that.price) return false;
        if (tariffId != that.tariffId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = tariffId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + price;
        return result;
    }
}
