package org.jetbrains.kotlin.psi;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.stubs.KotlinPlaceHolderStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: loaded from: classes6.dex */
public class KtValueArgumentName extends KtElementImplStub<KotlinPlaceHolderStub<KtValueArgumentName>> implements ValueArgumentName {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "stub";
        } else if (i == 2 || i == 3) {
            objArr[0] = "org/jetbrains/kotlin/psi/KtValueArgumentName";
        } else {
            objArr[0] = "node";
        }
        if (i == 2) {
            objArr[1] = "getReferenceExpression";
        } else if (i != 3) {
            objArr[1] = "org/jetbrains/kotlin/psi/KtValueArgumentName";
        } else {
            objArr[1] = "getAsName";
        }
        if (i != 2 && i != 3) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtValueArgumentName(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtValueArgumentName(KotlinPlaceHolderStub<KtValueArgumentName> kotlinPlaceHolderStub) {
        super(kotlinPlaceHolderStub, KtStubElementTypes.VALUE_ARGUMENT_NAME);
        if (kotlinPlaceHolderStub == null) {
            $$$reportNull$$$0(1);
        }
    }

    @Override // org.jetbrains.kotlin.psi.ValueArgumentName
    public KtSimpleNameExpression getReferenceExpression() {
        KtSimpleNameExpression ktSimpleNameExpression = (KtSimpleNameExpression) getStubOrPsiChild(KtStubElementTypes.REFERENCE_EXPRESSION);
        if (ktSimpleNameExpression == null) {
            $$$reportNull$$$0(2);
        }
        return ktSimpleNameExpression;
    }

    @Override // org.jetbrains.kotlin.psi.ValueArgumentName
    public Name getAsName() {
        Name referencedNameAsName = getReferenceExpression().getReferencedNameAsName();
        if (referencedNameAsName == null) {
            $$$reportNull$$$0(3);
        }
        return referencedNameAsName;
    }
}
