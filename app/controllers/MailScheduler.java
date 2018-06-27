/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

/**
 *
 * @author indra
 */
import java.util.*;
import models.Contact;
import models.User;
import java.text.*;

public class MailScheduler {

    public String callScheduler(User user, Contact contact) {
        System.out.println("Mail Scheduled...");
        Timer timer = new Timer();
        
        Date birthday = contact.birthdate;
        Calendar cal = Calendar.getInstance();
        cal.setTime(birthday);
        int currYear = Calendar.getInstance().get(Calendar.YEAR);
        cal.set(Calendar.YEAR, currYear);
        birthday = cal.getTime();
        
        int hrs = contact.birthdayReminder;
        Date scheduled = new Date(birthday.getTime() - (hrs * 3600 * 1000));
        String timeScheduled = new SimpleDateFormat("MM/dd/yyyy HH:mm a").format(scheduled);
        System.out.println("Mail Time Scheduled: " +timeScheduled);
        timer.schedule(new Testing(user, contact), scheduled);
        return timeScheduled;
    }
}
