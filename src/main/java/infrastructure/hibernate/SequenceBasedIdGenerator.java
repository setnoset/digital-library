package infrastructure.hibernate;

import infrastructure.hibernate.model.SequenceDAO;
import model.IdGenerator;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

class SequenceBasedIdGenerator implements IdGenerator<Long> {
    private EntityManagerFactory entityManagerFactory;
    private String sequenceName;

    SequenceBasedIdGenerator(EntityManagerFactory entityManagerFactory, String sequenceName) {
        this.entityManagerFactory = entityManagerFactory;
        this.sequenceName = sequenceName;
    }

    @Override
    public Long nextId() {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        SequenceDAO sequence = getSequence(em);
        Long id = sequence.getNextValue();
        sequence.increment();

        em.getTransaction().commit();
        em.close();

        return id;
    }

    private SequenceDAO getSequence(EntityManager em) {
        TypedQuery<SequenceDAO> query = em.createQuery(
                "select seq from SequenceDAO seq where seq.name = :name", SequenceDAO.class
        ).setParameter("name", sequenceName);

        try {
            return query.getSingleResult();
        } catch (NoResultException ex) {
            SequenceDAO sequence = new SequenceDAO(sequenceName);
            em.persist(sequence);
            return sequence;
        }
    }
}
