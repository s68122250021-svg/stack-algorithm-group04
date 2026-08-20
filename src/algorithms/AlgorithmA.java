package algorithms;

import models.ExpressionResult;
import models.OperationCounter;
import utils.Tokenizer;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class AlgorithmA {

    public ExpressionResult evaluate(String expression) {
        long startTime = System.nanoTime();
        OperationCounter counter = new OperationCounter();

        try {
            List<String> tokens = Tokenizer.tokenize(expression);
            List<String> postfix = infixToPostfix(tokens, counter);
            double result = evaluatePostfix(postfix, counter);
            String postfixStr = String.join(" ", postfix);
            long elapsedTime = System.nanoTime() - startTime;
            return ExpressionResult.success(result, postfixStr, elapsedTime, counter);
        } catch (Exception e) {
            long elapsedTime = System.nanoTime() - startTime;
            String errorMessage = (e.getMessage() != null && !e.getMessage().isEmpty()) 
                    ? e.getMessage() 
                    : "รูปแบบนิพจน์ไม่ถูกต้อง";
            return ExpressionResult.error(errorMessage, elapsedTime, counter);
        }
    }

    public List<String> infixToPostfix(List<String> tokens, OperationCounter counter) {
        List<String> postfix = new ArrayList<>();
        Deque<String> operatorStack = new ArrayDeque<>();

        for (String token : tokens) {
            if (counter != null) counter.incrementLoop();

            if (Tokenizer.isNumber(token)) {
                postfix.add(token);
            } else if (token.equals("(")) {
                operatorStack.push(token);
                if (counter != null) counter.incrementPush();
            } else if (token.equals(")")) {
                boolean foundOpen = false;
                while (!operatorStack.isEmpty()) {
                    if (counter != null) counter.incrementComparison();
                    String top = operatorStack.pop();
                    if (counter != null) counter.incrementPop();

                    if (top.equals("(")) {
                        foundOpen = true;
                        break;
                    }
                    postfix.add(top);
                }
                if (!foundOpen) {
                    throw new IllegalArgumentException("วงเล็บไม่ครบคู่");
                }
            } else if (Tokenizer.isOperator(token)) {
                while (!operatorStack.isEmpty()) {
                    if (counter != null) counter.incrementComparison();
                    String top = operatorStack.peek();
                    if (top.equals("(")) break;

                    if (Tokenizer.priority(top) >= Tokenizer.priority(token)) {
                        postfix.add(operatorStack.pop());
                        if (counter != null) counter.incrementPop();
                    } else {
                        break;
                    }
                }
                operatorStack.push(token);
                if (counter != null) counter.incrementPush();
            } else {
                throw new IllegalArgumentException("token ไม่ถูกต้อง: " + token);
            }
        }

        while (!operatorStack.isEmpty()) {
            String top = operatorStack.pop();
            if (counter != null) counter.incrementPop();
            if (top.equals("(")) {
                throw new IllegalArgumentException("วงเล็บไม่ครบคู่");
            }
            postfix.add(top);
        }

        return postfix;
    }

    public double evaluatePostfix(List<String> postfix, OperationCounter counter) {
        Deque<Double> operandStack = new ArrayDeque<>();

        for (String token : postfix) {
            if (counter != null) counter.incrementLoop();

            if (Tokenizer.isNumber(token)) {
                operandStack.push(Double.parseDouble(token));
                if (counter != null) counter.incrementPush();
            } else if (Tokenizer.isOperator(token)) {
                if (operandStack.size() < 2) {
                    throw new IllegalArgumentException("รูปแบบนิพจน์ไม่ถูกต้อง: ตัวเลขไม่เพียงพอสำหรับเครื่องหมาย '" + token + "'");
                }

                double b = operandStack.pop();
                double a = operandStack.pop();
                if (counter != null) {
                    counter.incrementPop();
                    counter.incrementPop();
                }

                double result = calculate(a, token, b);
                operandStack.push(result);
                if (counter != null) counter.incrementPush();
            }
        }

        if (operandStack.size() != 1) {
            throw new IllegalArgumentException("รูปแบบนิพจน์ไม่ถูกต้อง: ลำดับเครื่องหมายหรือตัวเลขไม่ถูกต้อง");
        }

        double finalResult = operandStack.pop();
        if (counter != null) counter.incrementPop();
        return finalResult;
    }

    private double calculate(double a, String op, double b) {
        switch (op) {
            case "+": return a + b;
            case "-": return a - b;
            case "*": return a * b;
            case "/":
                if (b == 0.0) {
                    throw new ArithmeticException("หารด้วยศูนย์");
                }
                return a / b;
            default:
                throw new IllegalArgumentException("operator ไม่ถูกต้อง: " + op);
        }
    }

    public void traceEvaluate(String expression) {
        System.out.println("\n=== STEP-BY-STEP TRACE (Algorithm A) ===");
        try {
            List<String> tokens = Tokenizer.tokenize(expression);
            Deque<String> operatorStack = new ArrayDeque<>();
            List<String> postfixList = new ArrayList<>();

            System.out.println("\n--- Step 1: Infix to Postfix Conversion ---");
            System.out.println("Token | Operator Stack | Postfix List");
            System.out.println("---------------------------------------------------------------");

            for (String token : tokens) {
                if (Tokenizer.isNumber(token)) {
                    postfixList.add(token);
                } else if (token.equals("(")) {
                    operatorStack.push(token);
                } else if (token.equals(")")) {
                    while (!operatorStack.isEmpty() && !operatorStack.peek().equals("(")) {
                        postfixList.add(operatorStack.pop());
                    }
                    if (operatorStack.isEmpty()) throw new IllegalArgumentException("วงเล็บไม่ครบคู่");
                    operatorStack.pop();
                } else if (Tokenizer.isOperator(token)) {
                    while (!operatorStack.isEmpty() && !operatorStack.peek().equals("(")
                            && Tokenizer.priority(operatorStack.peek()) >= Tokenizer.priority(token)) {
                        postfixList.add(operatorStack.pop());
                    }
                    operatorStack.push(token);
                }
                System.out.println(token + " | " + operatorStack.toString() + " | " + String.join(" ", postfixList));
            }

            while (!operatorStack.isEmpty()) {
                if (operatorStack.peek().equals("(")) throw new IllegalArgumentException("วงเล็บไม่ครบคู่");
                postfixList.add(operatorStack.pop());
            }
            System.out.println("(End) | " + operatorStack.toString() + " | " + String.join(" ", postfixList));

            System.out.println("\n--- Step 2: Postfix Evaluation Trace ---");
            System.out.println("Token | Operand Stack");
            System.out.println("---------------------------------------------------------------");

            Deque<Double> operandStack = new ArrayDeque<>();
            for (String token : postfixList) {
                if (Tokenizer.isNumber(token)) {
                    operandStack.push(Double.parseDouble(token));
                } else if (Tokenizer.isOperator(token)) {
                    if (operandStack.size() < 2) {
                        throw new IllegalArgumentException("ตัวเลขไม่เพียงพอสำหรับเครื่องหมาย '" + token + "'");
                    }
                    double b = operandStack.pop();
                    double a = operandStack.pop();
                    operandStack.push(calculate(a, token, b));
                }
                System.out.println(token + " | " + operandStack.toString());
            }

            System.out.println("\nResult: " + operandStack.peek());
        } catch (Exception e) {
            System.out.println("Error Trace: " + e.getMessage());
        }
    }
}