package platine.lille1.univ.fr.finegardens.fragments;
import platine.lille1.univ.fr.finegardens.*;
import platine.lille1.univ.fr.finegardens.Controller.JardinController;
import platine.lille1.univ.fr.finegardens.activities.DescriptionJardinActivity;
import platine.lille1.univ.fr.finegardens.activities.MainActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.*;
import android.os.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.LOCATION_SERVICE;


/**
 * Created by cactus on 16/02/2018.
 */

public class AjouterJardinFragment extends Fragment{
    private EditText mNomJardinView;
    private EditText mAdresseJardinView;
    private EditText mDescriptionView;
    private Button mBtnAjoutJardin;
    private Button mBtnUploadImage;
    private TextView mFileName;
    private ImageView btnPlacePicker;
    String fileUrl;
    double lng;
    double lat;
    public StorageReference mStorage;
    int PLACE_PICKER_REQUEST = 1;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LocationManager lm = (LocationManager)getActivity().getSystemService(LOCATION_SERVICE);
        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(location != null) {
                lng = location.getLongitude();

                lat = location.getLatitude();
            }else {
                Toast.makeText(getActivity(), "Vérfiez votre connection internet ", Toast.LENGTH_SHORT).show();
            }
             mStorage = FirebaseStorage.getInstance().getReference();

        }
        return inflater.inflate(R.layout.activity_ajouter_jardin, container, false);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2 && requestCode == RESULT_OK){
        final Uri uri = data.getData();
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),
                R.style.AppTheme_Custom_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Téléchargement de l'image...");
        progressDialog.show();
        new android.os.Handler().postDelayed(new Runnable() {
            public void run() {

                final StorageReference filePath = mStorage.child("Images").child(uri.getLastPathSegment());
                filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {

                        String filename = filePath.getName();
                        mFileName.setText(filename);
                        progressDialog.dismiss();
                        filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Uri downloadUri = taskSnapshot.getMetadata().getDownloadUrl();
                                fileUrl = downloadUri.toString();
                                Log.d("url",fileUrl);

                            }
                        });
                        Toast.makeText(getActivity(), "Téléchargement terminé avec succés", Toast.LENGTH_SHORT).show();
                    }
                });
            }
    },3000);
        }if(requestCode == 1 && resultCode == RESULT_OK){
            Log.d("on activity","");
            final Place place = PlacePicker.getPlace(getActivity(), data);
            final CharSequence name = place.getName();
            final CharSequence address = place.getAddress();
            lat = place.getLatLng().latitude;
            lng = place.getLatLng().longitude;
            String attributions = (String) place.getAttributions();
            if (attributions == null) {
                attributions = "";
            }Log.d("adresse: ", (String) address);

            //mName.setText(name);
            mAdresseJardinView.setText(address);
            // mAttributions.setText(Html.fromHtml(attributions));
        }else
            super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mNomJardinView =(EditText)getView().findViewById(R.id.input_nom);
        mAdresseJardinView = (EditText)getView().findViewById(R.id.input_adresse);
        mDescriptionView = (EditText)getView().findViewById(R.id.input_description);
        mBtnAjoutJardin = (Button)getView().findViewById(R.id.btn_ajouter);
        mBtnUploadImage = (Button)getView().findViewById(R.id.btn_upload);
        mFileName = (TextView)getView().findViewById(R.id.filename);
        mBtnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,2);
            }
        });
        btnPlacePicker = (ImageView) getView().findViewById(R.id.btn_adrese);
        btnPlacePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }


            }

        });

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
                        JardinController.ajouterJardin(nomJardin,adresseJardin,descriptionJardin,lng,lat,fileUrl);
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Le jardin a été bien ajouté !", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(getActivity().getBaseContext(),
                                MainActivity.class);


                        startActivity(intent);

                    }
                },3000);

            }

        });
    }
    }
