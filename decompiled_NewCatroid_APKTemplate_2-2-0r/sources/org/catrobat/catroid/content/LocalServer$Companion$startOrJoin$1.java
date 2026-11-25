package org.catrobat.catroid.content;

import android.util.Log;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import org.catrobat.catroid.utils.ErrorLog;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/LocalServer$Companion$startOrJoin$1.class
  classes14.dex
 */
/* JADX INFO: compiled from: LocalServer.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "org.catrobat.catroid.content.LocalServer$Companion$startOrJoin$1", f = "LocalServer.kt", i = {}, l = {30, 32}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
final class LocalServer$Companion$startOrJoin$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    final /* synthetic */ String $ip;
    final /* synthetic */ String $port;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    LocalServer$Companion$startOrJoin$1(String str, String str2, Continuation<? super LocalServer$Companion$startOrJoin$1> continuation) {
        super(2, continuation);
        this.$ip = str;
        this.$port = str2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new LocalServer$Companion$startOrJoin$1(this.$ip, this.$port, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return ((LocalServer$Companion$startOrJoin$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        try {
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                LocalServer.Companion companion = LocalServer.INSTANCE;
                LocalServer.isRunning = true;
                String str = this.$ip;
                if (str == null || str.length() == 0) {
                    this.label = 1;
                    if (LocalServer.INSTANCE.startServer(this.$port, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else {
                    this.label = 2;
                    if (LocalServer.INSTANCE.connectToServer(this.$ip, this.$port, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                }
            } else {
                if (i != 1 && i != 2) {
                    throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                }
                ResultKt.throwOnFailure(obj);
            }
        } catch (Exception e) {
            if (LocalServer.isRunning) {
                ErrorLog errorLog = ErrorLog.INSTANCE;
                String message = e.getMessage();
                if (message == null) {
                    message = "Unknown socket error";
                }
                errorLog.log(message);
                Log.e("LocalServer", "Ошибка: " + e.getMessage(), e);
            }
        } finally {
            LocalServer.INSTANCE.stop();
        }
        return Unit.INSTANCE;
    }
}
