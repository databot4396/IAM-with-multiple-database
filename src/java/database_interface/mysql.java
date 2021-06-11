
package database_interface;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class mysql 
{
    public void insert(String name,long number,String email,String hid,int rc,String psw1,String access)
   {
       try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/zoho","root","Tharani.sri1");
            PreparedStatement ps = con.prepareStatement("insert into userinfo(name,phone,email,hid,rc,psw,access) values(?,?,?,?,?,?,?)");
            ps.setString(1, name);
            ps.setLong(2, number);
            ps.setString(3,email);
            ps.setString(4,hid);
            ps.setInt(5,rc);
            ps.setString(6,psw1);
            ps.setString(7,access);
            ps.executeUpdate();
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
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/zoho","root","Tharani.sri1");
            PreparedStatement ps1=con.prepareStatement("update userinfo set rc=? where email=?");  
            ps1.setInt(1,rc);  
            ps1.setString(2,email);
            ps1.executeUpdate();
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
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/zoho","root","Tharani.sri1");
            PreparedStatement ps1=con.prepareStatement("update userinfo set access=? where email=?");  
            ps1.setString(1,"yes");  
            ps1.setString(2,email);
            ps1.executeUpdate();
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
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/zoho","root","Tharani.sri1");
            PreparedStatement ps1=con.prepareStatement("update userinfo set psw=? where email=?");
            ps1.setString(1,newpsw);  
            ps1.setString(2,email);
            ps1.executeUpdate();
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
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/zoho","root","Tharani.sri1");
            PreparedStatement ps2=con.prepareStatement("insert into trans(zid,trans,amount,current_date_and_time,balance) values(?,?,?,CURRENT_TIMESTAMP,?)");
            ps2.setInt(1,zid);  
            ps2.setString(2,trans);
            ps2.setInt(3,withdraw);
            ps2.setInt(4,rc);
            ps2.executeUpdate();
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
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/zoho","root","Tharani.sri1");
            PreparedStatement ps=con.prepareStatement("select * from userinfo where email=?");  
            ps.setString(1,email);  
            ResultSet rs=ps.executeQuery();
            if(rs.next())
                {  
                   return rs.getString(7);
                }
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
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/zoho","root","Tharani.sri1");
            PreparedStatement ps=con.prepareStatement("select * from "+db+" where "+col+"=?");  
            ps.setString(1,email);  
            ResultSet rs=ps.executeQuery();
            if(rs.next())
            {  
               return rs.getInt("zid");
            }
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
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/zoho","root","Tharani.sri1");
            PreparedStatement ps2=con.prepareStatement("select * from "+db+" where zid=?");
            ps2.setInt(1,zid); 
            return ps2.executeQuery();
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
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/zoho","root","Tharani.sri1");
            PreparedStatement ps2=con.prepareStatement("select * from "+db+" where email=?");
            ps2.setString(1,email); 
            return ps2.executeQuery();
        }
       catch (Exception ex)
        {
            System.out.println(ex);
        }
       return null;
   } 
   
    public ResultSet selectall(String db)
   {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/zoho","root","Tharani.sri1");
            PreparedStatement ps=con.prepareStatement("select * from "+db+"");
            return ps.executeQuery();
         }
       catch (Exception ex)
        {
            System.out.println(ex);
        }
       return null;
   }
}
