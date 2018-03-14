package platine.lille1.univ.fr.finegardens.fragments;
import platine.lille1.univ.fr.finegardens.*;
import platine.lille1.univ.fr.finegardens.Controller.JardinController;
import platine.lille1.univ.fr.finegardens.activities.MainActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.*;
import android.os.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.LOCATION_SERVICE;

public class AjouterJardinFragment extends Fragment{
    private EditText mNomJardinView;
    private EditText mAdresseJardinView;
    private EditText mDescriptionView;
    private Button mBtnAjoutJardin;
    private Button mBtnUploadImage;
    private Button mGeolocButton;
    private TextView mFileName;
    String fileUrl;
    double longitude;
    double latitude;
    public StorageReference mStorage;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        LocationManager lm = (LocationManager)getActivity().getSystemService(LOCATION_SERVICE);
        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            longitude = location.getLongitude();
            latitude = location.getLatitude();
            mStorage = FirebaseStorage.getInstance().getReference();
        }

        return inflater.inflate(R.layout.activity_ajouter_jardin, container, false);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //if(requestCode == 2 && requestCode == RESULT_OK);
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

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mNomJardinView = getView().findViewById(R.id.input_nom);
        mAdresseJardinView = getView().findViewById(R.id.input_adresse);
        mDescriptionView = getView().findViewById(R.id.input_description);
        mBtnAjoutJardin = getView().findViewById(R.id.btn_ajouter);
        mBtnUploadImage = getView().findViewById(R.id.btn_upload);
        mFileName = getView().findViewById(R.id.filename);
        mBtnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,2);
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
                        JardinController.ajouterJardin(nomJardin,adresseJardin,descriptionJardin,longitude,latitude,fileUrl);
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Le jardin a été bien ajouté !", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(getActivity().getBaseContext(),
                                MainActivity.class);


                        startActivity(intent);

                    }
                },3000);

            }

        });

        this.mGeolocButton = getView().findViewById(R.id.geoloc_button);
        this.mGeolocButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(getContext(), Locale.getDefault());

                try {
                    addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String city = addresses.get(0).getLocality();
                    String state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    String postalCode = addresses.get(0).getPostalCode();
                    String knownName = addresses.get(0).getFeatureName();

                    mAdresseJardinView.setText(address + " " + city + " " + state + " " + country + " " + postalCode + " " + knownName);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "ERREUR LOCATION", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    }
