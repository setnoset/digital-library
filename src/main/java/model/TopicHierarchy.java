package model;

import java.util.Optional;
import java.util.Set;

public abstract class TopicHierarchy {
    public Set<Topic> getDirectSubtopics(Topic topic) {
        Set<Topic> subtopics = directSubtopics(topic);
        validateDirectSubtopics(topic, subtopics);

        return subtopics;
    }

    public void addSubtopicRelationship(SubtopicRelationship subtopicRelationship) {
        addSubtopicRelationship(subtopicRelationship.getTopic(), subtopicRelationship.getSubtopic());
    }

    public void addSubtopicRelationship(Topic topic, Topic subtopic) {
        checkNotNull(topic, subtopic);
        checkTopicNotEqualSubtopic(topic, subtopic);
        checkNoCyclesCreated(topic, subtopic);
        checkNoOtherDirectSupertopic(topic, subtopic);

        addVerifiedSubtopicRelationship(topic, subtopic);

        assert directSubtopics(topic).contains(subtopic);
        assert directSupertopic(subtopic).orElseThrow().equals(topic);
    }

    private void validateDirectSubtopics(Topic topic, Set<Topic> directSubtopics) {
        for (Topic subtopic : directSubtopics)
            if (!directSupertopic(subtopic).stream().anyMatch(t -> t.equals(topic)))
                throw new IllegalStateException(
                        "topic '%s' is not a direct supertopic of '%s'"
                        .formatted(topic, subtopic)
                );
    }

    private void checkNotNull(Object... objects) {
        for (Object obj : objects)
            if (obj == null)
                throw new NullPointerException("parameters must not be null");
    }

    private void checkTopicNotEqualSubtopic(Topic topic, Topic subtopic) {
        if (topic.equals(subtopic))
            throw new IllegalGraphModificationException("topic cannot be subtopic of itself");
    }

    private void checkNoCyclesCreated(Topic topic, Topic subtopic) {
        if (isSubtopic(topic, subtopic))
            throw new IllegalGraphModificationException(
                    "topic '%1$s' cannot be added as subtopic of '%2$s' because '%1$s' is already a supertopic of '%2$s'"
                    .formatted(subtopic, topic)
            );
    }

    private void checkNoOtherDirectSupertopic(Topic topic, Topic subtopic) {
        if (directSupertopic(subtopic).isPresent() && !directSubtopics(topic).contains(subtopic))
            throw new IllegalGraphModificationException(
                    "topic '%1$s' cannot be added as subtopic of '%2$s' because '%1$s' already has a direct supertopic"
                    .formatted(subtopic, topic)
            );
    }

    private boolean isSubtopic(Topic t1, Topic t2) {
        for (Topic t : directSubtopics(t2))
            if (isSubtopic(t1, t))
                return true;

        return false;
    }

    protected abstract Set<Topic> directSubtopics(Topic topic);

    protected abstract Optional<Topic> directSupertopic(Topic topic);

    protected abstract void addVerifiedSubtopicRelationship(Topic topic, Topic subtopic);
}
