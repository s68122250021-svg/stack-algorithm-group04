package utils;
 
import java.util.ArrayList;
import java.util.List;
 
public class Tokenizer {
 
    public static List<String> tokenize(String expression) throws IllegalArgumentException {
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
 
        return tokens;
    }
 
    public static boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/");
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
