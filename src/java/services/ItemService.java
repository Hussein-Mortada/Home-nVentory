/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dataaccess.CategoryDB;
import dataaccess.ItemDB;
import java.util.List;
import models.Category;
import models.Item;
import models.User;

/**
 *
 * @author Hussein
 */
public class ItemService {
        public boolean add(String itemName, Double price, User user, Integer categoryId) throws Exception {
            ItemDB idb = new ItemDB();
            Category category = new CategoryDB().get(categoryId);
            Item item = new Item();
            item.setCategory(category);
            item.setItemName(itemName);
            item.setOwner(user);
            item.setPrice(price);
            if (itemName==null ||itemName==""||user==null||category==null||price==null){
                return false;
            }
            if (price < 0) {
                return false;
            }
            idb.insert(item);
            return true;
    }
        public List<Item> getAll(User user){
            ItemDB idb = new ItemDB();
            if(user==null){
                return null;
            }
            List<Item> items=user.getItemList();
            return items;
        }
        
        
        public boolean delete(User user,Integer itemID) throws Exception{
            ItemDB idb = new ItemDB();
//            if(itemID==null||user==null)
//                return false;
            Item item = idb.get(itemID);
//            if(!item.getOwner().equals(user)){
//                return false;
//            }
//            if(item==null){
//                return false;
//            }
            idb.delete(item);
            return true;
        }
        public boolean update(User user, Integer itemID) throws Exception{
            ItemDB idb = new ItemDB();
            if(itemID==null||user==null)
                return false;
            Item item = idb.get(itemID);
            if(!item.getOwner().equals(user)){
                return false;
            }
            if(item==null){
                return false;
            }
            idb.update(item);
            return true;
        }
}
