import automaton.PushdownAutomaton;
import automaton.State;

import java.util.Set;

public class Main {
    public static void main(String[] args) {
        State q0 = new State("q0");
        State q1 = new State("q1");
        State q2 = new State("q2");
        State q3 = new State("q3");

        Set<Character> alphabet = Set.of('0', '1', 'ε');
        Set<State> states = Set.of(q0, q1, q2, q3);
        Set<State> finals = Set.of(q0, q3);

        PushdownAutomaton automaton = new PushdownAutomaton(alphabet, states, q0, finals, true);
        automaton.makeTransition(q0, 'ε', 'ε', 'Z', Set.of(q1));
        automaton.makeTransition(q1, '0', 'ε', '0', Set.of(q1));
        automaton.makeTransition(q1, '1', '0', 'ε', Set.of(q2));
        automaton.makeTransition(q2, '1', '0', 'ε', Set.of(q2));
        automaton.makeTransition(q2, 'ε', 'Z', 'ε', Set.of(q3));

        String input = "000111";
        if (automaton.accepts(input)) {
            System.out.println("Aceita");
        } else {
            System.out.println("Rejeita");
        }
    }
}
