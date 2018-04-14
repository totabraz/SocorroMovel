package totabraz.com.socorromovel.domain;

import java.util.ArrayList;

/**
 * Created by totabraz on 14/04/18.
 */

public class User {
    private String Name;
    private String Email;
    private String PhoneNumber;
    private ArrayList<Smartphone> mySmartphones;

    public User() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public ArrayList<Smartphone> getMySmartphones() {
        return mySmartphones;
    }

    public void setMySmartphones(ArrayList<Smartphone> mySmartphones) {
        this.mySmartphones = mySmartphones;
    }
}
