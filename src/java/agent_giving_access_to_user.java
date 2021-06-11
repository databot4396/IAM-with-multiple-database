import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import database_interface.mysql;
import database_interface.pgsql;

public class agent_giving_access_to_user extends HttpServlet
{
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException 
    {
        response.setContentType("text/html;charset=UTF-8");
        
        String modalForm = request.getParameter("modalForm");
        
        PrintWriter out = response.getWriter();
        
        //agent giving access to user login
        
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/zoho","root","Tharani.sri1");
            String email=request.getParameter("id"); 
            String access="yes";
            mysql access_mysql=new mysql();
            pgsql access_pgsql=new pgsql();
            access_mysql.updateaccess(email);
            access_pgsql.updateaccess(email);
            out.println("<h2>Access for the user is given successfully</h2>");
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        } finally {
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
