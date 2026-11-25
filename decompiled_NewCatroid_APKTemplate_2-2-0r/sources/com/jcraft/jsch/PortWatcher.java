package com.jcraft.jsch;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;
import org.jetbrains.kotlin.daemon.common.LoopbackNetworkInterface;

/* JADX INFO: loaded from: classes10.dex */
class PortWatcher implements Runnable {
    private static InetAddress anyLocalAddress;
    private static Vector pool = new Vector();
    InetAddress boundaddress;
    int connectTimeout = 0;
    String host;
    int lport;
    int rport;
    Session session;
    ServerSocket ss;
    Runnable thread;

    static {
        anyLocalAddress = null;
        try {
            anyLocalAddress = InetAddress.getByName("0.0.0.0");
        } catch (UnknownHostException unused) {
        }
    }

    static String[] getPortForwarding(Session session) {
        int i;
        Vector vector = new Vector();
        synchronized (pool) {
            for (int i2 = 0; i2 < pool.size(); i2++) {
                PortWatcher portWatcher = (PortWatcher) pool.elementAt(i2);
                if (portWatcher.session == session) {
                    vector.addElement(portWatcher.lport + ":" + portWatcher.host + ":" + portWatcher.rport);
                }
            }
        }
        String[] strArr = new String[vector.size()];
        for (i = 0; i < vector.size(); i++) {
            strArr[i] = (String) vector.elementAt(i);
        }
        return strArr;
    }

    static PortWatcher getPort(Session session, String str, int i) throws JSchException {
        InetAddress inetAddress;
        try {
            InetAddress byName = InetAddress.getByName(str);
            synchronized (pool) {
                for (int i2 = 0; i2 < pool.size(); i2++) {
                    PortWatcher portWatcher = (PortWatcher) pool.elementAt(i2);
                    if (portWatcher.session == session && portWatcher.lport == i && (((inetAddress = anyLocalAddress) != null && portWatcher.boundaddress.equals(inetAddress)) || portWatcher.boundaddress.equals(byName))) {
                        return portWatcher;
                    }
                }
                return null;
            }
        } catch (UnknownHostException e) {
            throw new JSchException("PortForwardingL: invalid address " + str + " specified.", e);
        }
    }

    private static String normalize(String str) {
        if (str == null) {
            return str;
        }
        if (str.length() == 0 || str.equals("*")) {
            return "0.0.0.0";
        }
        return str.equals("localhost") ? LoopbackNetworkInterface.IPV4_LOOPBACK_INET_ADDRESS : str;
    }

    static PortWatcher addPort(Session session, String str, int i, String str2, int i2, ServerSocketFactory serverSocketFactory) throws JSchException {
        String strNormalize = normalize(str);
        if (getPort(session, strNormalize, i) != null) {
            throw new JSchException("PortForwardingL: local port " + strNormalize + ":" + i + " is already registered.");
        }
        PortWatcher portWatcher = new PortWatcher(session, strNormalize, i, str2, i2, serverSocketFactory);
        pool.addElement(portWatcher);
        return portWatcher;
    }

    static void delPort(Session session, String str, int i) throws JSchException {
        String strNormalize = normalize(str);
        PortWatcher port = getPort(session, strNormalize, i);
        if (port == null) {
            throw new JSchException("PortForwardingL: local port " + strNormalize + ":" + i + " is not registered.");
        }
        port.delete();
        pool.removeElement(port);
    }

    static void delPort(Session session) {
        synchronized (pool) {
            PortWatcher[] portWatcherArr = new PortWatcher[pool.size()];
            int i = 0;
            for (int i2 = 0; i2 < pool.size(); i2++) {
                PortWatcher portWatcher = (PortWatcher) pool.elementAt(i2);
                if (portWatcher.session == session) {
                    portWatcher.delete();
                    portWatcherArr[i] = portWatcher;
                    i++;
                }
            }
            for (int i3 = 0; i3 < i; i3++) {
                pool.removeElement(portWatcherArr[i3]);
            }
        }
    }

    PortWatcher(Session session, String str, int i, String str2, int i2, ServerSocketFactory serverSocketFactory) throws JSchException {
        int localPort;
        this.session = session;
        this.lport = i;
        this.host = str2;
        this.rport = i2;
        try {
            InetAddress byName = InetAddress.getByName(str);
            this.boundaddress = byName;
            ServerSocket serverSocket = serverSocketFactory == null ? new ServerSocket(i, 0, this.boundaddress) : serverSocketFactory.createServerSocket(i, 0, byName);
            this.ss = serverSocket;
            if (i != 0 || (localPort = serverSocket.getLocalPort()) == -1) {
                return;
            }
            this.lport = localPort;
        } catch (Exception e) {
            String str3 = "PortForwardingL: local port " + str + ":" + i + " cannot be bound.";
            if (e instanceof Throwable) {
                throw new JSchException(str3, e);
            }
            throw new JSchException(str3);
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        this.thread = this;
        while (this.thread != null) {
            try {
                Socket socketAccept = this.ss.accept();
                socketAccept.setTcpNoDelay(true);
                InputStream inputStream = socketAccept.getInputStream();
                OutputStream outputStream = socketAccept.getOutputStream();
                ChannelDirectTCPIP channelDirectTCPIP = new ChannelDirectTCPIP();
                channelDirectTCPIP.init();
                channelDirectTCPIP.setInputStream(inputStream);
                channelDirectTCPIP.setOutputStream(outputStream);
                this.session.addChannel(channelDirectTCPIP);
                channelDirectTCPIP.setHost(this.host);
                channelDirectTCPIP.setPort(this.rport);
                channelDirectTCPIP.setOrgIPAddress(socketAccept.getInetAddress().getHostAddress());
                channelDirectTCPIP.setOrgPort(socketAccept.getPort());
                channelDirectTCPIP.connect(this.connectTimeout);
                int i = channelDirectTCPIP.exitstatus;
            } catch (Exception unused) {
            }
        }
        delete();
    }

    void delete() {
        this.thread = null;
        try {
            ServerSocket serverSocket = this.ss;
            if (serverSocket != null) {
                serverSocket.close();
            }
            this.ss = null;
        } catch (Exception unused) {
        }
    }

    void setConnectTimeout(int i) {
        this.connectTimeout = i;
    }
}
