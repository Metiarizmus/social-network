package com.nikolai.network;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("one");
        list.add("two");
        list.add("three");
        list.add("four");
        list.add("five");
        list.add("six");
        list.add("seven");

        Random rand = new Random();
        Set<Integer> randomInt = new HashSet<>();

        for (int i = 0; i < 4; i++) {
            randomInt.add(rand.nextInt(list.size()));
        }

        System.out.println(randomInt);

    }
}
