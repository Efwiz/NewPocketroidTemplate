package kotlin.script.experimental.jvm.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.api.ResultValue;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: runtimeExceptionReporting.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u001a\u0012\u0010\u0000\u001a\u00020\u0003*\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u0005¨\u0006\u0006"}, d2 = {"renderError", "", "Lkotlin/script/experimental/api/ResultValue$Error;", "", "stream", "Ljava/io/PrintStream;", PathUtil.KOTLIN_SCRIPTING_JVM_NAME}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class RuntimeExceptionReportingKt {
    public static final void renderError(ResultValue.Error error, PrintStream stream) {
        Intrinsics.checkNotNullParameter(error, "<this>");
        Intrinsics.checkNotNullParameter(stream, "stream");
        StackTraceElement[] stackTrace = error.getError().getStackTrace();
        Throwable wrappingException = error.getWrappingException();
        StackTraceElement[] stackTrace2 = wrappingException != null ? wrappingException.getStackTrace() : null;
        if (error.getWrappingException() != null) {
            int length = stackTrace.length;
            Intrinsics.checkNotNull(stackTrace2);
            if (length >= stackTrace2.length) {
                stream.println(error.getError());
                Intrinsics.checkNotNull(stackTrace);
                renderError$printTrace(stream, stackTrace, stackTrace2.length);
                Throwable cause = error.getError().getCause();
                Throwable error2 = error.getError();
                HashSet hashSetHashSetOf = SetsKt.hashSetOf(error2);
                while (cause != null && hashSetHashSetOf.add(cause)) {
                    StackTraceElement[] stackTrace3 = cause.getStackTrace();
                    Intrinsics.checkNotNull(stackTrace3);
                    Sequence sequenceAsSequence = CollectionsKt.asSequence(CollectionsKt.asReversed(ArraysKt.asList(stackTrace3)));
                    StackTraceElement[] stackTrace4 = error2.getStackTrace();
                    Intrinsics.checkNotNullExpressionValue(stackTrace4, "getStackTrace(...)");
                    int iCount = SequencesKt.count(SequencesKt.takeWhile(SequencesKt.zip(sequenceAsSequence, CollectionsKt.asSequence(CollectionsKt.asReversed(ArraysKt.asList(stackTrace4)))), new Function1<Pair<? extends StackTraceElement, ? extends StackTraceElement>, Boolean>() { // from class: kotlin.script.experimental.jvm.util.RuntimeExceptionReportingKt$renderError$sameFramesCount$1
                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final Boolean invoke2(Pair<StackTraceElement, StackTraceElement> it) {
                            Intrinsics.checkNotNullParameter(it, "it");
                            return Boolean.valueOf(Intrinsics.areEqual(it.getFirst(), it.getSecond()));
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Boolean invoke(Pair<? extends StackTraceElement, ? extends StackTraceElement> pair) {
                            return invoke2((Pair<StackTraceElement, StackTraceElement>) pair);
                        }
                    }));
                    stream.println("Caused by: " + cause);
                    Intrinsics.checkNotNull(stackTrace3);
                    renderError$printTrace(stream, stackTrace3, iCount);
                    Throwable th = cause;
                    cause = cause.getCause();
                    error2 = th;
                }
                return;
            }
        }
        error.getError().printStackTrace(stream);
    }

    private static final void renderError$printTrace(PrintStream printStream, StackTraceElement[] stackTraceElementArr, int i) {
        Iterator it = ArraysKt.dropLast(stackTraceElementArr, i).iterator();
        while (it.hasNext()) {
            printStream.println("\tat " + ((StackTraceElement) it.next()));
        }
    }

    public static final String renderError(ResultValue.Error error) {
        Intrinsics.checkNotNullParameter(error, "<this>");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ByteArrayOutputStream byteArrayOutputStream2 = byteArrayOutputStream;
            PrintStream printStream = new PrintStream(byteArrayOutputStream2);
            renderError(error, printStream);
            printStream.flush();
            String string = byteArrayOutputStream2.toString();
            CloseableKt.closeFinally(byteArrayOutputStream, null);
            Intrinsics.checkNotNullExpressionValue(string, "use(...)");
            return string;
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                CloseableKt.closeFinally(byteArrayOutputStream, th);
                throw th2;
            }
        }
    }
}
