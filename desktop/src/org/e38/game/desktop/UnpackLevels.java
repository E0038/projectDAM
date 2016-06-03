package org.e38.game.desktop;

import org.tukaani.xz.LZMA2Options;
import org.tukaani.xz.XZ;
import org.tukaani.xz.XZInputStream;
import org.tukaani.xz.XZOutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.util.Base64;

/**
 * Created by sergi on 6/2/16.
 */
public class UnpackLevels {
    static String out_path = "./rawLevels.json";
    static String raw_path = "./android/assets/raw/levels.crypt.lzma";

    public static void main(String[] args) throws Exception {
        byte[] decoded = Base64.getDecoder().decode("Whiy3TtJhr484rDop7vsfg==");
        Cipher ciper = Cipher.getInstance("AES");
        SecretKeySpec key = new SecretKeySpec(decoded, 0, decoded.length, "AES");
        ciper.init(Cipher.ENCRYPT_MODE, key);
        Cipher dcip = Cipher.getInstance("AES");
        dcip.init(Cipher.DECRYPT_MODE, key);
        File tmp = File.createTempFile("lvl", "tmp");
        byte[] buffer = new byte[1 << 15];


    }
}
