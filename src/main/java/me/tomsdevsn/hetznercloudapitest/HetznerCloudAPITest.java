package me.tomsdevsn.hetznercloudapitest;

import me.tomsdevsn.hetznercloud.HetznerCloudAPI;

public class HetznerCloudAPITest {

    private static HetznerCloudAPI cloudAPI;

    public static void main(String[] args) {
        cloudAPI = new HetznerCloudAPI("API_TOKEN");
        System.out.println(cloudAPI.getServers().getServers());
    }
}