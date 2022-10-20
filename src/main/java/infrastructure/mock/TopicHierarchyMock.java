package infrastructure.mock;

import model.Topic;
import model.TopicHierarchy;

import java.util.*;

public class TopicHierarchyMock extends TopicHierarchy {
    Map<Topic, Set<Topic>> directSubtopicMap = new HashMap<>();

    @Override
    protected Set<Topic> directSubtopics(Topic topic) {
        return directSubtopicMap.getOrDefault(topic, new HashSet<>());
    }

    @Override
    protected Optional<Topic> directSupertopic(Topic topic) {
        for (Topic t : directSubtopicMap.keySet())
            if (directSubtopics(t).contains(topic))
                return Optional.of(t);

        return Optional.empty();
    }

    @Override
    protected void addVerifiedSubtopicRelationship(Topic topic, Topic subtopic) {
        var subtopics = directSubtopics(topic);
        subtopics.add(subtopic);
        directSubtopicMap.putIfAbsent(topic, subtopics);
    }

}
