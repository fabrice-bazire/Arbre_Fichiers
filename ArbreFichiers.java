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

    public void add (ArbreFichiers node){
        node.pere = this;
        if (this.fils1 == null && this.frereg == null && this.frered == null){
            this.fils1 = node;
        }else{
            if (fils1.pere.name.compareToIgnoreCase(node.name) < 0){
                ArbreFichiers tmp = fils1;
                ArbreFichiers tmp1 = frereg;
                this.fils1 = node;
                frereg = tmp;
                tmp = frered;
                frered = tmp1;
                //tmp ?
            }
        }
        //maj de la taille
    }

    public void delete () {
        
    }

    public String info (ArbreFichiers t){
        String s = "";
        if (t.type == false){
            s += "d\n";
        }else{
            s += "f\n";
        }
        s += t.name;
        s += String.valueOf(t.size);
        if (t.fils1 != null){
           s += info (t.fils1);
           if (t.frereg != null){
               s += info (t.frereg);
           }
            if (t.frered != null){
                s += info (t.frered);
            }
        }
        return s;
    }

    public String met4 (){
        String s = "";
        return s;
    }

    public ArbreFichiers met5 (String s){
        return new ArbreFichiers();
    }

    public void main (String[]args){

    }
}
