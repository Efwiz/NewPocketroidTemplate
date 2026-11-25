package org.jetbrains.kotlin.psi;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType;
import org.jetbrains.kotlin.psi.stubs.KotlinPlaceHolderWithTextStub;

/* JADX INFO: loaded from: classes6.dex */
public abstract class KtStringTemplateEntryWithExpression extends KtStringTemplateEntry {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "stub";
        } else if (i == 2) {
            objArr[0] = "elementType";
        } else if (i != 3) {
            objArr[0] = "node";
        } else {
            objArr[0] = "visitor";
        }
        objArr[1] = "org/jetbrains/kotlin/psi/KtStringTemplateEntryWithExpression";
        if (i != 3) {
            objArr[2] = "<init>";
        } else {
            objArr[2] = "accept";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtStringTemplateEntryWithExpression(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtStringTemplateEntryWithExpression(KotlinPlaceHolderWithTextStub<? extends KtStringTemplateEntryWithExpression> kotlinPlaceHolderWithTextStub, IStubElementType iStubElementType) {
        super(kotlinPlaceHolderWithTextStub, iStubElementType);
        if (kotlinPlaceHolderWithTextStub == null) {
            $$$reportNull$$$0(1);
        }
        if (iStubElementType == null) {
            $$$reportNull$$$0(2);
        }
    }

    @Override // org.jetbrains.kotlin.psi.KtElementImplStub, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> ktVisitor, D d) {
        if (ktVisitor == null) {
            $$$reportNull$$$0(3);
        }
        return ktVisitor.visitStringTemplateEntryWithExpression(this, d);
    }
}
