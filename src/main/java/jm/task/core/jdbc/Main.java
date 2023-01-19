package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.Arrays;

public class Main {
    private static final UserService userService = new UserServiceImpl();

    public static void main(String[] args) {
        userService.createUsersTable();
        userService.saveUser("Alan", "Star", (byte) 37);
        userService.saveUser("Anna", "Star", (byte) 35);
        userService.saveUser("Max", "Light", (byte) 8);
        userService.saveUser("Jack", "Poo", (byte) 45);
        System.out.println(Arrays.toString(userService.getAllUsers().toArray()));
        userService.removeUserById(2);
        System.out.println("Users in table: ");
        System.out.println(Arrays.toString(userService.getAllUsers().toArray()));
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}