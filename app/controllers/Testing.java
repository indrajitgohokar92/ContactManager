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
import java.util.TimerTask;
import models.Contact;
import models.User;

public class Testing extends TimerTask {
    User user=null;
    Contact contact=null;
    public Testing(User user, Contact contact){
        this.user=user;
        this.contact=contact;
    }
    @Override
    public void run() {
        Mails.emailReminder(user, contact);
        System.out.println("Mail Sent...");
    }
}
