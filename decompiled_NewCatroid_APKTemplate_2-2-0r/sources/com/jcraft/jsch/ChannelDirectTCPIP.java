package com.jcraft.jsch;

import java.io.InputStream;
import java.io.OutputStream;
import org.jetbrains.kotlin.daemon.common.LoopbackNetworkInterface;

/* JADX INFO: loaded from: classes10.dex */
public class ChannelDirectTCPIP extends Channel {
    private static final int LOCAL_MAXIMUM_PACKET_SIZE = 16384;
    private static final int LOCAL_WINDOW_SIZE_MAX = 131072;
    private static final byte[] _type = Util.str2byte("direct-tcpip");
    String host;
    String originator_IP_address = LoopbackNetworkInterface.IPV4_LOOPBACK_INET_ADDRESS;
    int originator_port = 0;
    int port;

    ChannelDirectTCPIP() {
        this.type = _type;
        setLocalWindowSizeMax(131072);
        setLocalWindowSize(131072);
        setLocalPacketSize(16384);
    }

    @Override // com.jcraft.jsch.Channel
    void init() {
        this.f201io = new IO();
    }

    @Override // com.jcraft.jsch.Channel
    public void connect(int i) throws JSchException {
        this.connectTimeout = i;
        try {
            Session session = getSession();
            if (!session.isConnected()) {
                throw new JSchException("session is down");
            }
            if (this.f201io.in != null) {
                this.thread = new Thread(this);
                this.thread.setName("DirectTCPIP thread " + session.getHost());
                if (session.daemon_thread) {
                    this.thread.setDaemon(session.daemon_thread);
                }
                this.thread.start();
                return;
            }
            sendChannelOpen();
        } catch (Exception e) {
            this.f201io.close();
            this.f201io = null;
            Channel.del(this);
            if (e instanceof JSchException) {
                throw ((JSchException) e);
            }
        }
    }

    @Override // com.jcraft.jsch.Channel, java.lang.Runnable
    public void run() {
        try {
            sendChannelOpen();
            Buffer buffer = new Buffer(this.rmpsize);
            Packet packet = new Packet(buffer);
            Session session = getSession();
            while (isConnected() && this.thread != null && this.f201io != null && this.f201io.in != null) {
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
            eof();
            disconnect();
        } catch (Exception unused) {
            if (!this.connected) {
                this.connected = true;
            }
            disconnect();
        }
    }

    @Override // com.jcraft.jsch.Channel
    public void setInputStream(InputStream inputStream) {
        this.f201io.setInputStream(inputStream);
    }

    @Override // com.jcraft.jsch.Channel
    public void setOutputStream(OutputStream outputStream) {
        this.f201io.setOutputStream(outputStream);
    }

    public void setHost(String str) {
        this.host = str;
    }

    public void setPort(int i) {
        this.port = i;
    }

    public void setOrgIPAddress(String str) {
        this.originator_IP_address = str;
    }

    public void setOrgPort(int i) {
        this.originator_port = i;
    }

    @Override // com.jcraft.jsch.Channel
    protected Packet genChannelOpenPacket() {
        Buffer buffer = new Buffer(this.host.length() + 50 + this.originator_IP_address.length() + 128);
        Packet packet = new Packet(buffer);
        packet.reset();
        buffer.putByte((byte) 90);
        buffer.putString(this.type);
        buffer.putInt(this.f200id);
        buffer.putInt(this.lwsize);
        buffer.putInt(this.lmpsize);
        buffer.putString(Util.str2byte(this.host));
        buffer.putInt(this.port);
        buffer.putString(Util.str2byte(this.originator_IP_address));
        buffer.putInt(this.originator_port);
        return packet;
    }
}
