package platine.lille1.univ.fr.finegardens.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import platine.lille1.univ.fr.finegardens.R;
import platine.lille1.univ.fr.finegardens.entities.User;

public class SignUpActivity extends AppCompatActivity {

    private EditText lastnameEditText;
    private EditText firstnameEditText;
    private EditText mailEditText;
    private EditText loginEditText;
    private EditText passwordEditText;
    private EditText passwordConfirmationEditText;
    private Button createButton;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        this.loginEditText = findViewById(R.id.login);
        this.lastnameEditText = findViewById(R.id.lastname);
        this.firstnameEditText = findViewById(R.id.firstname);
        this.mailEditText = findViewById(R.id.mail);
        this.passwordEditText = findViewById(R.id.password);
        this.passwordConfirmationEditText = findViewById(R.id.password_confirmation);
        this.createButton = findViewById(R.id.create);
        this.createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lastname = lastnameEditText.getText().toString();
                String firstname = firstnameEditText.getText().toString();
                String mail = mailEditText.getText().toString();
                final String login = loginEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String passwordConfirmation = passwordConfirmationEditText.getText().toString();

                if (lastname.matches("") || firstname.matches("")
                        || mail.matches("") || login.matches("")
                        || password.matches("") || passwordConfirmation.matches("")) {
                    Toast.makeText(SignUpActivity.this, "Veuillez renseigner tous les champs", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(passwordConfirmation)) {
                    Toast.makeText(SignUpActivity.this, "Les deux mots de passe ne sont pas identiques", Toast.LENGTH_SHORT).show();
                } else {

                    User user = new User(lastname, firstname, mail, login);

                    mDatabase.child("Utilisateurs").child(mDatabase.push().getKey()).setValue(user);

                    mAuth.createUserWithEmailAndPassword(user.getMail(), password)
                            .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {

                                        FirebaseAuth.AuthStateListener mAuthListener = new FirebaseAuth.AuthStateListener() {
                                            @Override
                                            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                                if(user!=null){
                                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                            .setDisplayName(login).build();
                                                    user.updateProfile(profileUpdates);
                                                    //Intent intent = new Intent(l.this, nextActivity.class);
                                                   // startActivity(intent);
                                                }
                                            }
                                        };
                                        Log.d("SUCCESS : ", "createUserWithEmail:success");
                                    } else {
                                        Log.w("ERROR : ", "createUserWithEmail:failure", task.getException());
                                    }
                                }
                            });


                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    intent.putExtra("mail", user.getMail());
                    setResult(LoginActivity.RESULT_OK, intent);
                    finish();
                }

            }
        });

    }


}
