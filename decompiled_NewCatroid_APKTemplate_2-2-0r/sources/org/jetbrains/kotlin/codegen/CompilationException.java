package org.jetbrains.kotlin.codegen;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.diagnostics.PsiDiagnosticUtils;
import org.jetbrains.kotlin.util.ExceptionUtilKt;
import org.jetbrains.kotlin.utils.KotlinExceptionWithAttachments;

/* JADX INFO: loaded from: classes3.dex */
public class CompilationException extends KotlinExceptionWithAttachments {
    private final PsiElement element;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", CrashHianalyticsData.MESSAGE, "org/jetbrains/kotlin/codegen/CompilationException", "<init>"));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CompilationException(String str, Throwable th, PsiElement psiElement) {
        super(ExceptionUtilKt.getExceptionMessage("Back-end (JVM)", str, th, psiElement == null ? null : PsiDiagnosticUtils.atLocation(psiElement)), th);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        this.element = psiElement;
        withPsiAttachment("element.kt", psiElement);
    }

    public PsiElement getElement() {
        return this.element;
    }
}
