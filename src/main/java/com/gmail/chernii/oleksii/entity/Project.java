package com.gmail.chernii.oleksii.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;


/**
 * Created by Space on 10.04.2019.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Project {
    private String name;
    private String type;
    private int cost;
    private Date date;
}
