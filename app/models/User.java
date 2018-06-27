package models;

import play.db.jpa.*;
import play.data.validation.*;
import javax.persistence.*;

@Entity
@Table(name="Customer")
public class User extends Model {
    
    @Required
    @MaxSize(15)
    @MinSize(4)
    @Match(value="^\\w*$", message="Not a valid username")
    public String username;
    
    @Required
    @MaxSize(15)
    @MinSize(4)
    public String password;
    
    @Required
    @MaxSize(100)
    public String name;
    
    @Required
    @Email
    @MaxSize(50)
    public String emailId;

    
    public User(String name, String password, String username, String emailId) {
        this.name = name;
        this.password = password;
        this.username = username;
        this.emailId = emailId;
    }

    public String toString()  {
        return "User(" + username + ")";
    }
    
}
