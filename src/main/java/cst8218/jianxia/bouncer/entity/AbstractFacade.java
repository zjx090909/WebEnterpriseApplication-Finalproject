/**
 * This package contains the entity classes for the Bouncer application.
 */
package cst8218.jianxia.bouncer.entity;

import java.util.List;
import javax.persistence.EntityManager;

/**
 * This class represents a facade for the Bouncer entity.
 * 
 * The BouncerFacade class provides CRUD operations for managing bouncer entities
 * in the database.
 * 
 * @author zhangjianxia
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;
    
    /**
     * Constructs a new AbstractFacade with the specified entity class.
     * 
     * @param entityClass : the entity managed by the facade
     */
    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();
    
    /**
     * create a new entity in the database
     * @param entity : to be created entity
     * @return : the created entity after persistence
     */
    public T create(T entity) {
        getEntityManager().persist(entity);
        return entity;
    }
    
    /**
     * edit/update an entity in the database
     * @param entity : the entity to be updated
     */
    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    /**
     * delete an entity in the database
     * @param entity : the entity to be deleted
     */
    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }
    
    /**
     * Find an entity in the database by its primary key(id).
     * 
     * @param id: entity id, also is the primary key
     * @return The found entity, or null if no entity with the given id exists
     */
    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }
    
    /**
     * Find all the entity in the database
     * @return list of all the entities
     */
    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }
    
    /**
     * Get the count of the entities in the database
     * @return count value
     */
    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
}
