package platine.lille1.univ.fr.finegardens;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import platine.lille1.univ.fr.finegardens.Controller.JardinController;

public class AjouterJardinActivity extends AppCompatActivity {

    private EditText mNomJardinView;
    private EditText mAdresseJardinView;
    private EditText mDescriptionView;
    private Button mBtnAjoutJardin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_jardin);
        mNomJardinView =(EditText)findViewById(R.id.input_nom);
        mAdresseJardinView = (EditText)findViewById(R.id.input_adresse);
        mDescriptionView = (EditText)findViewById(R.id.input_description);
        mBtnAjoutJardin = (Button)findViewById(R.id.btn_ajouter);
        mBtnAjoutJardin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final String nomJardin = mNomJardinView.getText().toString();
                final String adresseJardin = mAdresseJardinView.getText().toString();
                final String descriptionJardin = mDescriptionView.getText().toString();

                if (TextUtils.isEmpty(nomJardin)) {
                    Toast.makeText(getApplicationContext(), "Saisir un nom valide!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(adresseJardin)) {
                    Toast.makeText(getApplicationContext(), "Saisir une adresse valide!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(descriptionJardin)) {
                    Toast.makeText(getApplicationContext(), "Saisir une description !", Toast.LENGTH_SHORT).show();
                    return;
                }
                final ProgressDialog progressDialog = new ProgressDialog(AjouterJardinActivity.this,
                        R.style.AppTheme_NoActionBar);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Enregistrement...");
                progressDialog.show();
                new android.os.Handler().postDelayed(new Runnable() {
                    public void run() {
                        JardinController.ajouterJardin(nomJardin,adresseJardin,descriptionJardin);
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Le jardin a été bien ajouté !", Toast.LENGTH_LONG).show();

                    }
                },3000);

            }

            });
    }
}
