package com.enjoythecode.childrensmokinganalysis.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Child {

    private int seqNbr;
    private int subjID;
    private int age;
    private double fev;
    private double height;
    private Sex sex;
    private Smoke smoke;

}
