package sellit.android.cs4251.com.sellit;

/**
 * Created by USER on 3/5/2018.
 */

public class UserInformation {
        private String Firstname;
        private String Lastname;
        private String Displayname;
        private String email;
        private String password;

    public UserInformation(){

    }
        public UserInformation(String f_name, String l_name, String d_name,String Email_ID,String n_pass) {
            this.Firstname=f_name;
            this.Lastname=l_name;
            this.Displayname=d_name;
            this.email = Email_ID;
            this.password = n_pass;
        }

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        this.Firstname = firstname;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastname) {
        this.Lastname = lastname;
    }

    public String getDisplayname() {
        return Displayname;
    }

    public void setDisplayname(String displayname) {
       this.Displayname = displayname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

