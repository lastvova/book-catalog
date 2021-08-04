package java.com.softserve.repository.impl;


import java.com.softserve.repository.BasicRepository;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

public abstract class BasicRepositoryImpl<T extends Serializable, I extends Serializable> implements BasicRepository<T, I> {

    protected final Class<T> basicClass;

    public BasicRepositoryImpl() {
        basicClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

}
