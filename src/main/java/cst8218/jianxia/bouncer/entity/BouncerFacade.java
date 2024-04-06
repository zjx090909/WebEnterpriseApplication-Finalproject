/**
 * This package contains the entity classes for the Bouncer application.
 */
package cst8218.jianxia.bouncer.entity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * BouncerFacade class represents a Bouncer entity in the Bouncer application.
 * 
 * The Bouncer class defines the properties and behavior of a bouncer object, including its position, velocity, and game behavior constants.
 * 
 * @author zhangjianxia
 */
@Stateless
public class BouncerFacade extends AbstractFacade<Bouncer> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BouncerFacade() {
        super(Bouncer.class);
    }
    
}
