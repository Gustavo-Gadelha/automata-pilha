package automaton;

import java.util.Set;
import java.util.Stack;

public record Transition(Character seeing, Character pushing, Set<State> routes) {

    public static final char EPSILON = 'ε';

    public boolean check(Stack<Character> stack) {
        // ε -> ε | Epsilon-Epsilon transition
        if (seeing == EPSILON && pushing == EPSILON) {
            return true;
        }

        // ε -> a | Pushing a character
        if (seeing == EPSILON) {
            stack.push(pushing);
            return true;
        }

        // All operations bellow require a non-empty stack
        if (stack.isEmpty()) {
            return false;
        }

        Character top = stack.peek();

        // a -> ε | Popping a character
        if (seeing == top && pushing == EPSILON) {
            stack.pop();
            return true;
        }

        // a -> b | Replacing a character
        if (seeing == top && pushing != top) {
            stack.pop();
            stack.push(pushing);
            return true;
        }

        // a -> a | Replace with itself, do nothing
        return seeing == pushing;
    }

    @Override
    public String toString() {
        return "%s -> %s para %s".formatted(seeing, pushing, routes);
    }
}
