package temporary_user_storage_in_jvm;

public class current_user 
{
     static String email;
    public static String getemail()
    {  
        return email;  
    }  
    public void setemail(String email) {  
        this.email =email;  
    }   
}
