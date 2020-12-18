package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args)  {
        UserService userService =  new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Andrey", "Lopatov", (byte) 44);
        userService.saveUser("Chelovel", "Gorbachev", (byte) 54);
        userService.saveUser("Valerii", "Leont'ev", (byte) 33);
        userService.saveUser("Mister", "Liliputov", (byte) 15);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();

    }
}
