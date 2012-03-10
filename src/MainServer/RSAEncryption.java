/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MainServer;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSAEncryption {

    private final static BigInteger one = new BigInteger("1");
    private final static SecureRandom random = new SecureRandom();
    private PrivateKey privateKey;
    public PublicKey publicKey;
    private BigInteger modulus;
    private int N;
    public KeyPair kp;
    // generate an N-bit (roughly) public and private key

   public RSAEncryption() throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        kp = kpg.genKeyPair();
        publicKey = kp.getPublic();
        privateKey = kp.getPrivate();



    }

    public byte[] rsaEncrypt(byte[] data, PublicKey p) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, p);
        byte[] cipherData = null;
        try {
            cipherData = cipher.doFinal(data);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(RSAEncryption.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(RSAEncryption.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cipherData;
    }

    public byte[] rsaDecrypt(byte[] data) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] cipherData = null;
        try {
            cipherData = cipher.doFinal(data);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(RSAEncryption.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(RSAEncryption.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cipherData;
    }

   public PublicKey readKeyFromClient(String keyFile) throws IOException {

        try {
            BigInteger m = new BigInteger(keyFile);
            BigInteger e = new BigInteger("65537");

            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(m, e);
            KeyFactory fact = KeyFactory.getInstance("RSA");
            PublicKey pubKey = fact.generatePublic(keySpec);
            return pubKey;
        } catch (Exception e) {
            throw new RuntimeException("Spurious serialisation error", e);
        } finally {
        }
    }

    public Object toObject(byte[] bytes) {
        Object obj = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bis);
            obj = ois.readObject();
        } catch (IOException ex) {
            //TODO: Handle the exception
        } catch (ClassNotFoundException ex) {
            //TODO: Handle the exception
        }
        return obj;
    }
    // BigInteger encrypt(BigInteger message) {
    //   return message.modPow(publicKey, modulus);
    //  }

//   BigInteger decrypt(BigInteger encrypted) {
    //    return encrypted.modPow(privateKey, modulus);
//   }
    public String toString() {
        String s = "";
        s += "public  = " + publicKey + "\n";
        s += "private = " + privateKey + "\n";
        s += "modulus = " + modulus;
        return s;
    }
    /*
    public String getEncryptedString(String s){
    BigInteger message=new BigInteger(s.getBytes());
    
    return new String(message.modPow(publicKey, modulus).toByteArray());
    }
    public String getDecryptedString(String s){
    BigInteger message=new BigInteger(s.getBytes());
    return new String(message.modPow(privateKey, modulus).toByteArray());
    }
     *
     */
}
