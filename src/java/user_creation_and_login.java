import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import database_interface.mysql;
import database_interface.pgsql;
import temporary_user_storage_in_jvm.temporary_user_storage_in_jvm;
import temporary_user_storage_in_jvm.user_creation;
import temporary_user_storage_in_jvm.current_user;

public class user_creation_and_login extends HttpServlet
{
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException 
    {
        response.setContentType("text/html;charset=UTF-8");
        
        PrintWriter out = response.getWriter();
        
        mysql user_mysql=new mysql();
        
        pgsql user_pgsql=new pgsql();
        
        user_creation new_user=new user_creation();
        
        current_user CurrentUser=new current_user();
        
        temporary_user_storage_in_jvm lc=new temporary_user_storage_in_jvm();
        
        String modalForm = request.getParameter("modalForm");
        
        try
        {    
            // userlogin
            
            if("userlogin".equals(modalForm))
            {
                String user_email = request.getParameter("uname");
                String user_password = request.getParameter("psw");
                int row=0;
                user_creation details_of_user=null;
                details_of_user=lc.get(user_email);
                Map<String,user_creation> getting_all_users = new LinkedHashMap<>();
                getting_all_users=lc.getall();
                int size=getting_all_users.size();
                if(details_of_user==null&&size<=2)
                {
                    ResultSet rs=user_mysql.selectresultset1(user_email,"userinfo");
                   
                    if(rs.next())
                    {     
                         row=rs.getRow();
                       new_user.create(rs.getString(1),rs.getLong(3),user_email,rs.getString(7),rs.getString(4),rs.getInt(6),rs.getInt(5),rs.getString(8));       
                    }   
                    if(row==0)
                    {
                        ResultSet rs1=user_pgsql.selectresultset1(user_email,"userinfo");
                        if(rs1.next())
                        {  
                            new_user.create(rs1.getString(1),rs1.getLong(3),user_email,rs1.getString(7),rs1.getString(4),rs1.getInt(6),rs1.getInt(5),rs1.getString(8));
                        }   
                    }
                }  
                row=0;
                if(details_of_user==null&&size>2)
                {
                    ResultSet rs1=user_mysql.selectresultset1(user_email,"userinfo");
                    
                    if(rs1.next())
                    {  
                            row=rs1.getRow();
                            getting_all_users=lc.getall();
                            Map.Entry<String,user_creation> entry = getting_all_users.entrySet().iterator().next();
                            String key=entry.getKey();
                            lc.remove(key);
                            new_user.create(rs1.getString(1),rs1.getLong(3),user_email,rs1.getString(7),rs1.getString(4),rs1.getInt(6),rs1.getInt(5),rs1.getString(8));
                    }
                    if(row==0)
                    {
                        ResultSet rs11=user_pgsql.selectresultset1(user_email,"userinfo");
                        if(rs11.next())
                        {   
                            if(user_password.equals(rs11.getString(7)))
                            {
                                getting_all_users=lc.getall();
                                Map.Entry<String,user_creation> entry = getting_all_users.entrySet().iterator().next();
                                String key=entry.getKey();
                                lc.remove(key);
                                new_user.create(rs11.getString(1),rs11.getLong(3),user_email,rs11.getString(7),rs11.getString(4),rs11.getInt(6),rs11.getInt(5),rs11.getString(8));
                            }
                        }
                    }
                }
                details_of_user=lc.get(user_email);
                 if("yes".equals(details_of_user.getaccess()))
                 {
                        if(user_password.equals(details_of_user.getpsw()))
                        {
                            CurrentUser.setemail(user_email);
                            out.println("<h1>Login successfull</h1>");
                            out.println("<h3>Welcome :"+details_of_user.getname()+"</h3>");
                            out.println("<h3>Email id :" +details_of_user.getemail()+"</h3>");
                            out.println("<h3>Phone number :" +details_of_user.getphone()+"</h3>");
                            out.println("<h3>H_id :" +details_of_user.gethid()+"</h3>");
                            out.println("<h3>RC :" +details_of_user.getrc()+"</h3>");
                            out.println("<h3>ZID :" +details_of_user.getzid()+"</h3>");
                            out.println("<form action='user_transactions' method='get'>");  
                            out.println("<input type=hidden name=modalForm value=deposit>");
                            out.println("Deposit :<input type=text name=uname>");
                            out.println("<input type=submit value=ok name=modalForm>");
                            out.println("</form>");
                            out.println("<br><br>");
                            out.println("<form action='user_transactions' method='get'>");  
                            out.println("<input type=hidden name=modalForm value=withdraw>");
                            out.println("withdraw :<input type=text name=uname>");
                            out.println(" <input type=submit value=ok>");
                            out.println("</form>");
                            out.println("<br><br>");
                            out.println("<form action='user_transactions' method='get'>");  
                            out.println("<input type=hidden name=modalForm value=viewtrans>");
                            out.println("To view your transaction");
                            out.println(" <input type=submit value=Transaction>");
                            out.println("</form>");
                            out.println("<br><br>");
                            out.println("<form action='user_transactions' method='get'>");  
                            out.println("<input type=hidden name=modalForm value=viewdeposit>");
                            out.println("To view your deposit history");
                            out.println(" <input type=submit value=Transaction>");
                            out.println("</form>");
                            out.println("<br><br>");
                            out.println("<form action='user_transactions' method='get'>");  
                            out.println("<input type=hidden name=modalForm value=viewwithdraw>");
                            out.println("To view your withdraw history");
                            out.println(" <input type=submit value=Transaction>");
                            out.println("</form>");
                            out.println("<br><br>");
                            out.println("<a href=user_password_change.jsp>Change password</a>");
                            out.println("<br><br>");
                            out.println("<form action='user_transactions' method='get'>");  
                            out.println("<input type=hidden name=modalForm value=sendrc>");
                            out.println("Enter the ZID you need to send rc :<input type=text name=uname>");
                            out.println("<br><br>");
                            out.println("Enter rc :<input type=text name=rc>");
                            out.println("<br><br>");
                            out.println("<input type=submit value=ok>");
                            out.println("</form><br><br>");
                             out.println("<a href=user_login.jsp><button type=\"button\">LOGOUT</button></a>");
                        }
                        else
                        {
                            RequestDispatcher rd=request.getRequestDispatcher("/UserLoginFailed.jsp");
                            rd.forward(request, response);
                        }
                }
                else
                {
                    RequestDispatcher rd=request.getRequestDispatcher("/NoAccessForUserToLogin.jsp");
                    rd.forward(request, response);
                }
            }
            
            //user creation
            
            if("usercreation".equals(modalForm))
            {
                String user_name = request.getParameter("name");
                long user_number = Long.parseLong(request.getParameter("number"));
                String user_email = request.getParameter("email");
                String user_password = request.getParameter("psw");
                String user_hid = request.getParameter("h_id"); 
                int user_recharge = Integer.valueOf(request.getParameter("rc"));
                String user_repeat_password = request.getParameter("psw-repeat");
                String user_access="no";
                String regex = "^(?=.*[0-9])"+ "(?=.*[a-z])(?=.*[A-Z])"+ "(?=.*[@#$%^&+=])"+ "(?=\\S+$).{8,20}$";
                Pattern p = Pattern.compile(regex);
                Matcher m = p.matcher(user_password);
                boolean val=m.matches();
           //   if(val==true)
              //{
                    if(user_repeat_password.equals(user_password))
                    {        
                        user_mysql.insert(user_name,user_number,user_email,user_hid,user_recharge,user_password,user_access);
                        user_pgsql.insert(user_name,user_number,user_email,user_hid,user_recharge,user_password,user_access);
                        RequestDispatcher rd=request.getRequestDispatcher("/SignupSuccess.jsp");  
                        rd.forward(request, response);
                    }
                    else
                    {
                        RequestDispatcher rd=request.getRequestDispatcher("/IncorrectPassword.jsp");  
                        rd.forward(request, response);
                    }
                /*}
                    else
                    {
                        RequestDispatcher rd=request.getRequestDispatcher("/WrongPasswordcretiria.jsp");  
                        rd.forward(request, response);
                    }*/
            }
            
            // change password by user
            
            if("changepsw".equals(modalForm))
            {
                String user_email = request.getParameter("uname");
                String user_password = request.getParameter("psw");
                String user_newpassword = request.getParameter("psw1");
                String oldpassword=user_mysql.select(user_email);
                if(user_password.equals(oldpassword))
                {    
                    user_mysql.updatepsw(user_newpassword, user_email);
                    user_pgsql.updatepsw(user_newpassword, user_email);
                    RequestDispatcher rd=request.getRequestDispatcher("/UserPasswordChangeSuccess.jsp");
                    rd.forward(request, response);   
                }
                else
                {
                    RequestDispatcher rd=request.getRequestDispatcher("/WrongOldPassword.jsp");
                    rd.forward(request, response);
                }
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
