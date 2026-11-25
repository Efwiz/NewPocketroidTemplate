package org.jetbrains.kotlin.util;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.cli.common.arguments.K2JsArgumentConstants;

/* JADX INFO: compiled from: WithLogger.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0001\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\n\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/util/DummyLogger;", "Lorg/jetbrains/kotlin/util/Logger;", "()V", "error", "", CrashHianalyticsData.MESSAGE, "", "fatal", "", K2JsArgumentConstants.RUNTIME_DIAGNOSTIC_LOG, CompilerOptions.WARNING, "kotlin-util-io"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class DummyLogger implements Logger {
    public static final DummyLogger INSTANCE = new DummyLogger();

    private DummyLogger() {
    }

    @Override // org.jetbrains.kotlin.util.Logger
    public void log(String message) {
        Intrinsics.checkNotNullParameter(message, "message");
        System.out.println((Object) message);
    }

    @Override // org.jetbrains.kotlin.util.Logger
    /* JADX INFO: renamed from: error */
    public void mo7307error(String message) {
        Intrinsics.checkNotNullParameter(message, "message");
        System.out.println((Object) ("e: " + message));
    }

    @Override // org.jetbrains.kotlin.util.Logger
    public void warning(String message) {
        Intrinsics.checkNotNullParameter(message, "message");
        System.out.println((Object) ("w: " + message));
    }

    @Override // org.jetbrains.kotlin.util.Logger
    public Void fatal(String message) {
        Intrinsics.checkNotNullParameter(message, "message");
        System.out.println((Object) ("e: " + message));
        System.exit(1);
        throw new RuntimeException("System.exit returned normally, while it was supposed to halt JVM.");
    }
}
