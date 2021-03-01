package uwl.senate.coc.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;

public class EncryptionUtils {
	private final static String TYPE = "AES";
	private final static String ALGORITHM = "AES";

	private static byte[] keyBytes = { 
			0x18, 0x33, 0x24, 0x01, 
			0x49, 0x73, 0x24, 0x28, 
			0x78, 0x41, 0x49, 0x63, 
			0x41, 0x73, 0x30, 0x31 };

	public static String encrypt( String plain ) {
		try {
			Cipher cipher = Cipher.getInstance( TYPE );
			SecretKeySpec key = new SecretKeySpec( keyBytes, ALGORITHM );
			cipher.init( Cipher.ENCRYPT_MODE, key );
			return Base64.encodeBase64String( cipher.doFinal( plain.getBytes() ) );
		} catch( Exception e) {
			throw new RuntimeException("encoding error from Encryption Utils: " + e.getMessage() );
		}
	}

	public static String decrypt( String encoded ) {
		try {
			Cipher cipher = Cipher.getInstance( TYPE );
			SecretKeySpec key = new SecretKeySpec( keyBytes, ALGORITHM );
			cipher.init( Cipher.DECRYPT_MODE, key, cipher.getParameters() );
			return new String( cipher.doFinal( Base64.decodeBase64(encoded) ) );
		} catch( Exception e ) {
			throw new RuntimeException("encoding error from Encryption Utils: " + e.getMessage() );
		}
	}
}
