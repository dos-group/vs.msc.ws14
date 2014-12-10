package de.tuberlin.cit.tcpdaemon;

import org.apache.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.util.Random;

public class Client implements Runnable {
    private Logger logger = Logger.getLogger(this.getClass());

    private String host = "localhost";
    private int port = 6789;

    public void run() {
        try {
            execute();
        } catch (Exception e) {
            logger.error("Cannot connect to " + host, e);
        }
    }

    private void execute() throws IOException, InterruptedException {
        Socket socket = new Socket(host, port);
        try {
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String request = Long.toString(new Random().nextLong());
            logger.info("TO SERVER: " + request);
            writer.println(request);

            String response = "";
            String in;
            while ((in = reader.readLine()) != null) {
                response += in;
            }
            logger.info("FROM SERVER: " + response + " (" + request.equals(response) + ")");
        } finally {
            socket.close();
        }
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
