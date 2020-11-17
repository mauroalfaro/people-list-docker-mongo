package com.alfarosoft.peoplelist.service;

import com.alfarosoft.peoplelist.database.HibernateSessionFactory;
import com.alfarosoft.peoplelist.exception.PeopleListException;
import com.alfarosoft.peoplelist.model.Store;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static net.logstash.logback.argument.StructuredArguments.keyValue;

public class StoreService {
    private HibernateSessionFactory hibernateSessionFactory;
    private Session storeSession;
    private static final Logger LOG = LoggerFactory.getLogger(StoreService.class);

    public StoreService(HibernateSessionFactory hibernateSessionFactory) throws Exception {
        this.hibernateSessionFactory = hibernateSessionFactory;
        storeSession = hibernateSessionFactory.buildSession();
    }

    public Store getStore (String id){
        return retrieveStoreFromDatabase(id);
    }

    public List<Store> getStores() {
        storeSession.beginTransaction();
        List<Store> storeList = storeSession.createQuery("from Store", Store.class).list();
        storeSession.getTransaction().commit();
        LOG.info("Stores returned");
        return storeList;
    }

    public Store addStore (Store store){
        storeSession.beginTransaction();
        storeSession.save(store);
        storeSession.getTransaction().commit();
        LOG.info("Store created");
        return store;
    }

    public Store updateStore (String id, Store store){
        Store storeRetrieved = retrieveStoreFromDatabase(id);
        storeRetrieved.setStoreName(store.getStoreName());
        storeRetrieved.setAddress(store.getAddress());
        storeSession.update(storeRetrieved);
        storeSession.getTransaction().commit();
        LOG.info("Store updated");
        return storeRetrieved;
    }

    public void removeStore (String id){
        storeSession.delete(retrieveStoreFromDatabase(id));
        storeSession.getTransaction().commit();
        LOG.info("Store removed");
    }

    private Store retrieveStoreFromDatabase (String id){
        Store storeRetrieved;
        try {
            storeSession.beginTransaction();
            Query selectQuery = storeSession.createQuery("from Store WHERE Id=:paramId");
            selectQuery.setParameter("paramId", id);
            storeRetrieved = (Store) selectQuery.uniqueResult();
            LOG.info("Store retrieved", keyValue("storeRetrieved" , storeRetrieved));
            return storeRetrieved;
        } catch (EntityNotFoundException e){
            LOG.error("Store not found", keyValue("storeIdInError", id));
            throw new PeopleListException("Store with id " + id + " was not found", 404);
        }
    }
}
