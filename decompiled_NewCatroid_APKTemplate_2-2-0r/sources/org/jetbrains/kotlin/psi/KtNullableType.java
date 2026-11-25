package org.jetbrains.kotlin.psi;

import java.util.Collections;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.stubs.KotlinPlaceHolderStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;
import org.jetbrains.kotlin.psi.stubs.elements.KtTokenSets;

/* JADX INFO: loaded from: classes6.dex */
public class KtNullableType extends KtElementImplStub<KotlinPlaceHolderStub<KtNullableType>> implements KtTypeElement {
    /* JADX WARN: Code duplicated, block: B:21:0x0032  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 5) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 5) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "stub";
        } else if (i == 2 || i == 3) {
            objArr[0] = "org/jetbrains/kotlin/psi/KtNullableType";
        } else if (i == 4) {
            objArr[0] = "visitor";
        } else if (i != 5) {
            objArr[0] = "node";
        } else {
            objArr[0] = "org/jetbrains/kotlin/psi/KtNullableType";
        }
        if (i == 2) {
            objArr[1] = "getQuestionMarkNode";
        } else if (i == 3) {
            objArr[1] = "getTypeArgumentsAsTypes";
        } else if (i != 5) {
            objArr[1] = "org/jetbrains/kotlin/psi/KtNullableType";
        } else {
            objArr[1] = "getAnnotationEntries";
        }
        if (i != 2 && i != 3) {
            if (i == 4) {
                objArr[2] = "accept";
            } else if (i != 5) {
                objArr[2] = "<init>";
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 5) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtNullableType(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtNullableType(KotlinPlaceHolderStub<KtNullableType> kotlinPlaceHolderStub) {
        super(kotlinPlaceHolderStub, KtStubElementTypes.NULLABLE_TYPE);
        if (kotlinPlaceHolderStub == null) {
            $$$reportNull$$$0(1);
        }
    }

    public ASTNode getQuestionMarkNode() {
        ASTNode aSTNodeFindChildByType = getNode().findChildByType(KtTokens.QUEST);
        if (aSTNodeFindChildByType == null) {
            $$$reportNull$$$0(2);
        }
        return aSTNodeFindChildByType;
    }

    @Override // org.jetbrains.kotlin.psi.KtTypeElement
    public List<KtTypeReference> getTypeArgumentsAsTypes() {
        KtTypeElement innerType = getInnerType();
        List<KtTypeReference> listEmptyList = innerType == null ? Collections.emptyList() : innerType.getTypeArgumentsAsTypes();
        if (listEmptyList == null) {
            $$$reportNull$$$0(3);
        }
        return listEmptyList;
    }

    @Override // org.jetbrains.kotlin.psi.KtElementImplStub, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> ktVisitor, D d) {
        if (ktVisitor == null) {
            $$$reportNull$$$0(4);
        }
        return ktVisitor.visitNullableType(this, d);
    }

    @IfNotParsed
    public KtTypeElement getInnerType() {
        return (KtTypeElement) KtStubbedPsiUtil.getStubOrPsiChild(this, KtTokenSets.TYPE_ELEMENT_TYPES, KtTypeElement.ARRAY_FACTORY);
    }

    public KtModifierList getModifierList() {
        return (KtModifierList) getStubOrPsiChild(KtStubElementTypes.MODIFIER_LIST);
    }

    public List<KtAnnotationEntry> getAnnotationEntries() {
        KtModifierList modifierList = getModifierList();
        List<KtAnnotationEntry> annotationEntries = modifierList != null ? modifierList.getAnnotationEntries() : Collections.emptyList();
        if (annotationEntries == null) {
            $$$reportNull$$$0(5);
        }
        return annotationEntries;
    }
}
