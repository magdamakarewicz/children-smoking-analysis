package com.enjoythecode.service;

import com.enjoythecode.model.Child;
import com.enjoythecode.model.Sex;
import com.enjoythecode.model.Smoke;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChildServiceTest {

    ChildService childService;
    List<Child> childrenListForTest;

    @BeforeEach
    public void init() {
        childService = new ChildService();
        childrenListForTest = List.of(
                new Child(1, 301, 9, 1.7, 57, Sex.MALE, Smoke.NO),
                new Child(2, 451, 8, 1.7, 67, Sex.FEMALE, Smoke.NO),
                new Child(3, 501, 4, 1.7, 64, Sex.MALE, Smoke.NO),
                new Child(4, 642, 4, 1.5, 53, Sex.FEMALE, Smoke.YES),
                new Child(5, 901, 5, 1.8, 57, Sex.FEMALE, Smoke.YES),
                new Child(6, 1701, 6, 2.3, 61, Sex.MALE, Smoke.YES)
        );
    }

    @Test
    public void shouldPrintChildWithSeqNbr1ForOldestChildAndSeqNbr3And4ForYoungestChild() {
        //given
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        //when
        childService.printOldestAndYoungestChildInfo(childrenListForTest);

        //then
        String expectedOutput = "The oldest child is 9 years old. List of children: \n" +
                "Child(seqNbr=1, subjID=301, age=9, fev=1.7, height=57.0, sex=MALE, smoke=NO)\n\n" +
                "The youngest child is 4 years old. List of children: \n" +
                "Child(seqNbr=3, subjID=501, age=4, fev=1.7, height=64.0, sex=MALE, smoke=NO)\n" +
                "Child(seqNbr=4, subjID=642, age=4, fev=1.5, height=53.0, sex=FEMALE, smoke=YES)\n";
        assertEquals(expectedOutput, outContent.toString());

        //clean up
        System.setOut(System.out);
    }

}