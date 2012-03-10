package Main;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayList;

public class User implements Serializable {

    private String Name;
    private String UserName;
    private String cookie;
    private InetAddress privateIP;
    private InetAddress publicIP;
    private int privatePort;
    private int publicPort;
    private Settings settings;
    private boolean online;
    private ArrayList<Friend> friends;

    public User(String username, String name) {
        this.UserName = username;
        this.Name = name;
        settings = new Settings();

    }

    public String getName() {
        return Name;
    }

    public String getUserName() {
        return UserName;
    }

    public InetAddress getprivateIP() {
        return privateIP;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public InetAddress getpublicIP() {
        return publicIP;
    }

    public void setpublicIP(InetAddress publicIP) {
        this.publicIP = publicIP;
    }

    public boolean getOnline() {
        return online;
    }

    public void setOnline(boolean value) {
        online = value;
    }

    public void setFriends(ArrayList<Friend> friends) {
        this.friends = friends;
    }

    public Settings getSettings() {
        return settings;

    }

    public void setPrivateIP(InetAddress privateIP) {
        this.privateIP = privateIP;
    }

    public void setPublicPort(int publicPort) {
        this.publicPort = publicPort;
    }

    public ArrayList<Friend> getFriends() {
        return friends;
    }
    
   
    
}
