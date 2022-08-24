package ru.clevertec.check.runner.util.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationYaml {

    private DataSource datasource;
    private Liquibase liquibase;
    private Hibernate hibernate;
}
