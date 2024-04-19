package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.IOException;

public class Subject {
    private String name;
    private int hours;
    public Subject() {
    }

    public Subject(String name, int hours) {
        this.name = name;
        this.hours = hours;
    }

    // Геттеры и сеттеры
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
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

    public static Subject deserializeFromJson(String filename) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(filename), Subject.class);
    }

    public static Subject deserializeFromXml(String filename) throws IOException {
        XmlMapper mapper = new XmlMapper();
        return mapper.readValue(new File(filename), Subject.class);
    }
    @Override
    public String toString() {
        return "Subject{" +
                "name='" + name + '\'' +
                ", hours='" + hours + '\'' +
                '}';
    }
}