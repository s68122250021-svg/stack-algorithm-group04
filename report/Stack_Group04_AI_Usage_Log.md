# Group 04 - Generative AI Usage Log

## งานที่ใช้ Generative AI ช่วย
- วิเคราะห์ Input / Output / Constraints / Edge Cases ของ Group 4
- ออกแบบ Algorithm A และ Algorithm B และเขียน Pseudocode
- จัดทำ Step-by-step และสถานะ Stack
- อธิบาย Correctness: Precondition, Invariant, Postcondition, Termination
- วิเคราะห์ Time / Space Complexity
- ตรวจ Test Cases และโครงสร้าง Java
- ตรวจความสอดคล้องระหว่าง Report, Presentation, CSV และ Source Code

## ตัวอย่าง Prompt
> ออกแบบ Expression Processor สำหรับ Group 4 โดยใช้ Stack รองรับ Infix/Postfix, + - * /, parentheses และ positive integers พร้อม Algorithm อย่างน้อย 2 วิธี, Pseudocode, Step-by-step, Correctness, Big-O, Java และ Test Cases

## ข้อผิดพลาด/ประเด็นที่ตรวจพบระหว่างตรวจงาน
1. ค่า `n` ของ Benchmark ต้องเป็น 100, 1,000, 10,000 และ 50,000 ไม่ใช่ 101, 1001, 10001, 50001
2. จำนวน Actual tokens ของ expression รูปแบบที่ใช้ทดลองเป็น `2n - 1` เมื่อ `n` คือจำนวน Operand
3. Presentation เดิมใช้ค่า benchmark คนละชุดกับ Report/CSV
4. Report/Presentation อ้างชื่อ CSV เก่า `Stack_Group04_Performance_Results.csv` ทั้งที่ไฟล์ปัจจุบันคือ `Stack_Group04_Experiment.csv`
5. ไฟล์ input ที่ชื่อ `input_100.txt`, `input_1000.txt`, ... เดิมมีจำนวน Operand เพียงครึ่งหนึ่งโดยประมาณ จึงไม่ตรงกับชื่อ

## ส่วนที่แก้ไข
- ใช้ชุด benchmark เดียวกันทั้ง Report/CSV/Presentation
- ระบุ `n` เป็นจำนวน Operand และแยก Actual tokens
- ปรับชื่อแหล่งข้อมูลเป็น `Stack_Group04_Experiment.csv`
- เตรียม input ขนาด 100, 1,000, 10,000 และ 50,000 Operand ให้สอดคล้องกับชื่อไฟล์
- เพิ่ม/ตรวจ Pseudocode ให้สอดคล้องกับ Java implementation

## วิธีตรวจสอบความถูกต้อง
- ตรวจ Source Code จริงของ Algorithm A/B และ Tokenizer
- ตรวจ Mandatory Test Cases: normal, parentheses, invalid operator order, division by zero, empty input และ required example
- ตรวจว่า Algorithm A และ B ให้ผลลัพธ์ตรงกัน
- ตรวจ Benchmark ที่ n = 100, 1,000, 10,000, 50,000 และค่าเฉลี่ย 5 รอบ
- ตรวจ Report และ Presentation ว่าใช้ข้อมูล benchmark ชุดเดียวกับ CSV

## Big-O ของกลุ่ม
ทั้ง Algorithm A และ Algorithm B มี Time Complexity โดยรวม `O(n)` และ Auxiliary Space `O(n)` สำหรับ input ขนาด n tokens

## Reflection
ผลจาก AI ต้องตรวจเทียบกับ Source Code, Test Cases และผลการทดลองจริงก่อนใช้งาน โดยเฉพาะ Complexity, Validation และ Benchmark
