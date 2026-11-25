package org.jetbrains.kotlin.psi;

import java.util.Collections;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.search.LocalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.search.SearchScope;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.typeRefHelpers.TypeRefHelpersKt;

/* JADX INFO: loaded from: classes6.dex */
public class KtDestructuringDeclarationEntry extends KtNamedDeclarationNotStubbed implements KtVariableDeclaration {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final TokenSet VAL_VAR_KEYWORDS = TokenSet.create(KtTokens.VAL_KEYWORD, KtTokens.VAR_KEYWORD);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2 || i == 3 || i == 4 || i == 6 || i == 7) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2 || i == 3 || i == 4 || i == 6 || i == 7) ? 2 : 3];
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 6:
            case 7:
                objArr[0] = "org/jetbrains/kotlin/psi/KtDestructuringDeclarationEntry";
                break;
            case 5:
                objArr[0] = "visitor";
                break;
            default:
                objArr[0] = "node";
                break;
        }
        if (i == 1) {
            objArr[1] = "getValueParameters";
        } else if (i == 2) {
            objArr[1] = "getContextReceivers";
        } else if (i == 3) {
            objArr[1] = "getTypeConstraints";
        } else if (i == 4) {
            objArr[1] = "getTypeParameters";
        } else if (i == 6) {
            objArr[1] = "getParentNode";
        } else if (i != 7) {
            objArr[1] = "org/jetbrains/kotlin/psi/KtDestructuringDeclarationEntry";
        } else {
            objArr[1] = "getUseScope";
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 6:
            case 7:
                break;
            case 5:
                objArr[2] = "accept";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2 && i != 3 && i != 4 && i != 6 && i != 7) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.psi.KtNamedDeclaration
    /* JADX INFO: renamed from: getFqName */
    public FqName mo7391getFqName() {
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtDeclarationWithInitializer
    public KtExpression getInitializer() {
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtCallableDeclaration
    /* JADX INFO: renamed from: getReceiverTypeReference */
    public KtTypeReference mo7393getReceiverTypeReference() {
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtTypeParameterListOwner
    /* JADX INFO: renamed from: getTypeConstraintList */
    public KtTypeConstraintList mo7394getTypeConstraintList() {
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtTypeParameterListOwner
    /* JADX INFO: renamed from: getTypeParameterList */
    public KtTypeParameterList mo7395getTypeParameterList() {
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtCallableDeclaration
    public KtParameterList getValueParameterList() {
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtDeclarationWithInitializer
    public boolean hasInitializer() {
        return false;
    }

    @Override // org.jetbrains.kotlin.psi.KtNamedDeclarationNotStubbed, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    /* JADX INFO: renamed from: getName */
    public /* bridge */ /* synthetic */ String getMyName() {
        return super.getMyName();
    }

    @Override // org.jetbrains.kotlin.psi.KtNamedDeclarationNotStubbed, org.jetbrains.kotlin.psi.KtNamed
    public /* bridge */ /* synthetic */ Name getNameAsName() {
        return super.getNameAsName();
    }

    @Override // org.jetbrains.kotlin.psi.KtNamedDeclarationNotStubbed, org.jetbrains.kotlin.psi.KtNamedDeclaration
    public /* bridge */ /* synthetic */ Name getNameAsSafeName() {
        return super.getNameAsSafeName();
    }

    @Override // org.jetbrains.kotlin.psi.KtNamedDeclarationNotStubbed, org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner
    /* JADX INFO: renamed from: getNameIdentifier */
    public /* bridge */ /* synthetic */ PsiElement mo7392getNameIdentifier() {
        return super.mo7392getNameIdentifier();
    }

    @Override // org.jetbrains.kotlin.psi.KtNamedDeclarationNotStubbed, org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public /* bridge */ /* synthetic */ int getTextOffset() {
        return super.getTextOffset();
    }

    @Override // org.jetbrains.kotlin.psi.KtNamedDeclarationNotStubbed, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public /* bridge */ /* synthetic */ PsiElement setName(String str) throws IncorrectOperationException {
        return super.setName(str);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtDestructuringDeclarationEntry(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.psi.KtCallableDeclaration
    /* JADX INFO: renamed from: getTypeReference */
    public KtTypeReference mo7396getTypeReference() {
        return TypeRefHelpersKt.getTypeReference(this);
    }

    @Override // org.jetbrains.kotlin.psi.KtCallableDeclaration
    /* JADX INFO: renamed from: setTypeReference */
    public KtTypeReference mo7397setTypeReference(KtTypeReference ktTypeReference) {
        return TypeRefHelpersKt.setTypeReference(this, mo7392getNameIdentifier(), ktTypeReference);
    }

    @Override // org.jetbrains.kotlin.psi.KtCallableDeclaration
    public PsiElement getColon() {
        return findChildByType(KtTokens.COLON);
    }

    @Override // org.jetbrains.kotlin.psi.KtCallableDeclaration, org.jetbrains.kotlin.psi.KtDeclarationWithBody
    public List<KtParameter> getValueParameters() {
        List<KtParameter> listEmptyList = Collections.emptyList();
        if (listEmptyList == null) {
            $$$reportNull$$$0(1);
        }
        return listEmptyList;
    }

    @Override // org.jetbrains.kotlin.psi.KtCallableDeclaration
    public List<KtContextReceiver> getContextReceivers() {
        List<KtContextReceiver> listEmptyList = Collections.emptyList();
        if (listEmptyList == null) {
            $$$reportNull$$$0(2);
        }
        return listEmptyList;
    }

    @Override // org.jetbrains.kotlin.psi.KtTypeParameterListOwner
    public List<KtTypeConstraint> getTypeConstraints() {
        List<KtTypeConstraint> listEmptyList = Collections.emptyList();
        if (listEmptyList == null) {
            $$$reportNull$$$0(3);
        }
        return listEmptyList;
    }

    @Override // org.jetbrains.kotlin.psi.KtTypeParameterListOwner
    public List<KtTypeParameter> getTypeParameters() {
        List<KtTypeParameter> listEmptyList = Collections.emptyList();
        if (listEmptyList == null) {
            $$$reportNull$$$0(4);
        }
        return listEmptyList;
    }

    @Override // org.jetbrains.kotlin.psi.KtExpressionImpl, org.jetbrains.kotlin.psi.KtElementImpl, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> ktVisitor, D d) {
        if (ktVisitor == null) {
            $$$reportNull$$$0(5);
        }
        return ktVisitor.visitDestructuringDeclarationEntry(this, d);
    }

    @Override // org.jetbrains.kotlin.psi.KtVariableDeclaration
    public boolean isVar() {
        return getParentNode().findChildByType(KtTokens.VAR_KEYWORD) != null;
    }

    private ASTNode getParentNode() {
        ASTNode treeParent = getNode().getTreeParent();
        if (treeParent == null) {
            $$$reportNull$$$0(6);
        }
        return treeParent;
    }

    @Override // org.jetbrains.kotlin.psi.KtValVarKeywordOwner
    public PsiElement getValOrVarKeyword() {
        ASTNode aSTNodeFindChildByType = getParentNode().findChildByType(VAL_VAR_KEYWORDS);
        if (aSTNodeFindChildByType == null) {
            return null;
        }
        return aSTNodeFindChildByType.getPsi();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public SearchScope getUseScope() {
        KtElement enclosingElementForLocalDeclaration = KtPsiUtil.getEnclosingElementForLocalDeclaration(this, false);
        if (enclosingElementForLocalDeclaration instanceof KtParameter) {
            enclosingElementForLocalDeclaration = KtPsiUtil.getEnclosingElementForLocalDeclaration((KtParameter) enclosingElementForLocalDeclaration, false);
        }
        if (enclosingElementForLocalDeclaration != null) {
            return new LocalSearchScope(enclosingElementForLocalDeclaration);
        }
        SearchScope useScope = super.getUseScope();
        if (useScope == null) {
            $$$reportNull$$$0(7);
        }
        return useScope;
    }
}
