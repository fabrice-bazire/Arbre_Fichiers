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

    //Méthode 1
    public void add (ArbreFichiers node){
        ArbreFichiers tmp = this.fils1;
        node.pere = this;
        if (tmp == null){
            tmp = node;
        }else {
            if (tmp.name.compareToIgnoreCase(node.name) > 0) {
                while (tmp.name.compareToIgnoreCase(node.name) > 0) { //if this.fils1.name est apres node.name dans l'ordre alphabétique
                    tmp = tmp.frered;
                }
                node.frered = tmp.frered;
                node.frereg = tmp.frereg;
            } else {
                while (tmp.name.compareToIgnoreCase(node.name) <= 0) { //if this.fils1.name est avant node.name dans l'ordre alphabétique
                    tmp = tmp.frereg;
                }
                node.frereg = tmp.frereg;
                node.frered = tmp.frered;
            }
        }
        //NE FONCTIONNE PAS !!!
        /*tmp = this;
        while(tmp.name != ""){//mise a jour de la taille des noeuds jusqu'à la racine
            tmp.size = tmp.size + node.size;
            tmp = tmp.pere;
        }*/
    }

    //Méthode 2
    public void delete () {
        
    }

    //Méthode 3
    public String info (){
        String s = "";
        if (this.fils1 != null){
            ArbreFichiers tmp = this.fils1;
            if (tmp.frereg != null){//on se met tout à gauche pour parcourir de gauche à droite et afficher dans l'ordre alphabétique
                tmp = tmp.frereg;
            }
            while (tmp.frered != null){//on va jusqu'au bout à droite
                if (tmp.type){
                    s+="f";
                }else{
                    s+="d";
                }
                s += " " + tmp.name + " " + String.valueOf(tmp.size) + " ";
                if (tmp.fils1 != null){
                    s += tmp.fils1.info();
                }
            }
        }
        return s;
    }

    //Méthode 4
    public String info_branche (){
        String s = "";
        return s;
    }

    //Méthode 5
    public ArbreFichiers acces (String s){
        return new ArbreFichiers();
    }

    public static void main (String[]args){
        //Test des méthodes add et info
        ArbreFichiers test = new ArbreFichiers();
        test.add(new ArbreFichiers(null, null, null, null, "test2", true, "bonjour tout le monde", "bonjour tout le monde".length()));
        test.add(new ArbreFichiers(null, null, null, null, "test3", true, "hello everybody", "hello everybody".length()));
        test.add(new ArbreFichiers(null, null, null, null, "dossierdetest", false, null, 0));
        System.out.print(test.info());
    }
}
