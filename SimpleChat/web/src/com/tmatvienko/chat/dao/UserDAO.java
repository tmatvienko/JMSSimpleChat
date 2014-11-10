package com.tmatvienko.chat.dao;

import com.tmatvienko.chat.model.User;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by tmatvienko on 11/10/14.
 */
@Stateless
public class UserDAO {

    @PersistenceContext
    private EntityManager em;

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public User findByLogin(String login) {
        TypedQuery<User> query = em.createNamedQuery(User.Names.FIND_BY_NAME, User.class);
        query.setParameter(User.Parameters.LOGIN, login);
        List<User> users = query.getResultList();
        return users.isEmpty() ? null : users.get(0);
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public User findById(Long id) {
        return em.find(User.class, id);
    }

    public boolean delete(@NotNull long id) {
        Query query = em.createNamedQuery(User.Names.DELETE_BY_ID);
        query.setParameter(User.Parameters.ID, id);
        return query.executeUpdate() != 0;
    }

    public User create(User user) {
        em.persist(user);
        return user;
    }

    public User update(@NotNull @Valid User user) {
        em.merge(user);
        return user;
    }
}
