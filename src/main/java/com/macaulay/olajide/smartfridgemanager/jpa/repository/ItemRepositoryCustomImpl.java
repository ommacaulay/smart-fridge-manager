package com.macaulay.olajide.smartfridgemanager.jpa.repository;

import com.macaulay.olajide.smartfridgemanager.jpa.model.Item;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class ItemRepositoryCustomImpl implements ItemRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;


    @Override
    public List<Item> findNotForgottenItemsLessOrEqualToFillFactor(Double fillFactor) {
        Query query = entityManager.createNativeQuery("SELECT i.* FROM item as i " +
                "join type t on t.id = i.type_id " +
                "WHERE t.total_fill_factor <= ? " +
                "AND t.is_forgotten = 0", Item.class);
        query.setParameter(1, fillFactor);
        return query.getResultList();
    }
}
