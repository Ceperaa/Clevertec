package ru.clevertec.check.runner.util.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataSource {

    private String url;
    private String driver;
    private String username;
    private String password;
}