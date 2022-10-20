package infrastructure.hibernate;

import model.IdGenerator;

import jakarta.persistence.EntityManagerFactory;

public class IdGeneratorFactory {
    EntityManagerFactory entityManagerFactory;

    public IdGeneratorFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public IdGenerator<Long> createDocumentIdGenerator() {
        return new SequenceBasedIdGenerator(entityManagerFactory, "DOCUMENT_ID_SEQ");
    }

    public IdGenerator<Long> createTopicIdGenerator() {
        return new SequenceBasedIdGenerator(entityManagerFactory, "TOPIC_ID_SEQ");
    }
}
