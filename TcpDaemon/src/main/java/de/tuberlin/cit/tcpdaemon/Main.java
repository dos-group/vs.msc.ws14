package de.tuberlin.cit.tcpdaemon;

import java.util.List;
import java.util.Random;

public class Main {
    private String file = "hosts";
    private int port = 6789;
    private int sleep = 2;

    public static void main(String[] args) throws InterruptedException {
        Main main = new Main();
        main.parseArgs(args);
        main.execute();
    }

    private void execute() throws InterruptedException {
        Server server = new Server();
        server.setPort(port);
        new Thread(server).start();

        Client client = new Client();
        client.setPort(port);

        SocketScanner scanner = new SocketScanner();
        List<String> hosts = scanner.getHostsFromFile(file);

        Random random = new Random();
        while (!hosts.isEmpty()) {
            Thread.sleep(sleep * 1000);
            client.setHost(hosts.get(random.nextInt(hosts.size())));
            client.run();
        }
    }

    private void parseArgs(String[] args) {
        int i = 0;
        try {
            for (i = 0; i < args.length; i++) {
                if ("-p".equals(args[i])) {
                    port = Integer.parseInt(args[++i]);
                } else if ("-s".equals(args[i])) {
                    sleep = Integer.parseInt(args[++i]);
                } else if ("-f".equals(args[i])) {
                    file = args[++i];
                } else {
                    System.err.println("Unexpected argument: " + args[i]);
                    printHelp(1);
                }
            }
        } catch (NumberFormatException nfe) {
            System.err.println("Argument " + args[i] + " should be a number.");
            printHelp(1);
        } catch (IndexOutOfBoundsException iobe) {
            System.err.println("Argument " + args[--i] + " should be followed by a value.");
            printHelp(1);
        }
    }

    private void printHelp(int status) {
        System.out.println("-p      port  (default: 6789)");
        System.out.println("-s      sleep (default: 2)");
        System.out.println("-f      file  (default: hosts)");
        System.exit(status);
    }
}
