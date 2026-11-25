package org.jetbrains.kotlin.utils;

import java.io.Closeable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: exceptionUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u000b\n\u0000\u001a\u0010\u0010\u0000\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u001a\u0012\u0010\u0004\u001a\u00060\u0005j\u0002`\u00062\u0006\u0010\u0007\u001a\u00020\b\u001a\n\u0010\t\u001a\u00020\n*\u00020\b¨\u0006\u000b"}, d2 = {"closeQuietly", "", "closeable", "Ljava/io/Closeable;", "rethrow", "Ljava/lang/RuntimeException;", "Lkotlin/RuntimeException;", "e", "", "isProcessCanceledException", "", "util.runtime"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ExceptionUtilsKt {
    public static final RuntimeException rethrow(Throwable e) throws Throwable {
        Intrinsics.checkNotNullParameter(e, "e");
        throw e;
    }

    public static final void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Throwable unused) {
            }
        }
    }

    public static final boolean isProcessCanceledException(Throwable th) {
        Intrinsics.checkNotNullParameter(th, "<this>");
        Class<?> superclass = th.getClass();
        while (!Intrinsics.areEqual(superclass.getCanonicalName(), "org.jetbrains.kotlin.com.intellij.openapi.progress.ProcessCanceledException")) {
            superclass = superclass.getSuperclass();
            if (superclass == null) {
                return false;
            }
        }
        return true;
    }
}
