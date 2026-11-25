package com.koushikdutta.async;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/* JADX INFO: loaded from: classes10.dex */
class SocketChannelWrapper extends ChannelWrapper {
    SocketChannel mChannel;

    @Override // com.koushikdutta.async.ChannelWrapper
    public int getLocalPort() {
        return this.mChannel.socket().getLocalPort();
    }

    SocketChannelWrapper(SocketChannel socketChannel) throws IOException {
        super(socketChannel);
        this.mChannel = socketChannel;
    }

    @Override // java.nio.channels.ReadableByteChannel
    public int read(ByteBuffer byteBuffer) throws IOException {
        return this.mChannel.read(byteBuffer);
    }

    @Override // com.koushikdutta.async.ChannelWrapper
    public boolean isConnected() {
        return this.mChannel.isConnected();
    }

    @Override // com.koushikdutta.async.ChannelWrapper
    public int write(ByteBuffer byteBuffer) throws IOException {
        return this.mChannel.write(byteBuffer);
    }

    @Override // com.koushikdutta.async.ChannelWrapper
    public int write(ByteBuffer[] byteBufferArr) throws IOException {
        return (int) this.mChannel.write(byteBufferArr);
    }

    @Override // com.koushikdutta.async.ChannelWrapper
    public SelectionKey register(Selector selector) throws ClosedChannelException {
        return register(selector, 8);
    }

    @Override // com.koushikdutta.async.ChannelWrapper
    public void shutdownOutput() {
        try {
            this.mChannel.socket().shutdownOutput();
        } catch (Exception unused) {
        }
    }

    @Override // com.koushikdutta.async.ChannelWrapper
    public void shutdownInput() {
        try {
            this.mChannel.socket().shutdownInput();
        } catch (Exception unused) {
        }
    }

    @Override // java.nio.channels.ScatteringByteChannel
    public long read(ByteBuffer[] byteBufferArr) throws IOException {
        return this.mChannel.read(byteBufferArr);
    }

    @Override // java.nio.channels.ScatteringByteChannel
    public long read(ByteBuffer[] byteBufferArr, int i, int i2) throws IOException {
        return this.mChannel.read(byteBufferArr, i, i2);
    }

    @Override // com.koushikdutta.async.ChannelWrapper
    public Object getSocket() {
        return this.mChannel.socket();
    }
}
