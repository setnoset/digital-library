package model;

public class NamedTopic extends Topic {
    private final String name;

    NamedTopic(Long id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "NamedTopic{" +
                "name='" + name + '\'' +
                '}';
    }
}
