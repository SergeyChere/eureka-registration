package config;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Date;

public class ExampleEurekaClient {

    private static InstanceInfo getInstanceInfo(EurekaClient eurekaClient, String vipAddress, boolean secure) {
        InstanceInfo nextServerInfo = null;
        try {
            nextServerInfo = eurekaClient.getNextServerFromEureka(vipAddress, secure);
        } catch (Exception e) {
            System.err.println("Cannot get an instance of example service to talk to from eureka");
        }
        return nextServerInfo;
    }

    public static void sendRequestToServiceUsingEureka(EurekaClient eurekaClient, String vipAddress) {
        InstanceInfo nextServerInfo = getInstanceInfo(eurekaClient, vipAddress, false);

        System.out.println("Found an instance of example service to talk to from eureka: "
                + nextServerInfo.getVIPAddress() + ":" + nextServerInfo.getPort());

        System.out.println("healthCheckUrl: " + nextServerInfo.getHealthCheckUrl());
        System.out.println("override: " + nextServerInfo.getOverriddenStatus());

        Socket s = new Socket();
        int serverPort = nextServerInfo.getPort();
        try {
            s.connect(new InetSocketAddress(nextServerInfo.getHostName(), serverPort));
        } catch (IOException e) {
            System.err.println("Could not connect to the server :"
                    + nextServerInfo.getHostName() + " at port " + serverPort);
        } catch (Exception e) {
            System.err.println("Could not connect to the server :"
                    + nextServerInfo.getHostName() + " at port " + serverPort + "due to Exception " + e);
        }
        try {
            String request = "FOO " + new Date();
            System.out.println("Connected to server. Sending a sample request: " + request);

            PrintStream out = new PrintStream(s.getOutputStream());
            out.println(request);

            System.out.println("Waiting for server response..");
            BufferedReader rd = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String str = rd.readLine();
            if (str != null) {
                System.out.println("Received response from server: " + str);
                System.out.println("Exiting the client. Demo over..");
            }
            rd.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
