package platine.lille1.univ.fr.finegardens;


import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;

import android.widget.Button;
import android.widget.EditText;


/**
 * A login screen that offers login via login/password.
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    //Firebase
    private Firebase mRef;
    // UI references.
    private EditText mPasswordView;
    private EditText mLoginView;
    private Button mBtnLoginView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginView = (EditText) findViewById(R.id.input_login);
        mPasswordView = (EditText) findViewById(R.id.input_password);

        mBtnLoginView = (Button) findViewById(R.id.btn_login);
        mBtnLoginView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            }
        });

    }


}

