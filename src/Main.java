import algorithms.AlgorithmA;
import algorithms.AlgorithmB;
import models.ExpressionResult;
 
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
                    String exprA = scanner.nextLine();
                    runAlgorithm("Algorithm A", algorithmA.evaluate(exprA));
                    break;
 
                case "2":
                    System.out.print("ป้อนนิพจน์ (Infix): ");
                    String exprB = scanner.nextLine();
                    runAlgorithm("Algorithm B", algorithmB.evaluate(exprB));
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
        System.out.println("Expression Processor: ");
        System.out.println("1. Algorithm A (Infix -> Postfix -> Evaluate)");
        System.out.println("2. Algorithm B (Direct Infix Evaluate)");
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
}
/*import algorithms.AlgorithmA;
import algorithms.AlgorithmB;
import models.ExpressionResult;
import java.util.Random;

public class BenchmarkExperiment {

    public static void main(String[] args) {
        int[] sizes = {100, 1000, 10000, 50000};
        int runs = 5;

        AlgorithmA algoA = new AlgorithmA();
        AlgorithmB algoB = new AlgorithmB();

        // กำหนดรูปแบบเส้นกรอบและช่องความกว้างของแต่ละ Column
        String lineSeparator = "+----------+-------------+-----------------+------------+------------+-------------+-----------------+------------+------------------+";
        String headerFormat  = "| %-8s | %-11s | %-15s | %-10s | %-10s | %-11s | %-15s | %-10s | %-16s |%n";
        String rowFormat     = "| %-8d | %-11s | %,15d | %,10d | %,10d | %,11d | %,15d | %,10d | %,16.2f |%n";

        System.out.println(lineSeparator);
        System.out.printf(headerFormat, 
            "Size (n)", "Algorithm", "Avg Time (ns)", "Push", "Pop", "Comparisons", "Loop Iter", "Data Moved", "Est. Memory(KB)");
        System.out.println(lineSeparator);

        for (int n : sizes) {
            String expr = generateExpression(n);

            // Test Algorithm A
            long totalTimeA = 0;
            long pushA = 0, popA = 0, compA = 0, loopA = 0, dataMovedA = 0;
            
            Runtime runtime = Runtime.getRuntime();
            runtime.gc();
            long memBeforeA = runtime.totalMemory() - runtime.freeMemory();

            for (int r = 0; r < runs; r++) {
                ExpressionResult res = algoA.evaluate(expr);
                totalTimeA += res.getElapsedTimeNanos();
                if (r == 0) {
                    pushA = res.getCounter().getPushCount();
                    popA = res.getCounter().getPopCount();
                    compA = res.getCounter().getComparisonCount();
                    loopA = res.getCounter().getLoopCount();
                    dataMovedA = n; // Algorithm A ย้ายข้อมูลลง Postfix List ขนาด n items
                }
            }
            long memAfterA = runtime.totalMemory() - runtime.freeMemory();
            double estMemA = Math.max(0, (memAfterA - memBeforeA) / 1024.0);

            System.out.printf(rowFormat, n, "Algorithm A", totalTimeA / runs, pushA, popA, compA, loopA, dataMovedA, estMemA);

            // Test Algorithm B
            long totalTimeB = 0;
            long pushB = 0, popB = 0, compB = 0, loopB = 0, dataMovedB = 0;

            runtime.gc();
            long memBeforeB = runtime.totalMemory() - runtime.freeMemory();

            for (int r = 0; r < runs; r++) {
                ExpressionResult res = algoB.evaluate(expr);
                totalTimeB += res.getElapsedTimeNanos();
                if (r == 0) {
                    pushB = res.getCounter().getPushCount();
                    popB = res.getCounter().getPopCount();
                    compB = res.getCounter().getComparisonCount();
                    loopB = res.getCounter().getLoopCount();
                    dataMovedB = 0; // Algorithm B ประมวลผลตรงผ่าน Stack
                }
            }
            long memAfterB = runtime.totalMemory() - runtime.freeMemory();
            double estMemB = Math.max(0, (memAfterB - memBeforeB) / 1024.0);

            System.out.printf(rowFormat, n, "Algorithm B", totalTimeB / runs, pushB, popB, compB, loopB, dataMovedB, estMemB);
            System.out.println(lineSeparator);
        }
    }

    // สุ่มสร้างนิพจน์ขนาด n token
    private static String generateExpression(int n) {
        StringBuilder sb = new StringBuilder("1");
        String[] ops = {"+", "-", "*", "/"};
        Random rand = new Random(42); // fixed seed for reproducibility
        for (int i = 1; i < n; i += 2) {
            sb.append(" ").append(ops[rand.nextInt(ops.length)]).append(" ").append(rand.nextInt(9) + 1);
        }
        return sb.toString();
    }
}*/