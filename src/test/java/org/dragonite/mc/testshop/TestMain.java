package org.dragonite.mc.testshop;

public class TestMain {

    public static void main(String[] args) {
        var e = new Exception("2");
        var e2 = new RuntimeException(e);

        System.out.println(e2.getCause() != null);
        System.out.println(e.getCause() != null);
    }
}
