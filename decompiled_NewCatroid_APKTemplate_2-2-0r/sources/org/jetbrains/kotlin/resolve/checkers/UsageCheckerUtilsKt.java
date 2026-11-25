package org.jetbrains.kotlin.resolve.checkers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtConstructorCalleeExpression;
import org.jetbrains.kotlin.psi.KtDotQualifiedExpression;
import org.jetbrains.kotlin.psi.KtImportDirective;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.psi.KtUserType;

/* JADX INFO: compiled from: usageCheckerUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0000¨\u0006\u0003"}, d2 = {"isUsageAsAnnotationOrImport", "", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class UsageCheckerUtilsKt {
    public static final boolean isUsageAsAnnotationOrImport(PsiElement psiElement) {
        Intrinsics.checkNotNullParameter(psiElement, "<this>");
        PsiElement lightParent = psiElement.getLightParent();
        if (!(lightParent instanceof KtUserType)) {
            return (lightParent instanceof KtDotQualifiedExpression) && (((KtDotQualifiedExpression) lightParent).getLightParent() instanceof KtImportDirective);
        }
        KtUserType ktUserType = (KtUserType) lightParent;
        if ((ktUserType.getLightParent() instanceof KtUserType) && isUsageAsAnnotationOrImport(lightParent)) {
            return true;
        }
        return (ktUserType.getLightParent() instanceof KtTypeReference) && (ktUserType.getLightParent().getLightParent() instanceof KtConstructorCalleeExpression) && (ktUserType.getLightParent().getLightParent().getLightParent() instanceof KtAnnotationEntry);
    }
}
