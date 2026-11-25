package org.jetbrains.kotlin.psi;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType;
import org.jetbrains.kotlin.psi.stubs.KotlinPlaceHolderWithTextStub;

/* JADX INFO: loaded from: classes6.dex */
public abstract class KtStringTemplateEntry extends KtElementImplStub<KotlinPlaceHolderWithTextStub<? extends KtStringTemplateEntry>> {
    public static final KtStringTemplateEntry[] EMPTY_ARRAY = new KtStringTemplateEntry[0];

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "stub";
        } else if (i != 2) {
            objArr[0] = "node";
        } else {
            objArr[0] = "elementType";
        }
        objArr[1] = "org/jetbrains/kotlin/psi/KtStringTemplateEntry";
        objArr[2] = "<init>";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtStringTemplateEntry(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtStringTemplateEntry(KotlinPlaceHolderWithTextStub<? extends KtStringTemplateEntry> kotlinPlaceHolderWithTextStub, IStubElementType iStubElementType) {
        super(kotlinPlaceHolderWithTextStub, iStubElementType);
        if (kotlinPlaceHolderWithTextStub == null) {
            $$$reportNull$$$0(1);
        }
        if (iStubElementType == null) {
            $$$reportNull$$$0(2);
        }
    }

    public KtExpression getExpression() {
        return (KtExpression) findChildByClass(KtExpression.class);
    }
}
