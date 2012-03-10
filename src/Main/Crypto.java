package Main;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;

public class Crypto {
  static String algorithm = "AES";
  static Key key;
  static Cipher cipher;

  public static void init(){
	  try {
		key = KeyGenerator.getInstance(algorithm).generateKey();
		cipher = Cipher.getInstance(algorithm);
	} catch (NoSuchAlgorithmException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (NoSuchPaddingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
  }
  


  private static byte[] encrypt(String input) throws InvalidKeyException, BadPaddingException,
      IllegalBlockSizeException {
    cipher.init(Cipher.ENCRYPT_MODE, key);
    byte[] inputBytes = input.getBytes();
    return cipher.doFinal(inputBytes);
  }

  private static String decrypt(byte[] encryptionBytes) throws InvalidKeyException,
      BadPaddingException, IllegalBlockSizeException {
    cipher.init(Cipher.DECRYPT_MODE, key);
    byte[] recoveredBytes = cipher.doFinal(encryptionBytes);
    String recovered = new String(recoveredBytes);
    return recovered;
  }
}