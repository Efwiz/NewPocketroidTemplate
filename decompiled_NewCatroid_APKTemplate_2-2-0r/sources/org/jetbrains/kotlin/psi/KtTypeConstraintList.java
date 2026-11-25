package org.jetbrains.kotlin.psi;

import java.util.List;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.psi.stubs.KotlinPlaceHolderStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: loaded from: classes6.dex */
public class KtTypeConstraintList extends KtElementImplStub<KotlinPlaceHolderStub<KtTypeConstraintList>> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 3 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 3 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "stub";
        } else if (i == 2) {
            objArr[0] = "visitor";
        } else if (i != 3) {
            objArr[0] = "node";
        } else {
            objArr[0] = "org/jetbrains/kotlin/psi/KtTypeConstraintList";
        }
        if (i != 3) {
            objArr[1] = "org/jetbrains/kotlin/psi/KtTypeConstraintList";
        } else {
            objArr[1] = "getConstraints";
        }
        if (i == 2) {
            objArr[2] = "accept";
        } else if (i != 3) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 3) {
            throw new IllegalStateException(str2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtTypeConstraintList(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtTypeConstraintList(KotlinPlaceHolderStub<KtTypeConstraintList> kotlinPlaceHolderStub) {
        super(kotlinPlaceHolderStub, KtStubElementTypes.TYPE_CONSTRAINT_LIST);
        if (kotlinPlaceHolderStub == null) {
            $$$reportNull$$$0(1);
        }
    }

    @Override // org.jetbrains.kotlin.psi.KtElementImplStub, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> ktVisitor, D d) {
        if (ktVisitor == null) {
            $$$reportNull$$$0(2);
        }
        return ktVisitor.visitTypeConstraintList(this, d);
    }

    public List<KtTypeConstraint> getConstraints() {
        List stubOrPsiChildrenAsList = getStubOrPsiChildrenAsList(KtStubElementTypes.TYPE_CONSTRAINT);
        if (stubOrPsiChildrenAsList == null) {
            $$$reportNull$$$0(3);
        }
        return stubOrPsiChildrenAsList;
    }
}
