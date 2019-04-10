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
        return this.fils1.contenu;
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
                } else {
                    node.frered = this.fils1.frered;
                    node.frereg = this.fils1;
                    this.fils1 = node;
                }
            } else {
                while (this.fils1.frered != null && this.fils1.name.compareToIgnoreCase(node.name) <= 0) {
                    this.decaler1posdroite();
                }
                if (this.fils1.name.compareToIgnoreCase(node.name) <= 0) {
                    node.frered = this.fils1.frered;
                    node.frereg = this.fils1;
                    this.fils1 = node;
                } else {
                    node.frereg = this.fils1.frereg;
                    node.frered = this.fils1;
                    this.fils1 = node;
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

    //Méthode 3
    public String info (){
        String s = "";
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
                    System.out.println("a");
                    s += this.fils1.fils1.info();
                    System.out.print(this.fils1.fils1.info() + "test\n");
                }
                s+="\n";
                this.fils1 = this.fils1.frered;
            }
        }
        return s;
    }

    //Méthode 4
    public String info_branche (){
        ArbreFichiers tmp = this;
        String s = tmp.fils1.name;
        while (tmp != null){
            System.out.println(tmp.name);
            s = tmp.name + "/" + s;
            tmp = tmp.pere;
        }
        return s;
    }
    //A REVOIR !!!!

    /*public String info_branche (){
        String s = "";
        if (this.pere.name.equals("root")){
            return ("root/" + this.name);
        }else{
            s+=this.pere.info_branche();
        }
        return s;
    }*/

    //Méthode 5
    public ArbreFichiers acces (String s){
        if (s.equals("..")){
            return this.pere;
        }else{
            while (this.fils1.frereg != null){//on se met tout à gauche pour parcourir de gauche à droite
                this.decaler1posgauche();
            }
            try {
                while (this.fils1 != null) {//on va jusqu'au bout à droit
                    if (this.fils1.type == false && this.fils1.name.equals(s)) {
                        return this.fils1;
                    }
                    this.fils1 = this.fils1.frered;
                }
            }catch(Exception e){
                System.out.println("Le dossier \"" + s +"\" n'existe pas");
            }
        }
        return null;
    }

    public static void main (String[]args){
        //Test des méthodes add et info
        ArbreFichiers test = new ArbreFichiers(null,null,null,null, "root", false, null, 0);
        test.add(new ArbreFichiers(null,null,null,null, "francais", true, "bonjour", "bonjour".length()));
        test.add(new ArbreFichiers(null,null,null,null, "anglais", true, "hello", "hello".length()));
        test.add(new ArbreFichiers(null,null,null,null, "allemand", true, "hallo", "hallo".length()));
        test.add(new ArbreFichiers(null,null,null,null, "suedois", false, null, 0));
        test.fils1.add(new ArbreFichiers(null,null,null,null, "suisse", false, null, 0));
        test.fils1.fils1.add(new ArbreFichiers(null,null,null,null, "danois", false, null, 0));
        test.fils1.fils1.fils1.add(new ArbreFichiers(null,null,null,null, "amical", true, "hey", "hey".length()));
        System.out.println("danois : " + test.fils1.fils1.fils1.size);
        System.out.println("suisse : " + test.fils1.fils1.size);
        System.out.println("suedois : " + test.fils1.size);
        System.out.println(test.info_branche());
        //System.out.println(test.info());
    }
}
