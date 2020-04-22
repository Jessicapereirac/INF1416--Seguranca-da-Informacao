/*

 INF1416 - Segurança da Informação - Trabalho 2
 Jéssica Pereira - 1711179
  
 */

import java.security.KeyPair;
import java.security.KeyPairGenerator;

class MySignatureTeste {

    public static void main(String[] args) throws Exception {

        if(args.length < 2) {
            System.err.println("erro ao receber os parametros");
            System.exit(1);
        }
        System.out.println("\n==================================");
        System.out.println("\nTexto plano: " + args[0]);

        String text = args[0];
        byte[] plainText = text.getBytes("UTF8");

        System.out.print("\nTexto plano ( em hexa ): ");
        for(int i = 0; i < plainText.length; i++)
            System.out.print(String.format("%02X", plainText[i]));

        System.out.println("\n\n==================================");
        System.out.println("\nGerando chave RSA...");

        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair key = keyGen.generateKeyPair();

        System.out.println("Chave RSA gerada");

        System.out.println("\n==================================");

        System.out.println("\nGerando assinatura...");
        MySignature mySignature = MySignature.getInstance(args[1]);
        mySignature.initSign(key.getPrivate());
        mySignature.update(plainText);

        System.out.println("\n\nAssinatura gerada");

        System.out.println("\n==================================");

        byte[] signature = mySignature.sign();
        System.out.print("\n\nSignature ( em hexa ): ");
        for(int i = 0; i != signature.length; i++)
            System.out.print(String.format("%02x", signature[i]));

        mySignature.initVerify(key.getPublic());
        mySignature.update(plainText);

        System.out.println("\n\n==================================");

        if(mySignature.verify(signature))
            System.out.println("\nAssinatura VÁLIDA");
        else
            System.err.println("\nAssinatura INVÁLIDA");

        System.out.println("\n==================================");

    }

}