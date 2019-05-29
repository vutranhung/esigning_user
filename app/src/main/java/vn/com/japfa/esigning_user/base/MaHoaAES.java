package vn.com.japfa.esigning_user.base;

import android.util.Base64;

import java.security.Key;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class MaHoaAES {

    String IV = "IV_VALUE_16_BYTE";
    String PASSWORD = "PASSWORD_VALUE";
    String SALT = "SALT_VALUE";

    public String encryptAndEncode(String raw) {
        try {
            Cipher c = getCipher(Cipher.ENCRYPT_MODE);
            //chuyển string sang byte, đối với mã hóa file... tương tự
            byte[] encryptedVal = c.doFinal(raw.getBytes("UTF-8"));
            String s = Base64.encodeToString(encryptedVal,Base64.DEFAULT);
            return s;
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public String decodeAndDecrypt(String encrypted) throws Exception
    {
        //chuyển string sang byte, đối với mã hóa file... tương tự
        byte[] decodedValue = encrypted.getBytes("UTF-8");
        Cipher c = getCipher(Cipher.DECRYPT_MODE);
        byte[] decValue = c.doFinal(decodedValue);
        return new String(decValue,"UTF8");
    }

    //Tạo một đối tượng Cipher (đối tượng này dùng để mã hóa, giải mã) và chỉ rõ các thông tin
    private Cipher getCipher(int mode) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] iv = IV.getBytes("UTF-8");
        cipher.init(mode, generateKey(), new IvParameterSpec(iv));
        return cipher;
    }

    //Tạo key (tạo khóa mã hóa/giải mã)
    public Key generateKey() throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        char[] password = PASSWORD.toCharArray();
        byte[] salt = SALT.getBytes("UTF-8");

        KeySpec spec = new PBEKeySpec(password, salt, 65536, 128);
        SecretKey tmp = factory.generateSecret(spec);
        byte[] encoded = tmp.getEncoded();
        return new SecretKeySpec(encoded, "AES");
    }

}
