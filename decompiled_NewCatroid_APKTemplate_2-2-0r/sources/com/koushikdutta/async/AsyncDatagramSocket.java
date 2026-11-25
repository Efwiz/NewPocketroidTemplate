package com.koushikdutta.async;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes10.dex */
public class AsyncDatagramSocket extends AsyncNetworkSocket {
    public void disconnect() throws IOException {
        this.socketAddress = null;
        ((DatagramChannelWrapper) getChannel()).disconnect();
    }

    @Override // com.koushikdutta.async.AsyncNetworkSocket
    public InetSocketAddress getRemoteAddress() {
        if (isOpen()) {
            return super.getRemoteAddress();
        }
        return ((DatagramChannelWrapper) getChannel()).getRemoteAddress();
    }

    public void connect(InetSocketAddress inetSocketAddress) throws IOException {
        this.socketAddress = inetSocketAddress;
        ((DatagramChannelWrapper) getChannel()).mChannel.connect(inetSocketAddress);
    }

    public void send(final String str, final int i, final ByteBuffer byteBuffer) {
        if (getServer().getAffinity() != Thread.currentThread()) {
            getServer().run(new Runnable() { // from class: com.koushikdutta.async.AsyncDatagramSocket.1
                @Override // java.lang.Runnable
                public void run() {
                    AsyncDatagramSocket.this.send(str, i, byteBuffer);
                }
            });
        } else {
            try {
                ((DatagramChannelWrapper) getChannel()).mChannel.send(byteBuffer, new InetSocketAddress(str, i));
            } catch (IOException unused) {
            }
        }
    }

    public void send(final InetSocketAddress inetSocketAddress, final ByteBuffer byteBuffer) {
        if (getServer().getAffinity() != Thread.currentThread()) {
            getServer().run(new Runnable() { // from class: com.koushikdutta.async.AsyncDatagramSocket.2
                @Override // java.lang.Runnable
                public void run() {
                    AsyncDatagramSocket.this.send(inetSocketAddress, byteBuffer);
                }
            });
        } else {
            try {
                ((DatagramChannelWrapper) getChannel()).mChannel.send(byteBuffer, new InetSocketAddress(inetSocketAddress.getHostName(), inetSocketAddress.getPort()));
            } catch (IOException unused) {
            }
        }
    }
}
