



public class Viginere{
    
    static String ciph = "QFXWJEILAWFKMUMZALGAKMPUQLAVY";
    static String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static char alphtemp[] = alphabet.toCharArray();
    static char alph[] = new char[alphtemp.length * 2];
	   
    static char array[] = new char[alph.length];
    static char ciphArray[] = ciph.toCharArray();

    public static int charIndex(char c, char alph[]){
	int index = 0;
	for (int i = 0; i < alph.length/2; i++) {
	    if (c == alph[i]) {
		index = i;
	    }
	}
	return index;		
    }
 
    public static char charShift(char key, char ciph){
	
	char plain = alph[26 + charIndex(ciph,alph) - (charIndex(key,alph))];	
	return plain;
	
    }

    public static void vig(){
	String ciph = "QFXWJEILAWFKMUMZALGAKMPUQLAVY";
	char ciphArray[] = ciph.toCharArray();
	char array[] = new char[ciphArray.length];
	
	for (int i = 0; i < 26; i++) {
    
	    for (int j = 0; j < ciphArray.length; j++) {	    

		for (int k = 0; k < ciphArray.length; k++) {

		    switch(k % 3){

		    case 0:{
			array[k] = charShift('I',ciphArray[k]);
			break;
		    }
			
		    case 1:{
			array[k] = charShift(alph[j],ciphArray[k]);
			break;
		    }
			
		    case 2:{
			array[k] = charShift(alph[i],ciphArray[k]);
			break;
		    }
		    default:{
			
		    }
			
		    }
		}
		System.out.print(j + " ");
		System.out.println(array);
	    }


	}

    }
    
    public static void main(String[] args) {


	for (int i = 0; i < alphtemp.length; i++) {
	    alph[i] = alphtemp[i];
	}
	
	for (int i = 0; i < alphtemp.length; i++) {
	    alph[i + (alph.length / 2)] = alphtemp[i];
	}
		

	vig();

	
    }
}
