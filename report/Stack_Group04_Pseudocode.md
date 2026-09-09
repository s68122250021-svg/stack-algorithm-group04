# Group 04 — Pseudocode
## Expression Processor: Infix และ Postfix

### Algorithm A — Infix → Postfix → Evaluate

INPUT: expression แบบ Infix
OUTPUT: Postfix expression และผลลัพธ์ หรือ Error

#### Part 1: InfixToPostfix
```text
ALGORITHM InfixToPostfix(expression)
    Create an empty operatorStack
    Create an empty postfixList
    tokens ← Tokenize(expression)

    FOR each token IN tokens
        IF token is a number
            Add token to postfixList
        ELSE IF token is "("
            Push token to operatorStack
        ELSE IF token is ")"
            WHILE operatorStack is not empty AND operatorStack.peek() is not "("
                Pop operator from operatorStack, add to postfixList
            END WHILE
            IF operatorStack is empty
                RETURN error "วงเล็บไม่ครบคู่"
            END IF
            Pop "(" from operatorStack
        ELSE IF token is an operator (+, -, *, /)
            WHILE operatorStack is not empty
                AND operatorStack.peek() is not "("
                AND Priority(operatorStack.peek()) >= Priority(token)
                Pop operator from operatorStack, add to postfixList
            END WHILE
            Push token to operatorStack
        ELSE
            RETURN error "token ไม่ถูกต้อง"
        END IF
    END FOR

    WHILE operatorStack is not empty
        IF operatorStack.peek() is "("
            RETURN error "วงเล็บไม่ครบคู่"
        END IF
        Pop operator from operatorStack, add to postfixList
    END WHILE

    RETURN postfixList
```

#### Part 2: EvaluatePostfix
```text
ALGORITHM EvaluatePostfix(postfixList)
    Create an empty operandStack
    FOR each token IN postfixList
        IF token is a number
            Push token to operandStack
        ELSE IF token is an operator
            IF operandStack has fewer than 2 elements
                RETURN error "นิพจน์ไม่ถูกต้อง"
            END IF
            b ← operandStack.pop()
            a ← operandStack.pop()
            IF token is "/" AND b = 0
                RETURN error "หารด้วยศูนย์"
            END IF
            result ← Calculate(a, token, b)
            Push result to operandStack
        END IF
    END FOR
    IF operandStack has exactly 1 element
        RETURN operandStack.pop()
    ELSE
        RETURN error "นิพจน์ไม่ถูกต้อง"
    END IF
```

### Algorithm B — Direct Infix Evaluation

INPUT: expression แบบ Infix
OUTPUT: ผลลัพธ์ หรือ Error

```text
ALGORITHM EvaluateInfixDirect(expression)
    Create an empty operandStack
    Create an empty operatorStack
    tokens ← Tokenize(expression)

    FOR each token IN tokens
        IF token is a number
            Push token to operandStack
        ELSE IF token is "("
            Push token to operatorStack
        ELSE IF token is ")"
            WHILE operatorStack is not empty AND operatorStack.peek() is not "("
                ApplyTopOperator(operandStack, operatorStack)
            END WHILE
            IF operatorStack is empty
                RETURN error "วงเล็บไม่ครบคู่"
            END IF
            Pop "(" from operatorStack
        ELSE IF token is an operator (+, -, *, /)
            WHILE operatorStack is not empty
                AND operatorStack.peek() is not "("
                AND Priority(operatorStack.peek()) >= Priority(token)
                ApplyTopOperator(operandStack, operatorStack)
            END WHILE
            Push token to operatorStack
        ELSE
            RETURN error "token ไม่ถูกต้อง"
        END IF
    END FOR

    WHILE operatorStack is not empty
        IF operatorStack.peek() is "("
            RETURN error "วงเล็บไม่ครบคู่"
        END IF
        ApplyTopOperator(operandStack, operatorStack)
    END WHILE

    IF operandStack has exactly 1 element
        RETURN operandStack.pop()
    ELSE
        RETURN error "นิพจน์ไม่ถูกต้อง"
    END IF
```

Input validation (empty expression, Operand/Operator order, balanced parentheses, unsupported token) is performed by `Tokenizer.validateTokens()` before evaluation.