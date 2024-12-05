package automaton;

import java.util.Set;

public abstract class Automaton {
    protected final Set<Character> alphabet;
    protected final Set<State> states;
    protected final Set<State> finals;
    protected final State initial;

    public Automaton(Set<Character> alphabet, Set<State> states, State initial, Set<State> finals) {
        this.alphabet = alphabet;
        this.states = states;
        this.initial = initial;
        this.finals = finals;
    }

    public abstract boolean accepts(String input);
}
