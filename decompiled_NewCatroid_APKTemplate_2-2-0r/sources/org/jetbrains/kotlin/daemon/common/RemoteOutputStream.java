package org.jetbrains.kotlin.daemon.common;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.rmi.Remote;
import java.rmi.RemoteException;
import kotlin.Metadata;

/* JADX INFO: compiled from: RemoteOutputStream.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J \u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH&J\u0010\u0010\u0004\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\bH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/daemon/common/RemoteOutputStream;", "Ljava/rmi/Remote;", "close", "", "write", "data", "", TypedValues.CycleType.S_WAVE_OFFSET, "", "length", "dataByte", "daemon-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface RemoteOutputStream extends Remote {
    void close() throws RemoteException;

    void write(int dataByte) throws RemoteException;

    void write(byte[] data, int offset, int length) throws RemoteException;
}
