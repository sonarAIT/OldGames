import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

class Encrypt {
    static String encrypt(String s) throws Exception{
        Random ra = new Random();
        int keyInt = ra.nextInt(10);
        int ivInt = ra.nextInt(10);
        byte[] KeyByte = new byte[16];
        byte[] IvByte = new byte[16];
        Arrays.fill(KeyByte,(byte) keyInt);
        Arrays.fill(IvByte,(byte) ivInt);
        IvParameterSpec iv = new IvParameterSpec(IvByte);
        SecretKeySpec key = new SecretKeySpec(KeyByte,"AES");
        Cipher encryptEr = Cipher.getInstance("AES/CBC/PKCS5Padding");
        encryptEr.init(Cipher.ENCRYPT_MODE,key,iv);
        byte[] str = encryptEr.doFinal(s.getBytes());
        byte[] str2 = Base64.getEncoder().encode(str);
        return String.format("%d%d%s", keyInt, ivInt,new String(str2));
    }
}
