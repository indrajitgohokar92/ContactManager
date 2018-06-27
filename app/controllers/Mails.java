/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;
import models.Contact;
import models.User;
import play.mvc.Mailer;

/**
 *
 * @author indra
 */
public class Mails extends Mailer {

    public static void emailReminder(User user, Contact contact) {
        String contactName = contact.firstname + " " + contact.lastname;
        setSubject("Birthday reminder for %s", contactName);
        addRecipient(user.emailId);
        setFrom("Birthday reminder <rohit@gmail.com>");
        send(user, contact);
        System.out.println("Mail Sent...");
    }

}
