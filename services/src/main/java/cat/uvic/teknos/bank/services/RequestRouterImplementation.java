package cat.uvic.teknos.bank.services;

import cat.uvic.teknos.bank.models.Customer;
import cat.uvic.teknos.bank.services.controllers.Controller;
import cat.uvic.teknos.bank.services.controllers.CustomerController;
import cat.uvic.teknos.bank.services.exception.ResourceNotFoundException;
import cat.uvic.teknos.bank.services.exception.ServerErrorException;
import org.gradle.internal.impldep.com.fasterxml.jackson.core.JsonProcessingException;
import org.gradle.internal.impldep.com.fasterxml.jackson.databind.ObjectMapper;
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
        var controllerName =  pathParts[1];
        var responseJsonBody = "";

        switch (controllerName) {
            case "courses":
                responseJsonBody = manageCustomers(request, method, pathParts, responseJsonBody);
                break;

        }

        RawHttpResponse response = null;
        try {
            // TODO: Router logic



            response = rawHttp.parseResponse("HTTP/1.1 200 OK\r\n" +
                    "Content-Type: text/json\r\n" +
                    "Content-Length: " + responseJsonBody.length() + "\r\n" +
                    "\r\n" +
                    responseJsonBody);
        } catch (ResourceNotFoundException exception) {
            response = null;
        } catch (ServerErrorException exception) {
            response = null;
        }

        return null;
    }

    private String manageCustomers(RawHttpRequest request, String method, String[] pathParts, String responseJsonBody) {
        var controller = controllers.get(pathParts[1]);

        if (method == "POST") {
            var studentJson = request.getBody().get().toString();
            var mapper = new ObjectMapper();
            try {
                var customer = mapper.readValue(studentJson, Customer.class);
                controller.post(customer);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        } else if (method == "GET" && pathParts.length == 2) {
            responseJsonBody = controller.get();

        } else if (method == "DELETE") {
            var customerId = Integer.parseInt(pathParts[2]);
            controller.delete(customerId);

        } else if (method == "PUT") {
            var customerId = Integer.parseInt(pathParts[2]);
            var mapper = new ObjectMapper();

            try {
                var studentJson = request.getBody().get().toString();
                var customer = mapper.readValue(studentJson, Customer.class);
                controller.put(customerId, customer);

            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return responseJsonBody;
    }

}
