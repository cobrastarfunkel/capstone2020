package encryption;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class SymmetricKey {
	private SecretKeySpec secretKey;
	private Cipher cipher;
	private final String ALGORITHM = "AES";
	private final int KEY_LENGTH = 16;

	public SymmetricKey(String secret)
			throws NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException {

		byte[] key = new byte[secret.length()];
		key = fixKeyLength(secret, KEY_LENGTH);

		this.secretKey = new SecretKeySpec(key, ALGORITHM);
		this.cipher = Cipher.getInstance(ALGORITHM);
	}

	/**
	 * Needed because AES requires specific byte lengths so if the secret being used
	 * to generate the key is smaller this method pads it
	 * 
	 * @param secret String: Secret used to generate key
	 * @param length int: Required length of secret for Cipher
	 * @return byte[] of the the padded secret key
	 * @throws UnsupportedEncodingException
	 */
	private byte[] fixKeyLength(String secret, int length) throws UnsupportedEncodingException {
		if (secret.length() < length) {
			int paddSecret = length - secret.length();
			for (int i = 0; i < paddSecret; i++) {
				secret += " ";
			}
		}
		return secret.substring(0, length).getBytes("UTF-8");
	}

	public void encryptFile(File file)
			throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
		this.cipher.init(Cipher.ENCRYPT_MODE, this.secretKey);
		writeFile(file);
	}

	public void decryptFile(File file)
			throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
		this.cipher.init(Cipher.DECRYPT_MODE, this.secretKey);
		writeFile(file);
	}

	public byte[] decryptBytes(byte[] encryptedText)
			throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		byte[] retByte = encryptedText;
		this.cipher.init(Cipher.DECRYPT_MODE, this.secretKey);
		retByte = cipher.doFinal(encryptedText);
		return retByte;
	}

	private void writeFile(File file) throws IllegalBlockSizeException, BadPaddingException, IOException {
		FileInputStream fin = new FileInputStream(file);
		byte[] input = new byte[(int) file.length()];
		fin.read(input);

		FileOutputStream fout = new FileOutputStream(file);
		byte[] output = this.cipher.doFinal(input);
		fout.write(output);

		fout.flush();
		fout.close();
		fin.close();
	}
}
