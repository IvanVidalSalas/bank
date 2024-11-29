package cat.uvic.teknos.bank.services;

import cat.uvic.teknos.bank.services.exception.ServerException;
import rawhttp.core.RawHttp;
import rawhttp.core.RawHttpOptions;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Server {

    public final int PORT = 3007;
    private final RequestRouter requestRouter;
    private volatile boolean SHUTDOWN_SERVER = false;

    private final ExecutorService threadPool = Executors.newFixedThreadPool(10);
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private ServerSocket serverSocket;

    public Server(RequestRouter requestRouter) {
        this.requestRouter = requestRouter;
    }

    public void start() {

        System.out.println("Server started on port: " + PORT);

        scheduler.scheduleAtFixedRate(this::checkShutdown, 0, 2, TimeUnit.SECONDS);

        try {
            serverSocket = new ServerSocket(PORT);

            while (!SHUTDOWN_SERVER) {
                try {
                    var clientSocket = serverSocket.accept();
                    threadPool.execute(() -> clientRequest(clientSocket));
                } catch (IOException e) {
                    if (!SHUTDOWN_SERVER) {
                        System.err.println("Error accepting client connection: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            throw new ServerException("Couldn't start the server", e);
        } finally {
            shutdown();
            System.out.println("The Server has closed.");
        }
    }

    private void clientRequest(java.net.Socket clientSocket) {

        try (clientSocket) {

            System.out.println("Request on thread: " + Thread.currentThread().getName());

            var rawHttp = new RawHttp(RawHttpOptions.newBuilder().doNotInsertHostHeaderIfMissing().build());
            var request = rawHttp.parseRequest(clientSocket.getInputStream()).eagerly();
            var response = requestRouter.execRequest(request);

            System.out.println("Received request: " + request.getStartLine());

            response.writeTo(clientSocket.getOutputStream());
        } catch (IOException e) {
            System.err.println("Error handling client request: " + e.getMessage());
        }
    }

    private void checkShutdown() {

        Properties properties = new Properties();

        try (FileInputStream fis = new FileInputStream("services/src/main/resources/server.properties")) {

            properties.load(fis);
            String shutdown = properties.getProperty("shutdown");

            if ("true".equalsIgnoreCase(shutdown)) {

                SHUTDOWN_SERVER = true;

                if (serverSocket != null && !serverSocket.isClosed()) {
                    try {
                        serverSocket.close();
                    } catch (IOException e) {
                        System.err.println("Error closing server socket: " + e.getMessage());
                    }
                }

                shutdown();
            }
        } catch (IOException e) {
            System.err.println("Error reading server.properties: " + e.getMessage());
        }
    }

    private void shutdown() {
        try {
            threadPool.shutdownNow();
            scheduler.shutdownNow();
        } catch (Exception e) {
            System.err.println("Error shutting down services: " + e.getMessage());
        }
    }
}
