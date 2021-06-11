import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import  temporary_user_storage_in_jvm.current_user;
import database_interface.mysql;
import database_interface.pgsql;
import temporary_user_storage_in_jvm.temporary_user_storage_in_jvm;
import temporary_user_storage_in_jvm.user_creation;

public class user_transactions extends HttpServlet
{
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException 
    {
        response.setContentType("text/html;charset=UTF-8");
        
        PrintWriter out = response.getWriter();
        
        String modalForm = request.getParameter("modalForm");
        
        current_user user=new current_user();
        
        temporary_user_storage_in_jvm lc=new temporary_user_storage_in_jvm();
        
        user_creation details_of_user=null;   
        
        mysql user_trans_mysql=new mysql();
        
        pgsql user_trans_pgsql=new pgsql();
        
        try 
        {
            // deposit by user
            
            if("deposit".equals(modalForm))
            {
                String user_email =user.getemail();
                int rc=0;
                int zid=0;
                int row=0;
                details_of_user=lc.get(user_email);
                int deposit_amount = Integer.valueOf(request.getParameter("uname"));
                ResultSet rs=user_trans_mysql.selectresultset1(user_email,"userinfo");
                if(rs.next())
                {  row=rs.getRow();
                   rc=rs.getInt(6);
                   zid=rs.getInt("zid");
                }
                if(row==0)
                {
                    ResultSet rs1=user_trans_pgsql.selectresultset1(user_email,"userinfo");
                    if(rs1.next())
                    {
                       rc=rs1.getInt(6);  
                       zid=rs1.getInt("zid");
                    }   
                   }
                rc=rc+deposit_amount;
                details_of_user.setrc(rc);
                user_trans_mysql.update(rc,user_email);
                user_trans_mysql.inserttrans(zid,"deposit",deposit_amount,rc);
                user_trans_pgsql.update(rc,user_email);
                user_trans_pgsql.inserttrans(zid,"deposit",deposit_amount,rc);
                out.println("<h1>Deposit successfull</h1>");
            }
            
            //withdraw by user
            
            if("withdraw".equals(modalForm))
            {
                String user_email =user.getemail();
                int rc=0;
                int zid=0;
                int row=0;
                details_of_user=lc.get(user_email);
                int withdraw_amount = Integer.valueOf(request.getParameter("uname"));
                ResultSet rs=user_trans_mysql.selectresultset1(user_email,"userinfo");
                if(rs.next())
                {  
                    row=rs.getRow();
                   rc=rs.getInt(6);
                   zid=rs.getInt("zid");
                }
                if(row==0)
                {
                    ResultSet rs1=user_trans_pgsql.selectresultset1(user_email,"userinfo");
                    if(rs1.next())
                    {
                       rc=rs1.getInt(6);  
                       zid=rs1.getInt("zid");
                    }   
                   }
                rc=rc-withdraw_amount;
                details_of_user.setrc(rc);
                user_trans_mysql.update(rc,user_email);
                user_trans_mysql.inserttrans(zid,"withdraw",withdraw_amount,rc);
                user_trans_pgsql.update(rc,user_email);
                user_trans_pgsql.inserttrans(zid,"withdraw",withdraw_amount,rc);
                out.println("<h1>withdraw successfull</h1>");
            }
            
            //user can view the withdraw transaction
            
            if("viewwithdraw".equals(modalForm))
            {
                String email=user.getemail();
                out.println("<h1>WITHDRAW TRANSACTION</h1>");
                int zid=user_trans_mysql.selecttrans("userinfo","email",email);
                ResultSet rs1=user_trans_mysql.selectresultset(zid,"trans");
                out.print("<table border='1' width='100%'");   
                out.print("<tr><th>Transaction</th><th>Amount</th><th>Balance</th><th>Time</th></tr>");
                while(rs1.next())
                {
                    if("withdraw".equals(rs1.getString("trans"))){
                    java.sql.Timestamp dbSqlTimestamp = rs1.getTimestamp(4);
                    out.print("<tr><td>"+rs1.getString(2)+"</td><td>"+rs1.getInt(3)+"</td><td>"+rs1.getInt(5)+"</td><td>"+dbSqlTimestamp+"</td></tr>");        
                }
            }
                out.print("</table>"); 
            }
            
            //for user to view all transactions
            
            if("viewtrans".equals(modalForm))
            {
                String email=user.getemail();
                out.println("<h1>ALL THE TRANSACTIONS</h1>");
                int zid=user_trans_mysql.selecttrans("userinfo","email",email);
                ResultSet rs1=user_trans_mysql.selectresultset(zid,"trans");
                out.print("<table border='1' width='100%'");   
                out.print("<tr><th>Transaction</th><th>Amount</th><th>Balance</th><th>Time</th></tr>");
                while(rs1.next())
                {
                    java.sql.Timestamp dbSqlTimestamp = rs1.getTimestamp(4);
                    out.print("<tr><td>"+rs1.getString(2)+"</td><td>"+rs1.getInt(3)+"</td><td>"+rs1.getInt(5)+"</td><td>"+dbSqlTimestamp+"</td></tr>");        
                }
                out.print("</table>");   
            }
            
            //for user to send amount to another using zid
            
            if("sendrc".equals(modalForm))
            { 
                String user_email =user.getemail();
                String send_email="";
                int rc=0;
                int zid=0;
                int row=0;
                details_of_user=lc.get(user_email);
                int send_zid=Integer.valueOf(request.getParameter("uname"));
                int send_amount=Integer.valueOf(request.getParameter("rc"));
                ResultSet rs=user_trans_mysql.selectresultset1(user_email,"userinfo");
                row=rs.getRow();
                if(rs.next())
                {
                    row=rs.getRow();
                   rc=rs.getInt(6);  
                   zid=rs.getInt("zid");
                }   
                if(row==0)
                {
                    ResultSet rs1=user_trans_mysql.selectresultset1(user_email,"userinfo");
                    if(rs1.next())
                    {
                       rc=rs1.getInt(6);  
                       zid=rs1.getInt("zid");
                    }   
                   }
                rc=rc-send_amount;
                details_of_user.setrc(rc);
                user_trans_mysql.update(rc,user_email);
                user_trans_mysql.inserttrans(zid,"sent",send_amount,rc);
                user_trans_pgsql.update(rc,user_email);
                user_trans_pgsql.inserttrans(zid,"sent",send_amount,rc);
                rc=0;
                row=0;
                ResultSet rs2=user_trans_mysql.selectresultset(zid,"userinfo");
                if(rs2.next())
                {  
                    row=rs2.getRow();
                    rc=rs2.getInt(6);
                    send_email=rs2.getString("email");;
                }
                if(row==0)
                {
                   ResultSet rs22=user_trans_pgsql.selectresultset(zid,"userinfo");
                    if(rs22.next())
                    {  
                        row=rs22.getRow();
                       rc=rs22.getInt(6);  
                    } 
                }   
                details_of_user=lc.get(send_email);
                rc=rc+send_amount;
                details_of_user.setrc(rc);
                user_trans_mysql.update(rc,send_email);
                user_trans_mysql.inserttrans(send_zid,"receive",send_amount,rc);
                user_trans_pgsql.update(rc,send_email);
                user_trans_pgsql.inserttrans(send_zid,"receive",send_amount,rc);
                out.println("successfully transfered");
            }
           
            //for user to see the deposit transaction
            
            if("viewdeposit".equals(modalForm))
            {
                String email=user.getemail();
                out.println("<h1>DEPOSIT TRANSACTION</h1>");
                int zid=user_trans_mysql.selecttrans("userinfo","email",email);
                ResultSet rs1=user_trans_mysql.selectresultset(zid,"trans");
                out.print("<table border='1' width='100%'");   
                out.print("<tr><th>Transaction</th><th>Amount</th><th>Balance</th><th>Time</th></tr>");
                while(rs1.next())
                {
                    if("deposit".equals(rs1.getString("trans")))
                    {
                        java.sql.Timestamp dbSqlTimestamp = rs1.getTimestamp(4);
                        out.print("<tr><td>"+rs1.getString(2)+"</td><td>"+rs1.getInt(3)+"</td><td>"+rs1.getInt(5)+"</td><td>"+dbSqlTimestamp+"</td></tr>");        
                    }
                }
                out.print("</table>");     
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
        finally 
        {
            out.close();
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException 
    {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
    {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
