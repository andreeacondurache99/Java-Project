import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class ClientThread extends Thread {

    private Socket socket = null;
    private final ServerSocket serverSocket;
    private PrintWriter out;
    private boolean loggedIn = false;
    private User userLoggedIn;

    public ClientThread(Socket socket, ServerSocket serverSocket) {
        this.socket = socket;
        this.serverSocket = serverSocket;
    }

    public void run() {
        try {
            // Get the request from the input stream: client → server
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // Send the response to the output stream: server → client
            out = new PrintWriter(socket.getOutputStream());

            boolean running = true;
            socket.setSoTimeout(60000); //60 secunde
            while (running) {

                String request = in.readLine(); //the request from client
                System.out.println("Server received the request... " + request);
                if (request == null) {
                    System.out.println("The client doesn't have any other requests");
                    break;
                }
                if (request.equals("stop")) {
                    running = false;
                    stopServer();
                } else if (request.startsWith("register")) {
                    //register name: adds a new person to the social network;
                    register(request);
                } else if (request.startsWith("login")) {
                    //login name: establishes a connection between the server and the client;
                    login(request);
                } else if (request.equals("exit")) {
                    closeClient();
                } else System.out.println("Incorrect command!");

            }

        } catch (SocketTimeoutException e) {
            out.println("You've been gone for too long... ");
            out.println();
            System.err.println("The client is not here anymore... " + e);
        } catch (IOException e) {
            System.err.println("Communication error... " + e);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close(); // or use try-with-resources
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }

    private void register(String request) {

        String[] commandParam = request.split(" "); //contains register, name
        System.out.println(commandParam[0] +" "+ commandParam[1] +" "+ commandParam[2]);
//        String answer;
//        if (loggedIn)
//            answer = "You're already connected to another account!";
//        else {
//            String[] commandParam = request.split(" "); //contains register, name
//
//            User person = new User(commandParam[1]);
//            if (SocialNetwork.addUser(person))
//                answer = "User " + person.getUsername() + " has been registered succesfully!";
//
//            else answer = "User " + person.getUsername() + " already exists!";
//        }
//        out.println(answer);
//        out.flush();
    }

    private boolean login(String request) throws SQLException, NoSuchAlgorithmException {

        String[] commandParam = request.split(" "); //contains register, name
        System.out.println(commandParam[0] +" "+ commandParam[1] +" "+ commandParam[2]);

        String email = commandParam[1];
        String rawPassword = commandParam[2];
        return false;
//        if(ServerFunctions.alreadyExistingUser(email)){
//            String password = ServerFunctions.bytesToHex(ServerFunctions.hashing(rawPassword));
//
//            System.out.println( ServerFunctions.verifyCredentials(email, password));
//        }
//        return false;

//        String answer;
//        String[] commandParam = request.split(" "); //contains register, name
//
//        User person = new User(commandParam[1]);
//
//        if (!SocialNetwork.existsUser(person))
//            answer = "User doesn't exist";
//        else if (loggedIn)
//            answer = "User is already connected!";
//        else {
//            loggedIn = true;
//            userLoggedIn = person;
//            answer = "User " + userLoggedIn.getUsername() + " logged in succesfully!";
//        }
//
//        out.println(answer);
//        out.flush();

    }

    private void closeClient() throws IOException {
        System.out.println("The client disconnected!");
        out.println("Goodbye!");
        out.flush();
        socket.close();
    }

    private void stopServer() throws IOException {
        String answer = "Server Stopped!";
        out.println(answer);
        out.flush();
        serverSocket.close();
    }
}