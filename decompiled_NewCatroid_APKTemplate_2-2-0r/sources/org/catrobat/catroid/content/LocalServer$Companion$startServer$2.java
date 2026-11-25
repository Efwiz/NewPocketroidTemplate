package org.catrobat.catroid.content;

import android.util.Log;
import java.net.ServerSocket;
import java.net.Socket;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/LocalServer$Companion$startServer$2.class
  classes14.dex
 */
/* JADX INFO: compiled from: LocalServer.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "org.catrobat.catroid.content.LocalServer$Companion$startServer$2", f = "LocalServer.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
final class LocalServer$Companion$startServer$2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ String $port;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    LocalServer$Companion$startServer$2(String str, Continuation<? super LocalServer$Companion$startServer$2> continuation) {
        super(2, continuation);
        this.$port = str;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new LocalServer$Companion$startServer$2(this.$port, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((LocalServer$Companion$startServer$2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
        }
        ResultKt.throwOnFailure(obj);
        LocalServer.Companion companion = LocalServer.INSTANCE;
        LocalServer.serverSocket = new ServerSocket(Integer.parseInt(this.$port));
        LocalServer.Companion companion2 = LocalServer.INSTANCE;
        LocalServer.connectedPort = this.$port;
        LocalServer.Companion companion3 = LocalServer.INSTANCE;
        LocalServer.connectedIP = LocalServer.INSTANCE.getLocalIPAddress();
        Log.d("LocalServer", "Сервер запущен на " + LocalServer.connectedIP + ":" + this.$port);
        ServerSocket serverSocket = LocalServer.serverSocket;
        Intrinsics.checkNotNull(serverSocket);
        Socket socketAccept = serverSocket.accept();
        LocalServer.Companion companion4 = LocalServer.INSTANCE;
        Intrinsics.checkNotNull(socketAccept);
        companion4.setupConnection(socketAccept);
        return Unit.INSTANCE;
    }
}
