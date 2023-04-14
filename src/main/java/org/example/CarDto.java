package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CarDto {
    private long id;
    private String stamp;
    private String model;
    private String state_number;
    private int age;
}
