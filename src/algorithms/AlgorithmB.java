package algorithms;

import models.ExpressionResult;
import models.OperationCounter;
import utils.Tokenizer;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class AlgorithmB {

    public ExpressionResult evaluate(String expression) {
        long startTime = System.nanoTime();
        OperationCounter counter = new OperationCounter();

        try {
            List<String> tokens = Tokenizer.tokenize(expression);
            double result = evaluateInfix(tokens, counter);
            long elapsedTime = System.nanoTime() - startTime;
            return ExpressionResult.success(result, "(ไม่ได้สร้าง Postfix ใน Algorithm B)", elapsedTime, counter);
        } catch (Exception e) {
            long elapsedTime = System.nanoTime() - startTime;
            String errorMessage = (e.getMessage() != null && !e.getMessage().isEmpty()) 
                    ? e.getMessage() 
                    : "รูปแบบนิพจน์ไม่ถูกต้อง";
            return ExpressionResult.error(errorMessage, elapsedTime, counter);
        }
    }

    public double evaluateInfix(List<String> tokens, OperationCounter counter) {
        Deque<Double> operandStack = new ArrayDeque<>();
        Deque<String> operatorStack = new ArrayDeque<>();

        for (String token : tokens) {
            if (counter != null) counter.incrementLoop();

            if (Tokenizer.isNumber(token)) {
                operandStack.push(Double.parseDouble(token));
                if (counter != null) counter.incrementPush();
            } else if (token.equals("(")) {
                operatorStack.push(token);
                if (counter != null) counter.incrementPush();
            } else if (token.equals(")")) {
                boolean foundOpen = false;
                while (!operatorStack.isEmpty()) {
                    if (counter != null) counter.incrementComparison();
                    String top = operatorStack.peek();
                    if (top.equals("(")) {
                        operatorStack.pop();
                        if (counter != null) counter.incrementPop();
                        foundOpen = true;
                        break;
                    }
                    applyTopOperator(operandStack, operatorStack, counter);
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
                        applyTopOperator(operandStack, operatorStack, counter);
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
            if (counter != null) counter.incrementComparison();
            String top = operatorStack.peek();
            if (top.equals("(")) {
                throw new IllegalArgumentException("วงเล็บไม่ครบคู่");
            }
            applyTopOperator(operandStack, operatorStack, counter);
        }

        if (operandStack.size() != 1) {
            throw new IllegalArgumentException("รูปแบบนิพจน์ไม่ถูกต้อง: ลำดับเครื่องหมายหรือตัวเลขไม่ถูกต้อง");
        }

        double finalResult = operandStack.pop();
        if (counter != null) counter.incrementPop();
        return finalResult;
    }

    private void applyTopOperator(Deque<Double> operandStack, Deque<String> operatorStack, OperationCounter counter) {
        if (operatorStack.isEmpty()) {
            throw new IllegalArgumentException("รูปแบบนิพจน์ไม่ถูกต้อง: เครื่องหมายไม่สมบูรณ์");
        }
        String op = operatorStack.pop();
        if (counter != null) counter.incrementPop();

        if (operandStack.size() < 2) {
            throw new IllegalArgumentException("รูปแบบนิพจน์ไม่ถูกต้อง: ตัวเลขไม่เพียงพอสำหรับเครื่องหมาย '" + op + "'");
        }

        double b = operandStack.pop();
        double a = operandStack.pop();
        if (counter != null) {
            counter.incrementPop();
            counter.incrementPop();
        }

        double result = calculate(a, op, b);
        operandStack.push(result);
        if (counter != null) counter.incrementPush();
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
        System.out.println("\n=== STEP-BY-STEP TRACE (Algorithm B) ===");
        try {
            List<String> tokens = Tokenizer.tokenize(expression);
            Deque<Double> operandStack = new ArrayDeque<>();
            Deque<String> operatorStack = new ArrayDeque<>();

            System.out.println("Token | Operand Stack | Operator Stack");
            System.out.println("---------------------------------------------------------------");

            for (String token : tokens) {
                if (Tokenizer.isNumber(token)) {
                    operandStack.push(Double.parseDouble(token));
                } else if (token.equals("(")) {
                    operatorStack.push(token);
                } else if (token.equals(")")) {
                    while (!operatorStack.isEmpty() && !operatorStack.peek().equals("(")) {
                        applyTopOperator(operandStack, operatorStack, null);
                    }
                    if (operatorStack.isEmpty()) throw new IllegalArgumentException("วงเล็บไม่ครบคู่");
                    operatorStack.pop();
                } else if (Tokenizer.isOperator(token)) {
                    while (!operatorStack.isEmpty() && !operatorStack.peek().equals("(")
                            && Tokenizer.priority(operatorStack.peek()) >= Tokenizer.priority(token)) {
                        applyTopOperator(operandStack, operatorStack, null);
                    }
                    operatorStack.push(token);
                }
                System.out.println(token + " | " + operandStack.toString() + " | " + operatorStack.toString());
            }

            while (!operatorStack.isEmpty()) {
                if (operatorStack.peek().equals("(")) throw new IllegalArgumentException("วงเล็บไม่ครบคู่");
                applyTopOperator(operandStack, operatorStack, null);
            }
            System.out.println("(End) | " + operandStack.toString() + " | " + operatorStack.toString());

            System.out.println("\nResult: " + operandStack.peek());
        } catch (Exception e) {
            System.out.println("Error Trace: " + e.getMessage());
        }
    }
}