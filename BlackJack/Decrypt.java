import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.util.Base64;

class Decrypt {
    static String decrypt(String s) throws Exception{
        int keyInt = Integer.parseInt(s.substring(0, 1));
        int ivInt = Integer.parseInt(s.substring(1, 2));
        byte[] KeyByte = new byte[16];
        byte[] IvByte = new byte[16];
        Arrays.fill(KeyByte,(byte) keyInt);
        Arrays.fill(IvByte,(byte) ivInt);
        IvParameterSpec iv = new IvParameterSpec(IvByte);
        SecretKeySpec key = new SecretKeySpec(KeyByte,"AES");
        Cipher decryptEr = Cipher.getInstance("AES/CBC/PKCS5Padding");
        decryptEr.init(Cipher.DECRYPT_MODE,key,iv);
        byte[] str2 = Base64.getDecoder().decode(s.substring(2));
        byte[] str = decryptEr.doFinal(str2);
        return new String(str);
    }
}
