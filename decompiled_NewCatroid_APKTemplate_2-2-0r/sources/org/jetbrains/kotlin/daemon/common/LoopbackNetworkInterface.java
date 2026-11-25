package org.jetbrains.kotlin.daemon.common;

import java.io.IOException;
import java.io.Serializable;
import java.net.ConnectException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.cli.common.CompilerSystemProperties;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: NetworkUtils.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0007\bÆ\u0002\u0018\u00002\u00020\u0001:\u0003\"#$B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u001b\u0010\u0006\u001a\u00020\u00078FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\tR\u001b\u0010\f\u001a\u00020\u00078FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u000e\u0010\u000b\u001a\u0004\b\r\u0010\tR\u001b\u0010\u000f\u001a\u00020\u00108FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\u000b\u001a\u0004\b\u0011\u0010\u0012R\u001b\u0010\u0014\u001a\u00020\u00158FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0018\u0010\u000b\u001a\u0004\b\u0016\u0010\u0017R\u001b\u0010\u0019\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u001c\u0010\u000b\u001a\u0004\b\u001a\u0010\u001bR\u001b\u0010\u001d\u001a\u00020\u001e8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b!\u0010\u000b\u001a\u0004\b\u001f\u0010 ¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/daemon/common/LoopbackNetworkInterface;", "", "()V", "IPV4_LOOPBACK_INET_ADDRESS", "", "IPV6_LOOPBACK_INET_ADDRESS", "SERVER_SOCKET_BACKLOG_SIZE", "", "getSERVER_SOCKET_BACKLOG_SIZE", "()I", "SERVER_SOCKET_BACKLOG_SIZE$delegate", "Lkotlin/Lazy;", "SOCKET_CONNECT_ATTEMPTS", "getSOCKET_CONNECT_ATTEMPTS", "SOCKET_CONNECT_ATTEMPTS$delegate", "SOCKET_CONNECT_INTERVAL_MS", "", "getSOCKET_CONNECT_INTERVAL_MS", "()J", "SOCKET_CONNECT_INTERVAL_MS$delegate", "clientLoopbackSocketFactory", "Lorg/jetbrains/kotlin/daemon/common/LoopbackNetworkInterface$ClientLoopbackSocketFactory;", "getClientLoopbackSocketFactory", "()Lorg/jetbrains/kotlin/daemon/common/LoopbackNetworkInterface$ClientLoopbackSocketFactory;", "clientLoopbackSocketFactory$delegate", "loopbackInetAddressName", "getLoopbackInetAddressName", "()Ljava/lang/String;", "loopbackInetAddressName$delegate", "serverLoopbackSocketFactory", "Lorg/jetbrains/kotlin/daemon/common/LoopbackNetworkInterface$ServerLoopbackSocketFactory;", "getServerLoopbackSocketFactory", "()Lorg/jetbrains/kotlin/daemon/common/LoopbackNetworkInterface$ServerLoopbackSocketFactory;", "serverLoopbackSocketFactory$delegate", "AbstractClientLoopbackSocketFactory", "ClientLoopbackSocketFactory", "ServerLoopbackSocketFactory", "daemon-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LoopbackNetworkInterface {
    public static final String IPV4_LOOPBACK_INET_ADDRESS = "127.0.0.1";
    public static final String IPV6_LOOPBACK_INET_ADDRESS = "::1";
    public static final LoopbackNetworkInterface INSTANCE = new LoopbackNetworkInterface();

    /* JADX INFO: renamed from: SERVER_SOCKET_BACKLOG_SIZE$delegate, reason: from kotlin metadata */
    private static final Lazy SERVER_SOCKET_BACKLOG_SIZE = LazyKt.lazy(new Function0<Integer>() { // from class: org.jetbrains.kotlin.daemon.common.LoopbackNetworkInterface$SERVER_SOCKET_BACKLOG_SIZE$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final Integer invoke() {
            Integer intOrNull;
            String value = CompilerSystemProperties.DAEMON_RMI_SOCKET_BACKLOG_SIZE_PROPERTY.getValue();
            return Integer.valueOf((value == null || (intOrNull = StringsKt.toIntOrNull(value)) == null) ? 50 : intOrNull.intValue());
        }
    });

    /* JADX INFO: renamed from: SOCKET_CONNECT_ATTEMPTS$delegate, reason: from kotlin metadata */
    private static final Lazy SOCKET_CONNECT_ATTEMPTS = LazyKt.lazy(new Function0<Integer>() { // from class: org.jetbrains.kotlin.daemon.common.LoopbackNetworkInterface$SOCKET_CONNECT_ATTEMPTS$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final Integer invoke() {
            Integer intOrNull;
            String value = CompilerSystemProperties.DAEMON_RMI_SOCKET_CONNECT_ATTEMPTS_PROPERTY.getValue();
            return Integer.valueOf((value == null || (intOrNull = StringsKt.toIntOrNull(value)) == null) ? 3 : intOrNull.intValue());
        }
    });

    /* JADX INFO: renamed from: SOCKET_CONNECT_INTERVAL_MS$delegate, reason: from kotlin metadata */
    private static final Lazy SOCKET_CONNECT_INTERVAL_MS = LazyKt.lazy(new Function0<Long>() { // from class: org.jetbrains.kotlin.daemon.common.LoopbackNetworkInterface$SOCKET_CONNECT_INTERVAL_MS$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final Long invoke() {
            Long longOrNull;
            String value = CompilerSystemProperties.DAEMON_RMI_SOCKET_CONNECT_INTERVAL_PROPERTY.getValue();
            return Long.valueOf((value == null || (longOrNull = StringsKt.toLongOrNull(value)) == null) ? 10L : longOrNull.longValue());
        }
    });

    /* JADX INFO: renamed from: serverLoopbackSocketFactory$delegate, reason: from kotlin metadata */
    private static final Lazy serverLoopbackSocketFactory = LazyKt.lazy(new Function0<ServerLoopbackSocketFactory>() { // from class: org.jetbrains.kotlin.daemon.common.LoopbackNetworkInterface$serverLoopbackSocketFactory$2
        @Override // kotlin.jvm.functions.Function0
        public final LoopbackNetworkInterface.ServerLoopbackSocketFactory invoke() {
            return new LoopbackNetworkInterface.ServerLoopbackSocketFactory();
        }
    });

    /* JADX INFO: renamed from: clientLoopbackSocketFactory$delegate, reason: from kotlin metadata */
    private static final Lazy clientLoopbackSocketFactory = LazyKt.lazy(new Function0<ClientLoopbackSocketFactory>() { // from class: org.jetbrains.kotlin.daemon.common.LoopbackNetworkInterface$clientLoopbackSocketFactory$2
        @Override // kotlin.jvm.functions.Function0
        public final LoopbackNetworkInterface.ClientLoopbackSocketFactory invoke() {
            return new LoopbackNetworkInterface.ClientLoopbackSocketFactory();
        }
    });

    /* JADX INFO: renamed from: loopbackInetAddressName$delegate, reason: from kotlin metadata */
    private static final Lazy loopbackInetAddressName = LazyKt.lazy(new Function0<String>() { // from class: org.jetbrains.kotlin.daemon.common.LoopbackNetworkInterface$loopbackInetAddressName$2
        @Override // kotlin.jvm.functions.Function0
        public final String invoke() {
            try {
                return InetAddress.getByName(null) instanceof Inet6Address ? LoopbackNetworkInterface.IPV6_LOOPBACK_INET_ADDRESS : LoopbackNetworkInterface.IPV4_LOOPBACK_INET_ADDRESS;
            } catch (IOException unused) {
                return LoopbackNetworkInterface.IPV4_LOOPBACK_INET_ADDRESS;
            }
        }
    });

    private LoopbackNetworkInterface() {
    }

    public final int getSERVER_SOCKET_BACKLOG_SIZE() {
        return ((Number) SERVER_SOCKET_BACKLOG_SIZE.getValue()).intValue();
    }

    public final int getSOCKET_CONNECT_ATTEMPTS() {
        return ((Number) SOCKET_CONNECT_ATTEMPTS.getValue()).intValue();
    }

    public final long getSOCKET_CONNECT_INTERVAL_MS() {
        return ((Number) SOCKET_CONNECT_INTERVAL_MS.getValue()).longValue();
    }

    public final ServerLoopbackSocketFactory getServerLoopbackSocketFactory() {
        return (ServerLoopbackSocketFactory) serverLoopbackSocketFactory.getValue();
    }

    public final ClientLoopbackSocketFactory getClientLoopbackSocketFactory() {
        return (ClientLoopbackSocketFactory) clientLoopbackSocketFactory.getValue();
    }

    public final String getLoopbackInetAddressName() {
        return (String) loopbackInetAddressName.getValue();
    }

    /* JADX INFO: compiled from: NetworkUtils.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0013\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0096\u0002J\b\u0010\f\u001a\u00020\u0007H\u0016¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/daemon/common/LoopbackNetworkInterface$ServerLoopbackSocketFactory;", "Ljava/rmi/server/RMIServerSocketFactory;", "Ljava/io/Serializable;", "()V", "createServerSocket", "Ljava/net/ServerSocket;", "port", "", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "daemon-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ServerLoopbackSocketFactory implements RMIServerSocketFactory, Serializable {
        public boolean equals(Object other) {
            return other == this || super.equals(other);
        }

        public int hashCode() {
            return super.hashCode();
        }

        public ServerSocket createServerSocket(int port) throws IOException {
            return new ServerSocket(port, LoopbackNetworkInterface.INSTANCE.getSERVER_SOCKET_BACKLOG_SIZE(), InetAddress.getByName(null));
        }
    }

    /* JADX INFO: compiled from: NetworkUtils.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\b&\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u001b\u0010\u0004\u001a\u00028\u00002\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0013\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0096\u0002J\b\u0010\u000e\u001a\u00020\bH\u0016J\u001d\u0010\u000f\u001a\u00028\u00002\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH$¢\u0006\u0002\u0010\t¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/daemon/common/LoopbackNetworkInterface$AbstractClientLoopbackSocketFactory;", "SocketType", "Ljava/io/Serializable;", "()V", "createSocket", "host", "", "port", "", "(Ljava/lang/String;I)Ljava/lang/Object;", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "socketCreate", "daemon-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class AbstractClientLoopbackSocketFactory<SocketType> implements Serializable {
        protected abstract SocketType socketCreate(String host, int port);

        public boolean equals(Object other) {
            return other == this || super.equals(other);
        }

        public int hashCode() {
            return super.hashCode();
        }

        public final SocketType createSocket(String host, int port) throws InterruptedException, IOException {
            Intrinsics.checkNotNullParameter(host, "host");
            int socket_connect_attempts = LoopbackNetworkInterface.INSTANCE.getSOCKET_CONNECT_ATTEMPTS();
            while (true) {
                try {
                    return socketCreate(host, port);
                } catch (ConnectException e) {
                    socket_connect_attempts--;
                    if (socket_connect_attempts <= 0) {
                        throw e;
                    }
                    Thread.sleep(LoopbackNetworkInterface.INSTANCE.getSOCKET_CONNECT_INTERVAL_MS());
                }
            }
        }
    }

    /* JADX INFO: compiled from: NetworkUtils.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\u0005¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0014¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/daemon/common/LoopbackNetworkInterface$ClientLoopbackSocketFactory;", "Lorg/jetbrains/kotlin/daemon/common/LoopbackNetworkInterface$AbstractClientLoopbackSocketFactory;", "Ljava/net/Socket;", "Ljava/rmi/server/RMIClientSocketFactory;", "()V", "socketCreate", "host", "", "port", "", "daemon-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ClientLoopbackSocketFactory extends AbstractClientLoopbackSocketFactory<Socket> implements RMIClientSocketFactory {
        @Override // org.jetbrains.kotlin.daemon.common.LoopbackNetworkInterface.AbstractClientLoopbackSocketFactory
        public /* bridge */ /* synthetic */ Socket createSocket(String str, int i) {
            return (Socket) createSocket(str, i);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jetbrains.kotlin.daemon.common.LoopbackNetworkInterface.AbstractClientLoopbackSocketFactory
        public Socket socketCreate(String host, int port) {
            Intrinsics.checkNotNullParameter(host, "host");
            return new Socket(InetAddress.getByName(null), port);
        }
    }
}
