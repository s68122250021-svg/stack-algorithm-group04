package models;
 
public class OperationCounter {
    // เก็บจำนวนการทำงานแต่ละประเภทที่เกิดขึ้นระหว่างประมวลผล
    private long pushCount;
    private long popCount;
    private long comparisonCount;
    private long loopCount;

    // เพิ่มจำนวนครั้งที่มีการ Push ข้อมูลเข้า Stack
    public void incrementPush() {
        pushCount++;
    }
 
    // เพิ่มจำนวนครั้งที่มีการ Pop ข้อมูลออกจาก Stack
    public void incrementPop() {
        popCount++;
    }
 
    // เพิ่มจำนวนครั้งที่มีการเปรียบเทียบเงื่อนไข
    public void incrementComparison() {
        comparisonCount++;
    }

    // เพิ่มจำนวนรอบการทำงานของ Loop
    public void incrementLoop() {
        loopCount++;
    }
 
    // คืนค่าจำนวน Push ที่นับไว้
    public long getPushCount() {
        return pushCount;
    }
 
    // คืนค่าจำนวน Pop ที่นับไว้
    public long getPopCount() {
        return popCount;
    }
 
    // คืนค่าจำนวน Comparison ที่นับไว้
    public long getComparisonCount() {
        return comparisonCount;
    }

    // คืนค่าจำนวนรอบ Loop ที่นับไว้
    public long getLoopCount() {
        return loopCount;
    }
 
    // แปลงค่าที่นับไว้ให้อยู่ในรูปแบบข้อความที่อ่านง่าย
    @Override
    public String toString() {
        return "Push=" + pushCount + ", Pop=" + popCount + ", Comparisons=" + comparisonCount + ", Loops: " + loopCount;
    }
}
