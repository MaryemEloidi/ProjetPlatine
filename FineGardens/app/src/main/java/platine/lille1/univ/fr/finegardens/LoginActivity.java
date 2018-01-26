package platine.lille1.univ.fr.finegardens;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import platine.lille1.univ.fr.finegardens.Controller.AuthenticationController;


/**
 * A login screen that offers login via login/password.
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * Id to identity READ_CONTACTS permission request.
     */

    // UI references.
    private EditText mPasswordView;
    private EditText mEmailView;
    private TextView signUpLink;
    private Button mBtnLoginView;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        mEmailView = findViewById(R.id.input_email);
        mPasswordView = findViewById(R.id.input_password);
        signUpLink = findViewById(R.id.link_signup);
        signUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivityForResult(intent, 1234);
            }
        });

        mBtnLoginView = (Button) findViewById(R.id.btn_login);
        mBtnLoginView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final String email = mEmailView.getText().toString();
                final String password = mPasswordView.getText().toString();


                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Saisir une adrese email!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Saisir un mot de passe!", Toast.LENGTH_SHORT).show();
                    return;
                }
                final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                        R.style.AppTheme_NoActionBar);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Authenticating...");
                progressDialog.show();
                        new android.os.Handler().postDelayed(new Runnable() {
                                public void run() {

                                AuthenticationController.signinWithEmailAndPassword(email, password, LoginActivity.this, new OnCompleteListener<AuthResult>()

                                {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (!task.isSuccessful()) {
                                            // there was an error
                                            if (password.length() < 6) {
                                                mPasswordView.setError("error minimum 6 lettres");
                                            } else {
                                                Toast.makeText(LoginActivity.this, "l'authentification a echoué", Toast.LENGTH_LONG).show();
                                                progressDialog.dismiss();

                                            }
                                        } else {
                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                });

                            }
                        },3000);
        }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1234) {
            if(resultCode == Activity.RESULT_OK){
                mEmailView.setText(data.getStringExtra("mail"));
                Toast.makeText(this, "Création du compte terminée", Toast.LENGTH_SHORT).show();
            }
        }
    }

}

