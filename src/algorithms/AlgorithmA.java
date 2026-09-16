package algorithms;

import models.ExpressionResult;
import models.OperationCounter;
import utils.Tokenizer;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlgorithmA {

    // รับนิพจน์แบบ Infix แล้วส่งต่อไปยังเมธอดที่รองรับตัวแปร
    public ExpressionResult evaluate(String expression) {
        return evaluate(expression, new HashMap<>());
    }

    // ประมวลผลนิพจน์: Tokenize -> Infix เป็น Postfix -> คำนวณผลลัพธ์
    public ExpressionResult evaluate(String expression, Map<String, Double> variables) {
        long startTime = System.nanoTime();
        OperationCounter counter = new OperationCounter();

        try {
            // แยกนิพจน์ออกเป็น Token
            List<String> tokens = Tokenizer.tokenize(expression);
            // แปลง Infix เป็น Postfix โดยใช้ Stack
            List<String> postfix = infixToPostfix(tokens, counter);
            // คำนวณ Postfix ด้วย Operand Stack
            double result = evaluatePostfix(postfix, counter, variables);
            String postfixStr = String.join(" ", postfix);
            long elapsedTime = System.nanoTime() - startTime;
            return ExpressionResult.success(result, postfixStr, elapsedTime, counter);
        } catch (Exception e) {
            // ถ้าเกิดข้อผิดพลาด ให้คืนค่าเป็น Error พร้อมเวลาที่ใช้
            long elapsedTime = System.nanoTime() - startTime;
            String errorMessage = (e.getMessage() != null && !e.getMessage().isEmpty())
                    ? e.getMessage() : "รูปแบบนิพจน์ไม่ถูกต้อง";
            return ExpressionResult.error(errorMessage, elapsedTime, counter);
        }
    }

    // แปลงนิพจน์จาก Infix เป็น Postfix โดยใช้ Operator Stack
    public List<String> infixToPostfix(List<String> tokens, OperationCounter counter) {
        List<String> postfix = new ArrayList<>();
        Deque<String> operatorStack = new ArrayDeque<>();

        // อ่าน Token ทีละตัวและจัดลำดับตามความสำคัญของ Operator
        for (String token : tokens) {
            if (counter != null) counter.incrementLoop();

            if (Tokenizer.isOperand(token)) {
                postfix.add(token);
            } else if (token.equals("(")) {
                operatorStack.push(token);
                if (counter != null) counter.incrementPush();
            } else if (token.equals(")")) {
                boolean foundOpen = false;
                // เจอ ')' ให้ Pop Operator จนกว่าจะเจอ '('
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
                if (!foundOpen) throw new IllegalArgumentException("วงเล็บไม่ครบคู่");
            } else if (Tokenizer.isOperator(token)) {
                // Pop Operator ที่มีความสำคัญมากกว่าหรือเท่ากันก่อน Push ตัวใหม่
                while (!operatorStack.isEmpty()) {
                    if (counter != null) counter.incrementComparison();
                    String top = operatorStack.peek();
                    if (top.equals("(")) break;
                    if (Tokenizer.priority(top) >= Tokenizer.priority(token)) {
                        postfix.add(operatorStack.pop());
                        if (counter != null) counter.incrementPop();
                    } else break;
                }
                operatorStack.push(token);
                if (counter != null) counter.incrementPush();
            } else {
                throw new IllegalArgumentException("token ไม่ถูกต้อง: " + token);
            }
        }

        // เมื่ออ่าน Token ครบแล้ว นำ Operator ที่เหลือใน Stack ไปต่อท้าย Postfix
        while (!operatorStack.isEmpty()) {
            String top = operatorStack.pop();
            if (counter != null) counter.incrementPop();
            if (top.equals("(")) throw new IllegalArgumentException("วงเล็บไม่ครบคู่");
            postfix.add(top);
        }
        return postfix;
    }

    // คำนวณ Postfix โดยใช้ค่าเริ่มต้นที่ไม่มีตัวแปร
    public double evaluatePostfix(List<String> postfix, OperationCounter counter) {
        return evaluatePostfix(postfix, counter, new HashMap<>());
    }

    // คำนวณนิพจน์ Postfix ด้วย Operand Stack
    public double evaluatePostfix(List<String> postfix, OperationCounter counter, Map<String, Double> variables) {
        Deque<Double> operandStack = new ArrayDeque<>();

        // อ่าน Postfix ทีละ Token แล้วคำนวณตามลำดับ
        for (String token : postfix) {
            if (counter != null) counter.incrementLoop();

            if (Tokenizer.isNumber(token) || Tokenizer.isVariable(token)) {
                double value;
                if (Tokenizer.isNumber(token)) {
                    value = Double.parseDouble(token);
                } else {
                    if (variables == null || !variables.containsKey(token)) {
                        throw new IllegalArgumentException("ไม่พบค่าของตัวแปร '" + token + "'");
                    }
                    value = variables.get(token);
                }
                operandStack.push(value);
                if (counter != null) counter.incrementPush();
            } else if (Tokenizer.isOperator(token)) {
                // Operator ต้องมี Operand อย่างน้อย 2 ค่าเพื่อคำนวณ
                if (operandStack.size() < 2) {
                    throw new IllegalArgumentException("รูปแบบนิพจน์ไม่ถูกต้อง: ตัวเลขไม่เพียงพอสำหรับเครื่องหมาย '" + token + "'");
                }
                double b = operandStack.pop();
                double a = operandStack.pop();
                if (counter != null) { counter.incrementPop(); counter.incrementPop(); }
                operandStack.push(calculate(a, token, b));
                if (counter != null) counter.incrementPush();
            }
        }

        // ผลลัพธ์ที่ถูกต้องต้องเหลือค่าเดียวใน Stack
        if (operandStack.size() != 1) {
            throw new IllegalArgumentException("รูปแบบนิพจน์ไม่ถูกต้อง: ลำดับเครื่องหมายหรือตัวเลขไม่ถูกต้อง");
        }
        double finalResult = operandStack.pop();
        if (counter != null) counter.incrementPop();
        return finalResult;
    }

    // คำนวณทางคณิตศาสตร์ตาม Operator ที่ได้รับ
    private double calculate(double a, String op, double b) {
        switch (op) {
            case "+": return a + b;
            case "-": return a - b;
            case "*": return a * b;
            case "/":
                // ป้องกันการหารด้วยศูนย์
                if (b == 0.0) throw new ArithmeticException("หารด้วยศูนย์");
                return a / b;
            default: throw new IllegalArgumentException("operator ไม่ถูกต้อง: " + op);
        }
    }

    // แสดงขั้นตอนการแปลง Infix เป็น Postfix พร้อมสถานะของ Stack แต่ละ Token
    public void traceEvaluate(String expression) {
        System.out.println("\n=== STEP-BY-STEP TRACE (Algorithm A) ===");
        try {
            List<String> tokens = Tokenizer.tokenize(expression);
            Deque<String> operatorStack = new ArrayDeque<>();
            List<String> postfixList = new ArrayList<>();
            System.out.println("\n--- Step 1: Infix to Postfix Conversion ---");
            System.out.println("Token | Operator Stack | Postfix List");
            System.out.println("---------------------------------------------------------------");

            // ประมวลผลและแสดง Stack/Postfix หลังอ่านแต่ละ Token
            for (String token : tokens) {
                if (Tokenizer.isOperand(token)) postfixList.add(token);
                else if (token.equals("(")) operatorStack.push(token);
                else if (token.equals(")")) {
                    while (!operatorStack.isEmpty() && !operatorStack.peek().equals("(")) postfixList.add(operatorStack.pop());
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

            System.out.println("\nPostfix: " + String.join(" ", postfixList));
            System.out.println("หมายเหตุ: Trace รองรับตัวแปรสำหรับการแปลง Postfix; การคำนวณตัวแปรต้องระบุค่าในเมนูหลัก");
        } catch (Exception e) {
            System.out.println("Error Trace: " + e.getMessage());
        }
    }
}
