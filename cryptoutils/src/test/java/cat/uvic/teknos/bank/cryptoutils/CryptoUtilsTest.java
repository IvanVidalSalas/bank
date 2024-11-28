package cat.uvic.teknos.bank.cryptoutils;

import org.junit.jupiter.api.Test;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

import static org.junit.jupiter.api.Assertions.*;

class CryptoUtilsTest {

    @Test
    void getHash() {
        var text = "Some text...";
        var base64Test = "quonJ6BjRSC1DBOGuBWNdqixj8z20nuP+QH7cVvp7PI=";

        assertEquals(base64Test, CryptoUtils.getHash(text));
    }

    @Test
    void createSecretKey() {
        var secretKey = CryptoUtils.createSecretKey();
        assertNotNull(secretKey);
        assertEquals("AES", secretKey.getAlgorithm());
    }

    @Test
    void decodeSecretKey() {
        var secretKey = CryptoUtils.createSecretKey();
        var base64Key = CryptoUtils.toBase64(secretKey.getEncoded());
        var decodedKey = CryptoUtils.decodeSecretKey(base64Key);

        assertEquals(secretKey, decodedKey);
    }

    @Test
    void encrypt() {
        var secretKey = CryptoUtils.createSecretKey();
        var plainText = "This is a test.";
        var encryptedText = CryptoUtils.encrypt(plainText, secretKey);

        assertNotNull(encryptedText);
        assertNotEquals(plainText, encryptedText);
    }

    @Test
    void decrypt() {
        var secretKey = CryptoUtils.createSecretKey();
        var plainText = "This is a test.";
        var encryptedText = CryptoUtils.encrypt(plainText, secretKey);
        var decryptedText = CryptoUtils.decrypt(encryptedText, secretKey);

        assertEquals(plainText, decryptedText);
    }

    @Test
    void asymmetricEncrypt() throws Exception {
        var keyPair = generateKeyPair();
        var plainText = "Some asymmetric text.";
        var encryptedText = CryptoUtils.asymmetricEncrypt(plainText, keyPair.getPublic());

        assertNotNull(encryptedText);
        assertNotEquals(plainText, encryptedText);
    }

    @Test
    void asymmetricDecrypt() throws Exception {
        var keyPair = generateKeyPair();
        var plainText = "Some asymmetric text.";
        var encryptedText = CryptoUtils.asymmetricEncrypt(plainText, keyPair.getPublic());
        var decryptedText = CryptoUtils.asymmetricDecrypt(encryptedText, keyPair.getPrivate());

        assertEquals(plainText, decryptedText);
    }

    @Test
    void toBase64() {
        var text = "Test";
        var bytes = text.getBytes();
        var base64 = CryptoUtils.toBase64(bytes);

        assertEquals("VGVzdA==", base64);
    }

    @Test
    void fromBase64() {
        var base64 = "VGVzdA==";
        var bytes = CryptoUtils.fromBase64(base64);

        assertEquals("Test", new String(bytes));
    }

    private KeyPair generateKeyPair() throws Exception {
        var keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        return keyGen.generateKeyPair();
    }
}
