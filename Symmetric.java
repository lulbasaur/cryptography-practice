import java.util.Arrays;
import java.util.Random;

public class Symmetric{


    /********************
     **      AUX       **
     *******************/

    static char[] hex= {'0','1','2','3',
			'4','5','6','7',
			'8','9','A','B',
			'C','D','E','F'};
    
    
    static public String byteArrayToHexString(byte[] bytes){
	String out = "";
	for ( int j = 0; j < bytes.length; j++ ) {
	    int v = bytes[j] & 0xFF;
	    out += hex[v >>> 4];
	    out += hex[v & 0x0F];
	}
	return out;
    }
    
    static public byte[] hexStringtoByteArray(String hex, byte[] dst) {

	for (int i = 0; i < dst.length; i++) {
	    dst[i] = (byte) Integer.parseInt(hex.substring(i*2, i*2 + 2), 16);
	}
	return dst;
    }
            

    static public  byte[] int_to_byte_array(int i) {
	return new byte[] {(byte)((i >>> 24) & 0xFF),(byte)((i >>> 16) & 0xFF),(byte)((i >>> 8) & 0xFF),(byte)(i & 0xFF)};
    }
    /************************
     **       AUX END      **
     ************************/





    
    /************************
     **   Key generation   **
     ************************/
    

    static public  int rotate_left_32_bits(int binary, int n) {
	return (binary << n) | (binary >> (Integer.SIZE - n));
    }     
        
    public static byte[] create_w_bytes(int k, byte[] dst){
	int n = 0;
	int h = 0;
	for (int i = 0; h < dst.length; i++) {
	    System.arraycopy(int_to_byte_array(rotate_left_32_bits(k,n)), 0, dst, h, 4);
	    n += 5;
	    h += 4;
	}
	return dst;
    }
            
    public static byte[][] create_rkeys_bytes_2(byte[] W, byte[][] dst){       
	for (int i = 0; i < dst.length; i++) {
	    System.arraycopy(W, i*6, dst[i], 0, 6);
	}
	return dst;
    }
    
    
    
    /************************
     ** Key generation END **
     ************************/



    

    /*****************************
     **          XOR            **
     *****************************/    

    static public byte[] XOR_bytes(byte[] input1, byte[] input2, byte[] dst){

	dst[0] = (byte)(input1[0] ^ input2[0]);
	dst[1] = (byte)(input1[1] ^ input2[1]);
	dst[2] = (byte)(input1[2] ^ input2[2]);
	dst[3] = (byte)(input1[3] ^ input2[3]);
	dst[4] = (byte)(input1[4] ^ input2[4]);
	dst[5] = (byte)(input1[5] ^ input2[5]);
	
	return dst;
    }
    /*******************************
     **         XOR END           **
     *******************************/



    
    /*******************************
     **          S-BOX            **
     *******************************/


    public static final int[][] sbox =
    {{0x63, 0x7c, 0x77, 0x7b, 0xf2, 0x6b, 0x6f, 0xc5, 0x30, 0x01, 0x67, 0x2b, 0xfe, 0xd7, 0xab, 0x76},
     {0xca, 0x82, 0xc9, 0x7d, 0xfa, 0x59, 0x47, 0xf0, 0xad, 0xd4, 0xa2, 0xaf, 0x9c, 0xa4, 0x72, 0xc0},
     {0xb7, 0xfd, 0x93, 0x26, 0x36, 0x3f, 0xf7, 0xcc, 0x34, 0xa5, 0xe5, 0xf1, 0x71, 0xd8, 0x31, 0x15},
     {0x04, 0xc7, 0x23, 0xc3, 0x18, 0x96, 0x05, 0x9a, 0x07, 0x12, 0x80, 0xe2, 0xeb, 0x27, 0xb2, 0x75},
     {0x09, 0x83, 0x2c, 0x1a, 0x1b, 0x6e, 0x5a, 0xa0, 0x52, 0x3b, 0xd6, 0xb3, 0x29, 0xe3, 0x2f, 0x84},
     {0x53, 0xd1, 0x00, 0xed, 0x20, 0xfc, 0xb1, 0x5b, 0x6a, 0xcb, 0xbe, 0x39, 0x4a, 0x4c, 0x58, 0xcf},
     {0xd0, 0xef, 0xaa, 0xfb, 0x43, 0x4d, 0x33, 0x85, 0x45, 0xf9, 0x02, 0x7f, 0x50, 0x3c, 0x9f, 0xa8},
     {0x51, 0xa3, 0x40, 0x8f, 0x92, 0x9d, 0x38, 0xf5, 0xbc, 0xb6, 0xda, 0x21, 0x10, 0xff, 0xf3, 0xd2},
     {0xcd, 0x0c, 0x13, 0xec, 0x5f, 0x97, 0x44, 0x17, 0xc4, 0xa7, 0x7e, 0x3d, 0x64, 0x5d, 0x19, 0x73},
     {0x60, 0x81, 0x4f, 0xdc, 0x22, 0x2a, 0x90, 0x88, 0x46, 0xee, 0xb8, 0x14, 0xde, 0x5e, 0x0b, 0xdb},
     {0xe0, 0x32, 0x3a, 0x0a, 0x49, 0x06, 0x24, 0x5c, 0xc2, 0xd3, 0xac, 0x62, 0x91, 0x95, 0xe4, 0x79},
     {0xe7, 0xc8, 0x37, 0x6d, 0x8d, 0xd5, 0x4e, 0xa9, 0x6c, 0x56, 0xf4, 0xea, 0x65, 0x7a, 0xae, 0x08},
     {0xba, 0x78, 0x25, 0x2e, 0x1c, 0xa6, 0xb4, 0xc6, 0xe8, 0xdd, 0x74, 0x1f, 0x4b, 0xbd, 0x8b, 0x8a},
     {0x70, 0x3e, 0xb5, 0x66, 0x48, 0x03, 0xf6, 0x0e, 0x61, 0x35, 0x57, 0xb9, 0x86, 0xc1, 0x1d, 0x9e},
     {0xe1, 0xf8, 0x98, 0x11, 0x69, 0xd9, 0x8e, 0x94, 0x9b, 0x1e, 0x87, 0xe9, 0xce, 0x55, 0x28, 0xdf},
     {0x8c, 0xa1, 0x89, 0x0d, 0xbf, 0xe6, 0x42, 0x68, 0x41, 0x99, 0x2d, 0x0f, 0xb0, 0x54, 0xbb, 0x16}};

    
    
    public static byte[] sbox_substitution_bytes(byte[] src, byte[] dst){

	for (int i = 0; i < src.length; i++) {
	    int row = ((src[i] >>> 4) & 0x0F);
	    int col = (src[i] & 0x0F);
	    dst[i] = (byte)(sbox[row][col] & 0xFF);
	}
	    
	return dst;
    }
    
    
    /*******************************
     **         S-BOX END         **
     *******************************/

    

    /*******************************
     **         SHIFT ROW         **
     *******************************/

    public static byte[] shift_row_bytes(byte[] src, byte[] dst){
	if(src.length == 6){
	    byte temp = src[0];
	    dst[0] = src[1]; 
	    dst[1] = src[2];
	    dst[2] = src[3];
	    dst[3] = src[4];
	    dst[4] = src[5];
	    dst[5] = temp;
	}
	else{
	    System.out.println("shift_row_bytes length error!" + src.length);
	}
	return dst;
    }    
    
    /*******************************
     **         SHIFT ROW END     **
     *******************************/



    
    
    /*******************************
     **        MIX-COLUMNS        **
     *******************************/
       
    static int[] mult_2_lookup = {0x00,0x02,0x04,0x06,0x08,0x0a,0x0c,0x0e,0x10,0x12,0x14,0x16,0x18,0x1a,0x1c,0x1e,
			 0x20,0x22,0x24,0x26,0x28,0x2a,0x2c,0x2e,0x30,0x32,0x34,0x36,0x38,0x3a,0x3c,0x3e,
			 0x40,0x42,0x44,0x46,0x48,0x4a,0x4c,0x4e,0x50,0x52,0x54,0x56,0x58,0x5a,0x5c,0x5e,
			 0x60,0x62,0x64,0x66,0x68,0x6a,0x6c,0x6e,0x70,0x72,0x74,0x76,0x78,0x7a,0x7c,0x7e,
			 0x80,0x82,0x84,0x86,0x88,0x8a,0x8c,0x8e,0x90,0x92,0x94,0x96,0x98,0x9a,0x9c,0x9e,
			 0xa0,0xa2,0xa4,0xa6,0xa8,0xaa,0xac,0xae,0xb0,0xb2,0xb4,0xb6,0xb8,0xba,0xbc,0xbe,
			 0xc0,0xc2,0xc4,0xc6,0xc8,0xca,0xcc,0xce,0xd0,0xd2,0xd4,0xd6,0xd8,0xda,0xdc,0xde,
			 0xe0,0xe2,0xe4,0xe6,0xe8,0xea,0xec,0xee,0xf0,0xf2,0xf4,0xf6,0xf8,0xfa,0xfc,0xfe,
			 0x1b,0x19,0x1f,0x1d,0x13,0x11,0x17,0x15,0x0b,0x09,0x0f,0x0d,0x03,0x01,0x07,0x05,
			 0x3b,0x39,0x3f,0x3d,0x33,0x31,0x37,0x35,0x2b,0x29,0x2f,0x2d,0x23,0x21,0x27,0x25,
			 0x5b,0x59,0x5f,0x5d,0x53,0x51,0x57,0x55,0x4b,0x49,0x4f,0x4d,0x43,0x41,0x47,0x45,
			 0x7b,0x79,0x7f,0x7d,0x73,0x71,0x77,0x75,0x6b,0x69,0x6f,0x6d,0x63,0x61,0x67,0x65,
			 0x9b,0x99,0x9f,0x9d,0x93,0x91,0x97,0x95,0x8b,0x89,0x8f,0x8d,0x83,0x81,0x87,0x85,
			 0xbb,0xb9,0xbf,0xbd,0xb3,0xb1,0xb7,0xb5,0xab,0xa9,0xaf,0xad,0xa3,0xa1,0xa7,0xa5,
			 0xdb,0xd9,0xdf,0xdd,0xd3,0xd1,0xd7,0xd5,0xcb,0xc9,0xcf,0xcd,0xc3,0xc1,0xc7,0xc5,
			 0xfb,0xf9,0xff,0xfd,0xf3,0xf1,0xf7,0xf5,0xeb,0xe9,0xef,0xed,0xe3,0xe1,0xe7,0xe5};

      

        public static byte[] mix_columns_bytes(byte[] src, byte[] dst){
	    byte[] left =  {src[0],src[1],src[2]}; 
	    byte[] right = {src[3],src[4],src[5]};

	    dst[0] = (byte) (left[0]  ^  mult_2_lookup[left[1] & 0xFF] ^  mult_2_lookup[left[2]& 0xFF]);
	    dst[1] = (byte) (mult_2_lookup[left[0] & 0xFF] ^  mult_2_lookup[left[1] & 0xFF] ^  left[2]);
	    dst[2] = (byte) (mult_2_lookup[left[0] & 0xFF] ^  left[1] ^  mult_2_lookup[left[2] & 0xFF]);
	    dst[3] = (byte) (right[0] ^ mult_2_lookup[right[1] & 0xFF] ^ mult_2_lookup[right[2] & 0xFF]);
	    dst[4] = (byte) (mult_2_lookup[right[0] & 0xFF] ^ mult_2_lookup[right[1] & 0xFF] ^ right[2]);
	    dst[5] = (byte) (mult_2_lookup[right[0] & 0xFF] ^ right[1] ^ mult_2_lookup[right[2] & 0xFF]);
	
	    return dst;
	}
    /*******************************
     **        MIX-COLUMNS END    **
     *******************************/        

    
        
    /*******************************
     **        Encrypt            **
     *******************************/

    
    public static byte[] encrypt_bytes(byte[] plaintext, int key){
	long start_time_tot = System.nanoTime();

	long start_time_nano;
	long end_time_nano;
	byte[] byte_array = plaintext;
	byte[] state = new byte[6];
	byte[] rkeys_byte_array = new byte[6];
	byte[] w = new byte[68];
	byte[][] rkeys = new byte[11][6];
	
	create_w_bytes(key,w);
	create_rkeys_bytes_2(w,rkeys);
	for (int i = 0; i < rkeys.length-1; i++) {	    
	    XOR_bytes(byte_array,
		      rkeys[i],state);	    	 
	    sbox_substitution_bytes(state,state);
	    shift_row_bytes(state,state);
	    mix_columns_bytes(state,state);	    
	    byte_array = state;	    
	}
	XOR_bytes(byte_array,rkeys[rkeys.length -1],state);
	

	long end_time_tot = System.nanoTime();
	System.out.println("\nTime total:   " + ((end_time_tot - start_time_tot)) + " nano sec");

	return state;
    }
   
    
    
    /*******************************
     **        Encrypt END        **
     *******************************/


        
    /*******************************
     **    Bruteforce Chosen P    **
     *******************************/
    public static byte[] bruteforce_help(byte[][] rkeys, byte[] rkeys_byte_array,byte[] state, byte[] byte_array){	
	for (int i = 0; i < rkeys.length-1; i++) {	    
	    XOR_bytes(byte_array,
		      rkeys[i],state);	    	 
	    sbox_substitution_bytes(state,state);
	    shift_row_bytes(state,state);
	    mix_columns_bytes(state,state);	  
	    byte_array = state;	    
	}
	XOR_bytes(byte_array,rkeys[rkeys.length -1],state);
	return state;	
    }

    
    public static void bruteforce(byte[] plaintext, byte[] c1_byte_array){

	byte[] byte_array = plaintext;
	byte[] state = new byte[6];
	byte[] rkeys_byte_array = new byte[6];
	byte[] w = new byte[68];
	byte[][] rkeys = new byte[11][6];
	int key = 0x00000001;
	
	for (long i = 0; i < 4294967296L && key != 0; i++){
	    create_w_bytes(key,w);
	    create_rkeys_bytes_2(w,rkeys);
	    state = bruteforce_help(rkeys, rkeys_byte_array, state, byte_array);
	    if (Arrays.equals(state,c1_byte_array)) {
		System.out.println("Key:" +
				   byteArrayToHexString(
							int_to_byte_array(key)));
		break;
	    }
	    else{
		if ((i % 1048576L) == 0) {
		    System.out.println("Not key: " +
				       byteArrayToHexString(int_to_byte_array(key)));
		}
	    }
	    key=key+1;
	}

    }

    
    
    /*******************************
     **  Bruteforce Chosen P END  **
     *******************************/ 


        
    /*******************************
     **           MAIN            **
     *******************************/ 
    
    public static void main(String[] args) {
	byte[] encryption_result;
	String cipher;
	byte[] plaintext = {0x00,0x11,0x22,0x33,0x44,0x55};
	int key = 0x12345678;
	encryption_result = encrypt_bytes(plaintext,key);

	cipher = byteArrayToHexString(encryption_result);
	System.out.println("\nCIPHER: " + cipher + "\n");
	if(cipher.equals("BDCEF284CDFA")){
	    System.out.println("Success! \n");
	}
	
	byte[] cipher_byte_array = hexStringtoByteArray("FBD040D6DB9C",new byte[6]);
	byte[] plaintext2 = hexStringtoByteArray("0123456789AB",new byte[6]);

	//**************************************************
	//uncomment below to perform chosen plaintext attack
	//**************************************************
	//bruteforce(plaintext2,cipher_byte_array);

	
	// key found through the bruteforce method above
	int key_found = 0x3E76AC4B;

	

	encryption_result = encrypt_bytes(plaintext2,key_found);
	cipher = byteArrayToHexString(encryption_result);
	System.out.println("\nCIPHER: " + cipher + "\n");
	if(cipher.equals("FBD040D6DB9C")){
	    System.out.println("Success! \n");
	}
	byte[] plaintext3 = hexStringtoByteArray("9A6BCC10E84A",new byte[6]);
	
	encryption_result = encrypt_bytes(plaintext3,key_found);
	cipher = byteArrayToHexString(encryption_result);
	System.out.println("\nCIPHER: " + cipher + "\n");
	if(cipher.equals("E5CBFFF7E08B")){
	    System.out.println("Success! \n");
	}
		

    }   


    



}


