package com.jcraft.jsch;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

/* JADX INFO: loaded from: classes10.dex */
public interface ServerSocketFactory {
    ServerSocket createServerSocket(int i, int i2, InetAddress inetAddress) throws IOException;
}
