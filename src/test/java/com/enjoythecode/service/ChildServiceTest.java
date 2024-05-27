package com.enjoythecode.service;

import com.enjoythecode.exception.InsufficientDataException;
import com.enjoythecode.model.Child;
import com.enjoythecode.model.Sex;
import com.enjoythecode.model.Smoke;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ChildServiceTest {

    ChildService childService;
    List<Child> childrenListForTest;

    @BeforeEach
    public void init() {
        childService = new ChildService();
        childrenListForTest = List.of(
                new Child(1, 301, 9, 1.7, 57, Sex.MALE, Smoke.NO),
                new Child(2, 451, 8, 2.3, 67, Sex.FEMALE, Smoke.NO),
                new Child(3, 501, 4, 1.7, 64, Sex.MALE, Smoke.NO),
                new Child(4, 642, 4, 1.5, 53, Sex.FEMALE, Smoke.YES),
                new Child(5, 901, 5, 1.9, 57, Sex.FEMALE, Smoke.YES),
                new Child(6, 1701, 6, 1.7, 61, Sex.MALE, Smoke.YES)
        );
    }

    @Test
    public void shouldPrintChildWithSeqNbr1ForOldestChildAndSeqNbr3And4ForYoungestChild() {
        //given
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        String expectedOutput = """
                The oldest child is 9 years old. List of children:\s
                Child(seqNbr=1, subjID=301, age=9, fev=1.7, height=57.0, sex=MALE, smoke=NO)

                The youngest child is 4 years old. List of children:\s
                Child(seqNbr=3, subjID=501, age=4, fev=1.7, height=64.0, sex=MALE, smoke=NO)
                Child(seqNbr=4, subjID=642, age=4, fev=1.5, height=53.0, sex=FEMALE, smoke=YES)
                """;

        //when
        childService.printOldestAndYoungestChildInfo(childrenListForTest);

        //then
        assertEquals(expectedOutput, outContent.toString());

        //clean up
        System.setOut(System.out);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenPrintOldestAndYoungestChildFromNullList() {
        //given
        List<Child> childrenListForTest = null;

        //when
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> childService.printOldestAndYoungestChildInfo(childrenListForTest));

        //then
        SoftAssertions sa = new SoftAssertions();
        sa.assertThat(e).isExactlyInstanceOf(IllegalArgumentException.class);
        sa.assertThat(e).hasMessage("List is null or empty");
        sa.assertThat(e).hasNoCause();
        sa.assertAll();
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenPrintOldestAndYoungestChildFromEmptyList() {
        //given
        List<Child> childrenListForTest = new ArrayList<>();

        //when
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> childService.printOldestAndYoungestChildInfo(childrenListForTest));

        //then
        SoftAssertions sa = new SoftAssertions();
        sa.assertThat(e).isExactlyInstanceOf(IllegalArgumentException.class);
        sa.assertThat(e).hasMessage("List is null or empty");
        sa.assertThat(e).hasNoCause();
        sa.assertAll();
    }

    @Test
    public void shouldReturnMaleForWorseAvgFevSex() {
        //given
        Sex expectedResult = Sex.MALE;

        //when
        Sex worseAvgFevSex = childService.getWorseAvgFevSex(childrenListForTest);

        //then
        assertEquals(expectedResult, worseAvgFevSex);
    }

    @Test
    public void shouldThrowInsufficientDataExceptionWhenThereIsNoFemaleChildren() {
        //given
        List<Child> childrenListForTest = List.of(
                new Child(1, 301, 9, 1.7, 57, Sex.MALE, Smoke.NO),
                new Child(2, 451, 8, 1.7, 67, Sex.MALE, Smoke.NO),
                new Child(3, 501, 4, 1.7, 64, Sex.MALE, Smoke.NO)
        );

        //when
        Exception e = assertThrows(InsufficientDataException.class,
                () -> childService.getWorseAvgFevSex(childrenListForTest));

        //then
        SoftAssertions sa = new SoftAssertions();
        sa.assertThat(e).isExactlyInstanceOf(InsufficientDataException.class);
        sa.assertThat(e).hasMessage("Cannot calculate average FEV: insufficient data");
        sa.assertThat(e).hasNoCause();
        sa.assertAll();
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenGetWorseAvgFevSexFromNullList() {
        //given
        List<Child> childrenListForTest = null;

        //when
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> childService.getWorseAvgFevSex(childrenListForTest));

        //then
        SoftAssertions sa = new SoftAssertions();
        sa.assertThat(e).isExactlyInstanceOf(IllegalArgumentException.class);
        sa.assertThat(e).hasMessage("List is null or empty");
        sa.assertThat(e).hasNoCause();
        sa.assertAll();
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenGetWorseAvgFevSexFromEmptyList() {
        //given
        List<Child> childrenListForTest = new ArrayList<>();

        //when
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> childService.getWorseAvgFevSex(childrenListForTest));

        //then
        SoftAssertions sa = new SoftAssertions();
        sa.assertThat(e).isExactlyInstanceOf(IllegalArgumentException.class);
        sa.assertThat(e).hasMessage("List is null or empty");
        sa.assertThat(e).hasNoCause();
        sa.assertAll();
    }

    @Test
    public void shouldReturn0Coma5SmokingHabitsRate() {
        //given
        double expectedRate = 0.5;

        //when
        double smokingHabitsRate = childService.getSmokingHabitsRate(childrenListForTest);

        //then
        assertEquals(expectedRate, smokingHabitsRate);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenGetSmokingHabitRatesFromNullList() {
        //given
        List<Child> childrenListForTest = null;

        //when
        Exception e = assertThrows(IllegalArgumentException.class, () -> childService.getSmokingHabitsRate(childrenListForTest));

        //then
        SoftAssertions sa = new SoftAssertions();
        sa.assertThat(e).isExactlyInstanceOf(IllegalArgumentException.class);
        sa.assertThat(e).hasMessage("List is null or empty");
        sa.assertThat(e).hasNoCause();
        sa.assertAll();
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenWhenGetSmokingHabitRatesFromEmptyList() {
        //given
        List<Child> childrenListForTest = new ArrayList<>();

        //when
        Exception e = assertThrows(IllegalArgumentException.class, () -> childService.getSmokingHabitsRate(childrenListForTest));

        //then
        SoftAssertions sa = new SoftAssertions();
        sa.assertThat(e).isExactlyInstanceOf(IllegalArgumentException.class);
        sa.assertThat(e).hasMessage("List is null or empty");
        sa.assertThat(e).hasNoCause();
        sa.assertAll();
    }

    @Test
    public void shouldPrintAverageHeightForSmokingAndNonSmokingBoysAndGirls() {
        //given
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        String expectedOutput = """
                Average height of boys smokers: 61.00 inches
                Average height of boys non-smokers: 60.50 inches
                Average height of girls smokers: 55.00 inches
                Average height of girls non-smokers: 67.00 inches
                """;

        //when
        childService.printAverageHeightBySmokingStatus(childrenListForTest);

        //then
        assertEquals(expectedOutput, outContent.toString());

        //clean up
        System.setOut(System.out);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenPrintAverageHeightFromNullList() {
        //given
        List<Child> childrenListForTest = null;

        //when
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> childService.printAverageHeightBySmokingStatus(childrenListForTest));

        //then
        SoftAssertions sa = new SoftAssertions();
        sa.assertThat(e).isExactlyInstanceOf(IllegalArgumentException.class);
        sa.assertThat(e).hasMessage("List is null or empty");
        sa.assertThat(e).hasNoCause();
        sa.assertAll();
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenPrintAverageHeightFromEmptyList() {
        //given
        List<Child> childrenListForTest = new ArrayList<>();

        //when
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> childService.printAverageHeightBySmokingStatus(childrenListForTest));

        //then
        SoftAssertions sa = new SoftAssertions();
        sa.assertThat(e).isExactlyInstanceOf(IllegalArgumentException.class);
        sa.assertThat(e).hasMessage("List is null or empty");
        sa.assertThat(e).hasNoCause();
        sa.assertAll();
    }

    @Test
    public void shouldReturnListWithChildWithSeqNbr6() {
        //given
        List<Child> expectedResult = List.of(childrenListForTest.get(5));

        //when
        List<Child> smokingBoys = childService.getSmokingBoys(childrenListForTest);

        //then
        assertEquals(expectedResult, smokingBoys);
    }

    @Test
    public void shouldReturnEmptyListWhenThereIsNoSmokingBoy() {
        //given
        List<Child> childrenListForTest = List.of(
                new Child(1, 301, 9, 1.7, 57, Sex.MALE, Smoke.NO),
                new Child(2, 451, 8, 1.7, 67, Sex.MALE, Smoke.NO),
                new Child(3, 501, 4, 1.7, 64, Sex.FEMALE, Smoke.NO)
        );
        List<Child> expectedResult = new ArrayList<>();

        //when
        List<Child> smokingBoys = childService.getSmokingBoys(childrenListForTest);

        //then
        assertEquals(expectedResult, smokingBoys);
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenGetSmokingBoysFromFromNullList() {
        //given
        List<Child> childrenListForTest = null;

        //when
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> childService.printAverageHeightBySmokingStatus(childrenListForTest));

        //then
        SoftAssertions sa = new SoftAssertions();
        sa.assertThat(e).isExactlyInstanceOf(IllegalArgumentException.class);
        sa.assertThat(e).hasMessage("List is null or empty");
        sa.assertThat(e).hasNoCause();
        sa.assertAll();
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenGetSmokingBoysFromEmptyList() {
        //given
        List<Child> childrenListForTest = new ArrayList<>();

        //when
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> childService.printAverageHeightBySmokingStatus(childrenListForTest));

        //then
        SoftAssertions sa = new SoftAssertions();
        sa.assertThat(e).isExactlyInstanceOf(IllegalArgumentException.class);
        sa.assertThat(e).hasMessage("List is null or empty");
        sa.assertThat(e).hasNoCause();
        sa.assertAll();
    }

}