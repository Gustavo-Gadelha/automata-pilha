# Autômato de Pilha para a Linguagem L = { 0^n 1^n | n >= 0 }

Este projeto implementa um Autômato de Pilha (AP) em Java para reconhecer a linguagem \( L = \{ 0^n 1^n \mid n \geq
0 \} \), que consiste em cadeias com um número igual de zeros seguidos por um número igual de uns.

## Como Funciona

O autômato de pilha opera da seguinte maneira:

1. Quando encontra um `0`, empilha um símbolo `0` na pilha.
2. Quando encontra um `1`, desempilha um símbolo da pilha.
3. Se a pilha estiver vazia após o processamento de todos os caracteres, não houver mais símbolos a serem desempilhados
   e o automaton parou em um estado final, a entrada é aceita.
4. Caso contrário, a entrada é rejeitada.
