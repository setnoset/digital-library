package model;

public class SubtopicRelationship {
    private NamedTopic topic;
    private NamedTopic subtopic;

    private SubtopicRelationship(NamedTopic topic, NamedTopic subtopic) {
        this.topic = topic;
        this.subtopic = subtopic;
    }

    public NamedTopic getTopic() {
        return topic;
    }

    public NamedTopic getSubtopic() {
        return subtopic;
    }

    public static class Builder {
        public BuilderWithTopic topic(NamedTopic topic) {
            return new BuilderWithTopic(topic);
        }
    }

    public static class BuilderWithTopic {
        NamedTopic topic;

        private BuilderWithTopic(NamedTopic topic) {
            this.topic = topic;
        }

        public SubtopicRelationship hasSubtopic(NamedTopic subtopic) {
            return new SubtopicRelationship(topic, subtopic);
        }
    }
}
