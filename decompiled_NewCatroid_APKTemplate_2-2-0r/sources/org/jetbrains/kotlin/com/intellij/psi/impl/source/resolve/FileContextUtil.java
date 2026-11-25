package org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.SmartPsiElementPointer;

/* JADX INFO: loaded from: classes3.dex */
public final class FileContextUtil {
    public static final Key<SmartPsiElementPointer> INJECTED_IN_ELEMENT = Key.create("injectedIn");
    public static final Key<PsiFile> CONTAINING_FILE_KEY = Key.create("CONTAINING_FILE_KEY");

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "file";
        } else {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/resolve/FileContextUtil";
        if (i != 1) {
            objArr[2] = "getFileContext";
        } else {
            objArr[2] = "getContextFile";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    private FileContextUtil() {
    }

    public static PsiElement getFileContext(PsiFile psiFile) {
        if (psiFile == null) {
            $$$reportNull$$$0(0);
        }
        SmartPsiElementPointer smartPsiElementPointer = (SmartPsiElementPointer) psiFile.getUserData(INJECTED_IN_ELEMENT);
        if (smartPsiElementPointer == null) {
            return null;
        }
        return smartPsiElementPointer.getElement();
    }

    public static PsiFile getContextFile(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(1);
        }
        PsiFile containingFile = psiElement.getContainingFile();
        if (containingFile == null) {
            return null;
        }
        PsiElement context = containingFile.getContext();
        return context == null ? containingFile : getContextFile(context);
    }
}
