package platine.lille1.univ.fr.finegardens.fragments;
import platine.lille1.univ.fr.finegardens.*;
import platine.lille1.univ.fr.finegardens.Controller.JardinController;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.*;
import android.os.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * Created by cactus on 16/02/2018.
 */

public class AjouterJardinFragment extends Fragment{
    private EditText mNomJardinView;
    private EditText mAdresseJardinView;
    private EditText mDescriptionView;
    private Button mBtnAjoutJardin;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_ajouter_jardin, container, false);

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mNomJardinView =(EditText)getView().findViewById(R.id.input_nom);
        mAdresseJardinView = (EditText)getView().findViewById(R.id.input_adresse);
        mDescriptionView = (EditText)getView().findViewById(R.id.input_description);
        mBtnAjoutJardin = (Button)getView().findViewById(R.id.btn_ajouter);
        mBtnAjoutJardin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final String nomJardin = mNomJardinView.getText().toString();
                final String adresseJardin = mAdresseJardinView.getText().toString();
                final String descriptionJardin = mDescriptionView.getText().toString();

                if (TextUtils.isEmpty(nomJardin)) {
                    Toast.makeText(getActivity(), "Saisir un nom valide!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(adresseJardin)) {
                    Toast.makeText(getContext(), "Saisir une adresse valide!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(descriptionJardin)) {
                    Toast.makeText(getContext(), "Saisir une description !", Toast.LENGTH_SHORT).show();
                    return;
                }
                final ProgressDialog progressDialog = new ProgressDialog(getActivity(),
                        R.style.AppTheme_Custom_dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Enregistrement...");
                progressDialog.show();
                new android.os.Handler().postDelayed(new Runnable() {
                    public void run() {
                        JardinController.ajouterJardin(nomJardin,adresseJardin,descriptionJardin);
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Le jardin a été bien ajouté !", Toast.LENGTH_LONG).show();

                    }
                },3000);

            }

        });
    }
    }
