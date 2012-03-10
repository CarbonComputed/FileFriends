package Main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class NetEncryption {

	private AES aesEncryption;
	private RSA rsaEncryption;
	private UDTStream udt;

	public NetEncryption(UDTStream udt) {
		this.udt = udt;
		aesEncryption = new AES();
		rsaEncryption = new RSA();

	}

	public void sendAESkey() {
	
		try {
			rsaEncryption.cipher.init(Cipher.ENCRYPT_MODE,
					rsaEncryption.remotePubKey, rsaEncryption.random);
		} catch (InvalidKeyException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		byte[] preKey1 = null;
	//	byte[] preKey2 = null;
		// preKey1=Arrays.copyOf(aesEncryption.skeySpec.getEncoded(),
		// aesEncryption.skeySpec.getEncoded().length/2);
		// preKey2=Arrays.copyOfRange(aesEncryption.skeySpec.getEncoded(),
		// aesEncryption.skeySpec.getEncoded().length/2,aesEncryption.skeySpec.getEncoded().length);
		byte[] encKey1 = null;
	//	byte[] encKey2 = null;
		// String key =
		// Base64.encodeBase64String(aesEncryption.skeySpec.getEncoded());
		// preKey1=
		// Base64.encodeBase64String(key.substring(key.length()/2).getBytes());
		// preKey2=Base64.encodeBase64String(key.substring(preKey1.length(),key.length()).getBytes());
		preKey1 = aesEncryption.skeySpec.getEncoded();
		// preKey2=Arrays.copyOfRange(aesEncryption.skeySpec.getEncoded(),aesEncryption.skeySpec.getEncoded().length/2,aesEncryption.skeySpec.getEncoded().length
		// );

		try {

			encKey1 = rsaEncryption.cipher.doFinal(preKey1);
			System.out.println("key " + encKey1.length);
			// encKey2 = rsaEncryption.cipher.doFinal(preKey2);
			// System.out.println(Base64.encodeBase64(preKey1).length);
		} catch (IllegalBlockSizeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (BadPaddingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// System.out.println(new String(preKey1));
		// System.out.println(preKey2.length);

		// String b64key1 = new
		// String(Base64.encodeBase64(encKey)).substring(172);
		// String b64key2 = new
		// String(Base64.encodeBase64(encKey)).substring(172,344);
		//System.out.println("encoded key length: " + encKey1.length);
		try {
			// System.out.println("enckey "+ new String(encKey1));
			udt.send(encKey1);
			Thread.sleep(10);
			// udt.send(encKey2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(Base64.encodeBase64String(aesEncryption.skeySpec
				.getEncoded()));
		System.out.println("sent aes");
	}

	public void receiveAESkey() {
		DatagramPacket packet = null;
		try {
			packet = udt.receive(10000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("encoded key length: " + packet.getData().length);

		byte[] encrypted1 = packet.getData();
		System.out.println(encrypted1.length);

		try {
			rsaEncryption.cipher.init(Cipher.DECRYPT_MODE,
					rsaEncryption.privKey);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println(encrypted1.length);
		byte[] dec1 = null;
		// byte[] dec2 = null;
		try {
			dec1 = rsaEncryption.cipher.doFinal(encrypted1);
			// dec2 =rsaEncryption.cipher.doFinal(encrypted2);
		} catch (IllegalBlockSizeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (BadPaddingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		aesEncryption.skeySpec = new SecretKeySpec(dec1, "AES");
		System.out.println(Base64.encodeBase64String(aesEncryption.skeySpec
				.getEncoded()));

		System.out.println("GOT AES");
	}

	public void sendRSAKey() {
		byte[] ky = rsaEncryption.pubKey.getEncoded();
		try {
			udt.send(ky);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void receiveRSAKey() {
		DatagramPacket packet = null;
		try {
			packet = udt.receive(10000);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
		System.out.println("GOT Key");
		try {
			// byte[] b64 = Base64.decodeBase64(packet.getData());
			// System.out.println(new
			// String(Base64.encodeBase64(rsaEncryption.pubKey.getEncoded())));
			// System.out.println(new String(Arrays.copyOf(b64, b64.length-2)));

			rsaEncryption.remotePubKey = KeyFactory.getInstance("RSA")
					.generatePublic(new X509EncodedKeySpec(packet.getData()));
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public byte[] encrypt(byte[] data) {
		// byte[] b64 = Base64.encodeBase64(data);
		try {
			aesEncryption.cipher.init(Cipher.ENCRYPT_MODE,
					aesEncryption.skeySpec);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] b64encrypted = null;
		try {
			b64encrypted = aesEncryption.cipher.doFinal(data);
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b64encrypted;
	}

	public byte[] decrypt(byte[] data) {
		byte[] decrypted = null;
		try {
			aesEncryption.cipher.init(Cipher.DECRYPT_MODE,
					aesEncryption.skeySpec);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			decrypted = aesEncryption.cipher.doFinal(data);
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return decrypted;
	}

	private class RSA {
		public Key pubKey;
		public Key remotePubKey;
		public Key privKey;
		public SecureRandom random;
		public Cipher cipher;

		public RSA() {
			try {
				try {
					cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

				} catch (NoSuchPaddingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				random = new SecureRandom();
				KeyPairGenerator generator = null;
				generator = KeyPairGenerator.getInstance("RSA");

				generator.initialize(2048, random);
				KeyPair pair = generator.generateKeyPair();
				pubKey = pair.getPublic();

				privKey = pair.getPrivate();
				// System.out.println(Security.getProviders()[]);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private class AES {
		public SecretKeySpec skeySpec;

		public Cipher cipher;

		public AES() {
			KeyGenerator kgen = null;
			try {
				kgen = KeyGenerator.getInstance("AES");
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			kgen.init(256);
			SecretKey skey = kgen.generateKey();
			skeySpec = new SecretKeySpec(skey.getEncoded(), "AES");
			try {
				cipher = Cipher.getInstance("AES");
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public static void main(String args[]) {
		// NetEncryption ne = new NetEncryption();
	}
}
