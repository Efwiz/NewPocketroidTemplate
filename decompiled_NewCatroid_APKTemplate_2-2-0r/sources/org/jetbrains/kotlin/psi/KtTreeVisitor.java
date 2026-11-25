package org.jetbrains.kotlin.psi;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;

/* JADX INFO: loaded from: classes6.dex */
public class KtTreeVisitor<D> extends KtVisitor<Void, D> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        } else {
            objArr[0] = "file";
        }
        objArr[1] = "org/jetbrains/kotlin/psi/KtTreeVisitor";
        if (i != 1) {
            objArr[2] = "visitKtElement";
        } else {
            objArr[2] = "visitKtFile";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public Void visitKtElement(KtElement ktElement, D d) {
        if (ktElement == null) {
            $$$reportNull$$$0(0);
        }
        ktElement.acceptChildren(this, d);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public Void visitKtFile(KtFile ktFile, D d) {
        if (ktFile == null) {
            $$$reportNull$$$0(1);
        }
        super.visitKtFile(ktFile, (Object) d);
        ktFile.acceptChildren(this, d);
        return null;
    }
}
