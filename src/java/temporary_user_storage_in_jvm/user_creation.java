package temporary_user_storage_in_jvm;

import java.util.ArrayList;

public class user_creation
{
    public String access,name,email,hid,psw;
    public int zid;
    public int rc;
    public long phone;
    public ArrayList<String> trans=new ArrayList<String>();
    public ArrayList gettrans()
    {  
        return trans;  
    }  
    public void settrans(String trans1) 
    {  
        trans.add(trans1);  
    }
    public String getname()
    {  
        return name;  
    }  
    public void setname(String name) 
    {  
        this.name = name;  
    }
    public int getzid()
    {  
        return zid;  
    }  
    public void setzid(int zid) 
    {  
        this.zid = zid;  
    }
    public String getaccess()
    {  
        return access;  
    }  
    public void setaccess(String access) 
    {  
        this.access = access;  
    }  
    public String getemail()
    {  
        return email;  
    }  
    public void setemail(String email) 
    {  
        this.email = email;  
    }  
    public long getphone() 
    {  
      return phone;  
    }  
    public void setphone(long phone) 
    {  
     this.phone = phone;  
    }  
    public String gethid()
    {  
        return hid;  
    }  
    public void sethid(String hid) 
    {  
        this.hid = hid;  
    }  
    public int getrc()
    {  
        return rc;  
    }  
    public void setrc(int rc) 
    {  
        this.rc = rc;  
    }  
    public String getpsw()
    {  
      return psw;  
    }  
    public void setpsw(String password)
    {  
      this.psw = password;  
    } 
    public void create(String name,long phone,String email,String psw,String hid,int rc,int zid,String access)   
    {
        user_creation user_create=new user_creation();  
        temporary_user_storage_in_jvm user_store=new temporary_user_storage_in_jvm();
        user_create.setname(name);
        user_create.setphone(phone);
        user_create.setemail(email);
        user_create.setpsw(psw);
        user_create.sethid(hid);
        user_create.setrc(rc);
        user_create.setzid(zid);
        user_create.setaccess(access);
        user_store.add(email,user_create);
    }
}
