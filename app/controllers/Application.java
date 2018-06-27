package controllers;

import java.util.Date;
import java.util.List;
import play.mvc.*;
import play.data.validation.*;

import models.*;
/**
 *
 * @author indra
 */

public class Application extends Controller {

    @Before
    static void addUser() {
        User user = connected();
        if (user != null) {
            renderArgs.put("user", user);
        }
    }

    static User connected() {
        if (renderArgs.get("user") != null) {
            return renderArgs.get("user", User.class);
        }
        String username = session.get("user");
        if (username != null) {
            return User.find("byUsername", username).first();
        }
        return null;
    }

    // ~~
    public static void login(String username, String password) {
        User user = User.find("byUsernameAndPassword", username, password).first();
        if (user != null) {
            session.put("user", user.username);
            session.put("eId", user.emailId);
            home();
        }
        // Oops

        flash.error("Login failed");
        index();
    }

    public static void index() {
        if (connected() != null) {
            Date now = new Date();
            render(now);
        }
        render();
    }

    public static void home() {
        if (connected() != null) {
            render();
        }
    }

    public static void register() {
        render();
    }

    public static void saveUser(@Valid User user, String verifyPassword) {
        validation.required(verifyPassword);
        validation.equals(verifyPassword, user.password).message("Your password doesn't match");
        if (validation.hasErrors()) {
            render("@register", user, verifyPassword);
        }
        user.create();
        index();
    }

    public static void listContacts() {
        List<Contact> contacts = Contact.find("order by birthdate desc").fetch();
        render(contacts);
    }

    public static void callReminder(Long id) {
        if (id == null) {
            render();
        }
        Contact contact = Contact.findById(id);
        render(contact);
    }

    public static void editContact(Long id) {
        if (id == null) {
            render();
        }
        Contact contact = Contact.findById(id);
        render(contact);
    }

    public static void saveContact(@Valid Contact contact) {
        if (validation.hasErrors()) {
            if (request.isAjax()) {
                error("Invalid value");
            }
            render("@editContact", contact);
        }
        contact.save();
        flash.success("Contact saved!");
        listContacts();
    }

    public static void sendReminder(@Valid Contact contact) {
        User user = connected();
        String contactName = contact.firstname + " " + contact.lastname;
        if (validation.hasErrors()) {
            if (request.isAjax()) {
                error("Invalid value");
            }
            render("@callReminder", contact);
        }
        MailScheduler sch = new MailScheduler();
        String schTime = sch.callScheduler(user, contact);

        flash.success("Birthday reminder for " + contactName + " set for " + schTime);
        listContacts();
    }

    public static void settings() {
        render();
    }

    public static void saveSettings(String password, String verifyPassword) {
        User connected = connected();
        connected.password = password;
        validation.valid(connected);
        validation.required(verifyPassword);
        validation.equals(verifyPassword, password).message("Your password doesn't match");
        if (validation.hasErrors()) {
            render("@settings", connected, verifyPassword);
        }
        connected.save();
        flash.success("Password updated");
        home();
    }

    public static void logout() {
        session.clear();
        index();
    }

}
