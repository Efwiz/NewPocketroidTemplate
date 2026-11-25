package org.jetbrains.kotlin.psi;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.psi.stubs.KotlinPlaceHolderWithTextStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: loaded from: classes6.dex */
public class KtLiteralStringTemplateEntry extends KtStringTemplateEntry {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "stub";
        } else if (i != 2) {
            objArr[0] = "node";
        } else {
            objArr[0] = "visitor";
        }
        objArr[1] = "org/jetbrains/kotlin/psi/KtLiteralStringTemplateEntry";
        if (i != 2) {
            objArr[2] = "<init>";
        } else {
            objArr[2] = "accept";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtLiteralStringTemplateEntry(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtLiteralStringTemplateEntry(KotlinPlaceHolderWithTextStub<KtLiteralStringTemplateEntry> kotlinPlaceHolderWithTextStub) {
        super(kotlinPlaceHolderWithTextStub, KtStubElementTypes.LITERAL_STRING_TEMPLATE_ENTRY);
        if (kotlinPlaceHolderWithTextStub == null) {
            $$$reportNull$$$0(1);
        }
    }

    @Override // org.jetbrains.kotlin.psi.KtElementImplStub, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> ktVisitor, D d) {
        if (ktVisitor == null) {
            $$$reportNull$$$0(2);
        }
        return ktVisitor.visitLiteralStringTemplateEntry(this, d);
    }

    @Override // org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String getText() {
        KotlinPlaceHolderWithTextStub kotlinPlaceHolderWithTextStub = (KotlinPlaceHolderWithTextStub) getStub();
        if (kotlinPlaceHolderWithTextStub != null) {
            return kotlinPlaceHolderWithTextStub.text();
        }
        return super.getText();
    }
}
