package org.jetbrains.kotlin.cli.jvm.modules;

import kotlin.Metadata;
import org.jetbrains.kotlin.com.intellij.util.lang.JavaVersion;

/* JADX INFO: compiled from: javaVersionUtils.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u000b\n\u0000\u001a\u0006\u0010\u0000\u001a\u00020\u0001¨\u0006\u0002"}, d2 = {"isAtLeastJava9", "", "cli-base"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JavaVersionUtilsKt {
    public static final boolean isAtLeastJava9() {
        return JavaVersion.current().compareTo(JavaVersion.compose(9)) >= 0;
    }
}
