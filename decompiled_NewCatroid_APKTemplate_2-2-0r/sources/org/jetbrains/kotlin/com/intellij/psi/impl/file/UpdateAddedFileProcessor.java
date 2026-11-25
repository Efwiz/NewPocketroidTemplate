package org.jetbrains.kotlin.com.intellij.psi.impl.file;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointName;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.SourceTreeToPsiMap;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ChangeUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public abstract class UpdateAddedFileProcessor {
    private static final ExtensionPointName<UpdateAddedFileProcessor> EP_NAME = ExtensionPointName.create("org.jetbrains.kotlin.com.intellij.updateAddedFileProcessor");

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        } else {
            objArr[0] = "copyPsis";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/file/UpdateAddedFileProcessor";
        if (i != 1) {
            objArr[2] = "forElement";
        } else {
            objArr[2] = "updateAddedFiles";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public abstract boolean canProcessElement(PsiFile psiFile);

    public abstract void update(PsiFile psiFile, PsiFile psiFile2) throws IncorrectOperationException;

    public static UpdateAddedFileProcessor forElement(PsiFile psiFile) {
        if (psiFile == null) {
            $$$reportNull$$$0(0);
        }
        for (UpdateAddedFileProcessor updateAddedFileProcessor : EP_NAME.getExtensionList()) {
            if (updateAddedFileProcessor.canProcessElement(psiFile)) {
                return updateAddedFileProcessor;
            }
        }
        return null;
    }

    public static void updateAddedFiles(Iterable<? extends PsiFile> iterable) throws Throwable {
        if (iterable == null) {
            $$$reportNull$$$0(1);
        }
        for (PsiFile psiFile : iterable) {
            UpdateAddedFileProcessor updateAddedFileProcessorForElement = forElement(psiFile);
            if (updateAddedFileProcessorForElement != null) {
                TreeElement treeElement = (TreeElement) SourceTreeToPsiMap.psiElementToTree(psiFile);
                if (treeElement != null) {
                    ChangeUtil.encodeInformation(treeElement);
                }
                updateAddedFileProcessorForElement.update(psiFile, null);
                if (treeElement != null) {
                    ChangeUtil.decodeInformation(treeElement);
                }
            }
        }
    }
}
