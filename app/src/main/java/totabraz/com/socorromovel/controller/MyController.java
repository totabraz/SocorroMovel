package totabraz.com.socorromovel.controller;

import java.util.ArrayList;

import totabraz.com.socorromovel.dao.FirebaseDao;
import totabraz.com.socorromovel.domain.EmergencyNumber;

/**
 * Created by totabraz on 13/04/18.
 */

public class MyController {
    private FirebaseDao firebaseDao;

    public MyController() {
        this.firebaseDao = new FirebaseDao();
    }

    public ArrayList<EmergencyNumber> getEmergencyNumbers() {
        return firebaseDao.getEmegerncyNumber();
    }
}
