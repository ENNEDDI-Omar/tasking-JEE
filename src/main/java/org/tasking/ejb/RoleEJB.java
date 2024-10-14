package org.tasking.ejb;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.tasking.domain.entities.Role;

@Stateless
public class RoleEJB
{
    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    public void init()
    {
        if (findRoleByName("USER") == null) {
            createRole("USER");
        }
        if (findRoleByName("MANAGER") == null) {
            createRole("MANAGER");
        }
    }

    public Role findRoleByName(String name)
    {
        try {
            return em.createQuery("SELECT r FROM Role r WHERE r.name = :name", Role.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Role createRole(String name)
    {
        Role role = new Role(name);
        em.persist(role);
        return role;
    }
}
