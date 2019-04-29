import java.util.Scanner;
import java.lang.String;

public class ArbreFichiers {

    static Scanner sc = new Scanner (System.in);

    private ArbreFichiers pere;
    private ArbreFichiers fils1;
    private ArbreFichiers frereg;
    private ArbreFichiers frered;
    private String name;
    private boolean type;
    private String contenu;
    private int size;

    ArbreFichiers (){
         pere = null;
         fils1 = null;
         frereg = null;
         frered = null;
         name = null;
         type = false;
         contenu = null;
         size = 0;
    }

    ArbreFichiers (ArbreFichiers p, ArbreFichiers f1, ArbreFichiers fg, ArbreFichiers fd, String name, boolean type, String content, int size){
        pere = p;
        fils1 = f1;
        frereg = fg;
        frered = fd;
        this.name = name;
        this.type = type;
        contenu = content;
        this.size = size;
    }

    public ArbreFichiers getPere (){
        return this.pere;
    }

    public boolean getType () {
        return this.type;
    }

    public String getContenu () {
        return this.contenu;
    }

    public void decaler1posdroite () {
        ArbreFichiers tmp;
        tmp = this.fils1;
        this.fils1 = this.fils1.frered;
        this.fils1.frereg = tmp;
    }

    public void decaler1posgauche () {
        ArbreFichiers tmp;
        tmp = this.fils1;
        this.fils1 = this.fils1.frereg;
        this.fils1.frered = tmp;
    }

    //Méthode 1
    public void add (ArbreFichiers node){
        //System.out.println(this.name);
        ArbreFichiers tmp;
        if (this.fils1 == null){
            this.fils1 = node;
        }else {
            if (this.fils1.name.compareToIgnoreCase(node.name) > 0) {
                while (this.fils1.frereg != null && this.fils1.name.compareToIgnoreCase(node.name) > 0) {
                    this.decaler1posgauche();
                }
                if (this.fils1.name.compareToIgnoreCase(node.name) > 0) {
                    node.frereg = this.fils1.frereg;
                    node.frered = this.fils1;
                    this.fils1 = node;
                    this.fils1.pere = this;
                } else {
                    node.frered = this.fils1.frered;
                    node.frereg = this.fils1;
                    this.fils1 = node;
                    this.fils1.pere = this;
                }
            } else {
                while (this.fils1.frered != null && this.fils1.name.compareToIgnoreCase(node.name) <= 0) {
                    this.decaler1posdroite();
                }
                if (this.fils1.name.compareToIgnoreCase(node.name) <= 0) {
                    node.frered = this.fils1.frered;
                    node.frereg = this.fils1;
                    this.fils1 = node;
                    this.fils1.pere = this;
                } else {
                    node.frereg = this.fils1.frereg;
                    node.frered = this.fils1;
                    this.fils1 = node;
                    this.fils1.pere = this;
                }

            }
        }
        tmp = this;
        while (tmp != null){
            tmp.size += node.size;
            tmp = tmp.pere;
        }
    }

    //Méthode 2
    public void delete () {
        int taillearetirer = this.fils1.size;
        ArbreFichiers tmp;
        if (this.fils1.frereg == null) {
            this.fils1.frered.frereg = null;
            this.fils1 = this.fils1.frered;
        }
        if (this.fils1.frered == null) {
                this.fils1.frereg.frered = null;
                this.fils1 = this.fils1.frereg;
        }
        if (this.fils1.frereg != null && this.fils1.frered != null){
            tmp = this.fils1;
            this.fils1 = this.fils1.frereg;
            this.fils1.frereg = tmp.frereg.frereg;
            this.fils1.frered = tmp.frered;
        }
        tmp = this;
        while (tmp != null){
            tmp.size = tmp.size - taillearetirer;
            tmp = tmp.pere;
        }
    }
    //OK

    //Méthode 3
    public String info (){
        String s = "";
        ArbreFichiers tmp = this.fils1;
        if (this.fils1 != null){
            while (this.fils1.frereg != null){//on se met tout à gauche pour parcourir de gauche à droite
                this.decaler1posgauche();
            }
            while (this.fils1 != null){//on va jusqu'au bout à droite
                if (this.fils1.type){
                    s+="fichier : ";
                }else{
                    s+="dossier : ";
                }
                s += " " + this.fils1.name + " (taille : " + String.valueOf(this.fils1.size) + " kb)";
                if (this.fils1.fils1 != null){
                    s += this.fils1.fils1.info();
                }
                s+="\n";
                this.fils1 = this.fils1.frered;
            }
        }
        this.fils1 = tmp; //pour ne pas perdre le pointeur
        return s;
    }
    //OK

    //Méthode 4
    public String info_branche (){
        ArbreFichiers tmp = this;
        String s = "";
        while (tmp != null){
            s = tmp.name + "/" + s;
            tmp = tmp.pere;
        }
        return s;
    }

    //Méthode 5
    public ArbreFichiers acces (String s) {
        if (s.equals("..")){
            return this.pere;
        }else{
            while (this.fils1.frereg != null){//on se met tout à gauche pour parcourir de gauche à droite
                this.decaler1posgauche();
            }
            try {
                while (this.fils1 != null) {//on va jusqu'au bout à droit
                    if (this.fils1.name.equals(s)) {
                        return this.fils1;
                    }
                    this.fils1 = this.fils1.frered;
                }
            }catch(Exception e){
                System.out.println("Le fichier/dossier \"" + s +"\" n'existe pas");
            }
        }
        return null;
    }
    // OK


    public void setPere(ArbreFichiers pere) {
        this.pere = pere;
    }

    public ArbreFichiers getFils1() {
        return fils1;
    }

    public void setFils1(ArbreFichiers fils1) {
        this.fils1 = fils1;
    }

    public ArbreFichiers getFrereg() {
        return frereg;
    }

    public void setFrereg(ArbreFichiers frereg) {
        this.frereg = frereg;
    }

    public ArbreFichiers getFrered() {
        return frered;
    }

    public void setFrered(ArbreFichiers frered) {
        this.frered = frered;
    }

    public String getName() {
        return name;
    }

    public boolean isType() {
        return type;
    }

    public int getSize() {
        return size;
    }
}
