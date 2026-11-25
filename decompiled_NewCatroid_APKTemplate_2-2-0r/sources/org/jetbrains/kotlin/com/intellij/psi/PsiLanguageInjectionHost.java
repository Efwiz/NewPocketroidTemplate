package org.jetbrains.kotlin.com.intellij.psi;

import java.util.List;
import org.jetbrains.kotlin.com.intellij.model.psi.PsiExternalReferenceHost;
import org.jetbrains.kotlin.com.intellij.openapi.util.Segment;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiLanguageInjectionHost extends PsiExternalReferenceHost {

    @FunctionalInterface
    public interface InjectedPsiVisitor {
        void visit(PsiFile psiFile, List<Shred> list);
    }

    public interface Shred {
        void dispose();

        PsiLanguageInjectionHost getHost();

        Segment getHostRangeMarker();

        String getPrefix();

        TextRange getRange();

        TextRange getRangeInsideHost();

        String getSuffix();

        boolean isValid();
    }

    LiteralTextEscaper<? extends PsiLanguageInjectionHost> createLiteralTextEscaper();

    boolean isValidHost();

    PsiLanguageInjectionHost updateText(String str);
}
