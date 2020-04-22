/*

 INF1416 - Segurança da Informação - Trabalho 2
 Jéssica Pereira - 1711179
  
 */

import java.security.*;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

public class MySignature {

    private MessageDigest messageDigest;
    private Cipher cipher;

    private static PrivateKey privateKey;
    private static PublicKey publicKey;
    private static byte[] plainText;


    MySignature(String algoritimo) throws Exception {

        String[] algoritimos = algoritimo.split("with");

        if (algoritimos.length != 2) {
            System.err.println("entrada nao esperada");
            System.exit(1);
        }

        System.out.print("\nAlgoritimo Digest: " + algoritimos[0]);
        System.out.print("\nAlgoritimo Encription: " + algoritimos[1]);

        messageDigest = MessageDigest.getInstance(algoritimos[0]);
        cipher = Cipher.getInstance(algoritimos[1] + "/ECB/PKCS1Padding");

    }

    public static MySignature getInstance(String algoritmo) throws Exception {

        MySignature newSignature = new MySignature(algoritmo);
        return newSignature;

    }

    public void initSign (PrivateKey pk) throws InvalidKeyException {
        try {
            privateKey = pk;
        } catch (ClassCastException cce) {
            throw new InvalidKeyException("Chave privada errada");
        }
    }

    public void update(byte[] data) throws SignatureException {
        messageDigest.update(data);
    }

    public void initVerify(PublicKey ppk) throws InvalidKeyException {
        try {
            publicKey = ppk;
        } catch (ClassCastException cce) {
            throw new InvalidKeyException("Chave publica errada");
        }
    }

    public byte[] sign() throws SignatureException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {

        byte[] sing = null;
        byte[] digest = messageDigest.digest();

        System.out.print("\nDigest gerado: ");
        for (int i = 0; i != digest.length; i++)
            System.out.print(String.format("%02X", digest[i]));

        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        sing = cipher.doFinal(digest);

        return sing;
    }

    public boolean verify(byte[] signature) throws Exception {

        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        byte[] digest = messageDigest.digest();
        byte[] newPlainText = cipher.doFinal(signature);

        System.out.print("\nDigest recebido: ");
        for (int i = 0; i != digest.length; i++)
            System.out.print(String.format("%02X", digest[i]));

        if (Arrays.equals(newPlainText, digest))
            return true;
        else
            return false;
    }
}

