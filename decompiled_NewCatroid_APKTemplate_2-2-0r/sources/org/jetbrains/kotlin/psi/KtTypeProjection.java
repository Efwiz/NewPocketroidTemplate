package org.jetbrains.kotlin.psi;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.stubs.KotlinTypeProjectionStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: loaded from: classes6.dex */
public class KtTypeProjection extends KtModifierListOwnerStub<KotlinTypeProjectionStub> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "stub";
        } else if (i == 2 || i == 3) {
            objArr[0] = "org/jetbrains/kotlin/psi/KtTypeProjection";
        } else if (i != 4) {
            objArr[0] = "node";
        } else {
            objArr[0] = "visitor";
        }
        if (i == 2 || i == 3) {
            objArr[1] = "getProjectionKind";
        } else {
            objArr[1] = "org/jetbrains/kotlin/psi/KtTypeProjection";
        }
        if (i != 2 && i != 3) {
            if (i != 4) {
                objArr[2] = "<init>";
            } else {
                objArr[2] = "accept";
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtTypeProjection(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtTypeProjection(KotlinTypeProjectionStub kotlinTypeProjectionStub) {
        super(kotlinTypeProjectionStub, KtStubElementTypes.TYPE_PROJECTION);
        if (kotlinTypeProjectionStub == null) {
            $$$reportNull$$$0(1);
        }
    }

    public KtProjectionKind getProjectionKind() {
        KotlinTypeProjectionStub kotlinTypeProjectionStub = (KotlinTypeProjectionStub) getStub();
        if (kotlinTypeProjectionStub != null) {
            KtProjectionKind projectionKind = kotlinTypeProjectionStub.getProjectionKind();
            if (projectionKind == null) {
                $$$reportNull$$$0(2);
            }
            return projectionKind;
        }
        PsiElement projectionToken = getProjectionToken();
        IElementType elementType = projectionToken != null ? projectionToken.getNode().getElementType() : null;
        for (KtProjectionKind ktProjectionKind : KtProjectionKind.values()) {
            if (ktProjectionKind.getToken() == elementType) {
                if (ktProjectionKind == null) {
                    $$$reportNull$$$0(3);
                }
                return ktProjectionKind;
            }
        }
        throw new IllegalStateException(projectionToken.getText());
    }

    @Override // org.jetbrains.kotlin.psi.KtElementImplStub, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> ktVisitor, D d) {
        if (ktVisitor == null) {
            $$$reportNull$$$0(4);
        }
        return ktVisitor.visitTypeProjection(this, d);
    }

    public KtTypeReference getTypeReference() {
        return (KtTypeReference) getStubOrPsiChild(KtStubElementTypes.TYPE_REFERENCE);
    }

    public PsiElement getProjectionToken() {
        PsiElement psiElementFindChildByType = findChildByType(KtTokens.MUL);
        if (psiElementFindChildByType != null) {
            return psiElementFindChildByType;
        }
        KtModifierList modifierList = getModifierList();
        if (modifierList == null) {
            return null;
        }
        PsiElement modifier = modifierList.getModifier(KtTokens.IN_KEYWORD);
        if (modifier != null) {
            return modifier;
        }
        PsiElement modifier2 = modifierList.getModifier(KtTokens.OUT_KEYWORD);
        if (modifier2 != null) {
            return modifier2;
        }
        return null;
    }
}
