package model;

public class DocumentFactory {
    IdGenerator<Long> idGenerator;

    public DocumentFactory(IdGenerator<Long> idGenerator) {
        this.idGenerator = idGenerator;
    }

    public Document create(String title) {
        return new Document(idGenerator.nextId(), title);
    }
}
