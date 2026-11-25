package org.jetbrains.kotlin.util;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.cli.common.arguments.K2JsArgumentConstants;

/* JADX INFO: compiled from: WithLogger.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0001\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\b\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\t\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\nÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/util/Logger;", "", "error", "", CrashHianalyticsData.MESSAGE, "", "fatal", "", K2JsArgumentConstants.RUNTIME_DIAGNOSTIC_LOG, CompilerOptions.WARNING, "kotlin-util-io"}, k = 1, mv = {1, 5, 1}, xi = 48)
public interface Logger {
    /* JADX INFO: renamed from: error */
    void mo7307error(String message);

    Void fatal(String message);

    void log(String message);

    void warning(String message);
}
