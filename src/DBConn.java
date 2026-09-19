import java.sql.*;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
public class DBConn {
    String url = "jdbc:sqlite:inventory.db";
    
    public Connection connect(){
        Connection conn = null;
        
        try{
            conn = DriverManager.getConnection(url);
            System.out.println("Connected to SQLite Database!");
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return conn;
    }
    
    public String login(String account, String password){
        String query = "SELECT * FROM users WHERE username = ? OR email = ? OR user_id = ? AND password = ?";
        
        // Try with resources
        try(Connection conn = connect();
            PreparedStatement pstmt  = conn.prepareStatement(query)){
            pstmt.setString(1, account);
            pstmt.setString(2, account);
            pstmt.setString(3, account);
            pstmt.setString(4, password);
            
            ResultSet rs = pstmt.executeQuery();
            
            String role = rs.getString("role");
             
            return rs.next() ? role : "";
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return "";
        }
    }
    
    public boolean register(String name, String email, String username, String password){
        String query = "INSERT INTO users (name,email,username,password) VALUES (?,?,?,?)";
        
        // Try with resources
        try(Connection conn = connect();
            PreparedStatement pstmt  = conn.prepareStatement(query)){
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, username);
            pstmt.setString(4, password);
            
            pstmt.executeUpdate();
            
            return true;
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    public DefaultTableModel getAllProducts(){
        String[] headers = {"SKU" , "Product Name" ,"Category", "Description" , "Status"};
        DefaultTableModel model = new DefaultTableModel(headers, 0);
        
        String query = "SELECT * FROM products";
        
        try(Connection conn = connect();
            Statement stmt = conn.createStatement()){
            
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                String sku = rs.getString("sku");
                String name = rs.getString("product_name");
                String category = rs.getString("category");
                String desc = rs.getString("description");
                String status = rs.getString("status");
                
                String[] row = {sku,name,category,desc,status};
                
                model.addRow(row);
            }
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return model;
    } 
    
     public boolean addProduct(String sku, String name, String category, String desc, String status){
        String query = "INSERT INTO products (sku, product_name, category, description , status) VALUES (?,?,?,?,?)";
        
        // Try with resources
        try(Connection conn = connect();
            PreparedStatement pstmt  = conn.prepareStatement(query)){
            pstmt.setString(1, sku);
            pstmt.setString(2, name);
            pstmt.setString(3, category);
            pstmt.setString(4, desc);
            pstmt.setString(5, status);
            
            pstmt.executeUpdate();
            
            return true;
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
     
     public void demoMethod(){
         System.out.println("Demo");
     }
      
}
