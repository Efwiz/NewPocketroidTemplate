package org.jetbrains.kotlin.resolve.source;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.SourceElement;

/* JADX INFO: compiled from: PsiSourceElement.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002¨\u0006\u0003"}, d2 = {"getPsi", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "psi"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PsiSourceElementKt {
    public static final PsiElement getPsi(SourceElement sourceElement) {
        Intrinsics.checkNotNullParameter(sourceElement, "<this>");
        PsiSourceElement psiSourceElement = sourceElement instanceof PsiSourceElement ? (PsiSourceElement) sourceElement : null;
        if (psiSourceElement != null) {
            return psiSourceElement.getPsi();
        }
        return null;
    }
}
