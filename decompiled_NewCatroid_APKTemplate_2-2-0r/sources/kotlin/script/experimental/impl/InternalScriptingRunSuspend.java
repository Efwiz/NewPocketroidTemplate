package kotlin.script.experimental.impl;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: runSuspend.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u000b\u0010\u000f\u001a\u00028\u0000¢\u0006\u0002\u0010\u0010J\u001b\u0010\u0011\u001a\u00020\u00122\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\tH\u0016¢\u0006\u0002\u0010\u0013R\u0014\u0010\u0004\u001a\u00020\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R.\u0010\b\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\tX\u0086\u000eø\u0001\u0000ø\u0001\u0001¢\u0006\u0014\n\u0000\u0012\u0004\b\n\u0010\u0003\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006\u0014"}, d2 = {"Lkotlin/script/experimental/impl/InternalScriptingRunSuspend;", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlin/coroutines/Continuation;", "()V", "context", "Lkotlin/coroutines/CoroutineContext;", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "result", "Lkotlin/Result;", "getResult-xLWZpok$annotations", "getResult-xLWZpok", "()Lkotlin/Result;", "setResult", "(Lkotlin/Result;)V", "await", "()Ljava/lang/Object;", "resumeWith", "", ReifiedTypeInliner.pluginIntrinsicsMarkerSignature, PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
final class InternalScriptingRunSuspend<T> implements Continuation<T> {
    private Result<? extends T> result;

    /* JADX INFO: renamed from: getResult-xLWZpok$annotations, reason: not valid java name */
    public static /* synthetic */ void m4193getResultxLWZpok$annotations() {
    }

    @Override // kotlin.coroutines.Continuation
    public CoroutineContext getContext() {
        return EmptyCoroutineContext.INSTANCE;
    }

    /* JADX INFO: renamed from: getResult-xLWZpok, reason: not valid java name */
    public final Result<T> m4194getResultxLWZpok() {
        return this.result;
    }

    public final void setResult(Result<? extends T> result) {
        this.result = result;
    }

    @Override // kotlin.coroutines.Continuation
    public void resumeWith(Object result) {
        synchronized (this) {
            this.result = Result.m2897boximpl(result);
            Intrinsics.checkNotNull(this, "null cannot be cast to non-null type java.lang.Object");
            notifyAll();
            Unit unit = Unit.INSTANCE;
        }
    }

    public final T await() {
        T t;
        synchronized (this) {
            while (true) {
                Result<? extends T> result = this.result;
                if (result == null) {
                    Intrinsics.checkNotNull(this, "null cannot be cast to non-null type java.lang.Object");
                    wait();
                } else {
                    Intrinsics.checkNotNull(result);
                    t = (T) result.getValue();
                    ResultKt.throwOnFailure(t);
                }
            }
        }
        return t;
    }
}
