package kotlin.script.experimental.impl;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.ContinuationKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: runSuspend.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\u001a1\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u00012\u001c\u0010\u0002\u001a\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0003H\u0007¢\u0006\u0002\u0010\u0006¨\u0006\u0007"}, d2 = {"internalScriptingRunSuspend", ExifInterface.GPS_DIRECTION_TRUE, "block", "Lkotlin/Function1;", "Lkotlin/coroutines/Continuation;", "", "(Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class RunSuspendKt {
    @Deprecated(level = DeprecationLevel.ERROR, message = "For internal use only, use kotlinx.coroutines instead")
    public static final <T> T internalScriptingRunSuspend(Function1<? super Continuation<? super T>, ? extends Object> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        InternalScriptingRunSuspend internalScriptingRunSuspend = new InternalScriptingRunSuspend();
        ContinuationKt.startCoroutine(block, internalScriptingRunSuspend);
        return (T) internalScriptingRunSuspend.await();
    }
}
