# Group 04 - Expression Processor

## Overview
โปรเจกต์งานกลุ่มวิชา **CSD2103 การออกแบบและวิเคราะห์อัลกอริทึม**
หัวข้อ **Expression Processor: Infix และ Postfix ด้วย Stack (Java)**

ระบบรับนิพจน์แบบ Infix ตรวจสอบความถูกต้อง และคำนวณผลลัพธ์ด้วย 2 วิธี

## Supported Input
- Operators: `+ - * /`
- Parentheses: `( )`
- Positive integers และจำนวนหลายหลัก
- Spaces ระหว่าง Token ได้
- ไม่บังคับ: ทศนิยม, Unary Minus, ตัวแปร, Function, `[]`, `{}`

## Algorithms
### Algorithm A — Infix → Postfix → Evaluate
1. Tokenize และ Validate
2. แปลง Infix เป็น Postfix ด้วย Operator Stack
3. Evaluate Postfix ด้วย Operand Stack

### Algorithm B — Direct Infix Evaluation
ใช้ Operand Stack และ Operator Stack ประเมิน Infix โดยตรง โดยไม่สร้าง Postfix เป็นผลลัพธ์กลาง

## Required Example
Input: `3 + 4 * 2 / (1 - 5)`

Postfix: `3 4 2 * 1 5 - / +`

Result: `1`

## Complexity
ทั้ง Algorithm A และ Algorithm B มี Time Complexity โดยรวม `O(n)` เมื่อ `n` คือจำนวน Token ที่ประมวลผล และ Auxiliary Space เป็น `O(n)`

## Project Structure
```text
stack-algorithm-group04/
├── src/
│   ├── Main.java
│   ├── BenchmarkExperiment.java
│   ├── algorithms/
│   ├── models/
│   └── utils/
├── test/
├── data/
├── results/
├── diagrams/
├── presentation/
├── report/
├── group-members.md
└── README.md
```

## Testing
Mandatory cases cover normal input, nested parentheses, unbalanced parentheses, invalid operator/operand order, division by zero, empty input, and the required example. Both algorithms are run and their results are compared.

## Performance Experiment
ทดลอง `n = 100, 1,000, 10,000, 50,000` โดยเฉลี่ยผล 5 รอบต่อขนาด ข้อมูลผลการทดลองอยู่ใน `results/Stack_Group04_Experiment.csv` และกราฟอยู่ใน `results/Stack_Group04_Performance_Graph.svg` สำหรับการทดลองนี้ `n` คือจำนวน Operand และ Actual tokens = `2n - 1`.

## How to Run
Compile Java source code แล้วรัน `Main` จากนั้นเลือก:
```text
1. Algorithm A (Infix -> Postfix -> Evaluate)
2. Algorithm B (Direct Infix Evaluate)
3. Trace Algorithm A
4. Trace Algorithm B
5. Run Mandatory Test Cases
0. Exit
```
