package utils;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer {

    public static List<String> tokenize(String expression) throws IllegalArgumentException {
        if (expression == null || expression.trim().isEmpty()) {
            throw new IllegalArgumentException("นิพจน์ว่าง");
        }

        List<String> tokens = new ArrayList<>();
        int i = 0;
        int n = expression.length();

        while (i < n) {
            char c = expression.charAt(i);

            if (Character.isWhitespace(c)) {
                i++;
                continue;
            }

            if (Character.isDigit(c)) {
                StringBuilder num = new StringBuilder();
                while (i < n && Character.isDigit(expression.charAt(i))) {
                    num.append(expression.charAt(i));
                    i++;
                }
                tokens.add(num.toString());
                continue;
            }

            if (c == '+' || c == '-' || c == '*' || c == '/' || c == '(' || c == ')') {
                tokens.add(String.valueOf(c));
                i++;
                continue;
            }

            throw new IllegalArgumentException("พบตัวอักษรที่ไม่รองรับ: '" + c + "'");
        }

        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("นิพจน์ว่าง");
        }

        validateTokens(tokens);
        return tokens;
    }

    public static void validateTokens(List<String> tokens) throws IllegalArgumentException {
        if (tokens == null || tokens.isEmpty()) {
            throw new IllegalArgumentException("นิพจน์ว่าง");
        }

        int balance = 0;
        boolean expectOperand = true;

        for (String token : tokens) {
            if (isNumber(token)) {
                if (!expectOperand) {
                    throw new IllegalArgumentException("รูปแบบนิพจน์ไม่ถูกต้อง: ต้องมี Operator ระหว่างตัวเลข");
                }
                expectOperand = false;
                continue;
            }

            if (token.equals("(")) {
                if (!expectOperand) {
                    throw new IllegalArgumentException("รูปแบบนิพจน์ไม่ถูกต้อง: ไม่สามารถใส่ '(' หลังตัวเลขหรือ ')'");
                }
                balance++;
                expectOperand = true;
                continue;
            }

            if (token.equals(")")) {
                if (expectOperand) {
                    throw new IllegalArgumentException("รูปแบบนิพจน์ไม่ถูกต้อง: ')' อยู่ผิดตำแหน่ง หรือวงเล็บว่าง");
                }
                balance--;
                if (balance < 0) {
                    throw new IllegalArgumentException("วงเล็บไม่ครบคู่: พบ ')' เกิน");
                }
                expectOperand = false;
                continue;
            }

            if (isOperator(token)) {
                if (expectOperand) {
                    throw new IllegalArgumentException("รูปแบบนิพจน์ไม่ถูกต้อง: Operator อยู่ผิดตำแหน่ง '" + token + "'");
                }
                expectOperand = true;
                continue;
            }

            throw new IllegalArgumentException("token ไม่ถูกต้อง: " + token);
        }

        if (balance != 0) {
            throw new IllegalArgumentException("วงเล็บไม่ครบคู่");
        }
        if (expectOperand) {
            throw new IllegalArgumentException("รูปแบบนิพจน์ไม่ถูกต้อง: นิพจน์ลงท้ายด้วย Operator");
        }
    }

    public static boolean isOperator(String token) {
        return token != null &&
                (token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/"));
    }

    public static boolean isNumber(String token) {
        if (token == null || token.isEmpty()) return false;
        for (char c : token.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

    public static int priority(String operator) {
        switch (operator) {
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
            default:
                return 0;
        }
    }
}
