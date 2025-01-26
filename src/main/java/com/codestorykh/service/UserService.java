package com.codestorykh.service;

import com.codestorykh.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {


    @PersistenceContext
    private EntityManager entityManager;

    public User findUserByEmail(String email) {
        String jpql = "SELECT u FROM User u WHERE u.email = :email";
        return entityManager.createQuery(jpql, User.class)
                .setParameter("email", email)
                .getSingleResultOrNull();
    }

    public String getFullNameByEmail(String email) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<String> query = cb.createQuery(String.class);
        Root<User> root = query.from(User.class);

        // condition
        Predicate emailCondition = cb.equal(root.get("email"), email);
        Predicate phoneNumberCondition = cb.isNotNull(root.get("phoneNumber"));

        // combine conditions
        Predicate conditions = cb.and(emailCondition, phoneNumberCondition);

        query.select(cb.concat(root.get("firstName"), cb.concat(" ", root.get("lastName"))))
                .where(conditions);

        return entityManager.createQuery(query).getSingleResultOrNull();
    }

    public List<String> getUsers(){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<String> query = cb.createQuery(String.class);

        Root<User> root = query.from(User.class);

        Expression<String> replace = cb.function(
                "REPLACE",
                String.class,
                root.get("firstName"),
                cb.literal("o"), cb.literal("a")
        );

        Expression<String> left = cb.substring(
                root.get("firstName"), 1, 2);

        Expression<String> right = cb.substring(
                root.get("firstName"),
                cb.diff(cb.length(root.get("firstName")), 1)
        );

        //query.select(replace);
        //query.select(left);
        query.select(right);


        return entityManager.createQuery(query).getResultList();
    }
}
