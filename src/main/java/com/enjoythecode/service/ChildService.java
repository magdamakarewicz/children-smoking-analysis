package com.enjoythecode.service;

import com.enjoythecode.exception.InsufficientDataException;
import com.enjoythecode.model.Child;
import com.enjoythecode.model.Sex;
import com.enjoythecode.model.Smoke;

import java.util.*;
import java.util.stream.Collectors;

public class ChildService {

    public void printOldestAndYoungestChildInfo(List<Child> children) {
        int maxAge = getMaxAge(children);
        int minAge = getMinAge(children);

        System.out.println("The oldest child is " + maxAge + " years old. List of children: ");
        printChildrenWithAge(children, maxAge);
        System.out.println();

        System.out.println("The youngest child is " + minAge + " years old. List of children: ");
        printChildrenWithAge(children, minAge);
    }

    private int getMaxAge(List<Child> children) {
        return children.stream()
                .max(Comparator.comparingInt(Child::getAge))
                .orElseThrow(() -> new IllegalArgumentException("List is empty"))
                .getAge();
    }

    private int getMinAge(List<Child> children) {
        return children.stream()
                .min(Comparator.comparingInt(Child::getAge))
                .orElseThrow(() -> new IllegalArgumentException("List is empty"))
                .getAge();
    }

    private void printChildrenWithAge(List<Child> children, int age) {
        children.stream()
                .filter(child -> child.getAge() == age)
                .forEach(System.out::println);
    }

    public Sex getWorseAvgFevSex(List<Child> children) {
        double avgMaleFev = children.stream()
                .filter(c -> c.getSex() == Sex.MALE)
                .mapToDouble(Child::getFev)
                .average()
                .orElseThrow(() -> new InsufficientDataException("Cannot calculate average FEV: insufficient data"));

        double avgFemaleFev = children.stream()
                .filter(c -> c.getSex() == Sex.FEMALE)
                .mapToDouble(Child::getFev)
                .average()
                .orElseThrow(() -> new InsufficientDataException("Cannot calculate average FEV: insufficient data"));

        return avgMaleFev < avgFemaleFev ? Sex.MALE : Sex.FEMALE;
    }

    public double getSmokingHabitsRate(List<Child> children) {
        if (children == null || children.isEmpty()) {
            throw new IllegalArgumentException("List is null or empty");
        }
        long smokers = children.stream()
                .filter(c -> c.getSmoke() == Smoke.YES)
                .count();
        return Math.round((double) smokers / children.size() * 100.00) / 100.00;
    }

    public void printAverageHeightBySmokingStatus(List<Child> children) {
        printAverageHeightBySmokingStatusForSex(children, Sex.MALE);
        printAverageHeightBySmokingStatusForSex(children, Sex.FEMALE);
    }

    private void printAverageHeightBySmokingStatusForSex(List<Child> children, Sex sex) {
        double avgHeightSmokers = Optional.ofNullable(children)
                .orElseGet(Collections::emptyList)
                .stream()
                .filter(c -> c.getSex() == sex && c.getSmoke() == Smoke.YES)
                .mapToDouble(Child::getHeight)
                .average()
                .orElse(0.0);

        double avgHeightNonSmokers = Optional.ofNullable(children)
                .orElseGet(Collections::emptyList)
                .stream()
                .filter(c -> c.getSex() == sex && c.getSmoke() == Smoke.NO)
                .mapToDouble(Child::getHeight)
                .average()
                .orElse(0.0);

        Locale.setDefault(Locale.US);
        System.out.printf(Locale.US, "Average height of %s smokers: %.2f inches\n",
                sex == Sex.MALE ? "boys" : "girls", avgHeightSmokers);
        System.out.printf(Locale.US, "Average height of %s non-smokers: %.2f inches\n",
                sex == Sex.MALE ? "boys" : "girls", avgHeightNonSmokers);
    }

    public List<Child> getSmokingBoys(List<Child> children) {
        return Optional.ofNullable(children)
                .orElseGet(Collections::emptyList)
                .stream()
                .filter(c -> c.getSex() == Sex.MALE && c.getSmoke() == Smoke.YES)
                .collect(Collectors.toList());
    }

}