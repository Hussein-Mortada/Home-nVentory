/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dataaccess.CategoryDB;
import java.util.List;
import models.Category;

/**
 *
 * @author Hussein
 */
public class CategoryService {
    public Category get(Integer catID){
        CategoryDB cdb = new CategoryDB();
        Category cat = cdb.get(catID);
        return (cat==null)? null : cat;
    }
    public List<Category> getAll() throws Exception{
        CategoryDB cdb = new CategoryDB();
        return cdb.getAll();
    }
    public boolean add(Category cat) throws Exception{
        CategoryDB cdb = new CategoryDB();
        if(cat==null)
            return false;
        cdb.insert(cat);
        return true;
    }
    public boolean update(Category cat) throws Exception{
        CategoryDB cdb = new CategoryDB();
        if(cat==null)
            return false;
        cdb.update(cat);
        return true;
    }
}
