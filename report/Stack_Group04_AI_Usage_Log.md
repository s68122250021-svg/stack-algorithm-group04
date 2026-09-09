# Group 04 — Generative AI Usage Log

## งานที่ใช้ Generative AI ช่วย
- วิเคราะห์ปัญหา: Input, Output, Constraints, Assumptions, กรณีปกติ, กรณีขอบเขต และกรณี Error
- ออกแบบ Algorithm A และ Algorithm B พร้อม Pseudocode
- จัดทำตัวอย่าง Step-by-step และตารางสถานะ Stack
- อธิบาย Correctness: Precondition, Invariant, Postcondition และ Termination
- วิเคราะห์ Time Complexity / Space Complexity และเปรียบเทียบ A vs B
- ช่วยตรวจ Test Cases และโครงสร้างโปรแกรม Java
- ช่วยตรวจรูปแบบสไลด์และความสอดคล้องของเอกสารก่อนส่ง

## ตัวอย่าง Prompt
> ออกแบบ Expression Processor สำหรับ Group 4 โดยใช้ Stack รองรับ Infix/Postfix, + - * /, parentheses, positive integers และให้มี Algorithm อย่างน้อย 2 วิธี พร้อม Pseudocode, Step-by-step, Correctness, Big-O, Java และ Test Cases

## ข้อผิดพลาด/ประเด็นที่ตรวจพบ
1. Best Case ของภาพรวมระบบเคยถูกระบุเป็น O(1) ซึ่งไม่สอดคล้องกับการที่ระบบต้องอ่านและตรวจ Input
2. การตรวจสอบ Operand/Operator order จำเป็นต้องทำก่อนขั้นคำนวณ เพื่อรองรับกรณีเช่น `3 + * 4`
3. การกำหนด n ใน Benchmark ต้องแยกจำนวน Operand ออกจากจำนวน Token จริง (`2n - 1` สำหรับข้อมูลทดลอง)
4. ตาราง/กราฟผลการทดลองต้องใช้ชุดข้อมูลเดียวกับหลักฐานการรันโปรแกรมจริง
5. Layout ของสไลด์ต้องตรวจหลัง export เป็น PDF เพื่อป้องกันข้อความล้นหรือทับกัน

## ส่วนที่แก้ไข
- เปลี่ยน Best/Average/Worst ของภาพรวม Algorithm A และ B เป็น O(n)
- เพิ่ม Token validation และ Empty-input / Parentheses / Operator-position checks
- ปรับ Benchmark ให้ n หมายถึงจำนวน Operand และรายงาน Actual tokens แยกต่างหาก
- จัดข้อมูล CSV/กราฟ/สไลด์ Experimental Results ให้ใช้ชุดข้อมูลเดียวกัน
- ปรับ Layout หน้า Big-O และ Experimental Results

## วิธีตรวจสอบความถูกต้อง
- Compile และ run โปรแกรม Java
- รัน Mandatory Test Cases ทั้ง Algorithm A และ B
- เปรียบเทียบผลลัพธ์กับ Expected Result
- ทดสอบหารด้วยศูนย์, วงเล็บไม่ครบ, Operator ติดกัน และนิพจน์ว่าง
- ตรวจ Benchmark ขนาด 100, 1,000, 10,000 และ 50,000 โดยเฉลี่ย 5 รอบ
- ตรวจความสอดคล้องระหว่าง Source Code, Pseudocode, Report, Presentation และ CSV

## การวิเคราะห์ Big-O ของกลุ่ม
ทั้ง Algorithm A และ B มี Time Complexity โดยรวม O(n) เมื่อ n คือจำนวน Token ที่ประมวลผล และ Auxiliary Space เป็น O(n)

## Reflection
Generative AI ช่วยเร่งการจัดโครงสร้างคำอธิบายและช่วยตรวจความครบถ้วน แต่คำตอบต้องถูกตรวจสอบกับ Source Code, การรันจริง และเงื่อนไขของโจทย์ก่อนนำไปใช้ เพราะรายละเอียดอย่าง Complexity, Validation และผล Benchmark สามารถคลาดเคลื่อนได้