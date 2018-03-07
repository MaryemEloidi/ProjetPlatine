package platine.lille1.univ.fr.finegardens.Controller;

import android.app.Activity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

import platine.lille1.univ.fr.finegardens.entities.Jardin;


/**
 * Created by cactus on 21/01/2018.
 */

public class JardinController {
    private static final FirebaseAuth auth = FirebaseAuth.getInstance();
    private static final DatabaseReference mdatabase = FirebaseDatabase.getInstance().getReference();
    public  static void ajouterJardin(String nom, String adresse, String description, double longitude, double latitude){
        String jardinID = mdatabase.push().getKey();

        Jardin jardin = new Jardin(jardinID,nom,adresse,description,longitude,latitude);
        mdatabase.child("Jardins").child(jardinID).setValue(jardin);

    }
    public static void listJardin(){

    }
}
