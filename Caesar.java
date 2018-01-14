
import java.util.Scanner;

public class Caesar {

    public static int charIndex(char c, char alph[]){
	int index = 0;
	for (int i = 0; i < alph.length/2; i++) {
	    if (c == alph[i]) {
		index = i;
	    }
	}
	return index;		
    }

    public static void main(String[] args) {

	String text = "PMTTWKZGXBWOZIXPGEWZTL";
	char array[] = text.toCharArray();
	
	String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	char alphtemp[] = alphabet.toCharArray();
	char alph[] = new char[alphtemp.length * 2];
	
	for (int i = 0; i < alphtemp.length; i++) {
	    alph[i] = alphtemp[i];
	}
	for (int i = 0; i < alphtemp.length; i++) {
	    alph[i + (alph.length / 2)] = alphtemp[i];
	}
	
	System.out.println(alph);
	
	char plain[] = new char[alph.length];
	int index;
	int j;
	
	for(j = 0; j < alph.length/2 ; j++){
	    	    
	    for (int i = 0; i < array.length ; i++) {
		index = charIndex(array[i], alph);
		plain[i] = alph[j + index];
	    }	    
	    System.out.print(j + " ");
	    System.out.println(plain);

	    
	}
    }
}
