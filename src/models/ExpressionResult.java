package models;
 
public class ExpressionResult {
    // เก็บข้อมูลผลลัพธ์ที่ได้จากการประมวลผลนิพจน์
    private boolean success;
    private double value;
    private String postfix;
    private String errorMessage;
    private long elapsedTimeNanos;
    private OperationCounter counter;
 
    // สร้างผลลัพธ์กรณีประมวลผลสำเร็จ พร้อมเก็บค่าที่เกี่ยวข้อง
    public static ExpressionResult success(double value, String postfix, long elapsedTimeNanos, OperationCounter counter) {
        ExpressionResult r = new ExpressionResult();
        r.success = true;
        r.value = value;
        r.postfix = postfix;
        r.elapsedTimeNanos = elapsedTimeNanos;
        r.counter = counter;
        return r;
    }
 
    // สร้างผลลัพธ์กรณีเกิดข้อผิดพลาด พร้อมเก็บข้อความ Error
    public static ExpressionResult error(String message, long elapsedTimeNanos, OperationCounter counter) {
        ExpressionResult r = new ExpressionResult();
        r.success = false;
        r.errorMessage = message;
        r.elapsedTimeNanos = elapsedTimeNanos;
        r.counter = counter;
        return r;
    }
 
    // ตรวจสอบว่าการประมวลผลสำเร็จหรือไม่
    public boolean isSuccess() { return success; }

    // คืนค่าผลลัพธ์ที่คำนวณได้
    public double getValue() { return value; }

    // คืนค่า Postfix ที่สร้างขึ้น
    public String getPostfix() { return postfix; }

    // คืนค่าข้อความ Error
    public String getErrorMessage() { return errorMessage; }

    // คืนค่าเวลาที่ใช้ในการประมวลผล หน่วยนาโนวินาที
    public long getElapsedTimeNanos() { return elapsedTimeNanos; }

    // คืนค่า OperationCounter ที่ใช้เก็บจำนวนการทำงาน
    public OperationCounter getCounter() { return counter; }
}
