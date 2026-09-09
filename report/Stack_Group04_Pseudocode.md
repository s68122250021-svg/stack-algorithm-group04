# Group 04 - Pseudocode

## Algorithm A - Infix -> Postfix -> Evaluate

**Input:** Infix expression containing positive integers, `+ - * /`, parentheses, and optional spaces.

**Output:** Postfix expression and final numeric result, or an error.

```text
ALGORITHM A(expression)
1. tokens <- Tokenize(expression)
2. ValidateTokens(tokens)
3. postfix <- InfixToPostfix(tokens)
4. result <- EvaluatePostfix(postfix)
5. RETURN (postfix, result)
```

### InfixToPostfix
```text
FUNCTION InfixToPostfix(tokens)
1. postfix <- empty list
2. operatorStack <- empty stack
3. FOR EACH token IN tokens
4.     IF token is number THEN
5.         append token to postfix
6.     ELSE IF token = '(' THEN
7.         push token to operatorStack
8.     ELSE IF token = ')' THEN
9.         WHILE operatorStack is not empty AND top != '('
10.            pop top and append it to postfix
11.        IF operatorStack is empty THEN ERROR: unbalanced parentheses
12.        pop '('
13.    ELSE IF token is operator THEN
14.        WHILE operatorStack is not empty AND top != '('
15.              AND priority(top) >= priority(token)
16.            pop top and append it to postfix
17.        push token to operatorStack
18.    ELSE
19.        ERROR: invalid token
20. END FOR
21. WHILE operatorStack is not empty
22.    IF top = '(' THEN ERROR: unbalanced parentheses
23.    pop top and append it to postfix
24. RETURN postfix
END FUNCTION
```

### EvaluatePostfix
```text
FUNCTION EvaluatePostfix(postfix)
1. operandStack <- empty stack
2. FOR EACH token IN postfix
3.     IF token is number THEN push token to operandStack
4.     ELSE IF token is operator THEN
5.         IF operandStack has fewer than 2 values THEN ERROR
6.         b <- pop operandStack
7.         a <- pop operandStack
8.         IF token = '/' AND b = 0 THEN ERROR: division by zero
9.         result <- Calculate(a, token, b)
10.        push result to operandStack
11. END FOR
12. IF operandStack does not contain exactly 1 value THEN ERROR
13. RETURN pop operandStack
END FUNCTION
```

## Algorithm B - Direct Infix Evaluation

**Input:** Same constraints as Algorithm A.

**Output:** Final numeric result, or an error.

```text
FUNCTION AlgorithmB(expression)
1. tokens <- Tokenize(expression)
2. ValidateTokens(tokens)
3. operandStack <- empty stack
4. operatorStack <- empty stack
5. FOR EACH token IN tokens
6.     IF token is number THEN push token to operandStack
7.     ELSE IF token = '(' THEN push token to operatorStack
8.     ELSE IF token = ')' THEN
9.         WHILE operatorStack is not empty AND top != '('
10.            ApplyTopOperator()
11.        IF operatorStack is empty THEN ERROR: unbalanced parentheses
12.        pop '('
13.    ELSE IF token is operator THEN
14.        WHILE operatorStack is not empty AND top != '('
15.              AND priority(top) >= priority(token)
16.            ApplyTopOperator()
17.        push token to operatorStack
18.    ELSE
19.        ERROR: invalid token
20. END FOR
21. WHILE operatorStack is not empty
22.    IF top = '(' THEN ERROR: unbalanced parentheses
23.    ApplyTopOperator()
24. IF operandStack does not contain exactly 1 value THEN ERROR
25. RETURN pop operandStack
END FUNCTION
```

### ApplyTopOperator
```text
FUNCTION ApplyTopOperator()
1. IF operatorStack is empty THEN ERROR
2. op <- pop operatorStack
3. IF operandStack has fewer than 2 values THEN ERROR
4. b <- pop operandStack
5. a <- pop operandStack
6. IF op = '/' AND b = 0 THEN ERROR: division by zero
7. result <- Calculate(a, op, b)
8. push result to operandStack
END FUNCTION
```

## Complexity
For valid input with `n` tokens, both algorithms are `O(n)` time because each token is scanned once and each operator can be pushed and popped only a bounded number of times. Auxiliary space is `O(n)`.
