import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

    boolean running = true;

    public void request() {
        String serverAddress = "127.0.0.1"; // The server's IP address
        int PORT = 8100; // The server's port
        try (Socket socket = new Socket(serverAddress, PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            // Send a request to the server
            //String request = "register ana";

            while (running) {
                String request;
                Scanner myObj = new Scanner(System.in);
                request = myObj.nextLine();
                out.println(request);

                if (request.equals("exit") || request.equals("stop"))
                    running = false;

                // Wait the response from the server
                String response = in.readLine();
                if (response == null) {
                    System.out.println("You've lost connection to the server");
                    socket.close();
                } else System.out.println(response);
            }


        } catch (SocketTimeoutException e) {
            System.err.println("You've been gone for too long... " + e);
        } catch (UnknownHostException e) {
            System.err.println("No server listening... " + e);
        } catch (IOException e) {
            System.out.println("Can't connect to the server! " + e);
        }
    }
}
