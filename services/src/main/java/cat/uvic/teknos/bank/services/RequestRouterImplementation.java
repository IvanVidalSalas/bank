package cat.uvic.teknos.bank.services;

import cat.uvic.teknos.bank.cryptoutils.CryptoUtils;
import cat.uvic.teknos.bank.services.controllers.Controller;
import cat.uvic.teknos.bank.services.exception.ResourceNotFoundException;
import cat.uvic.teknos.bank.services.exception.ServerErrorException;

import rawhttp.core.RawHttp;
import rawhttp.core.RawHttpRequest;
import rawhttp.core.RawHttpResponse;

import javax.crypto.SecretKey;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.util.Map;

public class RequestRouterImplementation implements RequestRouter {
    private static final RawHttp rawHttp = new RawHttp();
    private final Map<String, Controller> controllers;
    private static final String KEYSTORE_PATH = "/server.p12";
    private static final String KEYSTORE_PASSWORD = "Teknos01.";
    private static final String KEY_ALIAS = "server";
    private final PrivateKey serverPrivateKey;

    public RequestRouterImplementation(Map<String, Controller> controllers) {
        this.controllers = controllers;

        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            try (InputStream keyStoreStream = getClass().getResourceAsStream(KEYSTORE_PATH)) {
                if (keyStoreStream == null) {
                    throw new FileNotFoundException("Keystore file not found in classpath: " + KEYSTORE_PATH);
                }
                keyStore.load(keyStoreStream, KEYSTORE_PASSWORD.toCharArray());
            }

            serverPrivateKey = (PrivateKey) keyStore.getKey(KEY_ALIAS, KEYSTORE_PASSWORD.toCharArray());

            if (serverPrivateKey == null) {
                throw new RuntimeException("Failed to load private key from keystore.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error loading server keystore: " + e.getMessage(), e);
        }
    }

    @Override
    public RawHttpResponse<?> execRequest(RawHttpRequest request) {
        String path = request.getUri().getPath();
        String[] pathParts = path.split("/");
        String method = request.getMethod();
        String controllerName;
        String responseJsonBody = "";
        RawHttpResponse<?> response;

        try {

            controllerName = pathParts[1];

            // Extract encrypted symmetric key
            String encryptedSymmetricKeyBase64 = request.getHeaders().getFirst("Symmetric-Key")
                    .orElseThrow(() -> new ServerErrorException("Missing Symmetric-Key header"));

            // Decrypt symmetric key
            String symmetricKeyBase64 = CryptoUtils.asymmetricDecrypt(encryptedSymmetricKeyBase64, serverPrivateKey);
            SecretKey symmetricKey = CryptoUtils.decodeSecretKey(symmetricKeyBase64);

            // Validate and decrypt the request body
            String requestBody = request.getBody().map(body -> {
                try {
                    String encryptedBody = new String(body.asRawBytes(), StandardCharsets.UTF_8);

                    // Validate hash
                    String bodyHash = request.getHeaders().getFirst("Body-Hash")
                            .orElseThrow(() -> new ServerErrorException("Missing Body-Hash header"));
                    String calculatedHash = CryptoUtils.getHash(encryptedBody);
                    if (!calculatedHash.equals(bodyHash)) {
                        throw new ServerErrorException("Body hash mismatch");
                    }

                    // Decrypt body if not empty
                    return encryptedBody.isEmpty() ? "" : CryptoUtils.decrypt(encryptedBody, symmetricKey);
                } catch (Exception e) {
                    throw new ServerErrorException("Error decrypting request body", e);
                }
            }).orElse("");

            switch (controllerName) {
                case "customer":
                    responseJsonBody = manageCustomers(request, method, pathParts, requestBody);
                    break;
                case "loan":
                    responseJsonBody = manageLoans(request, method, pathParts, requestBody);
                    break;
                case "account":
                    responseJsonBody = manageAccounts(request, method, pathParts, requestBody);
                    break;
                case "worker":
                    responseJsonBody = manageWorkers(request, method, pathParts, requestBody);
                    break;
                case "transaction":
                    responseJsonBody = manageTransactions(request, method, pathParts, requestBody);
                    break;
                default:
                    throw new ResourceNotFoundException("Unknown controller: " + controllerName);
            }

            String encryptedResponseBody = CryptoUtils.encrypt(responseJsonBody, symmetricKey);

            return rawHttp.parseResponse("HTTP/1.1 200 OK\r\n" +
                    "Content-Type: application/json\r\n" +
                    "Content-Length: " + encryptedResponseBody.length() + "\r\n" +
                    "\r\n" +
                    encryptedResponseBody);

        } catch (ResourceNotFoundException e) {
            return rawHttp.parseResponse("HTTP/1.1 404 Not Found\r\n" +
                    "Content-Type: application/json\r\n" +
                    "Content-Length: 0\r\n" +
                    "\r\n");
        } catch (ServerErrorException e) {
            return rawHttp.parseResponse("HTTP/1.1 500 Internal Server Error\r\n" +
                    "Content-Type: application/json\r\n" +
                    "Content-Length: 0\r\n" +
                    "\r\n");
        } catch (Exception e) {
            e.printStackTrace();
            return rawHttp.parseResponse("HTTP/1.1 500 Internal Server Error\r\n" +
                    "Content-Type: application/json\r\n" +
                    "Content-Length: 0\r\n" +
                    "\r\n");
        }
    }


    private String manageCustomers(RawHttpRequest request, String method, String[] pathParts, String responseJsonBody) {

        var controller = controllers.get(pathParts[1]);

        if (method.equals("POST")) {
            // New Customer
            var customerJson = request.getBody().get().toString();
            controller.post(customerJson);

        } else if (method.equals("GET") && pathParts.length == 2) {
            // All Customers
            responseJsonBody = controller.get();

        } else if (method.equals("GET") && pathParts.length == 3) {
            // Customer by ID
            responseJsonBody = controller.get(Integer.parseInt(pathParts[2]));

        } else if (method.equals("DELETE") && pathParts.length == 3) {
            var customerId = Integer.parseInt(pathParts[2]);
            controller.delete(customerId);

        } else if (method.equals("PUT")) {
            // Update Customer
            var customerId = Integer.parseInt(pathParts[2]);

            var customerJson = request.getBody().get().toString();
            controller.put(customerId, customerJson);

        }
        return responseJsonBody;
    }

    private String manageLoans(RawHttpRequest request, String method, String[] pathParts, String responseJsonBody) {
        var controller = controllers.get(pathParts[1]);

        if (method.equals("POST")) {
            // New Loan
            var loanJson = request.getBody().get().toString();
            controller.post(loanJson);

        } else if (method.equals("GET") && pathParts.length == 2) {
            //All Loans
            responseJsonBody = controller.get();

        } else if (method.equals("GET") && pathParts.length == 3) {
            // Loan by ID
            responseJsonBody = controller.get(Integer.parseInt(pathParts[2]));

        } else if (method.equals("DELETE") && pathParts.length == 3) {
            var loanId = Integer.parseInt(pathParts[2]);
            controller.delete(loanId);

        } else if (method.equals("PUT")) {
            // Update Loan
            var loanId = Integer.parseInt(pathParts[2]);
            var loanJson = request.getBody().get().toString();
            controller.put(loanId, loanJson);
        }
        return responseJsonBody;
    }

    private String manageAccounts(RawHttpRequest request, String method, String[] pathParts, String responseJsonBody) {
        var controller = controllers.get(pathParts[1]);

        if (method.equals("POST")) {
            // New Account
            var accountJson = request.getBody().get().toString();
            controller.post(accountJson);

        } else if (method.equals("GET") && pathParts.length == 2) {
            //All Accounts
            responseJsonBody = controller.get();

        } else if (method.equals("GET") && pathParts.length == 3) {
            // Account by ID
            responseJsonBody = controller.get(Integer.parseInt(pathParts[2]));

        } else if (method.equals("DELETE") && pathParts.length == 3) {
            var accountId = Integer.parseInt(pathParts[2]);
            controller.delete(accountId);

        } else if (method.equals("PUT") && pathParts.length == 3) {
            // Update Account
            var accountId = Integer.parseInt(pathParts[2]);
            var accountJson = request.getBody().get().toString();
            controller.put(accountId, accountJson);
        }

        return responseJsonBody;
    }

    private String manageWorkers(RawHttpRequest request, String method, String[] pathParts, String responseJsonBody) {
        var controller = controllers.get(pathParts[1]);

        if (method.equals("POST")) {
            // New worker
            var workerJson = request.getBody().get().toString();
            controller.post(workerJson);

        } else if (method.equals("GET") && pathParts.length == 2) {
            // All workers
            responseJsonBody = controller.get();

        } else if (method.equals("GET") && pathParts.length == 3) {
            // Worker by ID
            responseJsonBody = controller.get(Integer.parseInt(pathParts[2]));

        } else if (method.equals("DELETE") && pathParts.length == 3) {
            var workerId = Integer.parseInt(pathParts[2]);
            controller.delete(workerId);

        } else if (method.equals("PUT") && pathParts.length == 3) {
            // Update Worker
            var workerId = Integer.parseInt(pathParts[2]);
            var workerJson = request.getBody().get().toString();
            controller.put(workerId, workerJson);
        }

        return responseJsonBody;
    }

    private String manageTransactions(RawHttpRequest request, String method, String[] pathParts, String responseJsonBody) {

        var controller = controllers.get(pathParts[1]);

        if (method.equals("POST")) {
            // Create Transaction
            var transactionJson = request.getBody().get().toString();
            controller.post(transactionJson);

        } else if (method.equals("GET") && pathParts.length == 2) {
            // All transactions
            responseJsonBody = controller.get();

        } else if (method.equals("GET") && pathParts.length == 3) {
            // Transaction by ID
            responseJsonBody = controller.get(Integer.parseInt(pathParts[2]));

        } else if (method.equals("DELETE") && pathParts.length == 3) {
            var transactionId = Integer.parseInt(pathParts[2]);
            controller.delete(transactionId);

        } else if (method.equals("PUT") && pathParts.length == 3) {
            // Update Transaction
            var transactionId = Integer.parseInt(pathParts[2]);
            var transactionJson = request.getBody().get().toString();
            controller.put(transactionId, transactionJson);
        }

        return responseJsonBody;
    }

}
