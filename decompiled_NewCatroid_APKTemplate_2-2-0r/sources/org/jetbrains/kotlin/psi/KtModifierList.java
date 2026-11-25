package org.jetbrains.kotlin.psi;

import java.util.List;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.lexer.KtModifierKeywordToken;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;
import org.jetbrains.kotlin.psi.stubs.KotlinModifierListStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: loaded from: classes6.dex */
public abstract class KtModifierList extends KtElementImplStub<KotlinModifierListStub> implements KtAnnotationsContainer {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 4 || i == 5) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 4 || i == 5) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "nodeType";
                break;
            case 2:
                objArr[0] = "node";
                break;
            case 3:
                objArr[0] = "visitor";
                break;
            case 4:
            case 5:
                objArr[0] = "org/jetbrains/kotlin/psi/KtModifierList";
                break;
            case 6:
            case 7:
                objArr[0] = "tokenType";
                break;
            case 8:
                objArr[0] = "tokenTypes";
                break;
            case 9:
                objArr[0] = "child";
                break;
            default:
                objArr[0] = "stub";
                break;
        }
        if (i == 4) {
            objArr[1] = "getAnnotations";
        } else if (i != 5) {
            objArr[1] = "org/jetbrains/kotlin/psi/KtModifierList";
        } else {
            objArr[1] = "getAnnotationEntries";
        }
        switch (i) {
            case 3:
                objArr[2] = "accept";
                break;
            case 4:
            case 5:
                break;
            case 6:
                objArr[2] = "hasModifier";
                break;
            case 7:
            case 8:
                objArr[2] = "getModifier";
                break;
            case 9:
                objArr[2] = "deleteChildInternal";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 4 && i != 5) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtModifierList(KotlinModifierListStub kotlinModifierListStub, IStubElementType iStubElementType) {
        super(kotlinModifierListStub, iStubElementType);
        if (kotlinModifierListStub == null) {
            $$$reportNull$$$0(0);
        }
        if (iStubElementType == null) {
            $$$reportNull$$$0(1);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtModifierList(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(2);
        }
    }

    @Override // org.jetbrains.kotlin.psi.KtElementImplStub, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> ktVisitor, D d) {
        if (ktVisitor == null) {
            $$$reportNull$$$0(3);
        }
        return ktVisitor.visitModifierList(this, d);
    }

    public List<KtAnnotation> getAnnotations() {
        List stubOrPsiChildrenAsList = getStubOrPsiChildrenAsList(KtStubElementTypes.ANNOTATION);
        if (stubOrPsiChildrenAsList == null) {
            $$$reportNull$$$0(4);
        }
        return stubOrPsiChildrenAsList;
    }

    public List<KtAnnotationEntry> getAnnotationEntries() {
        List<KtAnnotationEntry> listCollectAnnotationEntriesFromStubOrPsi = KtPsiUtilKt.collectAnnotationEntriesFromStubOrPsi(this);
        if (listCollectAnnotationEntriesFromStubOrPsi == null) {
            $$$reportNull$$$0(5);
        }
        return listCollectAnnotationEntriesFromStubOrPsi;
    }

    public boolean hasModifier(KtModifierKeywordToken ktModifierKeywordToken) {
        if (ktModifierKeywordToken == null) {
            $$$reportNull$$$0(6);
        }
        KotlinModifierListStub kotlinModifierListStub = (KotlinModifierListStub) getStub();
        if (kotlinModifierListStub != null) {
            return kotlinModifierListStub.hasModifier(ktModifierKeywordToken);
        }
        return getModifier(ktModifierKeywordToken) != null;
    }

    public PsiElement getModifier(KtModifierKeywordToken ktModifierKeywordToken) {
        if (ktModifierKeywordToken == null) {
            $$$reportNull$$$0(7);
        }
        return findChildByType(ktModifierKeywordToken);
    }

    public PsiElement getModifier(TokenSet tokenSet) {
        if (tokenSet == null) {
            $$$reportNull$$$0(8);
        }
        return findChildByType(tokenSet);
    }

    public PsiElement getOwner() {
        return getParentByStub();
    }

    @Override // org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement
    public void deleteChildInternal(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(9);
        }
        super.deleteChildInternal(aSTNode);
        if (getFirstChild() == null) {
            delete();
        }
    }
}
