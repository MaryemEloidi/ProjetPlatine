package platine.lille1.univ.fr.finegardens.entities;

/**
 * Created by cactus on 14/01/2018.
 */

public class Authentication {
    private String email;
    private String mdp;
    public Authentication(String email, String mdp){
        this.email = email;
        this.mdp = mdp;
    }

}
