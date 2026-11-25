package org.jetbrains.kotlin.buildtools.api;

import androidx.core.app.NotificationCompat;
import kotlin.Metadata;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.cli.common.arguments.CommonCompilerArguments;

/* JADX INFO: compiled from: KotlinLogger.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH&J\u001c\u0010\t\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000bH&J\u0010\u0010\f\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH&J\u0010\u0010\r\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH&J\u0010\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0004ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000fÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/buildtools/api/KotlinLogger;", "", "isDebugEnabled", "", "()Z", "debug", "", NotificationCompat.CATEGORY_MESSAGE, "", "error", "throwable", "", CompilerOptions.INFO, "lifecycle", CommonCompilerArguments.WARN, "kotlin-build-tools-api"}, k = 1, mv = {1, 5, 1}, xi = 48)
public interface KotlinLogger {
    void debug(String msg);

    void error(String msg, Throwable throwable);

    void info(String msg);

    boolean isDebugEnabled();

    void lifecycle(String msg);

    void warn(String msg);

    static /* synthetic */ void error$default(KotlinLogger kotlinLogger, String str, Throwable th, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: error");
        }
        if ((i & 2) != 0) {
            th = null;
        }
        kotlinLogger.error(str, th);
    }
}
