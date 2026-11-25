package org.catrobat.catroid.content;

import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.concurrent.CancellationException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.SupervisorKt;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/LocalServer.class
  classes14.dex
 */
/* JADX INFO: compiled from: LocalServer.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Lorg/catrobat/catroid/content/LocalServer;", "", "()V", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LocalServer {
    private static Socket clientSocket;
    private static volatile String connectedIP;
    private static volatile String connectedPort;
    private static volatile boolean isRunning;
    private static Job job;
    private static OutputStream outputStream;
    private static ServerSocket serverSocket;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static volatile String receivedValue = "";
    private static final CoroutineScope coroutineScope = CoroutineScopeKt.CoroutineScope(Dispatchers.getIO().plus(SupervisorKt.SupervisorJob$default((Job) null, 1, (Object) null)));

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/LocalServer$Companion.class
      classes14.dex
     */
    /* JADX INFO: compiled from: LocalServer.kt */
    @Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0011\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00062\u0006\u0010\u0016\u001a\u00020\u0006H\u0082@¢\u0006\u0002\u0010\u0017J\u0006\u0010\u0018\u001a\u00020\u0006J\n\u0010\u0019\u001a\u0004\u0018\u00010\u0006H\u0002J\u0006\u0010\u001a\u001a\u00020\u0006J\u0006\u0010\u001b\u001a\u00020\u0006J\u0010\u0010\u001c\u001a\u00020\u00142\u0006\u0010\u001d\u001a\u00020\u0004H\u0002J\u000e\u0010\u001e\u001a\u00020\u00142\u0006\u0010\u001f\u001a\u00020\u0006J\u0010\u0010 \u001a\u00020\u00142\u0006\u0010\u001d\u001a\u00020\u0004H\u0002J\u0018\u0010!\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0016\u001a\u00020\u0006J\u0016\u0010\"\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u0006H\u0082@¢\u0006\u0002\u0010#J\u0006\u0010$\u001a\u00020\u0014R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006%"}, d2 = {"Lorg/catrobat/catroid/content/LocalServer$Companion;", "", "()V", "clientSocket", "Ljava/net/Socket;", "connectedIP", "", "connectedPort", "coroutineScope", "Lkotlinx/coroutines/CoroutineScope;", "isRunning", "", "job", "Lkotlinx/coroutines/Job;", "outputStream", "Ljava/io/OutputStream;", "receivedValue", "serverSocket", "Ljava/net/ServerSocket;", "connectToServer", "", "ip", "port", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getIP", "getLocalIPAddress", "getPort", "getValue", "listenForMessages", "socket", "send", "value", "setupConnection", "startOrJoin", "startServer", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "stop", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final void startOrJoin(String ip, String port) {
            Intrinsics.checkNotNullParameter(port, "port");
            stop();
            LocalServer.job = BuildersKt__Builders_commonKt.launch$default(LocalServer.coroutineScope, null, null, new LocalServer$Companion$startOrJoin$1(ip, port, null), 3, null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final Object startServer(String str, Continuation<? super Unit> continuation) throws Throwable {
            Object objWithContext = BuildersKt.withContext(Dispatchers.getIO(), new LocalServer$Companion$startServer$2(str, null), continuation);
            return objWithContext == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objWithContext : Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final Object connectToServer(String str, String str2, Continuation<? super Unit> continuation) throws Throwable {
            Object objWithContext = BuildersKt.withContext(Dispatchers.getIO(), new LocalServer$Companion$connectToServer$2(str, str2, null), continuation);
            return objWithContext == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objWithContext : Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void setupConnection(Socket socket) {
            LocalServer.clientSocket = socket;
            LocalServer.outputStream = socket.getOutputStream();
            listenForMessages(socket);
        }

        private final void listenForMessages(Socket socket) {
            try {
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    try {
                        BufferedReader bufferedReader2 = bufferedReader;
                        while (LocalServer.isRunning && !socket.isClosed()) {
                            String line = bufferedReader2.readLine();
                            if (line == null) {
                                Log.d("LocalServer", "Соединение разорвано удаленной стороной.");
                                break;
                            } else {
                                Companion companion = LocalServer.INSTANCE;
                                LocalServer.receivedValue = line;
                            }
                        }
                        Unit unit = Unit.INSTANCE;
                        CloseableKt.closeFinally(bufferedReader, null);
                        stop();
                    } catch (Throwable th) {
                        try {
                            throw th;
                        } catch (Throwable th2) {
                            CloseableKt.closeFinally(bufferedReader, th);
                            throw th2;
                        }
                    }
                } catch (Exception e) {
                    if (LocalServer.isRunning) {
                        Log.e("LocalServer", "Ошибка чтения: " + e.getMessage());
                    }
                }
            } catch (Throwable th3) {
                stop();
                throw th3;
            }
        }

        public final void send(String value) {
            Intrinsics.checkNotNullParameter(value, "value");
            BuildersKt__Builders_commonKt.launch$default(LocalServer.coroutineScope, null, null, new LocalServer$Companion$send$1(value, null), 3, null);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final synchronized void stop() {
            if (LocalServer.isRunning) {
                LocalServer.isRunning = false;
                try {
                    OutputStream outputStream = LocalServer.outputStream;
                    if (outputStream != null) {
                        outputStream.close();
                    }
                    Socket socket = LocalServer.clientSocket;
                    if (socket != null) {
                        socket.close();
                    }
                    ServerSocket serverSocket = LocalServer.serverSocket;
                    if (serverSocket != null) {
                        serverSocket.close();
                    }
                } catch (IOException e) {
                    Log.w("LocalServer", "Ошибка при закрытии: " + e.getMessage());
                } finally {
                    LocalServer.outputStream = null;
                    LocalServer.clientSocket = null;
                    LocalServer.serverSocket = null;
                    Job job = LocalServer.job;
                    if (job != null) {
                        Job.DefaultImpls.cancel$default(job, (CancellationException) null, 1, (Object) null);
                    }
                    LocalServer.job = null;
                }
            }
        }

        public final String getValue() {
            return LocalServer.receivedValue;
        }

        public final String getIP() {
            String str = LocalServer.connectedIP;
            return str == null ? "NaN" : str;
        }

        public final String getPort() {
            String str = LocalServer.connectedPort;
            return str == null ? "NaN" : str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final String getLocalIPAddress() {
            Object next;
            try {
                Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
                Intrinsics.checkNotNullExpressionValue(networkInterfaces, "getNetworkInterfaces(...)");
                ArrayList list = Collections.list(networkInterfaces);
                Intrinsics.checkNotNullExpressionValue(list, "list(...)");
                ArrayList arrayList = new ArrayList();
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    Enumeration<InetAddress> inetAddresses = ((NetworkInterface) it.next()).getInetAddresses();
                    Intrinsics.checkNotNullExpressionValue(inetAddresses, "getInetAddresses(...)");
                    ArrayList list2 = Collections.list(inetAddresses);
                    Intrinsics.checkNotNullExpressionValue(list2, "list(...)");
                    CollectionsKt.addAll(arrayList, list2);
                }
                Iterator it2 = arrayList.iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it2.next();
                    InetAddress inetAddress = (InetAddress) next;
                    if (!inetAddress.isLoopbackAddress() && (inetAddress instanceof Inet4Address)) {
                        break;
                    }
                }
                InetAddress inetAddress2 = (InetAddress) next;
                if (inetAddress2 != null) {
                    return inetAddress2.getHostAddress();
                }
                return null;
            } catch (Exception unused) {
                return null;
            }
        }
    }

    private LocalServer() {
    }
}
