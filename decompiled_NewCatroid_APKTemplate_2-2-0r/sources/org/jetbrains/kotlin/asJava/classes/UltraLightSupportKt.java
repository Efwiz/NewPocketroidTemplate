package org.jetbrains.kotlin.asJava.classes;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.JvmAnalysisFlags;
import org.jetbrains.kotlin.config.JvmDefaultMode;

/* JADX INFO: compiled from: ultraLightSupport.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\"\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00028@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u0005"}, d2 = {"jvmDefaultMode", "Lorg/jetbrains/kotlin/config/JvmDefaultMode;", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;", "getJvmDefaultMode", "(Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;)Lorg/jetbrains/kotlin/config/JvmDefaultMode;", "light-classes"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class UltraLightSupportKt {
    public static final JvmDefaultMode getJvmDefaultMode(KtUltraLightSupport ktUltraLightSupport) {
        Intrinsics.checkNotNullParameter(ktUltraLightSupport, "<this>");
        return (JvmDefaultMode) ktUltraLightSupport.getLanguageVersionSettings().getFlag(JvmAnalysisFlags.getJvmDefaultMode());
    }
}
