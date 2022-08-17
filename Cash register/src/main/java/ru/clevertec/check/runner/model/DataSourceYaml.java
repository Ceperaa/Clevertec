package ru.clevertec.check.runner.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataSourceYaml {

    private String url;
    private String driver;
    private String username;
    private String password;
}
