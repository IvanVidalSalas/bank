package cat.uvic.teknos.bank.services;

import cat.uvic.teknos.bank.models.Customer;
import cat.uvic.teknos.bank.services.controllers.Controller;
import cat.uvic.teknos.bank.services.controllers.CustomerController;
import cat.uvic.teknos.bank.services.exception.ResourceNotFoundException;
import cat.uvic.teknos.bank.services.exception.ServerErrorException;
import com.fasterxml.jackson.databind.ObjectMapper;

import rawhttp.core.RawHttp;
import rawhttp.core.RawHttpRequest;
import rawhttp.core.RawHttpResponse;

import java.util.Map;

public class RequestRouterImplementation implements RequestRouter {
    private static RawHttp rawHttp = new RawHttp();
    private final Map<String, Controller> controllers;

    public RequestRouterImplementation(Map<String, Controller> controllers){
        this.controllers = controllers;
    }

    @Override
    public RawHttpResponse<?> execRequest(RawHttpRequest request) {

        var path = request.getUri().getPath();
        var pathParts = path.split("/");
        var method = request.getMethod();
        var controllerName = pathParts[1];
        var responseJsonBody = "";
        RawHttpResponse<?> response;

        try {
            switch (controllerName) {
                case "customer":
                    responseJsonBody = manageCustomers(request, method, pathParts, responseJsonBody);
                    break;
                case "loan":
                    responseJsonBody = manageLoans(request, method, pathParts, responseJsonBody);
                    break;
                case "account":
                    responseJsonBody = manageAccounts(request, method, pathParts, responseJsonBody);
                    break;
                case "worker":
                    responseJsonBody = manageWorkers(request, method, pathParts, responseJsonBody);
                    break;
                case "transaction":
                    responseJsonBody = manageTransactions(request, method, pathParts, responseJsonBody);
                    break;
                default:
                    throw new ResourceNotFoundException("Unknown controller: " + controllerName);
            }
        } catch (ResourceNotFoundException e) {
            response = rawHttp.parseResponse("HTTP/1.1 404 Not Found\r\n" +
                    "Content-Type: application/json\r\n" +
                    "Content-Length: 0\r\n" +
                    "\r\n");
            return response;
        } catch (ServerErrorException e) {
            response = rawHttp.parseResponse("HTTP/1.1 500 Internal Server Error\r\n" +
                    "Content-Type: application/json\r\n" +
                    "Content-Length: 0\r\n" +
                    "\r\n");
            return response;
        } catch (Exception e) {
            e.printStackTrace(); // log the error for debugging
            response = rawHttp.parseResponse("HTTP/1.1 500 Internal Server Error\r\n" +
                    "Content-Type: application/json\r\n" +
                    "Content-Length: 0\r\n" +
                    "\r\n");
            return response;
        }
        try {
            return rawHttp.parseResponse("HTTP/1.1 200 OK\r\n" +
                    "Content-Type: application/json\r\n" +
                    "Content-Length: " + responseJsonBody.length() + "\r\n" +
                    "\r\n" +
                    responseJsonBody);
        } catch (Exception e) {
            e.printStackTrace(); // log the error for debugging
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
