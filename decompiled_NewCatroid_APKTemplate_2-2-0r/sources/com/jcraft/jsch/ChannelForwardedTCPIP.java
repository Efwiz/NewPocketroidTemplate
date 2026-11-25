package com.jcraft.jsch;

import java.io.PipedOutputStream;
import java.net.Socket;
import java.util.Vector;

/* JADX INFO: loaded from: classes10.dex */
public class ChannelForwardedTCPIP extends Channel {
    private static final int LOCAL_MAXIMUM_PACKET_SIZE = 16384;
    private static final int LOCAL_WINDOW_SIZE_MAX = 131072;
    private static final int TIMEOUT = 10000;
    private static Vector pool = new Vector();
    private Socket socket = null;
    private ForwardedTCPIPDaemon daemon = null;
    private Config config = null;

    ChannelForwardedTCPIP() {
        setLocalWindowSizeMax(131072);
        setLocalWindowSize(131072);
        setLocalPacketSize(16384);
        this.f201io = new IO();
        this.connected = true;
    }

    @Override // com.jcraft.jsch.Channel, java.lang.Runnable
    public void run() {
        try {
            Config config = this.config;
            if (config instanceof ConfigDaemon) {
                ConfigDaemon configDaemon = (ConfigDaemon) config;
                this.daemon = (ForwardedTCPIPDaemon) Class.forName(configDaemon.target).newInstance();
                PipedOutputStream pipedOutputStream = new PipedOutputStream();
                this.f201io.setInputStream(new Channel.PassiveInputStream(pipedOutputStream, 32768), false);
                this.daemon.setChannel(this, getInputStream(), pipedOutputStream);
                this.daemon.setArg(configDaemon.arg);
                new Thread(this.daemon).start();
            } else {
                ConfigLHost configLHost = (ConfigLHost) config;
                Socket socketCreateSocket = configLHost.factory == null ? Util.createSocket(configLHost.target, configLHost.lport, 10000) : configLHost.factory.createSocket(configLHost.target, configLHost.lport);
                this.socket = socketCreateSocket;
                socketCreateSocket.setTcpNoDelay(true);
                this.f201io.setInputStream(this.socket.getInputStream());
                this.f201io.setOutputStream(this.socket.getOutputStream());
            }
            sendOpenConfirmation();
            this.thread = Thread.currentThread();
            Buffer buffer = new Buffer(this.rmpsize);
            Packet packet = new Packet(buffer);
            try {
                Session session = getSession();
                while (this.thread != null && this.f201io != null && this.f201io.in != null) {
                    int i = this.f201io.in.read(buffer.buffer, 14, buffer.buffer.length - 142);
                    if (i <= 0) {
                        eof();
                        break;
                    }
                    packet.reset();
                    buffer.putByte((byte) 94);
                    buffer.putInt(this.recipient);
                    buffer.putInt(i);
                    buffer.skip(i);
                    synchronized (this) {
                        if (!this.close) {
                            session.write(packet, this, i);
                        }
                    }
                    break;
                }
            } catch (Exception unused) {
            }
            disconnect();
        } catch (Exception unused2) {
            sendOpenFailure(1);
            this.close = true;
            disconnect();
        }
    }

    @Override // com.jcraft.jsch.Channel
    void getData(Buffer buffer) {
        Session session;
        setRecipient(buffer.getInt());
        setRemoteWindowSize(buffer.getUInt());
        setRemotePacketSize(buffer.getInt());
        byte[] string = buffer.getString();
        int i = buffer.getInt();
        buffer.getString();
        buffer.getInt();
        try {
            session = getSession();
        } catch (JSchException unused) {
            session = null;
        }
        Config port = getPort(session, Util.byte2str(string), i);
        this.config = port;
        if (port == null) {
            this.config = getPort(session, null, i);
        }
        if (this.config == null && JSch.getLogger().isEnabled(3)) {
            JSch.getLogger().log(3, "ChannelForwardedTCPIP: " + Util.byte2str(string) + ":" + i + " is not registered.");
        }
    }

    private static Config getPort(Session session, String str, int i) {
        synchronized (pool) {
            for (int i2 = 0; i2 < pool.size(); i2++) {
                Config config = (Config) pool.elementAt(i2);
                if (config.session == session && ((config.rport == i || (config.rport == 0 && config.allocated_rport == i)) && (str == null || config.address_to_bind.equals(str)))) {
                    return config;
                }
            }
            return null;
        }
    }

    static String[] getPortForwarding(Session session) {
        int i;
        Vector vector = new Vector();
        synchronized (pool) {
            for (int i2 = 0; i2 < pool.size(); i2++) {
                Config config = (Config) pool.elementAt(i2);
                if (config instanceof ConfigDaemon) {
                    vector.addElement(config.allocated_rport + ":" + config.target + ":");
                } else {
                    vector.addElement(config.allocated_rport + ":" + config.target + ":" + ((ConfigLHost) config).lport);
                }
            }
        }
        String[] strArr = new String[vector.size()];
        for (i = 0; i < vector.size(); i++) {
            strArr[i] = (String) vector.elementAt(i);
        }
        return strArr;
    }

    static String normalize(String str) {
        if (str == null) {
            return "localhost";
        }
        return (str.length() == 0 || str.equals("*")) ? "" : str;
    }

    static void addPort(Session session, String str, int i, int i2, String str2, int i3, SocketFactory socketFactory) throws JSchException {
        String strNormalize = normalize(str);
        synchronized (pool) {
            if (getPort(session, strNormalize, i) != null) {
                throw new JSchException("PortForwardingR: remote port " + i + " is already registered.");
            }
            ConfigLHost configLHost = new ConfigLHost();
            configLHost.session = session;
            configLHost.rport = i;
            configLHost.allocated_rport = i2;
            configLHost.target = str2;
            configLHost.lport = i3;
            configLHost.address_to_bind = strNormalize;
            configLHost.factory = socketFactory;
            pool.addElement(configLHost);
        }
    }

    static void addPort(Session session, String str, int i, int i2, String str2, Object[] objArr) throws JSchException {
        String strNormalize = normalize(str);
        synchronized (pool) {
            if (getPort(session, strNormalize, i) != null) {
                throw new JSchException("PortForwardingR: remote port " + i + " is already registered.");
            }
            ConfigDaemon configDaemon = new ConfigDaemon();
            configDaemon.session = session;
            configDaemon.rport = i;
            configDaemon.allocated_rport = i;
            configDaemon.target = str2;
            configDaemon.arg = objArr;
            configDaemon.address_to_bind = strNormalize;
            pool.addElement(configDaemon);
        }
    }

    static void delPort(ChannelForwardedTCPIP channelForwardedTCPIP) {
        Session session;
        Config config;
        try {
            session = channelForwardedTCPIP.getSession();
        } catch (JSchException unused) {
            session = null;
        }
        if (session == null || (config = channelForwardedTCPIP.config) == null) {
            return;
        }
        delPort(session, config.rport);
    }

    static void delPort(Session session, int i) {
        delPort(session, null, i);
    }

    static void delPort(Session session, String str, int i) {
        synchronized (pool) {
            Config port = getPort(session, normalize(str), i);
            if (port == null) {
                port = getPort(session, null, i);
            }
            if (port == null) {
                return;
            }
            pool.removeElement(port);
            if (str == null) {
                str = port.address_to_bind;
            }
            if (str == null) {
                str = "0.0.0.0";
            }
            Buffer buffer = new Buffer(100);
            Packet packet = new Packet(buffer);
            try {
                packet.reset();
                buffer.putByte((byte) 80);
                buffer.putString(Util.str2byte("cancel-tcpip-forward"));
                buffer.putByte((byte) 0);
                buffer.putString(Util.str2byte(str));
                buffer.putInt(i);
                session.write(packet);
            } catch (Exception unused) {
            }
        }
    }

    static void delPort(Session session) {
        int[] iArr;
        int i;
        int i2;
        synchronized (pool) {
            iArr = new int[pool.size()];
            i2 = 0;
            for (int i3 = 0; i3 < pool.size(); i3++) {
                Config config = (Config) pool.elementAt(i3);
                if (config.session == session) {
                    iArr[i2] = config.rport;
                    i2++;
                }
            }
        }
        for (i = 0; i < i2; i++) {
            delPort(session, iArr[i]);
        }
    }

    public int getRemotePort() {
        Config config = this.config;
        if (config != null) {
            return config.rport;
        }
        return 0;
    }

    private void setSocketFactory(SocketFactory socketFactory) {
        Config config = this.config;
        if (config == null || !(config instanceof ConfigLHost)) {
            return;
        }
        ((ConfigLHost) config).factory = socketFactory;
    }

    static abstract class Config {
        String address_to_bind;
        int allocated_rport;
        int rport;
        Session session;
        String target;

        Config() {
        }
    }

    static class ConfigDaemon extends Config {
        Object[] arg;

        ConfigDaemon() {
        }
    }

    static class ConfigLHost extends Config {
        SocketFactory factory;
        int lport;

        ConfigLHost() {
        }
    }
}
