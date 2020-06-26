
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.SecureRandom;

class DeltaInfo {

    public static void main(String[] args) throws Exception {
        if (args.length < 3)
        {
            throw new Exception("Erro nos parâmetros. Informe a semente, o trecho connhecido(Star Wars) e o criptograma" );
        }

        //byte[] result = decrypt("SKYWALKER1977", "Star Wars", convertString("4979822887f7aaf4eb569366e96a7575e6cbcd2732849cf6b16bab14e42a832e68ff1986a084e9915129398cc6b83145e935772c886df189"));


        byte[] result = decrypt(args[0],args[1],convertString(args[2]));

        if(result != null)
        {
            System.out.println("Sucesso! :)");
        }
        else{
            System.out.println("Não encontrado :(");
        }
    }

    private static byte[] decrypt(String seed, String knownText, byte[] cryptogram) {
        String numeroAtual = "";
        System.out.println(numeroAtual);

        // Tentativas de achar a chave correta utilizando forca bruta, ou seja, tentando de 00000000 ate 99999999

        for (int j = 0; j <= 9; j++) {
            numeroAtual += j;
            for (int r = 0; r <= 9; r++) {
                numeroAtual += r;
                for (int s = 0; s <= 9; s++) {
                    numeroAtual += s;
                    for (int z = 0; z <= 9; z++) {
                        numeroAtual += z;
                        for (int i = 0; i <= 9; i++) {
                            numeroAtual += i;
                            for (int f = 0; f <= 9; f++) {
                                numeroAtual += f;
                                for (int a = 0; a <= 9; a++) {
                                    numeroAtual += a;
                                    for (int y = 0; y <= 9; y++) {
                                        numeroAtual += y;
                                        try {

                                            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
                                            random.setSeed(seed.getBytes());
                                            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
                                            keyGenerator.init(56, random);
                                            Key chave = keyGenerator.generateKey();
                                            byte[] secret = numeroAtual.getBytes();
                                            IvParameterSpec spec = new IvParameterSpec(secret);
                                            Cipher cc = Cipher.getInstance("DES/CBC/PKCS5Padding");
                                            cc.init(Cipher.DECRYPT_MODE, chave, spec);
                                            byte[] decrypted = cc.doFinal(cryptogram);
                                            String frase = new String(decrypted, StandardCharsets.UTF_8);
                                            if (frase.contains(knownText)) {
                                                System.out.println("Valor encontrado: " + numeroAtual + " -  " + frase);
                                                return decrypted;
                                            }

                                        } catch (Exception e) {
                                            System.out.println("Erro :(");
                                        }
                                        numeroAtual = numeroAtual.substring(0, 7);
                                    }
                                    numeroAtual = numeroAtual.substring(0, 6);
                                }
                                numeroAtual = numeroAtual.substring(0, 5);
                            }
                            numeroAtual = numeroAtual.substring(0, 4);
                        }
                        numeroAtual = numeroAtual.substring(0, 3);
                    }
                    numeroAtual = numeroAtual.substring(0, 2);
                }
                numeroAtual = numeroAtual.substring(0, 1);
            }
            numeroAtual = "";
            if (j == 9) return null;
        }
        return null;
    }

    private static byte[] convertString(String hex) {
        byte[] valorembytes = new byte[hex.length() / 2];
        int i = 0;
        while (i < hex.length()) {
            int x = ((Character.digit(hex.charAt(i), 16) << 4) + Character.digit(hex.charAt(i + 1), 16));
            valorembytes[i / 2] = (byte) x;
            i += 2;
        }
        return valorembytes;
    }
}