import algorithms.AlgorithmA;
import algorithms.AlgorithmB;
import models.ExpressionResult;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AlgorithmA algorithmA = new AlgorithmA();
        AlgorithmB algorithmB = new AlgorithmB();

        boolean running = true;

        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.print("ป้อนนิพจน์ (Infix): ");
                    runAlgorithm("Algorithm A", algorithmA.evaluate(scanner.nextLine()));
                    break;

                case "2":
                    System.out.print("ป้อนนิพจน์ (Infix): ");
                    runAlgorithm("Algorithm B", algorithmB.evaluate(scanner.nextLine()));
                    break;

                case "3":
                    System.out.print("ป้อนนิพจน์สำหรับ Trace (Algorithm A): ");
                    algorithmA.traceEvaluate(scanner.nextLine());
                    break;

                case "4":
                    System.out.print("ป้อนนิพจน์สำหรับ Trace (Algorithm B): ");
                    algorithmB.traceEvaluate(scanner.nextLine());
                    break;

                case "5":
                    runMandatoryTests(algorithmA, algorithmB);
                    break;

                case "0":
                    running = false;
                    System.out.println("ออกจากโปรแกรม");
                    break;

                default:
                    System.out.println("กรุณาเลือกเมนูที่ถูกต้อง");
            }
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("Expression Processor");
        System.out.println("1. Algorithm A (Infix -> Postfix -> Evaluate)");
        System.out.println("2. Algorithm B (Direct Infix Evaluate)");
        System.out.println("3. Trace Algorithm A (แสดง Stack ทีละ Token)");
        System.out.println("4. Trace Algorithm B (แสดง Stack ทีละ Token)");
        System.out.println("5. Run Mandatory Test Cases");
        System.out.println("0. ออกจากโปรแกรม");
        System.out.print("เลือกเมนู: ");
    }

    private static void runAlgorithm(String label, ExpressionResult result) {
        System.out.println("--- " + label + " ---");
        if (result.isSuccess()) {
            System.out.println("Postfix: " + result.getPostfix());
            System.out.println("ผลลัพธ์: " + result.getValue());
        } else {
            System.out.println("เกิดข้อผิดพลาด: " + result.getErrorMessage());
        }
        System.out.println("เวลาที่ใช้: " + result.getElapsedTimeNanos() + " ns");
        System.out.println("จำนวน Operation: " + result.getCounter());
    }

    private static void runMandatoryTests(AlgorithmA algorithmA, AlgorithmB algorithmB) {
        Map<String, String> tests = new LinkedHashMap<>();
        tests.put("3 + 4 * 2", "11");
        tests.put("(3 + 4) * 2", "14");
        tests.put("((8 + 2) * 5)", "50");
        tests.put("(3 + 4", "ERROR");
        tests.put("3 + 4)", "ERROR");
        tests.put("3 + * 4", "ERROR");
        tests.put("10 / (5 - 5)", "ERROR");
        tests.put("   ", "ERROR");
        tests.put("3 + 4 * 2 / (1 - 5)", "1");

        System.out.println("\n=== MANDATORY TEST CASES ===");
        System.out.printf("%-32s | %-16s | %-16s%n", "Input", "Algorithm A", "Algorithm B");
        System.out.println("--------------------------------------------------------------------------");

        for (Map.Entry<String, String> entry : tests.entrySet()) {
            ExpressionResult a = algorithmA.evaluate(entry.getKey());
            ExpressionResult b = algorithmB.evaluate(entry.getKey());
            String aOut = formatTestResult(a);
            String bOut = formatTestResult(b);
            System.out.printf("%-32s | %-16s | %-16s%n", display(entry.getKey()), aOut, bOut);

            if (!aOut.equals(bOut)) {
                System.out.println("WARNING: A และ B ให้ผลต่างกันใน Input นี้");
            }
        }
    }

    private static String formatTestResult(ExpressionResult result) {
        if (!result.isSuccess()) return "ERROR";
        if (Math.abs(result.getValue() - Math.rint(result.getValue())) < 1e-9) {
            return String.valueOf((long) Math.rint(result.getValue()));
        }
        return String.format("%.6f", result.getValue());
    }

    private static String display(String s) {
        return s.isEmpty() ? "<empty>" : s.replace("\t", "\\t");
    }
}
