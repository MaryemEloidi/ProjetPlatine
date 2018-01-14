package platine.lille1.univ.fr.finegardens.Controller;

import android.app.Activity;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;



/**
 * Created by Maryem on 14/01/2018.
 */

public class AuthenticationController {
    private static final FirebaseAuth auth = FirebaseAuth.getInstance();
    public static void signinWithEmailAndPassword(String email, final String mdp, final Activity activity, OnCompleteListener<AuthResult> onCompleteListener){
        auth.signInWithEmailAndPassword(email,mdp).addOnCompleteListener(activity, onCompleteListener);
    }
}
