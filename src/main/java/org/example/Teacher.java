package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.IOException;

public class Teacher {
    private String name;
    private String subject;
    public Teacher() {
    }

    public Teacher(String name, String subject) {
        this.name = name;
        this.subject = subject;
    }

    // Геттеры и сеттеры
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    // Методы для сериализации и десериализации
    public void serializeToJson(String filename) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(filename), this);
    }

    public void serializeToXml(String filename) throws IOException {
        XmlMapper mapper = new XmlMapper();
        mapper.writeValue(new File(filename), this);
    }

    public static Teacher deserializeFromJson(String filename) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(filename), Teacher.class);
    }

    public static Teacher deserializeFromXml(String filename) throws IOException {
        XmlMapper mapper = new XmlMapper();
        return mapper.readValue(new File(filename), Teacher.class);
    }
    @Override
    public String toString() {
        return "Teacher{" +
                "name='" + name + '\'' +
                ", subject=" + subject +
                '}';
    }
}
