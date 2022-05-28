import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Server {

    // Define the port on which the server is listening
    public static final int PORT = 8100;
    public static boolean running = true;

    public Server() throws IOException {
        ServerSocket serverSocket = null;

        try {
            //creation of a ServerSocket running at a specified port.
            serverSocket = new ServerSocket(PORT);

            while (running) {
                System.out.println("Waiting for a client ...");
                Socket socket = serverSocket.accept();
                // Execute the client's request in a new thread
                new ClientThread(socket, serverSocket).start();
            }
        } catch (SocketException e) {
            System.err.println("Server stopped!");
        } catch (IOException e) {
            System.err.println("Ooops... " + e);
        } finally {
            assert !(serverSocket == null);
            serverSocket.close();
            System.out.println("The server stopped!");
        }
    }


}