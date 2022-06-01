package com.example.interface3;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.*;
import java.util.*;

// A client sends messages to the server, the server spawns a thread to communicate with the client.
// Each communication with a client is added to an array list so any message sent gets sent to every other client
// by looping through it.

public class Client {

    // A client has a socket to connect to the server and a reader and writer to receive and send messages respectively.
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;

    public Client(Socket socket) {
        try {
            this.socket = socket;
            //this.username = username;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            // Gracefully close everything.
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    public BufferedWriter getBufferedWriter() {
        return bufferedWriter;
    }

    @Override
    public String toString() {
        return "Client{" +
                "socket=" + socket +
                ", bufferedReader=" + bufferedReader +
                ", bufferedWriter=" + bufferedWriter +
                '}';
    }

    public void sendMyMessage(String message) throws IOException {
        bufferedWriter.write(message);
        bufferedWriter.newLine();
        bufferedWriter.flush();
    }

    public String receiveMyMessage() throws IOException {
        return bufferedReader.readLine();
    }

    public void sendMessage2(Client client) throws Exception {
        boolean running = true;
        while (running) {
            String request;
            SignUpMenu sup = new SignUpMenu(client);
            LogInMenu lmn = new LogInMenu(client);
            ChangePassword cp = new ChangePassword(client);
            //ChangeFirstName cfn = new ChangeFirstName(client);
            Profile prof=new Profile(client);
            Admin admin = new Admin(client);
            System.out.println("Client is ON!");
            lmn.dewIt();
//            Scanner myObj = new Scanner(System.in);
//            request = myObj.nextLine();
//            System.out.println("REF " + request);
//            bufferedWriter.write(request);
//            bufferedWriter.newLine();
//            bufferedWriter.flush();
        }
        String response = bufferedReader.readLine();
        if (response == null) {
            System.out.println("You've lost connection to the server");
            socket.close();
        } else System.out.println(response);
    }

    // Sending a message isn't blocking and can be done without spawning a thread, unlike waiting for a message.
    public void sendMessage() {
        try {
            // Initially send the username of the client.
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            // Create a scanner for user input.
            Scanner scanner = new Scanner(System.in);
            // While there is still a connection with the server, continue to scan the terminal and then send the message.
            while (socket.isConnected()) {
                String messageToSend = scanner.nextLine();
                bufferedWriter.write(username + ": " + messageToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            // Gracefully close everything.
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    // Listening for a message is blocking so need a separate thread for that.
    public void listenForMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msgFromGroupChat;
                // While there is still a connection with the server, continue to listen for messages on a separate thread.
                while (socket.isConnected()) {
                    try {
                        // Get the messages sent from other users and print it to the console.
                        msgFromGroupChat = bufferedReader.readLine();
                        System.out.println(msgFromGroupChat);
                    } catch (IOException e) {
                        // Close everything gracefully.
                        closeEverything(socket, bufferedReader, bufferedWriter);
                    }
                }
            }
        }).start();
    }

    // Helper method to close everything so you don't have to repeat yourself.
    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        // Note you only need to close the outer wrapper as the underlying streams are closed when you close the wrapper.
        // Note you want to close the outermost wrapper so that everything gets flushed.
        // Note that closing a socket will also close the socket's InputStream and OutputStream.
        // Closing the input stream closes the socket. You need to use shutdownInput() on socket to just close the input stream.
        // Closing the socket will also close the socket's input stream and output stream.
        // Close the socket after closing the streams.
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Run the program.
    public static void main(String[] args) throws Exception {

        // Get a username for the user and a socket connection.
        //Scanner scanner = new Scanner(System.in);
        //System.out.print("Enter your username for the group chat: ");
        //String username = scanner.nextLine();
        // Create a socket to connect to the server.

        var emailAddress = "username@domain.com";
        var regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        // EmailValidation.patternMatches(emailAddress, regexPattern)


        String serverAddress = "127.0.0.1";
        Socket socket = new Socket(serverAddress, 8100);

        // Pass the socket and give the client a username.
        Client client = new Client(socket);
        client.sendMessage2(client);
        // Infinite loop to read and send messages.
        //client.listenForMessage();
        //client.sendMessage();
    }

    public static boolean isValidEmail(String email) {
        String regex = "^(.+)@(.+)$";
        //Compile regular expression to get the pattern
        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public static boolean isValidPassw(String password) {
         String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        //Compile regular expression to get the pattern
        Pattern pattern = Pattern.compile(regexPattern);

        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

}

