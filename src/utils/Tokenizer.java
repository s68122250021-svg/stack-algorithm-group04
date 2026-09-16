package utils;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer {

    // แยกนิพจน์ออกเป็น Token เช่น ตัวเลข ตัวแปร Operator และวงเล็บ
    public static List<String> tokenize(String expression) throws IllegalArgumentException {
        if (expression == null || expression.trim().isEmpty()) {
            throw new IllegalArgumentException("นิพจน์ว่าง");
        }

        List<String> tokens = new ArrayList<>();
        int i = 0;
        int n = expression.length();

        // อ่านนิพจน์ทีละตัวอักษรแล้วสร้าง Token
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

            // รองรับตัวแปรตัวอักษร 1 ตัว เช่น a, b, c
            if (Character.isLetter(c)) {
                tokens.add(String.valueOf(c));
                i++;
                continue;
            }

            // รองรับ Operator และวงเล็บที่ใช้ในนิพจน์
            if (c == '+' || c == '-' || c == '*' || c == '/' || c == '(' || c == ')') {
                tokens.add(String.valueOf(c));
                i++;
                continue;
            }

            throw new IllegalArgumentException("พบตัวอักษร/สัญลักษณ์ที่ไม่รองรับ: '" + c + "'");
        }

        if (tokens.isEmpty()) {
            throw new IllegalArgumentException("นิพจน์ว่าง");
        }

        // ตรวจสอบว่า Token ที่ได้เรียงอยู่ในรูปแบบนิพจน์ที่ถูกต้อง
        validateTokens(tokens);
        return tokens;
    }

    // ตรวจสอบลำดับ Token เช่น Operand, Operator และวงเล็บ
    public static void validateTokens(List<String> tokens) throws IllegalArgumentException {
        if (tokens == null || tokens.isEmpty()) {
            throw new IllegalArgumentException("นิพจน์ว่าง");
        }

        int balance = 0;
        boolean expectOperand = true;

        // ตรวจสอบ Token ทีละตัวเพื่อหานิพจน์ที่ผิดรูปแบบ
        for (String token : tokens) {
            if (isOperand(token)) {
                if (!expectOperand) {
                    throw new IllegalArgumentException("รูปแบบนิพจน์ไม่ถูกต้อง: ต้องมี Operator ระหว่าง Operand");
                }
                expectOperand = false;
                continue;
            }

            if (token.equals("(")) {
                if (!expectOperand) {
                    throw new IllegalArgumentException("รูปแบบนิพจน์ไม่ถูกต้อง: ไม่สามารถใส่ '(' หลัง Operand หรือ ')'");
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

        // ตรวจสอบวงเล็บและตรวจสอบว่าไม่ได้จบด้วย Operator
        if (balance != 0) {
            throw new IllegalArgumentException("วงเล็บไม่ครบคู่");
        }
        if (expectOperand) {
            throw new IllegalArgumentException("รูปแบบนิพจน์ไม่ถูกต้อง: นิพจน์ลงท้ายด้วย Operator");
        }
    }

    // ตรวจสอบว่า Token เป็น Operator หรือไม่
    public static boolean isOperator(String token) {
        return token != null &&
                (token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/"));
    }

    // ตรวจสอบว่า Token เป็นตัวเลขหรือไม่
    public static boolean isNumber(String token) {
        if (token == null || token.isEmpty()) return false;
        for (char c : token.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

    // ตรวจสอบว่า Token เป็นตัวแปร 1 ตัวอักษรหรือไม่
    public static boolean isVariable(String token) {
        return token != null && token.length() == 1 && Character.isLetter(token.charAt(0));
    }

    // ตรวจสอบว่า Token เป็น Operand ซึ่งอาจเป็นตัวเลขหรือตัวแปร
    public static boolean isOperand(String token) {
        return isNumber(token) || isVariable(token);
    }

    // คืนค่าลำดับความสำคัญของ Operator สำหรับการแปลง Infix เป็น Postfix
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
