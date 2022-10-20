package infrastructure.hibernate.model;

import jakarta.validation.constraints.NotNull;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "SEQUENCES")
public class SequenceDAO {
    @Id
    private String name;

    @NotNull
    private Long nextValue;

    public SequenceDAO() {}

    public SequenceDAO(String name) {
        this.name = name;
        this.nextValue = 0L;
    }

    public void increment() {
        nextValue++;
    }

    public Long getNextValue() {
        return nextValue;
    }
}
