package platine.lille1.univ.fr.finegardens.entities;

/**
 * Created by cactus on 21/01/2018.
 */

public class Jardin {
    public String id;
    public String nom;
    public String adresse;
    public String description;
    public long latitude;
    public long longitude;
    public String horaire_ouverture;
    public String horaire_fermeture;
    public float note;
    public Jardin(){

    }
    public Jardin(String id, String nom, String adresse, String description){
        this.id=id;
        this.nom = nom;
        this.adresse = adresse;
        this.description = description;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    public long getLongitude() {
        return longitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    public String getHoraire_ouverture() {
        return horaire_ouverture;
    }

    public void setHoraire_ouverture(String horaire_ouverture) {
        this.horaire_ouverture = horaire_ouverture;
    }

    public String getHoraire_fermeture() {
        return horaire_fermeture;
    }

    public void setHoraire_fermeture(String horaire_fermeture) {
        this.horaire_fermeture = horaire_fermeture;
    }

    public float getNote() {
        return note;
    }

    public void setNote(float note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return ""+ nom+ " "+ adresse;
    }
}
