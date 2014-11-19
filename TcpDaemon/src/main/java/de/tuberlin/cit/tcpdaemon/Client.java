package de.tuberlin.cit.tcpdaemon;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Random;

public class Client {
    private String host = "localhost";
    private int port = 6789;
    private int sleep = 2;

    public static void main(String[] args) {
        try {
            Client client = new Client();
            client.parseArgs(args);
            client.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void execute() throws IOException, InterruptedException {
        while (true) {
            Socket socket = new Socket(host, port);

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataOutputStream writer = new DataOutputStream(socket.getOutputStream());

            String request = Long.toString(new Random().nextLong());
            System.out.println("TO SERVER: " + request);
            writer.writeBytes(request + "\n");

            String response = reader.readLine();
            System.out.println("FROM SERVER: " + response + " (" + request.equals(response) + ")");
            socket.close();

            Thread.sleep(sleep * 1000);
        }
    }

    private void parseArgs(String[] args) {
        int i = 0;
        try {
            for (i = 0; i < args.length; i++) {
                if ("-h".equals(args[i])) {
                    host = args[++i];
                } else if ("-p".equals(args[i])) {
                    port = Integer.parseInt(args[++i]);
                } else if ("-s".equals(args[i])) {
                    sleep = Integer.parseInt(args[++i]);
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
        System.out.println("-h      host  (default: localhost");
        System.out.println("-p      port  (default: 6789");
        System.out.println("-s      sleep (default: 2");
        System.exit(status);
    }
}
