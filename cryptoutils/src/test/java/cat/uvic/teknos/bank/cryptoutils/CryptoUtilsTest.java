package cat.uvic.teknos.bank.cryptoutils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CryptoUtilsTest {

    @Test
    void getHash() {
        var text = "Some text...";
        var base64Test = "AAEA2727A0634520B50C1386B8158D76A8B18FCCF6D27B8FF901FB715BE9ECF2";

        assertEquals(base64Test, CryptoUtils.getHash(text));
    }

    @Test
    void createSecretKey() {

    }

    @Test
    void decodeSecretKey() {

    }

    @Test
    void encrypt() {

    }

    @Test
    void decrypt() {

    }

    @Test
    void asymmetricEncrypt() {

    }

    @Test
    void toBase64() {

    }

    @Test
    void asymmetricDecrypt() {

    }
}