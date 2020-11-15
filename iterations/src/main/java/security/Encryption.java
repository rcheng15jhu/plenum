package security;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom; 
import java.util.Base64;
import java.util.Base64.Encoder;
  
public class Encryption { 
    public static String makeSalt() 
    { 
        byte[] saltBytes = new byte[24]; 
        SecureRandom random = new SecureRandom(); 
        random.nextBytes(saltBytes);
        Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(saltBytes);
    } 
  
    public static String sha2_hash(String plainText, String salt)
    { 
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
            baos.write(salt.getBytes(StandardCharsets.UTF_8)); 
            baos.write(plainText.getBytes(StandardCharsets.UTF_8));
            byte[] appendedText = baos.toByteArray(); 
            MessageDigest md = MessageDigest.getInstance("SHA-256"); 
            byte[] result = md.digest(appendedText);
            Encoder encoder = Base64.getEncoder();
            return encoder.encodeToString(result);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
} 