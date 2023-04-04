package servlets;
import dataaccess.ItemDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Category;
import models.Item;
import models.User;
import services.CategoryService;
import services.ItemService;

public class HomeServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session =request.getSession();
        User user = (User)session.getAttribute("user");
        if(user==null){
            response.sendRedirect("login");
        }
        if(user!=null&&user.getRole().getRoleName().equals("regular user")){
            ItemService is = new ItemService();
            List<Item> itemlist = is.getAll(user);
            CategoryService cs = new CategoryService();
            try{
                List<Category> categorylist = cs.getAll();
                session.setAttribute("categorylist", categorylist);
            }
            catch(Exception ex){
            }
            session.setAttribute("itemlist", itemlist);
            getServletContext().getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
        }
        if(user!=null&&user.getRole().getRoleName().equals("system admin")){
            getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            HttpSession session =request.getSession();
            String action = (String) request.getParameter("action");
            if(action==null || action==""){
                response.sendRedirect("home");
            }
            if(action.equals("logout")){
                session.invalidate();
                response.sendRedirect("login");
            }
            else if(action.equals("profile")){
                response.sendRedirect("profile");
            }
            else if(action.equals("additem")){
                String itemname = request.getParameter("itemname");
                String testPrice = request.getParameter("price");
                if(testPrice==null || testPrice.equals("")){
                    request.setAttribute("message", "Enter a price");
                    request.setAttribute("itemname", itemname);
                    getServletContext().getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
                }
                for(int i=0;i<testPrice.length();i++){
                    char c = testPrice.charAt(i);
                    if(!Character.isDigit(c)&&c!='.'){
                        request.setAttribute("message", "Item price cannot have any letters... >:(");
                        getServletContext().getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response); 
                    }
                }
                Double itemprice = Double.parseDouble(testPrice);
                int categoryID = Integer.parseInt(request.getParameter("category"));
                if(itemname==null || itemname.equals("")){
                    request.setAttribute("message", "Enter an item name");
                    request.setAttribute("itemprice", itemprice);
                    getServletContext().getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
                }
                if(itemprice<0){
                    request.setAttribute("itemname", itemname);
                    request.setAttribute("message", "Price cannot be negative");
                    getServletContext().getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
                }
                ItemService is = new ItemService();
                User user = (User) session.getAttribute("user");
                try{
                    is.add(itemname, itemprice, user, categoryID);
                    request.setAttribute("message", "Item Added");
                    List<Item> itemlist = is.getAll(user);
                    session.setAttribute("itemlist", itemlist);
                    CategoryService cs = new CategoryService();
                    try{
                        List<Category> categorylist = cs.getAll();
                        session.setAttribute("categorylist", categorylist);
                    }
                    catch(Exception ex){
                    }
                    getServletContext().getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
                }
                catch(Exception ex){
                    
                }
            }
            else if(action.equals("delete")){
                ItemService is = new ItemService();
                ItemDB idb = new ItemDB();
                int itemid = Integer.parseInt((String) request.getParameter("itemId"));
                try{
                    Item item = idb.get(itemid);
                    User user = (User)session.getAttribute("user");
                    if(!item.getOwner().equals(user)){
                        request.setAttribute("message", "You are not the owner of this item!");
                        getServletContext().getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
                    }
                    is.delete(user, itemid);
                    request.setAttribute("message", "Item Deleted");
                    List<Item> itemlist = is.getAll(user);
                    itemlist.remove(item);
                    session.setAttribute("itemlist", itemlist);
                    CategoryService cs = new CategoryService();
                    List<Category> categorylist = cs.getAll();
                    session.setAttribute("categorylist", categorylist);
                    getServletContext().getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
                }
                catch(Exception ex){
                }

            }
            else if(action.equals("edit")){
                int itemid = Integer.parseInt((String) request.getParameter("itemId"));
                ItemDB idb = new ItemDB();
                try {
                    Item item = idb.get(itemid);
                    session.setAttribute("item", item);
                    request.setAttribute("itemname", item.getItemName());
                    request.setAttribute("itemprice", item.getPrice());
                } catch (Exception ex) {
                    Logger.getLogger(HomeServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                    request.setAttribute("itemID",itemid );
                    getServletContext().getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
                }
            else if(action.equals("edititem")){
                Item item = (Item)session.getAttribute("item");
                String itemname = request.getParameter("itemname");
                String testPrice = request.getParameter("price");
                if(testPrice==null || testPrice.equals("")){
                    request.setAttribute("message", "Enter a price");
                    request.setAttribute("itemname", itemname);
                    getServletContext().getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
                }
                for(int i=0;i<testPrice.length();i++){
                    char c = testPrice.charAt(i);
                    if(!Character.isDigit(c)&&c!='.'){
                        request.setAttribute("message", "Item price cannot have any letters... >:(");
                        getServletContext().getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response); 
                    }
                }
                Double itemprice = Double.parseDouble(testPrice);
                int categoryID = Integer.parseInt(request.getParameter("category"));
                if(itemname==null || itemname.equals("")){
                    request.setAttribute("message", "Enter an item name");
                    request.setAttribute("itemprice", itemprice);
                    getServletContext().getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
                }
                if(itemprice<0){
                    request.setAttribute("itemname", itemname);
                    request.setAttribute("message", "Price cannot be negative");
                    getServletContext().getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
                }
                ItemService is = new ItemService();
                User user = (User)session.getAttribute("user");
                try {
                    item.setItemName(itemname);
                    item.setPrice(itemprice);
                    CategoryService cs = new CategoryService();
                    Category cat = cs.get(categoryID);
                    item.setCategory(cat);
                    ItemDB idb = new ItemDB();
                    idb.update(item);
                } catch (Exception ex) {
                    Logger.getLogger(HomeServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                List<Item> items = is.getAll(item.getOwner());
                session.setAttribute("itemlist", items);
                session.setAttribute("item",null );
                request.setAttribute("message","Item modified");
                getServletContext().getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
            }
    }

}
