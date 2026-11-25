package org.jetbrains.kotlin.fir.java;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.config.JvmDefaultMode;
import org.jetbrains.kotlin.fir.FirSessionComponent;

/* JADX INFO: compiled from: JvmDefaultModeContainer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/java/FirJvmDefaultModeComponent;", "Lorg/jetbrains/kotlin/fir/FirSessionComponent;", "jvmDefaultMode", "Lorg/jetbrains/kotlin/config/JvmDefaultMode;", "(Lorg/jetbrains/kotlin/config/JvmDefaultMode;)V", "getJvmDefaultMode", "()Lorg/jetbrains/kotlin/config/JvmDefaultMode;", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJvmDefaultModeComponent implements FirSessionComponent {
    private final JvmDefaultMode jvmDefaultMode;

    public FirJvmDefaultModeComponent(JvmDefaultMode jvmDefaultMode) {
        Intrinsics.checkNotNullParameter(jvmDefaultMode, "jvmDefaultMode");
        this.jvmDefaultMode = jvmDefaultMode;
    }

    public final JvmDefaultMode getJvmDefaultMode() {
        return this.jvmDefaultMode;
    }
}
