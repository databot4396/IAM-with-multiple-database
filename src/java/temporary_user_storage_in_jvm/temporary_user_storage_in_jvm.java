package temporary_user_storage_in_jvm;

import java.util.LinkedHashMap;
import java.util.Map;

public class temporary_user_storage_in_jvm 
{
    public static Map<String,user_creation> users = new LinkedHashMap<>();
    public static void add(String email,user_creation e)
    {
        users.put(email,e);
    }
    public static user_creation get(String email)
    {
        return users.get(email);
    }
    public static void remove(String email)
    {
        users.remove(email);
    }
    public static Map<String,user_creation> getall() 
    {
         return users;
    } 
}
