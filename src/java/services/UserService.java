/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dataaccess.UserDB;
import java.util.List;
import models.Role;
import models.User;

/**
 *
 * @author Hussein
 */
public class UserService {
    public User get(String email) throws Exception{
        UserDB udb = new UserDB();
        User user = udb.get(email);
        return user;
    }
    public List<User> getAll() throws Exception{
        UserDB udb = new UserDB();
        List<User> users = udb.getAll();
        return users;
    }
    public String add(String email, String firstName, String lastName, String password, Role role) throws Exception{
        UserDB udb = new UserDB();
        User u2 = udb.get(email);
        if(u2!=null){
            return "Email already taken. ";
        }
        if(!email.contains("@")){
            return "Email must contain '@' symbol";
        }
        User user = new User(email,true,firstName,lastName,password);
        user.setRole(role);
        udb.insert(user);
        return "User added.";
    }
    public boolean update(User user) throws Exception{
        UserDB udb = new UserDB();
        udb.update(user);
        return true;
    }
    public String delete(User admin, User user) throws Exception{
        if(!admin.getRole().getRoleName().equals("system admin")){
            return "Not an admin.  Cannot delete an account.";
        }
        else if(admin.equals(user)){
            return "Cannot delete yourself.";
        }
        else{
            UserDB udb = new UserDB();
            udb.delete(user);
            return "User deleted.";
        }
        
    }
}
