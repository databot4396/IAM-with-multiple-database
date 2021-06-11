package database_interface;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class pgsql
{
   Connection con = null;
   Statement stmt = null;

   public void insert(String name,long number,String email,String hid,int rc,String psw1,String access)
   {
       try
        {
            Class.forName("org.postgresql.Driver");
            con= DriverManager.getConnection("jdbc:postgresql://localhost:5432/pramod","postgres", "1234");
            con.setAutoCommit(false); 
            stmt=con.createStatement();
            String sql="insert into userinfo(name,email,phone,hid,rc,psw,access) values ('"+name+"','"+email+"','"+number+"','"+hid+"','"+rc+"','"+psw1+"','"+access+"');";
            stmt.executeUpdate(sql);
            con.commit();
        }
       catch (Exception ex)
        {
            System.out.println(ex);
        }
   }
   
   public void update(int rc,String email)
   {
       try
        {
            Class.forName("org.postgresql.Driver");
            con= DriverManager.getConnection("jdbc:postgresql://localhost:5432/pramod","postgres", "1234");
            con.setAutoCommit(false);
            stmt=con.createStatement();
            String sql1="update userinfo set rc='"+rc+"' where email='"+email+"';";  
            stmt.executeUpdate(sql1);
            con.commit();
        }
       catch (Exception ex)
        {
            System.out.println(ex);
        }
   }
   
   public void updateaccess(String email)
   {
       try
        {
            Class.forName("org.postgresql.Driver");
            con= DriverManager.getConnection("jdbc:postgresql://localhost:5432/pramod","postgres", "1234");
            con.setAutoCommit(false);
            stmt=con.createStatement();
            String access="yes";
            String sql1="update userinfo set access='"+access+"' where email='"+email+"';";  
            stmt.executeUpdate(sql1);
            con.commit();
        }
       catch (Exception ex)
        {
            System.out.println(ex);
        }
   }
   
   public void updatepsw(String newpsw,String email)
   {
       try
        {
            Class.forName("org.postgresql.Driver");
            con= DriverManager.getConnection("jdbc:postgresql://localhost:5432/pramod","postgres", "1234");
            con.setAutoCommit(false); 
            stmt=con.createStatement();
            String sql1="update userinfo set psw='"+newpsw+"' where email='"+email+"';";  
            stmt.executeUpdate(sql1);
            con.commit();
        }
       catch (Exception ex)
        {
            System.out.println(ex);
        }
   }
   
   public void inserttrans(int zid,String trans,int withdraw,int rc)
   {
       try
        {
            Class.forName("org.postgresql.Driver");
            con= DriverManager.getConnection("jdbc:postgresql://localhost:5432/pramod","postgres", "1234");
            con.setAutoCommit(false);
            stmt=con.createStatement();
            String sql2="insert into trans(zid,trans,amount,current_date_and_time,balance) values ('"+zid+"','"+trans+"','"+withdraw+"',CURRENT_TIMESTAMP,'"+rc+"');";
            stmt.executeUpdate(sql2);
            con.commit();
        }
       catch (Exception ex)
        {
            System.out.println(ex);
        }
   }
   
   public String select(String email)
   {
       try
        {
            Class.forName("org.postgresql.Driver");
            con= DriverManager.getConnection("jdbc:postgresql://localhost:5432/pramod","postgres", "1234");
            con.setAutoCommit(false);
            stmt=con.createStatement();
            String sql="select * from userinfo where email='"+email+"';";  
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next())
            {  
                return rs.getString(7);
            }
           con.commit();
        }
       catch (Exception ex)
        {
            System.out.println(ex);
        }
       return null;
   }
   
   public int selecttrans(String db,String col,String email)
   {
       try
        {
            Class.forName("org.postgresql.Driver");
            con= DriverManager.getConnection("jdbc:postgresql://localhost:5432/pramod","postgres", "1234");
            con.setAutoCommit(false);
            stmt=con.createStatement();
            PreparedStatement ps=con.prepareStatement("select * from "+db+" where "+col+"=?");  
            ps.setString(1,email);  
            ResultSet rs=ps.executeQuery();
            if(rs.next())
            {  
               return rs.getInt("zid");
            }
            con.commit();
        }
       catch (Exception ex)
        {
            System.out.println(ex);
        }
       return 0;
   }
   
   public ResultSet selectresultset(int zid,String db)
   {
        try
        {
            Class.forName("org.postgresql.Driver");
            con= DriverManager.getConnection("jdbc:postgresql://localhost:5432/pramod","postgres", "1234");
            con.setAutoCommit(false);
            stmt=con.createStatement();
            String sql="select * from "+db+" where zid='"+zid+"';";  
            return stmt.executeQuery(sql);    
         }
       catch (Exception ex)
        {
            System.out.println(ex);
        }
       return null;
   }
   
   public ResultSet selectresultset1(String email,String db)
   {
        try
        {
           Class.forName("org.postgresql.Driver");
           con= DriverManager.getConnection("jdbc:postgresql://localhost:5432/pramod","postgres", "1234");
           con.setAutoCommit(false);
           stmt=con.createStatement();
           String sql="select * from "+db+" where email='"+email+"';"; 
           con.commit();
           return stmt.executeQuery(sql);
            
         }
       catch (Exception ex)
        {
            System.out.println(ex);
        }
       return null;
   }  
   
   public  ResultSet selectall(String db)
   {
       try
        {
            Class.forName("org.postgresql.Driver");
            con= DriverManager.getConnection("jdbc:postgresql://localhost:5432/pramod","postgres", "1234");
            con.setAutoCommit(false);
            stmt=con.createStatement();
            String sql="select * from "+db+";";  
            con.commit();
            return stmt.executeQuery(sql);  
        }
       catch (Exception ex)
        {
            System.out.println(ex);
        }
       return null;
   }
}
