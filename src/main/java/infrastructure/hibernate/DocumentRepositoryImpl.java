package infrastructure.hibernate;

import infrastructure.hibernate.model.DocumentDAO;
import model.Document;
import model.DocumentRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class DocumentRepositoryImpl implements DocumentRepository {
    private EntityManagerFactory entityManagerFactory;

    public DocumentRepositoryImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void saveDocument(Document document) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        DocumentDAO documentDAO = new DocumentDAO(document.getId(), document.getTitle());
        em.merge(documentDAO);

        em.getTransaction().commit();
        em.close();
    }

    @Override
    public Optional<Document> findDocumentById(Long id) {
        EntityManager em = entityManagerFactory.createEntityManager();

        TypedQuery<DocumentDAO> query = em.createQuery(
                "select d from DocumentDAO d where d.id = :id", DocumentDAO.class
        ).setParameter("id", id);

        try {
            DocumentDAO documentDAO = query.getSingleResult();
            return Optional.of(new Document(documentDAO.getId(), documentDAO.getTitle()));
        } catch (NoResultException ex) {
            return Optional.empty();
        } finally {
            em.close();
        }
    }

    @Override
    public Set<Document> findAllDocumentsByTitle(String title) {
        EntityManager em = entityManagerFactory.createEntityManager();

        TypedQuery<DocumentDAO> query = em.createQuery(
                "select d from DocumentDAO d where d.title = :title", DocumentDAO.class
        ).setParameter("title", title);

        Set<Document> documents = query.getResultStream().map(d -> new Document(d.getId(), d.getTitle())).collect(Collectors.toSet());

        em.close();
        return documents;
    }
}
