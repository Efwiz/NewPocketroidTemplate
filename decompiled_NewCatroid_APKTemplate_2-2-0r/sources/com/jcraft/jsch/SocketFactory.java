package com.jcraft.jsch;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/* JADX INFO: loaded from: classes10.dex */
public interface SocketFactory {
    Socket createSocket(String str, int i) throws IOException;

    InputStream getInputStream(Socket socket) throws IOException;

    OutputStream getOutputStream(Socket socket) throws IOException;
}
