package model;

import java.util.HashSet;
import java.util.Set;

public class Topic {
    private final Long id;
    private final Set<Document> literature = new HashSet<>();
    public static final Topic EVERYTHING = new Topic(-1L);

    Topic(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Set<Topic> getDirectSubtopics(TopicHierarchy topicHierarchy) {
        return topicHierarchy.getDirectSubtopics(this);
    }

    public void addSubtopic(NamedTopic subtopic, TopicHierarchy topicHierarchy) {
        topicHierarchy.addSubtopicRelationship(this, subtopic);
    }

    public Set<Document> getBaseLiterature() {
        return literature;
    }

    public void addToBaseLiterature(Document document) {
        literature.add(document);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || !(obj instanceof NamedTopic))
            return false;

        Topic other = (Topic) obj;

        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
