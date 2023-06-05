package login;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

public class Utils {
    public static String encodePassword(String password) {
        Encoder encoder = Base64.getEncoder();

        String encoded = encoder.encodeToString(password.getBytes());
        System.out.println("Encoded String : " + encoded);

        return encoded;
    }

    public static String decodePassword(String encoded) {
        Decoder decoder = Base64.getDecoder();

        byte[] stringBytes = decoder.decode(encoded);
        System.out.println("Decoded string : " + new String(stringBytes));

        return new String(stringBytes);
    }

    public static void main(String[] args) {
        encodePassword("shyam");
        decodePassword("c2h5YW0=");
    }
}
