package org.jetbrains.kotlin.daemon.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import kotlin.Metadata;

/* JADX INFO: compiled from: RemoteInputStream.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\u0010\u0012\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0004\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0005H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/daemon/common/RemoteInputStream;", "Ljava/rmi/Remote;", "close", "", "read", "", "", "length", "daemon-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface RemoteInputStream extends Remote {
    void close() throws RemoteException;

    int read() throws RemoteException;

    byte[] read(int length) throws RemoteException;
}
