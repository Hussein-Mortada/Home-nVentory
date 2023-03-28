/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dataaccess.RoleDB;
import java.util.List;
import models.Role;

/**
 *
 * @author Hussein
 */
public class RoleService {
    public Role get(Integer roleID){
        RoleDB rdb = new RoleDB();
        Role role = rdb.get(roleID);
        return role;
    }
    public List<Role> getAll(){
        RoleDB rdb = new RoleDB();
        return rdb.getAll();
    }
}
