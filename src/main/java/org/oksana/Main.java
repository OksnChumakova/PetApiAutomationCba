package org.oksana;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static boolean isValid(String inputString) {

        boolean wasBMet = false;
        boolean isStringValid = true;
        for (String ch : inputString.split("")) {

            if (ch.equals("b")) {
                wasBMet = true;
            }

            if (wasBMet && ch.equals("a")) {
                isStringValid = false;
            }
        }
        return isStringValid;
    }

    public static void main(String[] args) {
        System.out.println("abba - " + isValid("abba"));
        System.out.println("abb - " + isValid("abb"));
        System.out.println("aaa - " + isValid("aaa"));
        System.out.println("bbb - " + isValid("bbb"));
        System.out.println("a - " + isValid("a"));
        System.out.println("b - " + isValid("b"));
        System.out.println("aaaaaaaaaaaab - " + isValid("aaaaaaaaaaaab"));
        System.out.println("bbbbbbbbbbbbba - " + isValid("bbbbbbbbbbbbba"));

    }
}