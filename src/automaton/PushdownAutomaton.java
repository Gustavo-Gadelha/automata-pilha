package automaton;

import java.util.*;

import static automaton.Transition.EPSILON;

public class PushdownAutomaton extends Automaton {

    private final Map<State, Map<Character, Set<Transition>>> transitions;
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
                .computeIfAbsent(character, _ -> new HashSet<>())
                .add(new Transition(seeing, pushing, routes));
    }

    @Override
    public boolean accepts(String input) {
        Set<State> states = fetchEpsilons(Set.of(this.initial));

        for (char symbol : input.toCharArray()) {
            if (!alphabet.contains(symbol)) {
                throw new IllegalArgumentException("Simbolo não consta no alfabeto");
            }

            System.out.printf("Lendo o simbolo > %s%n", symbol);
            Set<State> futures = new HashSet<>();

            for (State each : states) {
                Map<Character, Set<Transition>> routes = this.transitions.get(each);
                if (routes == null) {
                    continue;
                }

                Set<Transition> transitions = routes.get(symbol);
                if (transitions == null) {
                    continue;
                }

                transitions.stream()
                        .filter(transition -> transition.check(this.stack))
                        .map(Transition::routes)
                        .forEach(futures::addAll);
            }

            states = fetchEpsilons(futures);
            System.out.printf("Stack %s%n", this.stack);
        }

        boolean stackCondition = stack.isEmpty() || !needEmptyStack;
        return states.stream().anyMatch(this.finals::contains) && stackCondition;
    }

    private Set<State> fetchEpsilons(Set<State> states) {
        Set<State> futures = new HashSet<>(states);
        Stack<State> visit = new Stack<>();
        visit.addAll(states);

        while (!visit.isEmpty()) {
            State current = visit.pop();

            Map<Character, Set<Transition>> routes = this.transitions.get(current);
            if (routes == null) {
                continue;
            }

            Set<Transition> epsilons = routes.get('ε');
            if (epsilons == null) {
                continue;
            }

            System.out.printf("Lendo transição epsilon > %s, %s%n", current, epsilons);
            epsilons.stream()
                    .filter(transition -> transition.check(this.stack))
                    .flatMap(transition -> transition.routes().stream())
                    .filter(state -> !futures.contains(state))
                    .filter(futures::add)
                    .forEach(visit::push);
        }

        return futures;
    }
}
