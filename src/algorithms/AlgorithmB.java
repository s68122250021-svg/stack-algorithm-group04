package algorithms;

import models.ExpressionResult;
import models.OperationCounter;
import utils.Tokenizer;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlgorithmB {

    // รับนิพจน์แบบ Infix แล้วส่งต่อไปยังเมธอดที่รองรับตัวแปร
    public ExpressionResult evaluate(String expression) {
        return evaluate(expression, new HashMap<>());
    }

    // ประมวลผลนิพจน์โดยคำนวณจาก Infix โดยตรง ไม่สร้าง Postfix
    public ExpressionResult evaluate(String expression, Map<String, Double> variables) {
        long startTime = System.nanoTime();
        OperationCounter counter = new OperationCounter();

        try {
            // แยกนิพจน์ออกเป็น Token แล้วคำนวณโดยตรง
            List<String> tokens = Tokenizer.tokenize(expression);
            double result = evaluateInfix(tokens, counter, variables);
            long elapsedTime = System.nanoTime() - startTime;
            return ExpressionResult.success(result, "(ไม่ได้สร้าง Postfix ใน Algorithm B)", elapsedTime, counter);
        } catch (Exception e) {
            // ถ้าเกิดข้อผิดพลาด ให้คืนค่าเป็น Error พร้อมเวลาที่ใช้
            long elapsedTime = System.nanoTime() - startTime;
            String errorMessage = (e.getMessage() != null && !e.getMessage().isEmpty())
                    ? e.getMessage() : "รูปแบบนิพจน์ไม่ถูกต้อง";
            return ExpressionResult.error(errorMessage, elapsedTime, counter);
        }
    }

    // คำนวณ Infix โดยใช้ Operand Stack และ Operator Stack
    public double evaluateInfix(List<String> tokens, OperationCounter counter) {
        return evaluateInfix(tokens, counter, new HashMap<>());
    }

    // อ่าน Token ทีละตัวและคำนวณตามลำดับความสำคัญของ Operator
    public double evaluateInfix(List<String> tokens, OperationCounter counter, Map<String, Double> variables) {
        Deque<Double> operandStack = new ArrayDeque<>();
        Deque<String> operatorStack = new ArrayDeque<>();

        // ประมวลผล Token ทั้งหมดจาก Infix
        for (String token : tokens) {
            if (counter != null) counter.incrementLoop();

            if (Tokenizer.isOperand(token)) {
                double value;
                if (Tokenizer.isNumber(token)) value = Double.parseDouble(token);
                else {
                    if (variables == null || !variables.containsKey(token)) {
                        throw new IllegalArgumentException("ไม่พบค่าของตัวแปร '" + token + "'");
                    }
                    value = variables.get(token);
                }
                operandStack.push(value);
                if (counter != null) counter.incrementPush();
            } else if (token.equals("(")) {
                // เก็บ '(' ไว้ใน Operator Stack เพื่อกำหนดขอบเขตของวงเล็บ
                operatorStack.push(token);
                if (counter != null) counter.incrementPush();
            } else if (token.equals(")")) {
                boolean foundOpen = false;
                // เจอ ')' ให้คำนวณ Operator จนกว่าจะเจอ '('
                while (!operatorStack.isEmpty()) {
                    if (counter != null) counter.incrementComparison();
                    String top = operatorStack.peek();
                    if (top.equals("(")) {
                        operatorStack.pop();
                        if (counter != null) counter.incrementPop();
                        foundOpen = true;
                        break;
                    }
                    applyTopOperator(operandStack, operatorStack, counter, variables);
                }
                if (!foundOpen) throw new IllegalArgumentException("วงเล็บไม่ครบคู่");
            } else if (Tokenizer.isOperator(token)) {
                // คำนวณ Operator เดิมก่อน ถ้ามีความสำคัญมากกว่าหรือเท่ากับ Operator ใหม่
                while (!operatorStack.isEmpty()) {
                    if (counter != null) counter.incrementComparison();
                    String top = operatorStack.peek();
                    if (top.equals("(")) break;
                    if (Tokenizer.priority(top) >= Tokenizer.priority(token)) {
                        applyTopOperator(operandStack, operatorStack, counter, variables);
                    } else break;
                }
                operatorStack.push(token);
                if (counter != null) counter.incrementPush();
            } else {
                throw new IllegalArgumentException("token ไม่ถูกต้อง: " + token);
            }
        }

        // เมื่ออ่าน Token ครบแล้ว คำนวณ Operator ที่เหลือใน Stack
        while (!operatorStack.isEmpty()) {
            if (counter != null) counter.incrementComparison();
            if (operatorStack.peek().equals("(")) throw new IllegalArgumentException("วงเล็บไม่ครบคู่");
            applyTopOperator(operandStack, operatorStack, counter, variables);
        }

        // ผลลัพธ์ที่ถูกต้องต้องเหลือค่าเดียวใน Operand Stack
        if (operandStack.size() != 1) {
            throw new IllegalArgumentException("รูปแบบนิพจน์ไม่ถูกต้อง: ลำดับเครื่องหมายหรือตัวเลขไม่ถูกต้อง");
        }
        double finalResult = operandStack.pop();
        if (counter != null) counter.incrementPop();
        return finalResult;
    }

    // นำ Operator บนสุดมาคำนวณกับ Operand 2 ค่า
    private void applyTopOperator(Deque<Double> operandStack, Deque<String> operatorStack,
                                  OperationCounter counter, Map<String, Double> variables) {
        if (operatorStack.isEmpty()) throw new IllegalArgumentException("รูปแบบนิพจน์ไม่ถูกต้อง: เครื่องหมายไม่สมบูรณ์");
        String op = operatorStack.pop();
        if (counter != null) counter.incrementPop();
        if (operandStack.size() < 2) throw new IllegalArgumentException("รูปแบบนิพจน์ไม่ถูกต้อง: Operand ไม่เพียงพอสำหรับเครื่องหมาย '" + op + "'");

        double b = operandStack.pop();
        double a = operandStack.pop();
        if (counter != null) { counter.incrementPop(); counter.incrementPop(); }
        operandStack.push(calculate(a, op, b));
        if (counter != null) counter.incrementPush();
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

    // แสดงขั้นตอนการทำงานของ Algorithm B และสถานะของ Stack แต่ละ Token
    public void traceEvaluate(String expression) {
        System.out.println("\n=== STEP-BY-STEP TRACE (Algorithm B) ===");
        try {
            List<String> tokens = Tokenizer.tokenize(expression);
            Deque<String> operandStack = new ArrayDeque<>();
            Deque<String> operatorStack = new ArrayDeque<>();
            System.out.println("Token | Operand Stack | Operator Stack");
            System.out.println("---------------------------------------------------------------");
            // แสดงสถานะของ Operand Stack และ Operator Stack หลังแต่ละ Token
            for (String token : tokens) {
                if (Tokenizer.isOperand(token)) operandStack.push(token);
                else if (token.equals("(")) operatorStack.push(token);
                else if (token.equals(")")) {
                    while (!operatorStack.isEmpty() && !operatorStack.peek().equals("(")) operatorStack.pop();
                    if (operatorStack.isEmpty()) throw new IllegalArgumentException("วงเล็บไม่ครบคู่");
                    operatorStack.pop();
                } else if (Tokenizer.isOperator(token)) {
                    while (!operatorStack.isEmpty() && !operatorStack.peek().equals("(")
                            && Tokenizer.priority(operatorStack.peek()) >= Tokenizer.priority(token)) {
                        operatorStack.pop();
                    }
                    operatorStack.push(token);
                }
                System.out.println(token + " | " + operandStack + " | " + operatorStack);
            }
            System.out.println("(End) | " + operandStack + " | " + operatorStack);
            System.out.println("หมายเหตุ: Trace แสดงตัวแปรได้ แต่การคำนวณตัวแปรต้องระบุค่าในเมนูหลัก");
        } catch (Exception e) {
            System.out.println("Error Trace: " + e.getMessage());
        }
    }
}
