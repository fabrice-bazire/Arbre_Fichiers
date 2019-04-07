public class Test {

    public static int factorielle (int n){
        int resultat;
        if(n==1){
            resultat = 1;
        } else {
            int sous_resultat = factorielle (n-1); //appel recursif
            resultat = sous_resultat *n; }
        return resultat;
    }

    public static void main (String[]args){
        System.out.println("fact 5 : " + factorielle(5));
    }
}
