# Group 04 - Expression Processor

## Overview

โปรเจกต์นี้เป็นงานกลุ่มวิชา **CSD2103 การออกแบบและวิเคราะห์อัลกอริทึม**
หัวข้อ **Expression Processor: Infix และ Postfix ด้วย Stack (Java)**

ระบบรองรับการรับนิพจน์คณิตศาสตร์แบบ Infix ตรวจสอบความถูกต้อง แปลงเป็น Postfix และคำนวณผลลัพธ์

## Supported Operators

ระบบรองรับ:

- `+`
- `-`
- `*`
- `/`
- วงเล็บ `(` `)`

รองรับจำนวนเต็มบวกหลายหลักและช่องว่างระหว่าง Token

ไม่รองรับ:

- จำนวนทศนิยม
- Unary Minus
- ตัวแปร
- Function ทางคณิตศาสตร์

## Algorithms

### Algorithm A - Infix to Postfix แล้ว Evaluate

1. Tokenize นิพจน์
2. ตรวจสอบรูปแบบ Operand / Operator / Parentheses
3. แปลง Infix เป็น Postfix โดยใช้ Operator Stack และ Priority
4. Evaluate Postfix โดยใช้ Operand Stack
5. คืนผลลัพธ์หรือแจ้ง Error

### Algorithm B - Direct Infix Evaluation

ใช้ `Operand Stack` และ `Operator Stack` ประมวลผล Infix โดยตรง โดยไม่ต้องสร้าง Postfix ก่อน

## Example

Input:

```text
3 + 4 * 2 / (1 - 5)
```

Postfix:

```text
3 4 2 * 1 5 - / +
```

Result:

```text
1
```

## Complexity

ทั้ง Algorithm A และ Algorithm B มี Time Complexity และ Space Complexity เป็น `O(n)` เมื่อ `n` คือจำนวน Token ของนิพจน์

## Project Structure

```text
src/
├── Main.java
├── BenchmarkExperiment.java
├── algorithms/
├── models/
└── utils/

test/
data/
results/
diagrams/
presentation/
report/
```

## Testing

โปรเจกต์มี Test Cases สำหรับกรณีปกติ กรณีขอบเขต นิพจน์ว่าง Input ไม่ถูกต้อง วงเล็บไม่ครบ และการหารด้วยศูนย์ รวมถึงการทดสอบทั้ง Algorithm A และ Algorithm B

## Performance Experiment

ทดลองเปรียบเทียบ Algorithm A และ Algorithm B ที่ขนาดข้อมูล 100, 1,000, 10,000 และ 50,000 โดยแต่ละขนาดทดลอง 5 รอบและใช้ค่าเฉลี่ยในการเปรียบเทียบ

ผลการทดลองอยู่ในโฟลเดอร์ `results/`

## How to Run

Compile Source Code ด้วย Java แล้วรัน `Main`

จากนั้นเลือก:

```text
1. Algorithm A (Infix -> Postfix -> Evaluate)
2. Algorithm B (Direct Infix Evaluate)
3. Trace Algorithm A
4. Trace Algorithm B
5. Run Mandatory Test Cases
0. Exit
```

## Group

Group 04 - Expression Processor

วิชา CSD2103 การออกแบบและวิเคราะห์อัลกอริทึม
