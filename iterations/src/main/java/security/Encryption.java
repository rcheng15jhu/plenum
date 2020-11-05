import java.io.ByteArrayOutputStream; 
import java.security.MessageDigest;
import sun.security.provider.SecureRandom; 
  
public class Encryption { 
    public static byte[] makeSalt() 
    { 
        byte[] salt = new byte[16]; 
        SecureRandom sr = new SecureRandom(); 
        sr.engineNextBytes(salt); 
        return salt;
    } 
  
    public static byte[] sha2_hash(String plainText, byte[] salt) throws Exception 
    { 
        ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
        baos.write(salt); 
        baos.write(plainText.getBytes()); 
        byte[] appendedText = baos.toByteArray(); 
        MessageDigest md = MessageDigest.getInstance("SHA-256"); 
        return md.digest(appendedText); 
    }
} 