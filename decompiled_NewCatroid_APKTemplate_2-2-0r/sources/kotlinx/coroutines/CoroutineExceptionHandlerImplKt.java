package kotlinx.coroutines;

import java.util.List;
import java.util.ServiceLoader;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.kotlin.cli.common.arguments.K2JsArgumentConstants;

/* JADX INFO: compiled from: CoroutineExceptionHandlerImpl.kt */
/* JADX INFO: loaded from: NewCatroid_APKTemplate_2-2-0r.apk:assets/kotlinx-coroutines-core-jvm-1.6.4.jar:kotlinx/coroutines/CoroutineExceptionHandlerImplKt.class */
@Metadata(mv = {1, 6, 0}, k = 2, xi = 48, d1 = {"\u0000\u001e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\u001a\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0000\"\u0014\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"handlers", "", "Lkotlinx/coroutines/CoroutineExceptionHandler;", "handleCoroutineExceptionImpl", "", "context", "Lkotlin/coroutines/CoroutineContext;", K2JsArgumentConstants.RUNTIME_DIAGNOSTIC_EXCEPTION, "", "kotlinx-coroutines-core"})
public final class CoroutineExceptionHandlerImplKt {

    @NotNull
    private static final List<CoroutineExceptionHandler> handlers = SequencesKt.toList(SequencesKt.asSequence(ServiceLoader.load(CoroutineExceptionHandler.class, CoroutineExceptionHandler.class.getClassLoader()).iterator()));

    public static final void handleCoroutineExceptionImpl(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        for (CoroutineExceptionHandler handler : handlers) {
            try {
                handler.handleException(context, exception);
            } catch (Throwable t) {
                Thread currentThread = Thread.currentThread();
                currentThread.getUncaughtExceptionHandler().uncaughtException(currentThread, CoroutineExceptionHandlerKt.handlerException(exception, t));
            }
        }
        Thread currentThread2 = Thread.currentThread();
        try {
            Result.Companion companion = Result.INSTANCE;
            kotlin.ExceptionsKt.addSuppressed(exception, new DiagnosticCoroutineContextException(context));
            Result.m2898constructorimpl(Unit.INSTANCE);
        } catch (Throwable th) {
            Result.Companion companion2 = Result.INSTANCE;
            Result.m2898constructorimpl(ResultKt.createFailure(th));
        }
        currentThread2.getUncaughtExceptionHandler().uncaughtException(currentThread2, exception);
    }
}
