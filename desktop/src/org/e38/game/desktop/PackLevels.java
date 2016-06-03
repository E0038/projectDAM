package org.e38.game.desktop;

import org.tukaani.xz.LZMA2Options;
import org.tukaani.xz.XZInputStream;
import org.tukaani.xz.XZOutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;

/**
 * Created by sergi on 6/2/16.
 */
public class PackLevels {
    static String raw_path = "./android/assets/raw/rawLevels.json";
    static String out_path = "./android/assets/raw/levels.crypt.lzma";

    public static void main(String[] args) throws Exception {
        byte[] decoded = com.badlogic.gdx.utils.Base64Coder.decode("Whiy3TtJhr484rDop7vsfg==");
        Cipher ciper = Cipher.getInstance("AES");
        System.out.println(new File(out_path).delete());
        SecretKeySpec key = new SecretKeySpec(decoded, 0, decoded.length, "AES");
        ciper.init(Cipher.ENCRYPT_MODE, key);
        Cipher dcip = Cipher.getInstance("AES");
        dcip.init(Cipher.DECRYPT_MODE, key);
        byte[] buffer = new byte[1 << 15];
        write(ciper, buffer);
//debug
//        Debug_Read(dcip, buffer);
    }

    private static void write(Cipher ciper, byte[] buffer) throws IOException {
        int n;
        try (FileInputStream inputStream = new FileInputStream(raw_path);
             CipherOutputStream outputStream = new CipherOutputStream(new FileOutputStream(out_path), ciper);
             XZOutputStream xzOutputStream = new XZOutputStream(outputStream, new LZMA2Options())
        ) {
            while ((n = inputStream.read(buffer)) != -1) {
                xzOutputStream.write(buffer, 0, n);
            }
        }
    }

    private static void Debug_Read(Cipher dcip, byte[] buffer) throws IOException {
        int n;
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             XZInputStream xzInputStream = new XZInputStream(new CipherInputStream(new FileInputStream(out_path), dcip));
        ) {

            while ((n = xzInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, n);
            }
            System.out.println(outputStream);
        }
    }
}
