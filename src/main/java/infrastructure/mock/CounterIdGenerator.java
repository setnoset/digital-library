package infrastructure.mock;

import model.IdGenerator;

public class CounterIdGenerator implements IdGenerator<Long> {
    Long id = 0L;

    @Override
    public Long nextId() {
        return id++;
    }
}
