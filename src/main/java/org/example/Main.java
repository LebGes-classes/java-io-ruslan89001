package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        // Чтение данных из файлов .xlsx
        List<Student> students = readStudentsFromExcel("students.xlsx");
        List<Teacher> teachers = readTeachersFromExcel("teachers.xlsx");
        List<Subject> subjects = readSubjectsFromExcel("subjects.xlsx");

        // Вывод информации о студентах, учителях и предметах
        System.out.println("Students:");
        students.forEach(System.out::println);
        System.out.println("\nTeachers:");
        teachers.forEach(System.out::println);
        System.out.println("\nSubjects:");
        subjects.forEach(System.out::println);

        // Сериализация и десериализация данных
        try {
            // Сериализация в JSON
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new File("students.json"), students);
            objectMapper.writeValue(new File("teachers.json"), teachers);
            objectMapper.writeValue(new File("subjects.json"), subjects);

            // Десериализация из JSON
            List<Student> deserializedStudents = objectMapper.readValue(new File("students.json"), objectMapper.getTypeFactory().constructCollectionType(List.class, Student.class));
            List<Teacher> deserializedTeachers = objectMapper.readValue(new File("teachers.json"), objectMapper.getTypeFactory().constructCollectionType(List.class, Teacher.class));
            List<Subject> deserializedSubjects = objectMapper.readValue(new File("subjects.json"), objectMapper.getTypeFactory().constructCollectionType(List.class, Subject.class));

            // Сериализация в XML
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.writeValue(new File("students.xml"), students);
            xmlMapper.writeValue(new File("teachers.xml"), teachers);
            xmlMapper.writeValue(new File("subjects.xml"), subjects);

            // Десериализация из XML
            List<Student> deserializedStudentsXml = xmlMapper.readValue(new File("students.xml"), xmlMapper.getTypeFactory().constructCollectionType(List.class, Student.class));
            List<Teacher> deserializedTeachersXml = xmlMapper.readValue(new File("teachers.xml"), xmlMapper.getTypeFactory().constructCollectionType(List.class, Teacher.class));
            List<Subject> deserializedSubjectsXml = xmlMapper.readValue(new File("subjects.xml"), xmlMapper.getTypeFactory().constructCollectionType(List.class, Subject.class));

            // Использование Stream API для обработки данных
            // Пример: Получение списка студентов старше 20 лет
            List<Student> studentsOver20 = students.stream()
                    .filter(student -> student.getAge() > 20)
                    .collect(Collectors.toList());

            // Пример: Получение списка предметов с более чем 2 часами
            List<Subject> subjectsOver50Hours = subjects.stream()
                    .filter(subject -> subject.getHours() > 2)
                    .collect(Collectors.toList());

            // Пример: Получение списка учителей по определенному предмету
            String subjectToFilter = "Математика";
            List<Teacher> teachersBySubject = teachers.stream()
                    .filter(teacher -> teacher.getSubject().equals(subjectToFilter))
                    .collect(Collectors.toList());

            // Сохранение результатов обработки в JSON файл
            objectMapper.writeValue(new File("studentsOver20.json"), studentsOver20);
            objectMapper.writeValue(new File("subjectsOver2Hours.json"), subjectsOver50Hours);
            objectMapper.writeValue(new File("teachersBySubject.json"), teachersBySubject);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Методы для чтения данных из файлов .xlsx
    private static List<Student> readStudentsFromExcel(String filename) {
        List<Student> students = new ArrayList<>();
        try (FileInputStream file = new FileInputStream(new File(filename));
             Workbook workbook = WorkbookFactory.create(file)) {
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                String name = getCellValueAsString(row.getCell(0));
                int age = (int) row.getCell(1).getNumericCellValue();
                students.add(new Student(name, age));
            }
        } catch (IOException | EncryptedDocumentException ex) {
            ex.printStackTrace();
        }
        return students;
    }

    private static List<Teacher> readTeachersFromExcel(String filename) {
        List<Teacher> teachers = new ArrayList<>();
        try (FileInputStream file = new FileInputStream(new File(filename));
             Workbook workbook = WorkbookFactory.create(file)) {
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                String name = getCellValueAsString(row.getCell(0));
                String subject = getCellValueAsString(row.getCell(1));
                teachers.add(new Teacher(name, subject));
            }
        } catch (IOException | EncryptedDocumentException ex) {
            ex.printStackTrace();
        }
        return teachers;
    }

    private static List<Subject> readSubjectsFromExcel(String filename) {
        List<Subject> subjects = new ArrayList<>();
        try (FileInputStream file = new FileInputStream(new File(filename));
             Workbook workbook = WorkbookFactory.create(file)) {
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                String name = getCellValueAsString(row.getCell(0));
                int hours = (int) row.getCell(1).getNumericCellValue();
                subjects.add(new Subject(name, hours));
            }
        } catch (IOException | EncryptedDocumentException ex) {
            ex.printStackTrace();
        }
        return subjects;
    }

    // Вспомогательный метод для получения значения ячейки как строки
    private static String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            default:
                return "";
        }
    }
}