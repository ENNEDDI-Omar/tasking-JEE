package org.tasking.ejb;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.tasking.domain.entities.Tag;

import java.util.List;

@Stateless
public class TagEJB {
    @PersistenceContext(unitName = "TaskingPU")
    private EntityManager em;

    @PostConstruct
    public void init() {
        if (findTagByName("tag1") == null) {
            Tag tag1 = new Tag("tag1");
            tag1.setColor("#FF0000");
            createTag(tag1);
        }
        if (findTagByName("tag2") == null) {
            Tag tag2 = new Tag("tag2");
            tag2.setColor("#0000FF");
            createTag(tag2);
        }
    }

    public Tag createTag(Tag tag) {
        if (tag.getColor() == null) {
            tag.setColor("#000000");
        }
        em.persist(tag);
        return tag;
    }

    public Tag findTagById(Long id) {
        return em.find(Tag.class, id);
    }

    public Tag findTagByName(String name) {
        try {
            return em.createQuery("SELECT t FROM Tag t WHERE t.name = :name", Tag.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Tag> findAllTags()
    {
        return em.createQuery("SELECT t FROM Tag t", Tag.class)
                .getResultList();
    }

    public Tag updateTag(Tag tag) {
        return em.merge(tag);
    }

    public void deleteTag(Long id) {
        Tag tag = findTagById(id);
        if (tag != null) {
            em.remove(tag);
        }
    }
}