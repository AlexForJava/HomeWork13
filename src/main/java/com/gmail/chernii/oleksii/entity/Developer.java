package com.gmail.chernii.oleksii.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Created by Space on 10.04.2019.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Developer {
    private String name;
    private int age;
    private Sex sex;
    private int salary;

    @Override
    public String toString() {
        return "Developer{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                ", salary=" + salary +
                '}';
    }
}
