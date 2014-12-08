package de.tuberlin.cit.tcpdaemon;

import org.apache.log4j.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    private Logger logger = Logger.getLogger(this.getClass());

    private int port = 6789;
    private boolean running = false;

    public void run() {
        try {
            execute();
        } catch (Exception e){
            logger.error("Failed running TCP Server.", e);
        }
    }

    private void execute() throws IOException {
        running = true;
        ServerSocket server = new ServerSocket(port);
        try {
            logger.info("STARTED server on port " + port);

            while (running) {
                Socket connection = server.accept();
                try {
                    logger.info("CONNECTED to " + connection.getInetAddress().getHostAddress());

                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String request = reader.readLine();
                    if (request != null) {
                        PrintWriter writer = new PrintWriter(connection.getOutputStream(), true);
                        writer.println(request);
                    }
                } finally {
                    connection.close();
                }
            }
        } finally {
            running = false;
            server.close();
        }
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isRunning() {
        return running;
    }

    public void stop() {
        this.running = false;
    }
}
