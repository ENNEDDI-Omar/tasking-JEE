package org.tasking.ejb;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.tasking.domain.entities.User;

import java.util.List;

@Stateless
public class UserEJB
{
    @PersistenceContext(unitName = "TaskingPU")
    private EntityManager em;

     public User createUser(User user)
    {
     em.persist(user);
     return user;
     }

     public User findUserById(Long id)
     {
         return em.find(User.class, id);
     }

    public User findUserByUsername(String username) {
        return em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", username)
                .getSingleResult();
    }

     public List<User> findAllUsers()
     {
         return em.createQuery("SELECT u FROM User u", User.class).getResultList();
     }

     public User updateUser(User user)
     {
            return em.merge(user);
     }

     public void deleteUser(Long Id)
     {
            User user = em.find(User.class, Id);
            if (user != null)
            {
                em.remove(user);
            }
     }
}
