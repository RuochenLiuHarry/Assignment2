/*
 * Copyright (c), Eclipse Foundation, Inc. and its licensors.
 *
 * All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v1.0, which is available at
 * https://www.eclipse.org/org/documents/edl-v10.php
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */
package cst8218.ruochen.slider.business;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

/**
 * Web assignment 1 
 * studentNumber:041088466
 * @author ruochenliu 
 */
// File: cst8218.alice.slider.business.AbstractFacade.java
/**
 * Abstract facade providing generic CRUD operations for entities.
 * @param <T> the type of the entity
 */

public abstract class AbstractFacade<T> {
    private Class<T> entityClass;
/**
     * Constructor to set the entity class.
     * @param entityClass the class of the entity
     */
    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();
    /**
     * Create a new entity.
     * @param entity the entity to create
     */
    public void create(T entity) {
        getEntityManager().persist(entity);
    }
    /**
     * Edit an existing entity.
     * @param entity the entity to edit
     */
    public void edit(T entity) {
        getEntityManager().merge(entity);
    }
    /**
     * Remove an entity.
     * @param entity the entity to remove
     */
    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }
    /**
     * Find an entity by its primary key.
     * @param id the primary key
     * @return the found entity
     */
    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }
    /**
     * Retrieve all entities.
     * @return list of all entities
     */
    public List<T> findAll() {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

}
