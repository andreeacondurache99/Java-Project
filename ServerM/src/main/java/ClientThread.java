import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class ClientThread extends Thread {

    private PrintWriter out;
    private boolean loggedIn = false;
    private User userLoggedIn;

    private ServerSocket serverSocket;
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    private String succesMessage = "succes";
    private String failedMessage = "failed";

    public ClientThread(Socket socket, ServerSocket serverSocket) throws IOException {
        try {
            this.socket = socket;
            this.serverSocket = serverSocket;
            bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
        } catch (IOException e){
            System.out.println("Error creating client thread");
            e.printStackTrace();
        }
    }

    private void sendMyMessage(String message) throws IOException {
        bufferedWriter.write(message);
        bufferedWriter.newLine();
        bufferedWriter.flush();
    }

    private String receiveMyMessage() throws IOException {
        return bufferedReader.readLine();
    }


    public void run() {
        try {
            // Get the request from the input stream: client → server
            //BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // Send the response to the output stream: server → client
            //out = new PrintWriter(socket.getOutputStream());

            boolean running = true;
            socket.setSoTimeout(60000); //60 secunde
            while (running) {

                String request = receiveMyMessage(); //the request from client
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

                    boolean valid = login(request);
                    if(valid){
                        sendMyMessage(succesMessage);
                    }
                    else{
                        sendMyMessage(failedMessage);
                    }

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

    private void register(String request) throws SQLException, NoSuchAlgorithmException {

        String[] commandParam = request.split(" "); //contains register, name
        System.out.println(commandParam[0] +" "+ commandParam[1] +" "+ commandParam[2] + " " + commandParam[3] + " " + commandParam[4]);

        String email = commandParam[1];
        String rawPassword = commandParam[2];
        String firstName = commandParam[3];
        String lastName = commandParam[4];

        if(loggedIn || DatabaseFunctions.alreadyExistingUser(email)){
            System.out.println("Acest email este deja folosit");
        }
        else{
            DatabaseFunctions.addToDatabase(email, rawPassword, firstName, lastName);
            System.out.println("Autentificare reusita");
        }
    }

    private boolean login(String request) throws SQLException, NoSuchAlgorithmException {

        String[] commandParam = request.split(" "); //contains register, name
        System.out.println(commandParam[0] +" "+ commandParam[1] +" "+ commandParam[2]);

        String email = commandParam[1];
        String rawPassword = commandParam[2];

        if(DatabaseFunctions.alreadyExistingUser(email)){
            boolean response = DatabaseFunctions.verifyCredentials(email, rawPassword);
            loggedIn = true;
            return response;
        }
        return false;
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