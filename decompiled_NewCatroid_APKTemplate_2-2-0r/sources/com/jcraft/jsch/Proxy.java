package com.jcraft.jsch;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/* JADX INFO: loaded from: classes10.dex */
public interface Proxy {
    void close();

    void connect(SocketFactory socketFactory, String str, int i, int i2) throws Exception;

    InputStream getInputStream();

    OutputStream getOutputStream();

    Socket getSocket();
}
