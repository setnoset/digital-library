package model;

public class TopicFactory {
    IdGenerator<Long> idGenerator;

    public TopicFactory(IdGenerator<Long> idGenerator) {
        this.idGenerator = idGenerator;
    }

    public NamedTopic createNamed(String name) {
        return new NamedTopic(idGenerator.nextId(), name);
    }
}
