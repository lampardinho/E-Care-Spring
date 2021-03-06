package com.tsystems.javaschool.ecare.dao;

import java.util.Set;


public interface IAbstractDAO<T>
{
    public T saveOrUpdate(T t);

    public T load(int id);

    public void delete(T t);

    public Set<T> getAll();

    public void deleteAll();

    public long getCount();


}
