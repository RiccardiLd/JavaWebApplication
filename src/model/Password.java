package model;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import org.apache.commons.codec.binary.Base64;

class Password {
    // The higher the number of iterations the more
    // expensive computing the hash is for us and
    // also for an attacker.
    private static final int iterations = 20*1000;
    private static final int saltLen = 32;
    private static final int desiredKeyLen = 256;

    /**
     * Computes a salted PBKDF2 hash of given plaintext password
     * suitable for storing in a database.
     * Empty passwords are not supported.
     * @param password the password input by the user.
     * @return a salted hash of the password.
     * @throws Exception IllegalStateException exception: empty password.
     */
     static String getSaltedHash(String password) throws Exception {
        byte[] salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(saltLen);
        // store the salt with the password
        return Base64.encodeBase64String(salt) + "$" + hash(password, salt);
    }

    /**
     * Checks whether given plaintext password corresponds
     * to a stored salted hash of the password.
     * @param password the password input by the user.
     * @param stored the hashed salted password stored in the database.
     * @return {@code true} if password matches {@code false} otherwise.
     * @throws Exception IllegalStateException exception: wrong stored password format.
     */
    static boolean check(String password, String stored) throws Exception{
        String[] saltAndHash = stored.split("\\$");
        if (saltAndHash.length != 2) {
            throw new IllegalStateException("The stored password must have the form 'salt$hash'");
        }
        String hashOfInput = hash(password, Base64.decodeBase64(saltAndHash[0]));
        return hashOfInput.equals(saltAndHash[1]);
    }

    /**
     * Using PBKDF2 from Sun, an alternative is scrypt.
     * @param password the password input by the user.
     * @param salt the salt.
     * @return the salted password hash.
     * @throws Exception IllegalStateException exception: empty password.
     * @see
     * <ul>
     *     <li><a href="https://github.com/wg/scrypt">Scrypt (alternative)</a></li>
     *     <li><a href="http://www.unlimitednovelty.com/2012/03/dont-use-bcrypt.html">Why you shouldn't use bcrypt</a></li>
     * </ul>
     */
    private static String hash(String password, byte[] salt) throws Exception {
        if (password == null || password.length() == 0) {
            throw new IllegalArgumentException("Empty passwords are not supported.");
        }
        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        SecretKey key = f.generateSecret(new PBEKeySpec(password.toCharArray(), salt, iterations, desiredKeyLen));
        return Base64.encodeBase64String(key.getEncoded());
    }
}