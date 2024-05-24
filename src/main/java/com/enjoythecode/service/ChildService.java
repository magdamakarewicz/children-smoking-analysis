package com.enjoythecode.service;

import com.enjoythecode.model.Child;

import java.util.Comparator;
import java.util.List;

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

}
