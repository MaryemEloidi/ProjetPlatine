package platine.lille1.univ.fr.finegardens.entities;

public class Jardin {
    private String id;
    private String nom;
    private String adresse;
    private String description;
    private double latitude;
    private double longitude;
    private String horaire_ouverture;
    private String horaire_fermeture;
    private float note;
    private String imageUrl;

    public Jardin(){}

    public Jardin(String id, String nom, String adresse, String description, double longitude, double latitude, String imageUrl){
        this.id=id;
        this.nom = nom;
        this.adresse = adresse;
        this.description = description;
        this.latitude = latitude;
        this.longitude =longitude;
        this.imageUrl = imageUrl;
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return  ""+ id;
    }
}
