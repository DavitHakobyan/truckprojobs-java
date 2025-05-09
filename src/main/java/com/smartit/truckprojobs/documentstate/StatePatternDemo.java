package com.smartit.truckprojobs.documentstate;

public class StatePatternDemo {
    public static void main(String[] args) {
        Document document = new Document(new DraftState());
        System.out.println("Initial State: " + document.getStateName());

        document.publish(); // Draft to Moderate
        System.out.println("Current State: " + document.getStateName());

        document.reject(); // Moderate to Draft
        System.out.println("Current State: " + document.getStateName());

        document.publish(); // Draft to Moderate
        document.publish(); // Moderate to Published
        System.out.println("Current State: " + document.getStateName());

        document.reject();
    }
}
