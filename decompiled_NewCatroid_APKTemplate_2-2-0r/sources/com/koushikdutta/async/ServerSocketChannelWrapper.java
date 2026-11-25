package com.koushikdutta.async;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/* JADX INFO: loaded from: classes10.dex */
class ServerSocketChannelWrapper extends ChannelWrapper {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    ServerSocketChannel mChannel;

    @Override // com.koushikdutta.async.ChannelWrapper
    public boolean isConnected() {
        return false;
    }

    @Override // com.koushikdutta.async.ChannelWrapper
    public void shutdownInput() {
    }

    @Override // com.koushikdutta.async.ChannelWrapper
    public void shutdownOutput() {
    }

    @Override // com.koushikdutta.async.ChannelWrapper
    public int getLocalPort() {
        return this.mChannel.socket().getLocalPort();
    }

    ServerSocketChannelWrapper(ServerSocketChannel serverSocketChannel) throws IOException {
        super(serverSocketChannel);
        this.mChannel = serverSocketChannel;
    }

    @Override // java.nio.channels.ReadableByteChannel
    public int read(ByteBuffer byteBuffer) throws IOException {
        throw new IOException("Can't read ServerSocketChannel");
    }

    @Override // com.koushikdutta.async.ChannelWrapper
    public int write(ByteBuffer byteBuffer) throws IOException {
        throw new IOException("Can't write ServerSocketChannel");
    }

    @Override // com.koushikdutta.async.ChannelWrapper
    public SelectionKey register(Selector selector) throws ClosedChannelException {
        return this.mChannel.register(selector, 16);
    }

    @Override // com.koushikdutta.async.ChannelWrapper
    public int write(ByteBuffer[] byteBufferArr) throws IOException {
        throw new IOException("Can't write ServerSocketChannel");
    }

    @Override // java.nio.channels.ScatteringByteChannel
    public long read(ByteBuffer[] byteBufferArr) throws IOException {
        throw new IOException("Can't read ServerSocketChannel");
    }

    @Override // java.nio.channels.ScatteringByteChannel
    public long read(ByteBuffer[] byteBufferArr, int i, int i2) throws IOException {
        throw new IOException("Can't read ServerSocketChannel");
    }

    @Override // com.koushikdutta.async.ChannelWrapper
    public Object getSocket() {
        return this.mChannel.socket();
    }
}
