package de.tuberlin.cit.tcpdaemon;

import org.apache.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SocketScanner {
    Logger logger = Logger.getLogger(this.getClass());

    public List<String> scan(String subnet, int port) {
        return scan(subnet, port, 1, 254);
    }

    public List<String> scan(String subnet, int port, int lowerBound, int upperBound) {
        List<String> list = new ArrayList<String>();
        for (int i = lowerBound; i <= upperBound; i++) {
            String host = subnet + "." + i;
            if (isPresent(host, port)) {
                list.add(host);
            }
        }
        return list;
    }

    public boolean isPresent(String host, int port) {
        boolean ret;
        try {
            Socket socket = new Socket(host, port);
            ret = true;
            socket.close();
        } catch (Exception e) {
            ret = false;
        }
        logger.debug(host + ":" + port + " isPresent=" + ret);
        return ret;
    }

    public List<String> getHostsFromFile(String file) {
        List<String> hosts = new ArrayList<String>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (!line.isEmpty()) {
                        hosts.add(line);
                    }
                }
            } finally {
                reader.close();
            }
        } catch (Exception e) {
            logger.error("Cannot read hosts from file " + file + ".", e);
        }
        return hosts;
    }
}
