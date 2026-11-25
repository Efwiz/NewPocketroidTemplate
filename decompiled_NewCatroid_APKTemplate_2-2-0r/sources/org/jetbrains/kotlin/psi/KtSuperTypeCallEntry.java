package org.jetbrains.kotlin.psi;

import java.util.Collections;
import java.util.List;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.psi.stubs.KotlinPlaceHolderStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: loaded from: classes6.dex */
public class KtSuperTypeCallEntry extends KtSuperTypeListEntry implements KtCallElement {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 4 || i == 5 || i == 6 || i == 7) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 4 || i == 5 || i == 6 || i == 7) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "stub";
                break;
            case 2:
                objArr[0] = "visitor";
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                objArr[0] = "org/jetbrains/kotlin/psi/KtSuperTypeCallEntry";
                break;
            default:
                objArr[0] = "node";
                break;
        }
        if (i == 3) {
            objArr[1] = "getCalleeExpression";
        } else if (i == 4) {
            objArr[1] = "getValueArguments";
        } else if (i == 5) {
            objArr[1] = "getLambdaArguments";
        } else if (i == 6 || i == 7) {
            objArr[1] = "getTypeArguments";
        } else {
            objArr[1] = "org/jetbrains/kotlin/psi/KtSuperTypeCallEntry";
        }
        switch (i) {
            case 2:
                objArr[2] = "accept";
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 4 && i != 5 && i != 6 && i != 7) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtSuperTypeCallEntry(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtSuperTypeCallEntry(KotlinPlaceHolderStub<? extends KtSuperTypeListEntry> kotlinPlaceHolderStub) {
        super(kotlinPlaceHolderStub, KtStubElementTypes.SUPER_TYPE_CALL_ENTRY);
        if (kotlinPlaceHolderStub == null) {
            $$$reportNull$$$0(1);
        }
    }

    @Override // org.jetbrains.kotlin.psi.KtSuperTypeListEntry, org.jetbrains.kotlin.psi.KtElementImplStub, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> ktVisitor, D d) {
        if (ktVisitor == null) {
            $$$reportNull$$$0(2);
        }
        return ktVisitor.visitSuperTypeCallEntry(this, d);
    }

    @Override // org.jetbrains.kotlin.psi.KtCallElement
    public KtConstructorCalleeExpression getCalleeExpression() {
        KtConstructorCalleeExpression ktConstructorCalleeExpression = (KtConstructorCalleeExpression) getRequiredStubOrPsiChild(KtStubElementTypes.CONSTRUCTOR_CALLEE);
        if (ktConstructorCalleeExpression == null) {
            $$$reportNull$$$0(3);
        }
        return ktConstructorCalleeExpression;
    }

    @Override // org.jetbrains.kotlin.psi.KtCallElement
    public KtValueArgumentList getValueArgumentList() {
        return (KtValueArgumentList) findChildByType(KtNodeTypes.VALUE_ARGUMENT_LIST);
    }

    @Override // org.jetbrains.kotlin.psi.KtCallElement
    public List<? extends ValueArgument> getValueArguments() {
        KtValueArgumentList valueArgumentList = getValueArgumentList();
        List<KtValueArgument> arguments = valueArgumentList != null ? valueArgumentList.getArguments() : Collections.emptyList();
        if (arguments == null) {
            $$$reportNull$$$0(4);
        }
        return arguments;
    }

    @Override // org.jetbrains.kotlin.psi.KtCallElement
    public List<KtLambdaArgument> getLambdaArguments() {
        List<KtLambdaArgument> listEmptyList = Collections.emptyList();
        if (listEmptyList == null) {
            $$$reportNull$$$0(5);
        }
        return listEmptyList;
    }

    @Override // org.jetbrains.kotlin.psi.KtSuperTypeListEntry
    public KtTypeReference getTypeReference() {
        return getCalleeExpression().getTypeReference();
    }

    @Override // org.jetbrains.kotlin.psi.KtCallElement
    public List<KtTypeProjection> getTypeArguments() {
        KtTypeArgumentList typeArgumentList = getTypeArgumentList();
        if (typeArgumentList == null) {
            List<KtTypeProjection> listEmptyList = Collections.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(6);
            }
            return listEmptyList;
        }
        List<KtTypeProjection> arguments = typeArgumentList.getArguments();
        if (arguments == null) {
            $$$reportNull$$$0(7);
        }
        return arguments;
    }

    @Override // org.jetbrains.kotlin.psi.KtCallElement
    public KtTypeArgumentList getTypeArgumentList() {
        KtUserType typeAsUserType = getTypeAsUserType();
        if (typeAsUserType != null) {
            return typeAsUserType.getTypeArgumentList();
        }
        return null;
    }
}
