package org.jetbrains.kotlin.psi;

import java.util.Collections;
import java.util.List;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;

/* JADX INFO: loaded from: classes6.dex */
@Deprecated
abstract class KtTypeParameterListOwnerNotStubbed extends KtNamedDeclarationNotStubbed implements KtTypeParameterListOwner {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2 || i == 3 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2 || i == 3 || i == 4) ? 2 : 3];
        if (i == 1 || i == 2 || i == 3 || i == 4) {
            objArr[0] = "org/jetbrains/kotlin/psi/KtTypeParameterListOwnerNotStubbed";
        } else {
            objArr[0] = "node";
        }
        if (i == 1 || i == 2) {
            objArr[1] = "getTypeConstraints";
        } else if (i == 3 || i == 4) {
            objArr[1] = "getTypeParameters";
        } else {
            objArr[1] = "org/jetbrains/kotlin/psi/KtTypeParameterListOwnerNotStubbed";
        }
        if (i != 1 && i != 2 && i != 3 && i != 4) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2 && i != 3 && i != 4) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtTypeParameterListOwnerNotStubbed(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX INFO: renamed from: getTypeParameterList */
    public KtTypeParameterList mo7395getTypeParameterList() {
        return (KtTypeParameterList) findChildByType(KtNodeTypes.TYPE_PARAMETER_LIST);
    }

    /* JADX INFO: renamed from: getTypeConstraintList */
    public KtTypeConstraintList mo7394getTypeConstraintList() {
        return (KtTypeConstraintList) findChildByType(KtNodeTypes.TYPE_CONSTRAINT_LIST);
    }

    public List<KtTypeConstraint> getTypeConstraints() {
        KtTypeConstraintList ktTypeConstraintListMo7394getTypeConstraintList = mo7394getTypeConstraintList();
        if (ktTypeConstraintListMo7394getTypeConstraintList == null) {
            List<KtTypeConstraint> listEmptyList = Collections.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(1);
            }
            return listEmptyList;
        }
        List<KtTypeConstraint> constraints = ktTypeConstraintListMo7394getTypeConstraintList.getConstraints();
        if (constraints == null) {
            $$$reportNull$$$0(2);
        }
        return constraints;
    }

    public List<KtTypeParameter> getTypeParameters() {
        KtTypeParameterList ktTypeParameterListMo7395getTypeParameterList = mo7395getTypeParameterList();
        if (ktTypeParameterListMo7395getTypeParameterList == null) {
            List<KtTypeParameter> listEmptyList = Collections.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(3);
            }
            return listEmptyList;
        }
        List<KtTypeParameter> parameters = ktTypeParameterListMo7395getTypeParameterList.getParameters();
        if (parameters == null) {
            $$$reportNull$$$0(4);
        }
        return parameters;
    }
}
