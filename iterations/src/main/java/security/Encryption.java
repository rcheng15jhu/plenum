package security;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom; 
  
public class Encryption { 
    public static String makeSalt() 
    { 
        byte[] saltBytes = new byte[24]; 
        SecureRandom random = new SecureRandom(); 
        random.nextBytes(saltBytes);
        return new String(saltBytes, StandardCharsets.UTF_8);
    } 
  
    public static String sha2_hash(String plainText, String salt) throws Exception 
    { 
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
            baos.write(salt.getBytes(StandardCharsets.UTF_8)); 
            baos.write(plainText.getBytes(StandardCharsets.UTF_8));
            byte[] appendedText = baos.toByteArray(); 
            MessageDigest md = MessageDigest.getInstance("SHA-256"); 
            byte[] result = md.digest(appendedText);
            return new String(result, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 