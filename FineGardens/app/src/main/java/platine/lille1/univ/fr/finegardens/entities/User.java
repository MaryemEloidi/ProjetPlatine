package platine.lille1.univ.fr.finegardens.entities;

import java.util.ArrayList;

public class User {

    private String lastname;
    private String firstname;
    private String mail;
    private String login;
    private ArrayList<String> favoris;

    public void setFavoris(ArrayList<String> favoris) {
        this.favoris = favoris;
    }

    public ArrayList<String> getFavoris() {

        return favoris;
    }

    public User() { }

    public User(String lastname, String firstname, String mail, String login) {
        this.favoris = new ArrayList<String>();
        this.lastname = lastname;
        this.firstname = firstname;
        this.mail = mail;
        this.login = login;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String toString() {
       return  ""+ login;
    }
}
