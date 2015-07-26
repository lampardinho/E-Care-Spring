package com.tsystems.javaschool.ecare.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Created by Kolia on 27.06.2015.
 */
@Entity
@Table(name = "users", schema = "", catalog = "ecare")
@NamedQueries(
        {
                @NamedQuery(name = "User.getAllUsers", query = "SELECT c FROM User c"),
                @NamedQuery(name = "User.findUserByLoginAndPassword", query = "SELECT c FROM User c WHERE c.email = :login AND c.password = :password"),
                @NamedQuery(name = "User.findUserByPhoneNumber", query = "SELECT cn.user FROM Contract cn WHERE cn.phoneNumber = :number"),
                @NamedQuery(name = "Client.findClientByLogin", query = "SELECT c FROM User c WHERE c.email = :login"),
                @NamedQuery(name = "Client.deleteAllClients", query = "DELETE FROM User WHERE isAdmin = 0"),
                @NamedQuery(name = "Client.size", query = "SELECT count(c) FROM User c WHERE c.isAdmin = 0")
        })
public class User implements Serializable
{
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "passport_data")
    private String passportData;

    @Column(name = "address")
    private String address;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "is_admin")
    private byte isAdmin;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "contract_locking",
            joinColumns = @JoinColumn(name = "locker_id"),
            inverseJoinColumns = @JoinColumn(name = "contract_id"))
    private Set<Contract> lockedContracts;

    public User()
    {
    }

    public User(String name, String surname, Date birthDate,
                String passportData, String address, String email,
                String password, byte isAdmin)
    {
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.passportData = passportData;
        this.address = address;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSurname()
    {
        return surname;
    }

    public void setSurname(String surname)
    {
        this.surname = surname;
    }

    public Date getBirthDate()
    {
        return birthDate;
    }

    public void setBirthDate(Date birthDate)
    {
        this.birthDate = birthDate;
    }

    public String getPassportData()
    {
        return passportData;
    }

    public void setPassportData(String passportData)
    {
        this.passportData = passportData;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public boolean getIsAdmin()
    {
        return isAdmin != 0;
    }

    public void setIsAdmin(boolean isAdmin)
    {
        this.isAdmin = (isAdmin) ? (byte) 1 : 0;
    }

    public Set<Contract> getLockedContracts()
    {
        return lockedContracts;
    }

    public void setLockedContracts(Set<Contract> lockedContracts)
    {
        this.lockedContracts = lockedContracts;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User that = (User) o;

        if (isAdmin != that.isAdmin) return false;
        if (userId != that.userId) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (birthDate != null ? !birthDate.equals(that.birthDate) : that.birthDate != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (passportData != null ? !passportData.equals(that.passportData) : that.passportData != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (surname != null ? !surname.equals(that.surname) : that.surname != null) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = userId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (birthDate != null ? birthDate.hashCode() : 0);
        result = 31 * result + (passportData != null ? passportData.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (int) isAdmin;
        return result;
    }


}
