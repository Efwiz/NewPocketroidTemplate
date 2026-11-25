package org.catrobat.catroid.content;

import java.net.Socket;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/LocalServer$Companion$connectToServer$2.class
  classes14.dex
 */
/* JADX INFO: compiled from: LocalServer.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "org.catrobat.catroid.content.LocalServer$Companion$connectToServer$2", f = "LocalServer.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
final class LocalServer$Companion$connectToServer$2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ String $ip;
    final /* synthetic */ String $port;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    LocalServer$Companion$connectToServer$2(String str, String str2, Continuation<? super LocalServer$Companion$connectToServer$2> continuation) {
        super(2, continuation);
        this.$ip = str;
        this.$port = str2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new LocalServer$Companion$connectToServer$2(this.$ip, this.$port, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((LocalServer$Companion$connectToServer$2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
        }
        ResultKt.throwOnFailure(obj);
        Socket socket = new Socket(this.$ip, Integer.parseInt(this.$port));
        LocalServer.Companion companion = LocalServer.INSTANCE;
        LocalServer.connectedIP = this.$ip;
        LocalServer.Companion companion2 = LocalServer.INSTANCE;
        LocalServer.connectedPort = this.$port;
        LocalServer.INSTANCE.setupConnection(socket);
        return Unit.INSTANCE;
    }
}
