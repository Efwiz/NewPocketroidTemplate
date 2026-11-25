package org.jetbrains.kotlin.idea.references;

import kotlin.Metadata;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: KtReferenceMutateService.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0003H&J \u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0006\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\nH&J.\u0010\u000b\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\t\u001a\u00020\n2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0003H&J\u001a\u0010\u000f\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0010\u001a\u00020\u0011H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0012À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/idea/references/KtReferenceMutateService;", "", "bindToElement", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "ktReference", "Lorg/jetbrains/kotlin/idea/references/KtReference;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "simpleNameReference", "Lorg/jetbrains/kotlin/idea/references/KtSimpleNameReference;", "shorteningMode", "Lorg/jetbrains/kotlin/idea/references/KtSimpleNameReference$ShorteningMode;", "bindToFqName", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "targetElement", "handleElementRename", "newElementName", "", "kt-references"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface KtReferenceMutateService {
    PsiElement bindToElement(KtReference ktReference, PsiElement element);

    PsiElement bindToElement(KtSimpleNameReference simpleNameReference, PsiElement element, KtSimpleNameReference.ShorteningMode shorteningMode);

    PsiElement bindToFqName(KtSimpleNameReference simpleNameReference, FqName fqName, KtSimpleNameReference.ShorteningMode shorteningMode, PsiElement targetElement);

    PsiElement handleElementRename(KtReference ktReference, String newElementName);

    static /* synthetic */ PsiElement bindToFqName$default(KtReferenceMutateService ktReferenceMutateService, KtSimpleNameReference ktSimpleNameReference, FqName fqName, KtSimpleNameReference.ShorteningMode shorteningMode, PsiElement psiElement, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: bindToFqName");
        }
        if ((i & 4) != 0) {
            shorteningMode = KtSimpleNameReference.ShorteningMode.DELAYED_SHORTENING;
        }
        if ((i & 8) != 0) {
            psiElement = null;
        }
        return ktReferenceMutateService.bindToFqName(ktSimpleNameReference, fqName, shorteningMode, psiElement);
    }
}
