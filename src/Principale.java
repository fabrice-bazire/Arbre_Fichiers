import java.io.*;
import java.util.Scanner;

public class Principale {

    public static ArbreFichiers creation_arbre_depuis_fichier () {
        String fichier = "src/toto.txt";
        String i;
        String contenu;
        String[] x;
        int nbetoile = 1;
        int ligneactuelle = 0; //compteur pour connaitre le numéro de la ligne en cours de lecture
        ArbreFichiers arbre = new ArbreFichiers(null,null,null,null, "root", false, null, 0);
        try{
            FileReader f = new FileReader (fichier);
            BufferedReader b = new BufferedReader(f);
            String s = " ";
            while((s = b.readLine())!=null){
                Scanner sc = new Scanner (s);
                sc.useDelimiter("\n");
                try {
                    i = sc.next();
                    //code de lecture
                    if (!(i.equals("racine")) && ligneactuelle == 0){
                        System.out.println("le fichier passé en paramètre n'est pas correct");
                        return null;
                    }
                    if (i.equals("fin")){
                        return arbre;
                    }
                    x = i.split(" ");
                    if (x[0].charAt(0) == '*'){
                        if (x[2].equals("d") && x[0].length() == 1) {
                            arbre.add(new ArbreFichiers(null,null,null,null, x[1], false, null, 0));
                        }
                        if (x[2].equals("f") && x[0].length() == 1){
                            contenu = b.readLine();
                            arbre.add(new ArbreFichiers(null,null,null,null, x[1], true, contenu, contenu.length()));
                        }
                    }
                    ligneactuelle++;
                }catch(Exception e) {
                    System.out.print(e.getMessage());
                }
            }
            f.close();
            b.close();
        }catch (IOException e) {
            System.out.println(e);
        }
        return null;
    }

    public static void main (String[]args){
        System.out.println(creation_arbre_depuis_fichier().info());
    }
}
