package de.tuberlin.cit.tcpdaemon;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private int port = 6789;

    public static void main(String[] args) {
        try {
            Server server = new Server();
            server.parseArgs(args);
            server.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void execute() throws IOException {
        ServerSocket server = new ServerSocket(port);
        System.out.println("STARTED server on port " + port);

        while (true) {
            Socket connection = server.accept();
            System.out.println("CONNECTED to " + connection.getInetAddress().getHostAddress());

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            DataOutputStream writer = new DataOutputStream(connection.getOutputStream());

            String request = reader.readLine();

            writer.writeBytes(request);
            writer.flush();
            connection.close();
        }
    }

    private void parseArgs(String[] args) {
        int i = 0;
        try {
            for (i = 0; i < args.length; i++) {
                if ("-p".equals(args[i])) {
                    port = Integer.parseInt(args[++i]);
                } else {
                    System.err.println("Unexpected argument: " + args[i]);
                    printHelp(1);
                }
            }
        } catch (NumberFormatException nfe) {
            System.err.println("Argument " + args[i] + " should be an integer.");
            printHelp(1);
        } catch (IndexOutOfBoundsException iobe) {
            System.err.println("Argument " + args[--i] + " should be followed by a value.");
            printHelp(1);
        }
    }

    private void printHelp(int status) {
        System.out.println("-p      port  (default: 6789");
        System.exit(status);
    }
}
