import java.io.*;
import java.util.Scanner;

public class Principale {

    static Scanner in = new Scanner (System.in);

    //etape 2
    //Méthode qui depuis un fichier respectant le format indiqué dans le sujet du projet retourne l'arbreFichiers correspondant
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
                //La chaine de caractere s, a pour but de recuperer la ligne en cours de lecture du fichier, et le while fais en sorte que tant que le fichier n'est pas a la fin, s prend la ligne suivante
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
                    x = i.split(" "); // Cette commande sert a transformer la chaine de caractere qui contient toute la ligne en un tableau de mot pour analyser mot par mot
                    if (x[0].charAt(0) == '*'){ //Ici on regarde si le premier carcatere du premier mot de la ligne est une etoile
                        if (x[1].equals("fin")){
                            arbre = arbre.getPere();
                        }
                        if (x[2].equals("d")) { //Ici on regarde si le deuxieme mot est un d, dans ce cas on doit créer un dossier
                            arbre.add(new ArbreFichiers(null,null,null,null, x[1], false, null, 0));
                            tmp = arbre;
                            arbre = arbre.getFils1();
                            arbre.setPere(tmp);
                        }
                        if (x[2].equals("f")){ //Ici on regarde si le deuxieme mot est un f, dans ce cas on doit créer un fichier
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
    //Méthode qui selon la commande passée en paramètre va l'éxécuter sur l'arbreFichiers en paramètre
    public static void implemente_commandes (ArbreFichiers a, String s){
        String[] x = s.split(" "); //Comme dans la methode du dessus, on transforme la chaine s (qui ici est la commande tapée par l'utilisateur) en tableau de mots
        //Ensuite on fais ce qu'il faut par rapport a la commande entrée par l'utilisateur
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
            String g = in.nextLine(); // l'utilisateur entre le contenu du fichier qu'il veur créer
            a.add(new ArbreFichiers(a,null,null,null, x[1], true, g, g.length())); //on crée le fichier avec le nom et le contenu que l'utilisateur a choisi
        }
        if (x[0].equals("less")){
            if (a.acces(x[1]) == null){
                System.out.println("aucun fichier sous ce nom");
            }else{
                a.setFils1(a.acces(x[1]));
                String [] contenu = a.getFils1().getContenu().split(" ");
                String res = "";
                for (int i = 0; i < contenu.length; i++){
                    if (contenu[i].charAt(0) == '_' && contenu[i].charAt(1) == '_' && contenu[i].charAt(2) == '_'){ //si les 3 premiers caracteres sont des _, alors on saute une ligne puis on affiche le contenu de la ligne
                        res += "\n";
                        for (int w = 3; w < contenu[i].length(); w++){
                            res += Character.toString(contenu[i].charAt(w));
                        }
                    }else{
                        if (contenu[i].charAt(contenu[i].length()-1) == '_' && contenu[i].charAt(contenu[i].length()-2) == '_' && contenu[i].charAt(contenu[i].length()-3) == '_'){ // si les 3 derniers caractere sont des _, alors on affiche la ligne puis on saute une ligne
                            for (int w = 0; w < contenu[i].length()-3; w++){
                                res += Character.toString(contenu[i].charAt(w));
                            }
                            res += "\n";
                        }else { // et sinon on affiche simplement la ligne
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

    //Le main constitue le menu interactif dans lequel on peut saisir les commandes
    public static void main (String[]args){
        ArbreFichiers arbre = new ArbreFichiers(null,null,null,null, "root", false, null, 0);
        if (args != null){
            try {
                arbre = creation_arbre_depuis_fichier(args[0]);
            }catch (ArrayIndexOutOfBoundsException e){
            }
        }
        String commande= "";
        while (true){ //on boucle infinie pour demander une commande a l'utilisateur
            System.out.println ("Veuillez entrer votre commande");
            commande = in.nextLine();
            if (commande.equals("rm") || commande.equals("pwd") || commande.equals("less") || commande.equals("mkfile") || commande.equals("mkdir") || commande.equals("cd") || commande.equals("ls")){
                implemente_commandes(arbre, commande);
            }else{
                if (commande.equals("quit")|| commande.equals("exit")){ //Le programme s'arrete seulement si l'utilisateur entre quit ou exit
                    break;
                }else {
                    System.out.println("commande invalide");
                }
            }
        }
        System.out.println("Vous avez quitté l'application\nAu revoir");
    }
}