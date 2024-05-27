package com.enjoythecode.app;

import com.enjoythecode.model.Child;
import com.enjoythecode.model.Sex;
import com.enjoythecode.model.Smoke;
import com.enjoythecode.service.ChildService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        String filePath = "children_smoking.txt";
        File file = new File(filePath);

        List<Child> children = readChildrenFromFile(filePath);

        //Check number of examined children
        System.out.println("Number of examined children = " + children.size());
        System.out.println();

        ChildService childService = new ChildService();

        //Call all service methods
        childService.printOldestAndYoungestChildInfo(children);
        System.out.println();
        System.out.println("Sex with worse average FEV: " + childService.getWorseAvgFevSex(children));
        System.out.println();
        System.out.printf("Percentage of children with smoking habits: %.2f%%\n", childService.getSmokingHabitsRate(children));
        System.out.println();
        childService.printAverageHeightBySmokingStatus(children);
        System.out.println();
        System.out.println("Smoking boys: ");
        childService.getSmokingBoys(children).forEach(System.out::println);

    }

    private static List<Child> readChildrenFromFile(String filePath) {
        List<Child> children = new ArrayList<>();
        try (
                FileReader fr = new FileReader(filePath);
                BufferedReader br = new BufferedReader(fr)
        ) {
            String nextLine;
            while ((nextLine = br.readLine()) != null) {
                Child child = getChild(nextLine);
                children.add(child);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return children;
    }

    private static Child getChild(String nextLine) {
        String[] values = nextLine.split(",");
        Child child = new Child(
                Integer.parseInt(values[0]),
                Integer.parseInt(values[1]),
                Integer.parseInt(values[2]),
                Double.parseDouble(values[3]),
                Double.parseDouble(values[4]),
                values[5].equals("1") ? Sex.MALE : Sex.FEMALE,
                values[6].equals("1") ? Smoke.YES : Smoke.NO
        );
        return child;
    }

}