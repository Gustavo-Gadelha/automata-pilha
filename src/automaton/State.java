package automaton;

public class State {
    private final String name;

    public State(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
