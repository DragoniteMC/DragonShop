package org.dragonite.mc.testshop;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class TestGUI {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String[] args) {
        var e = new Exception("2");
        var e2 = new RuntimeException(e);

        System.out.println(e2.getCause() != null);
        System.out.println(e.getCause() != null);


        Process<List<String>> task = new StringListProcess();

        Process<User> userTask = new UserProcess();

        var s = List.of("123", "456");
        doTask(task, s);

        var map = Map.of("name", "dragonite", "age", 18);
        doTask(userTask, map);
    }

    private static <T> void doTask(Process<T> process, Object o){
        try {
            var jt = MAPPER.constructType(process.getType());
            T t = MAPPER.convertValue(o, jt);
            process.doProcess(t);
        }catch (ClassCastException | IllegalArgumentException e){
            System.out.println("ClassCastException");
        }
    }



    public interface Process<T> {
        void doProcess(T t);

        Type getType();
    }

    public static class StringListProcess implements Process<List<String>>{

        @Override
        public void doProcess(List<String> strings) {
            strings.forEach(System.out::println);
        }

        @Override
        public Type getType() {
            return List.class;
        }

    }

    public static class UserProcess implements Process<User>{

        @Override
        public void doProcess(User user) {
            System.out.println(user.name);
            System.out.println(user.age);
        }

        @Override
        public Type getType() {
            return User.class;
        }
    }

    public static class User {
        public int age;
        public String name;
    }
}
