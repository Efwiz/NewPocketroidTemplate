package org.jetbrains.kotlin.util;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.diagnostics.PsiDiagnosticUtils;
import org.jetbrains.kotlin.utils.KotlinExceptionWithAttachments;

/* JADX INFO: compiled from: KotlinFrontEndException.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0003\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u001f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bB\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/util/KotlinFrontEndException;", "Lorg/jetbrains/kotlin/utils/KotlinExceptionWithAttachments;", CrashHianalyticsData.MESSAGE, "", "cause", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "(Ljava/lang/String;Ljava/lang/Throwable;Lcom/intellij/psi/PsiElement;)V", "(Ljava/lang/String;Ljava/lang/Throwable;)V", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinFrontEndException extends KotlinExceptionWithAttachments {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KotlinFrontEndException(String message, Throwable cause) {
        super(message, cause);
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(cause, "cause");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public KotlinFrontEndException(String message, Throwable cause, PsiElement element) {
        this(ExceptionUtilKt.getExceptionMessage("Front-end", message, cause, PsiDiagnosticUtils.atLocation(element)), cause);
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(cause, "cause");
        Intrinsics.checkNotNullParameter(element, "element");
        withPsiAttachment("element.kt", element);
    }
}
