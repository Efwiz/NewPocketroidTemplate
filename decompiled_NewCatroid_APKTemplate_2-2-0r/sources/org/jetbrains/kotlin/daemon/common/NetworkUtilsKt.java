package org.jetbrains.kotlin.daemon.common;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;
import kotlin.Metadata;
import kotlin.Pair;
import org.jetbrains.kotlin.cli.common.CompilerSystemProperties;

/* JADX INFO: compiled from: NetworkUtils.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000*\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\u0006\u0010\b\u001a\u00020\t\u001a*\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00010\u000b2\u0006\u0010\r\u001a\u00020\u00012\u0006\u0010\u000e\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u0001\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0005\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"DEFAULT_SERVER_SOCKET_BACKLOG_SIZE", "", "DEFAULT_SOCKET_CONNECT_ATTEMPTS", "DEFAULT_SOCKET_CONNECT_INTERVAL_MS", "", "SOCKET_ANY_FREE_PORT", "portSelectionRng", "Ljava/util/Random;", "ensureServerHostnameIsSetUp", "", "findPortAndCreateRegistry", "Lkotlin/Pair;", "Ljava/rmi/registry/Registry;", "attempts", "portRangeStart", "portRangeEnd", "daemon-common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class NetworkUtilsKt {
    public static final int DEFAULT_SERVER_SOCKET_BACKLOG_SIZE = 50;
    public static final int DEFAULT_SOCKET_CONNECT_ATTEMPTS = 3;
    public static final long DEFAULT_SOCKET_CONNECT_INTERVAL_MS = 10;
    public static final int SOCKET_ANY_FREE_PORT = 0;
    private static final Random portSelectionRng = new Random();

    public static final Pair<Registry, Integer> findPortAndCreateRegistry(int i, int i2, int i3) {
        int i4 = 0;
        RemoteException remoteException = null;
        while (true) {
            int i5 = i4 + 1;
            if (i4 < i) {
                int iNextInt = portSelectionRng.nextInt(i3 - i2) + i2;
                try {
                    return new Pair<>(LocateRegistry.createRegistry(iNextInt, LoopbackNetworkInterface.INSTANCE.getClientLoopbackSocketFactory(), LoopbackNetworkInterface.INSTANCE.getServerLoopbackSocketFactory()), Integer.valueOf(iNextInt));
                } catch (RemoteException e) {
                    remoteException = e;
                    i4 = i5;
                }
            } else {
                throw new IllegalStateException("Cannot find free port in " + i + " attempts", (Throwable) remoteException);
            }
        }
    }

    public static final void ensureServerHostnameIsSetUp() {
        if (CompilerSystemProperties.JAVA_RMI_SERVER_HOSTNAME.getValue() == null) {
            CompilerSystemProperties.JAVA_RMI_SERVER_HOSTNAME.setValue(LoopbackNetworkInterface.INSTANCE.getLoopbackInetAddressName());
        }
    }
}
