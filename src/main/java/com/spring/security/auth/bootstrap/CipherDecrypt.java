package com.spring.security.auth.bootstrap;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class CipherDecrypt {
    public static void main(String[] args) throws DecoderException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
//        String param = "JOnK3s90OI8Bm/SlOXRpZMMWTS1GZ5O/uPwjgV2v/2yxnEh8CjAQ80FaByrYr50h6uFTExkFs5exQOubWHdRQPGFT5N42oejcerMnRUjnQYwBjhY6UxJmuFA4qkX5gjCJ145vNfb4sDRMJUV+I09SPFyWVqFObqm1FheAWiv6xz3H7Qqp906YVnoNI5tsfhJ";
        String param = "uLbyh5N3EqFU8eayTu%2BoO5JdmvGJnUAO98lGckc9X8Kspfe6wozm5UxrX9U1sdC9mityx89Pvxz2NXMztdWMuAKA3xUMwVhKGyTn01kaIUOyY%2B3wBR3ShoyrCklAlAUYAd9EpYBvkxitXujNBp5eEsidXlDSof4SbwbIVf%2BlNv4%3D";

        String jsonString = decode(param, "62CB291D46399ABA0FF20E5686057E03", "7A7B109CF3C6D9553A07E90A97AEA093");

        System.out.println(jsonString);
    }

    public static String decode(String str, String strKey, String strIv)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException, DecoderException {

        SecretKeySpec key = new SecretKeySpec(Hex.decodeHex(strKey), "AES");
        IvParameterSpec iv = new IvParameterSpec(Hex.decodeHex(strIv));

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, key, iv);

        byte[] decode = Base64.getDecoder().decode(str);
        byte[] decrypt = cipher.doFinal(decode);

        return new String(decrypt);
    }
}
