package edu.ntu.sau.javasb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;


@Data
@AllArgsConstructor
public class User {

    private String name;
    private int salary;


}
