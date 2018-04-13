package totabraz.com.socorromovel.dao;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import totabraz.com.socorromovel.domain.EmergencyNumber;
import totabraz.com.socorromovel.utils.SysUtils;

/**
 * Created by totabraz on 13/04/18.
 */

public class FirebaseDao {


    private final String TAG;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private String userCPF;

    public FirebaseDao() {
        this.TAG = "FirebaseDao";
        this.mAuth = FirebaseAuth.getInstance();
        this.mDatabase = FirebaseDatabase.getInstance().getReference();
    }
//
//    private FirebaseAuth getAuth(){
//        return FirebaseAuth.getInstance();
//    }
//
//    public ArrayList<EmergencyNumber> getEmegerncyNumber() {
//        final ArrayList<EmergencyNumber> numbers = new ArrayList<>();
//        ValueEventListener numbersListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
//                    numbers.add(objSnapshot.getValue(EmergencyNumber.class));
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Getting Post failed, log a message
//                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
//            }
//
//            @Override
//            protected void finalize() throws Throwable {
//                super.finalize();
//            }
//        };
//        mDatabase.child(SysUtils.FB_EMERGENCY_NUMBERS).addValueEventListener(numbersListener);
//        if (numbers.size() < 1) return numbers;
//        else return null;
//    }
}
