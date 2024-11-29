package cat.uvic.teknos.bank.cryptoutils;

public class CryptoException extends RuntimeException {

  public CryptoException() {
  }

  public CryptoException(String message) {
    super(message);
  }

  public CryptoException(String message, Throwable cause) {
    super(message, cause);
  }

  public CryptoException(Throwable cause) {
    super(cause);
  }

  public CryptoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}



// keytool -list -v -keystore client1.p12 -storepass Teknos01. resources services
// keytool -list -v -keystore server.p12 -storepass Teknos01. resources client
