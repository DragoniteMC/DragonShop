package org.dragonite.mc.testshop;

import org.dragonitemc.dragonshop.api.RewardTask;

import java.util.List;

public class TestMain {

    public static void main(String[] args) {
        var e = new Exception("2");
        var e2 = new RuntimeException(e);

        System.out.println(e2.getCause() != null);
        System.out.println(e.getCause() != null);


        Process<?> task = new StringListProcess();

        var s = "hiawdhiawhdiawhidhwai";
        doTask(task, s);
    }

    private static <T> void doTask(Process<T> process, Object o){
        try {
            T t = (T)o;
            process.doProcess(t);
        }catch (ClassCastException e){
            System.out.println("ClassCastException");
        }
    }



    public interface Process<T> {
        void doProcess(T t);
    }

    public static class StringListProcess implements Process<List<String>>{

        @Override
        public void doProcess(List<String> strings) {
            strings.forEach(System.out::println);
        }

    }
}
