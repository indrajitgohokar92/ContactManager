package models;

import play.*;
import play.db.jpa.*;
import play.data.validation.*;

import javax.persistence.*;
import java.util.*;

@Entity
public class Contact extends Model {
    
    @Required
    public String firstname;
    
    @Required
    public String lastname;
    
    @Required
    public Date birthdate;
    
    @Required
    @Email
    public String email;
    
        
    
    @Range(min = 1, max = 24)
    public int birthdayReminder;
    
}

