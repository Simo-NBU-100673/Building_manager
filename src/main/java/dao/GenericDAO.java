package dao;


import org.hibernate.Session;
import org.hibernate.Transaction;
import session.SessionFactoryUtil;

import java.util.Collection;

//We are using the template design pattern
abstract class GenericDAO<T> {

    private final Class<T> type = getEntityClass();

    private void ensureNotNull(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Can not use entity: null");
        }
    }

    private void ensureNotNull(Collection<T> entities) {
        if (entities == null) {
            throw new IllegalArgumentException("Can not use Collection<null> as argument");
        }
    }

    private void ensurePositive(long id) {
        if (id < 0) {
            throw new IllegalArgumentException("Can not use negative id");
        }

        if (id == 0) {
            throw new IllegalArgumentException("Can not use id: 0");
        }
    }

    private void checkIfFound(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("No match found in " + type.getSimpleName() + " table");
        }
    }

    //create
    public void create(T entity) {
        ensureNotNull(entity);

        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
        }
    }

    //Create many new entities in the persistent layer(DB)
    public void create(Collection<T> entities) {
        ensureNotNull(entities);

        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            entities.stream().forEach(session::save);
            transaction.commit();
        }

    }

    //readById
    public T getById(long id) {
        ensurePositive(id);

        T entity;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            entity = session.get(type, id);
            transaction.commit();
        }
        checkIfFound(entity);

        return entity;
    }

    //read ALL
    public Collection<T> getAllEntities() {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT a FROM " + type.getSimpleName() + " a", type).getResultList();
        }
    }

    //update
    public void update(T entity) {
        ensureNotNull(entity);

        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(entity);
            transaction.commit();
        }
    }

    //delete
    public void delete(T entity) {
        ensureNotNull(entity);

        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        }
    }

    //delete
    public void deleteById(long id) {
        ensurePositive(id);

        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(getById(id));
            transaction.commit();
        }
    }

    protected abstract Class<T> getEntityClass();
}

