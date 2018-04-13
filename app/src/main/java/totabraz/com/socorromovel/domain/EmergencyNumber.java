package totabraz.com.socorromovel.domain;

/**
 * Created by totabraz on 12/04/18.
 */

public class EmergencyNumber {
    private String phone;
    private String name;

    public EmergencyNumber() {

    }

    public EmergencyNumber(String name, String phone) {
        this.phone = phone;
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
