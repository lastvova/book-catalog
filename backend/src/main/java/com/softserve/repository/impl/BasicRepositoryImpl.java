package com.softserve.repository.impl;


import com.softserve.repository.BasicRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

@Repository
@Slf4j
public abstract class BasicRepositoryImpl<T extends Serializable, I extends Serializable> implements BasicRepository<T, I> {

//    protected final Class<T> basicClass;
//
////    @Autowired
////    protected SessionFactory sessionFactory;
////
////    @Autowired
////    public BasicRepositoryImpl() {
////        basicClass = (Class<T>) ((ParameterizedType) getClass()
////                .getGenericSuperclass())
////                .getActualTypeArguments()[0];
////    }


}
