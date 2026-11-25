package org.jetbrains.kotlin.js.sourceMap;

import java.io.File;
import java.io.IOException;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.editor.Document;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.js.backend.ast.JsLocation;

/* JADX INFO: loaded from: classes5.dex */
public final class PsiUtils {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        } else {
            objArr[0] = "pathResolver";
        }
        objArr[1] = "org/jetbrains/kotlin/js/sourceMap/PsiUtils";
        objArr[2] = "extractLocationFromPsi";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    private PsiUtils() {
    }

    public static JsLocation extractLocationFromPsi(PsiElement psiElement, SourceFilePathResolver sourceFilePathResolver) throws IOException {
        if (psiElement == null) {
            $$$reportNull$$$0(0);
        }
        if (sourceFilePathResolver == null) {
            $$$reportNull$$$0(1);
        }
        PsiFile containingFile = psiElement.getContainingFile();
        int startOffset = psiElement.getNode().getStartOffset();
        Document document = containingFile.getViewProvider().getDocument();
        int lineNumber = document.getLineNumber(startOffset);
        return new JsLocation(sourceFilePathResolver.getPathRelativeToSourceRoots(new File(containingFile.getViewProvider().getVirtualFile().getPath())), lineNumber, startOffset - document.getLineStartOffset(lineNumber));
    }
}
