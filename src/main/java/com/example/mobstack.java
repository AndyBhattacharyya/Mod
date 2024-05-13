package com.example;

public class mobstack {
    private static int pos = 0;

    //Holding the mobs that have been spawned by right clicking items. Will be referenced
    private static mobspawning mob_stack[] = new mobspawning[50];

    //Means of adding a mob onto the stack
    public static void push(mobspawning t) {
        mob_stack[pos++] = t;
    }

    //Means of accessing the most recent mob spawned
    public static mobspawning pop() {
        return pos==0 ? null:mob_stack[--pos];
    }
}