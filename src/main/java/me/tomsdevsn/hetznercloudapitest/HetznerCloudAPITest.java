package me.tomsdevsn.hetznercloudapitest;

import me.tomsdevsn.hetznercloud.HetznerCloudAPI;
import me.tomsdevsn.hetznercloud.objects.general.Server;
import me.tomsdevsn.hetznercloud.objects.request.*;
import me.tomsdevsn.hetznercloud.objects.response.*;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class HetznerCloudAPITest {

    private static HetznerCloudAPI cloudAPI;

    private static final String TOKEN = System.getProperty("token");

    private static final String CLOUD_PREFIX = "hetznercloud-java-test";

    public static void main(String[] args) {

        cloudAPI = new HetznerCloudAPI(TOKEN);

        /* List all servers of the project */
        for (Server server : cloudAPI.getServers().getServers()) {
            System.out.println("Servername: " + server.getName() + " ID: " + server.getId());
        }

        /* Create server */
        RequestServer requestServer = RequestServer.builder()
                                      .name("cx11-" + CLOUD_PREFIX)
                                      .serverType("cx11")
                                      .location("fsn1")
                                      .image("ubuntu-16.04")
                                      .startAfterCreate(true)
                                      .sshKeys(Arrays.asList(2991L))
                                      .build();

        ResponseServer server = cloudAPI.createServer(requestServer);

        // Global Variables
        long serverID = server.getServer().getId();
        String ipv4 = server.getServer().getPublicNet().getIpv4().getIp();
        String ipv6 = server.getServer().getPublicNet().getIpv6().getIp();

        System.out.println("\n" +
                            "A new server has been created.\n" +
                "Name: " + server.getServer().getName() + "\n" +
                "ID: " + serverID + "\n" +
                "IPv4: " + ipv4 + "\n" +
                "IPv6: " + ipv6.substring(0, ipv6.length() - 3) + "1\n" +
                "Current status: " + server.getServer().getStatus() + "\n\n" +
                "Login data:\n" +
                "Username: root" +
                ((server.getRootPassword() == null) ? "" : "\nPassword (if not SSH-Key used): " + server.getRootPassword()));

        /* Actions for a Server */
        ResponseActionsServer allActions = cloudAPI.getAllActionsOfServer(serverID);

        System.out.println("\n" +
                            "All action for the Server");
        for (Action action : allActions.getActions()) {
            System.out.println("\n" +
                                "Action ID: " + action.getId() + "\n" +
                                "Command: " + action.getCommand() + "\n" +
                                "Status: " + action.getStatus() + "\n" +
                                "Progress: " + action.getProgress());
        }

        /* Reset Root Password (only works, when the QEMU-Agent is installed) */
        try {
            TimeUnit.SECONDS.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("\n" +
                            "New Root Password requested.\n" +
                            "Username: root\n" +
                            "Password: " + cloudAPI.resetRootPassword(serverID).getRootPassword());

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /* Change Server Type */
        RequestChangeType requestChangeType = RequestChangeType.builder()
                                             .serverType(RequestChangeType.ServerType.CX21.toString())
                                             .upgradeDisk(false)
                                             .build();

        ResponseChangeType responseChangeType = null;
        try {
            responseChangeType = cloudAPI.changeServerType(serverID, requestChangeType);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\n" +
                            "Change server Type requested.\n" +
                            "Progress: " + responseChangeType.getAction().getProgress());

        /* Change Server name */
        RequestServernameChange requestServernameChange = RequestServernameChange.builder()
                .name("cx21-01-" + CLOUD_PREFIX)
                .build();

        ResponseServernameChange responseServernameChange = cloudAPI.changeServerName(serverID, requestServernameChange);
        System.out.println("\n" +
                "Server name changed.\n" +
                "New name: " + responseServernameChange.getServer().getName());
    }
}