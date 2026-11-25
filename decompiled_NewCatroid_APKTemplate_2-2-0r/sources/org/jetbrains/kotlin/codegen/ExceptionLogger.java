package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Attachment;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;

/* JADX INFO: compiled from: ExceptionLogger.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001c\u0010\u0006\u001a\u00060\u0007j\u0002`\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0007R\u0013\u0010\u0003\u001a\u00070\u0004¢\u0006\u0002\b\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/codegen/ExceptionLogger;", "", "()V", "LOG", "Lorg/jetbrains/kotlin/com/intellij/openapi/diagnostic/Logger;", "Lorg/jetbrains/annotations/NotNull;", "logDescriptorNotFound", "Ljava/lang/AssertionError;", "Lkotlin/AssertionError;", "problemDescription", "", "psi", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ExceptionLogger {
    public static final ExceptionLogger INSTANCE = new ExceptionLogger();
    private static final Logger LOG;

    private ExceptionLogger() {
    }

    @JvmStatic
    public static final AssertionError logDescriptorNotFound(String problemDescription, PsiElement psi) {
        Intrinsics.checkNotNullParameter(problemDescription, "problemDescription");
        Intrinsics.checkNotNullParameter(psi, "psi");
        LOG.error(problemDescription, new Attachment("psi.kt", PsiUtilsKt.getElementTextWithContext(psi)));
        throw new AssertionError(problemDescription);
    }

    static {
        Logger logger = Logger.getInstance((Class<?>) ExceptionLogger.class);
        Intrinsics.checkNotNullExpressionValue(logger, "getInstance(...)");
        LOG = logger;
    }
}
