
package database_interface;

import java.sql.ResultSet;


public interface database
{
    void insert(String name,long number,String email,String hid,int rc,String psw1,String access);
    void update(int rc,String email);
    void updateaccess(String email);
    void updatepsw(String newpsw,String email);
    void inserttrans(int zid,String trans,int withdraw,int rc);
    String select(String email);
    int selecttrans(String db,String col,String email);
    ResultSet selectresultset(int zid,String db);
    ResultSet selectresultset1(String email,String db);
    ResultSet selectall(String db);
}
