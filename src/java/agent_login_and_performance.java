import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import database_interface.mysql;
import database_interface.pgsql;

public class agent_login_and_performance extends HttpServlet
{
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        
        PrintWriter out = response.getWriter();
        
        String modalForm = request.getParameter("modalForm");
        
        mysql user_mysql=new mysql();
        
        pgsql user_pgsql=new pgsql();
        
        try 
        {     
            // agent login
            
            if("agentlogin".equals(modalForm))
            {
                String agent_email = request.getParameter("uname");
                String agent_password = request.getParameter("psw");
                String email="agent@gmail.com";
                String password="agent";
                if(agent_password.equals(password)&&agent_email.equals(email))
                {
                    out.println("<h1>WELCOME ADMIN</h1>");  
                    out.print("<table border='1' width='100%'");   
                    out.print("<tr><th>Nameee</th><th>Email</th><th>Phone</th><th>H_ID</th><th>ZID</th><th>RC</th><th>Access</th></tr>");  
                    ResultSet rs= user_mysql.selectall("userinfo");
                    out.println("<h3>ACCESS NEED TO BE GIVEN FOR THE FOLLOWING USERS</h3>");  
                    while(rs.next())
                    {
                        if(!("yes".equals(rs.getString(8))))
                        {
                           out.print("<tr><td>"+rs.getString(1)+"</td><td>"+rs.getString(2)+"</td><td>"+rs.getString(3)+"</td><td>"+rs.getString(4)+"</td><td>"+rs.getString(5)+"</td><td>"+rs.getString(6)+"</td><td>"+rs.getString(8)+"</td> <td><a href='agent_giving_access_to_user?id="+rs.getString(2)+"'>Give access</a></td>  </tr>");                          
                        }
                    }
                    out.print("</table>");
                    out.println("<br><br><br>");
                    out.println("<a href=agent_password_change.jsp>Change password</a>");
                    out.println("<br><br><br>");
                    out.println("<form action='agent_login_and_performance' method='get'>");  
                    out.println("<input type=hidden name=modalForm value=viewtransbyadmin>");
                    out.println("Enter the ZID you need to see transaction :<input type=text name=uname>");                
                    out.println(" <input type=submit value=ok>");
                    out.println("</form>");
                    out.println("<form action='agent_login_and_performance' method='get'>");  
                    out.println("<input type=hidden name=modalForm value=allusers>");
                    out.println("Do you want to see all users that you have given access:");                
                    out.println(" <input type=submit value=ok>");
                    out.println("</form>");
                    out.println("<a href=agent_login.jsp><button type=\"button\">LOGOUT</button></a>");
                }
                else
                {
                    RequestDispatcher rd=request.getRequestDispatcher("/IncorrectAgentPassword.jsp");
                    rd.forward(request, response);
                }
            }  
            
            //agent password change for users
            
            if("adminchangepsw".equals(modalForm))
            {
                String user_email = request.getParameter("uname");
                String user_password = request.getParameter("psw");
                user_mysql.updatepsw(user_password,user_email);
                user_pgsql.updatepsw(user_password,user_email);
                out.println("<h1>User password changed successfully</h1>");
            }
            
            //for viewing all the users by agent
            
            if("allusers".equals(modalForm))
            {
                ResultSet rs= user_mysql.selectall("userinfo");
                out.println("<h1>ALL USERS</h1>");  
                out.print("<table border='1' width='100%'");   
                out.print("<tr><th>Nameee</th><th>Email</th><th>Phone</th><th>H_ID</th><th>ZID</th><th>RC</th><th>Access</th></tr>");  
                while(rs.next())
                {
                    if("yes".equals(rs.getString(8)))
                    {
                       out.print("<tr><td>"+rs.getString(1)+"</td><td>"+rs.getString(2)+"</td><td>"+rs.getString(3)+"</td><td>"+rs.getString(4)+"</td><td>"+rs.getString(5)+"</td><td>"+rs.getString(6)+"</td><td>"+rs.getString(8)+"</td>   </tr>");                          
                    }
                }
                out.print("</table>");
            }
            
            //for agent to see transactions of a particular user
            
            if("viewtransbyadmin".equals(modalForm))
            {
                int zid=Integer.valueOf(request.getParameter("uname"));
                ResultSet rs=user_mysql.selectresultset(zid,"trans");
                out.println("<h1>TRANSACTION OF ZID:"+zid+"</h1>");  
                out.print("<table border='1' width='100%'");   
                out.print("<tr><th>Transaction</th><th>Amount</th><th>Balance</th><th>Time</th></tr>");
                while(rs.next())
                {
                    java.sql.Timestamp dbSqlTimestamp = rs.getTimestamp(4);
                    out.print("<tr><td>"+rs.getString(2)+"</td><td>"+rs.getInt(3)+"</td><td>"+rs.getInt(5)+"</td><td>"+dbSqlTimestamp+"</td></tr>");        
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
