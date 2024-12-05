package automaton;

import java.util.*;

import static automaton.Transition.EPSILON;

public class PushdownAutomaton extends Automaton {

    private final Map<State, Map<Character, Transition>> transitions;
    private final Stack<Character> stack;
    private final boolean needEmptyStack;

    public PushdownAutomaton(Set<Character> alphabet, Set<State> states, State initial, Set<State> finals, boolean needEmptyStack) {
        super(alphabet, states, initial, finals);
        this.transitions = new HashMap<>();
        this.stack = new Stack<>();
        this.needEmptyStack = needEmptyStack;

        if (!alphabet.contains(EPSILON)) {
            this.alphabet.add(EPSILON);
        }
    }

    public void makeTransition(State start, Character character, Character seeing, Character pushing, Set<State> routes) {
        this.transitions
                .computeIfAbsent(start, _ -> new HashMap<>())
                .computeIfAbsent(character, _ -> new Transition(seeing, pushing, routes));
    }

    @Override
    public boolean accepts(String input) {
        Set<State> states = fetchEpsilons(Set.of(this.initial));

        for (char symbol : input.toCharArray()) {
            if (!alphabet.contains(symbol)) {
                throw new IllegalArgumentException("Simbolo não consta no alfabeto");
            }

            System.out.println("Lendo o simbolo -> " + symbol);
            Set<State> futures = new HashSet<>();

            for (State each : states) {
                Map<Character, Transition> transitions = this.transitions.get(each);
                if (transitions == null) {
                    continue;
                }

                Transition transition = transitions.get(symbol);
                if (transition == null) {
                    continue;
                }

                if (transition.check(this.stack)) {
                    futures.addAll(transition.routes());
                }
            }

            states = fetchEpsilons(futures);
            System.out.println("Stack " + this.stack);
        }

        boolean stackCondition = stack.isEmpty() || !needEmptyStack;
        return states.stream().anyMatch(this.finals::contains) && stackCondition;
    }

    private Set<State> fetchEpsilons(Set<State> states) {
        Set<State> futures = new HashSet<>(states);
        Stack<State> stack = new Stack<>();
        stack.addAll(states);

        while (!stack.isEmpty()) {
            State current = stack.pop();

            Map<Character, Transition> transitions = this.transitions.get(current);
            if (transitions == null) {
                continue;
            }

            Transition epsilon = transitions.get('ε');
            if (epsilon == null || !epsilon.check(this.stack)) {
                continue;
            }

            for (State each : epsilon.routes()) {
                System.out.println("Transição epsilon -> " + epsilon);
                if (!futures.contains(each)) {
                    futures.add(each);
                    stack.push(each);
                }
            }
        }

        return futures;
    }
}
