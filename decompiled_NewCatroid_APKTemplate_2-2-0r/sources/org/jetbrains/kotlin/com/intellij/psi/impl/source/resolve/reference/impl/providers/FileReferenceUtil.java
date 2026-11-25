package org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.reference.impl.providers;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;

/* JADX INFO: loaded from: classes3.dex */
public final class FileReferenceUtil {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/reference/impl/providers/FileReferenceUtil", "findFileReference"));
    }

    private FileReferenceUtil() {
    }

    public static PsiFile findFile(PsiElement psiElement) {
        if (psiElement == null) {
            return null;
        }
        return findFile(psiElement.getReferences());
    }

    public static PsiFile findFile(PsiReference... psiReferenceArr) {
        PsiReference lastFileReference;
        int length = psiReferenceArr.length;
        do {
            length--;
            if (length < 0) {
                return null;
            }
            lastFileReference = psiReferenceArr[length];
            if ((lastFileReference instanceof FileReferenceOwner) && !(lastFileReference instanceof PsiFileReference)) {
                lastFileReference = ((FileReferenceOwner) lastFileReference).getLastFileReference();
            }
        } while (!(lastFileReference instanceof PsiFileReference));
        PsiElement psiElementResolve = psiReferenceArr[length].resolve();
        if (psiElementResolve instanceof PsiFile) {
            return (PsiFile) psiElementResolve;
        }
        return null;
    }

    public static PsiFileReference findFileReference(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(0);
        }
        PsiReference[] references = psiElement.getReferences();
        for (int length = references.length - 1; length >= 0; length--) {
            PsiReference lastFileReference = references[length];
            if ((lastFileReference instanceof FileReferenceOwner) && !(lastFileReference instanceof PsiFileReference)) {
                lastFileReference = ((FileReferenceOwner) lastFileReference).getLastFileReference();
            }
            if (lastFileReference instanceof PsiFileReference) {
                return (PsiFileReference) references[length];
            }
        }
        return null;
    }
}
