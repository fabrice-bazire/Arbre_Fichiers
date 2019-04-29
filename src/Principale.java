import java.io.*;
import java.util.Scanner;

public class Principale {

    static Scanner in = new Scanner (System.in);

    //etape 2
    public static ArbreFichiers creation_arbre_depuis_fichier (String fichier) {
        ArbreFichiers tmp;
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
                        if (x[1].equals("fin")){
                            arbre = arbre.getPere();
                        }
                        if (x[2].equals("d")) {
                            arbre.add(new ArbreFichiers(null,null,null,null, x[1], false, null, 0));
                            tmp = arbre;
                            arbre = arbre.getFils1();
                            arbre.setPere(tmp);
                        }
                        if (x[2].equals("f")){
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

    //etape 3
    public static void implemente_commandes (ArbreFichiers a, String s){
        String[] x = s.split(" ");
        if (x[0].equals("ls")){
            System.out.println(a.info());
        }
        if (x[0].equals("cd")){
            if (x[1].equals("..")){
                a = a.getPere();
            }else{
                a = a.acces(x[1]);
            }
        }
        if (x[0].equals("mkdir")){
            a.add(new ArbreFichiers(a,null,null,null, x[1], false, null, 0));
        }
        if (x[0].equals("mkfile")){
            System.out.println("Tapez le contenu de votre fichier !");
            String g = in.nextLine();
            a.add(new ArbreFichiers(a,null,null,null, x[1], true, g, g.length()));
        }
        if (x[0].equals("less")){
            if (a.acces(x[1]) == null){
                System.out.println("aucun fichier sous ce nom");
            }else{
                a.setFils1(a.acces(x[1]));
                String [] contenu = a.getFils1().getContenu().split(" ");
                String res = "";
                for (int i = 0; i < contenu.length; i++){
                    if (contenu[i].charAt(0) == '_' && contenu[i].charAt(1) == '_' && contenu[i].charAt(2) == '_'){
                        res += "\n";
                        for (int w = 3; w < contenu[i].length(); w++){
                            res += Character.toString(contenu[i].charAt(w));
                        }
                    }else{
                        if (contenu[i].charAt(contenu[i].length()-1) == '_' && contenu[i].charAt(contenu[i].length()-2) == '_' && contenu[i].charAt(contenu[i].length()-3) == '_'){
                            for (int w = 0; w < contenu[i].length()-3; w++){
                                res += Character.toString(contenu[i].charAt(w));
                            }
                            res += "\n";
                        }else {
                            res += contenu[i] + " ";
                        }
                    }
                }
                System.out.println(res);
            }
        }
        if (x[0].equals("pwd")){
            System.out.println(a.info_branche());
        }
        if (x[0].equals("rm")) {
            a.delete();
        }
        if (x[0].equals("quit") || x[0].equals("exit")){
            return;
        }
    }

    public static void main (String[]args){
        ArbreFichiers arbre;
        if (args[0].equals("")){
            arbre = new ArbreFichiers(null,null,null,null, "root", false, null, 0);
        }else {
            arbre = creation_arbre_depuis_fichier(args[0]);
        }
        String commande= "";
        while (! commande.equals("quit") || ! commande.equals("exit")){
            System.out.println ("Veuillez entrer votre commande");
            commande = in.nextLine();
            if (commande.equals("rm") || commande.equals("pwd") || commande.equals("less") || commande.equals("mkfile") || commande.equals("mkdir") || commande.equals("cd") || commande.equals("ls")){
                implemente_commandes(arbre, commande);
            }else{
                if (commande.equals("quit")|| commande.equals("exit")){
                    break;
                }else {
                    System.out.println("commande invalide");
                }
            }
        }
        System.out.println("Vous avez quitté l'application\nAu revoir");
    }
}