package org.jetbrains.kotlin.resolve.calls.util;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.StubBasedPsiElement;
import org.jetbrains.kotlin.psi.KtNamedDeclaration;

/* JADX INFO: compiled from: underscoreUtil.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003¨\u0006\u0004"}, d2 = {"isSingleUnderscore", "", "Lorg/jetbrains/kotlin/psi/KtNamedDeclaration;", "(Lorg/jetbrains/kotlin/psi/KtNamedDeclaration;)Z", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class UnderscoreUtilKt {
    public static final boolean isSingleUnderscore(KtNamedDeclaration ktNamedDeclaration) {
        Intrinsics.checkNotNullParameter(ktNamedDeclaration, "<this>");
        if ((ktNamedDeclaration instanceof StubBasedPsiElement) && ((StubBasedPsiElement) ktNamedDeclaration).getStub() != null) {
            return false;
        }
        PsiElement nameIdentifier = ktNamedDeclaration.mo7392getNameIdentifier();
        return Intrinsics.areEqual(nameIdentifier != null ? nameIdentifier.getText() : null, "_");
    }
}
