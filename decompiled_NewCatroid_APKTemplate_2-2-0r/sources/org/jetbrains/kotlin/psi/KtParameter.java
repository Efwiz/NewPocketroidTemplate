package org.jetbrains.kotlin.psi;

import java.util.Collections;
import java.util.List;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentation;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentationProviders;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.search.LocalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.search.SearchScope;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.stubs.KotlinParameterStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;
import org.jetbrains.kotlin.psi.typeRefHelpers.TypeRefHelpersKt;

/* JADX INFO: loaded from: classes6.dex */
public class KtParameter extends KtNamedDeclarationStub<KotlinParameterStub> implements KtCallableDeclaration, KtValVarKeywordOwner {
    public static final TokenSet VAL_VAR_TOKEN_SET = TokenSet.create(KtTokens.VAL_KEYWORD, KtTokens.VAR_KEYWORD);

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
                objArr[0] = "org/jetbrains/kotlin/psi/KtParameter";
                break;
            default:
                objArr[0] = "node";
                break;
        }
        if (i == 3) {
            objArr[1] = "getValueParameters";
        } else if (i == 4) {
            objArr[1] = "getContextReceivers";
        } else if (i == 5) {
            objArr[1] = "getTypeConstraints";
        } else if (i == 6) {
            objArr[1] = "getTypeParameters";
        } else if (i != 7) {
            objArr[1] = "org/jetbrains/kotlin/psi/KtParameter";
        } else {
            objArr[1] = "getUseScope";
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

    @Override // org.jetbrains.kotlin.psi.KtNamedDeclarationStub, org.jetbrains.kotlin.psi.KtNamedDeclaration
    /* JADX INFO: renamed from: getFqName */
    public /* bridge */ /* synthetic */ FqName mo7391getFqName() {
        return super.mo7391getFqName();
    }

    @Override // org.jetbrains.kotlin.psi.KtNamedDeclarationStub, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    public /* bridge */ /* synthetic */ String getName() {
        return super.getName();
    }

    @Override // org.jetbrains.kotlin.psi.KtNamedDeclarationStub, org.jetbrains.kotlin.psi.KtNamed
    public /* bridge */ /* synthetic */ Name getNameAsName() {
        return super.getNameAsName();
    }

    @Override // org.jetbrains.kotlin.psi.KtNamedDeclarationStub, org.jetbrains.kotlin.psi.KtNamedDeclaration
    public /* bridge */ /* synthetic */ Name getNameAsSafeName() {
        return super.getNameAsSafeName();
    }

    @Override // org.jetbrains.kotlin.psi.KtNamedDeclarationStub, org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner
    /* JADX INFO: renamed from: getNameIdentifier */
    public /* bridge */ /* synthetic */ PsiElement mo7392getNameIdentifier() {
        return super.mo7392getNameIdentifier();
    }

    @Override // org.jetbrains.kotlin.psi.KtNamedDeclarationStub, org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public /* bridge */ /* synthetic */ int getTextOffset() {
        return super.getTextOffset();
    }

    @Override // org.jetbrains.kotlin.psi.KtNamedDeclarationStub, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public /* bridge */ /* synthetic */ PsiElement setName(String str) throws IncorrectOperationException {
        return super.setName(str);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtParameter(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtParameter(KotlinParameterStub kotlinParameterStub) {
        super(kotlinParameterStub, KtStubElementTypes.VALUE_PARAMETER);
        if (kotlinParameterStub == null) {
            $$$reportNull$$$0(1);
        }
    }

    @Override // org.jetbrains.kotlin.psi.KtElementImplStub, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> ktVisitor, D d) {
        if (ktVisitor == null) {
            $$$reportNull$$$0(2);
        }
        return ktVisitor.visitParameter(this, d);
    }

    @Override // org.jetbrains.kotlin.psi.KtCallableDeclaration
    /* JADX INFO: renamed from: getTypeReference */
    public KtTypeReference mo7396getTypeReference() {
        return (KtTypeReference) getStubOrPsiChild(KtStubElementTypes.TYPE_REFERENCE);
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

    public PsiElement getEqualsToken() {
        return findChildByType(KtTokens.EQ);
    }

    public boolean hasDefaultValue() {
        KotlinParameterStub kotlinParameterStub = (KotlinParameterStub) getStub();
        if (kotlinParameterStub != null) {
            return kotlinParameterStub.hasDefaultValue();
        }
        return getDefaultValue() != null;
    }

    public KtExpression getDefaultValue() {
        PsiElement equalsToken;
        KotlinParameterStub kotlinParameterStub = (KotlinParameterStub) getStub();
        if ((kotlinParameterStub == null || (kotlinParameterStub.hasDefaultValue() && !getContainingKtFile().getIsCompiled())) && (equalsToken = getEqualsToken()) != null) {
            return (KtExpression) PsiTreeUtil.getNextSiblingOfType(equalsToken, KtExpression.class);
        }
        return null;
    }

    public boolean isMutable() {
        KotlinParameterStub kotlinParameterStub = (KotlinParameterStub) getStub();
        if (kotlinParameterStub != null) {
            return kotlinParameterStub.isMutable();
        }
        return findChildByType(KtTokens.VAR_KEYWORD) != null;
    }

    public boolean isVarArg() {
        KtModifierList modifierList = getModifierList();
        return modifierList != null && modifierList.hasModifier(KtTokens.VARARG_KEYWORD);
    }

    public boolean hasValOrVar() {
        KotlinParameterStub kotlinParameterStub = (KotlinParameterStub) getStub();
        if (kotlinParameterStub != null) {
            return kotlinParameterStub.hasValOrVar();
        }
        return getValOrVarKeyword() != null;
    }

    @Override // org.jetbrains.kotlin.psi.KtValVarKeywordOwner
    public PsiElement getValOrVarKeyword() {
        KotlinParameterStub kotlinParameterStub = (KotlinParameterStub) getStub();
        if (kotlinParameterStub == null || kotlinParameterStub.hasValOrVar()) {
            return findChildByType(VAL_VAR_TOKEN_SET);
        }
        return null;
    }

    public KtDestructuringDeclaration getDestructuringDeclaration() {
        if (getStub() != 0) {
            return null;
        }
        return (KtDestructuringDeclaration) findChildByType(KtNodeTypes.DESTRUCTURING_DECLARATION);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem
    public ItemPresentation getPresentation() {
        return ItemPresentationProviders.getItemPresentation(this);
    }

    public boolean isLoopParameter() {
        return getLightParent() instanceof KtForExpression;
    }

    private <T extends PsiElement> boolean checkParentOfParentType(Class<T> cls) {
        PsiElement parent = getLightParent();
        if (parent == null) {
            return false;
        }
        return cls.isInstance(parent.getLightParent());
    }

    public boolean isCatchParameter() {
        return checkParentOfParentType(KtCatchClause.class);
    }

    public boolean isLambdaParameter() {
        return checkParentOfParentType(KtFunctionLiteral.class);
    }

    public boolean isFunctionTypeParameter() {
        return checkParentOfParentType(KtFunctionType.class);
    }

    @Override // org.jetbrains.kotlin.psi.KtCallableDeclaration, org.jetbrains.kotlin.psi.KtDeclarationWithBody
    public List<KtParameter> getValueParameters() {
        List<KtParameter> listEmptyList = Collections.emptyList();
        if (listEmptyList == null) {
            $$$reportNull$$$0(3);
        }
        return listEmptyList;
    }

    @Override // org.jetbrains.kotlin.psi.KtCallableDeclaration
    public List<KtContextReceiver> getContextReceivers() {
        List<KtContextReceiver> listEmptyList = Collections.emptyList();
        if (listEmptyList == null) {
            $$$reportNull$$$0(4);
        }
        return listEmptyList;
    }

    @Override // org.jetbrains.kotlin.psi.KtTypeParameterListOwner
    public List<KtTypeConstraint> getTypeConstraints() {
        List<KtTypeConstraint> listEmptyList = Collections.emptyList();
        if (listEmptyList == null) {
            $$$reportNull$$$0(5);
        }
        return listEmptyList;
    }

    @Override // org.jetbrains.kotlin.psi.KtTypeParameterListOwner
    public List<KtTypeParameter> getTypeParameters() {
        List<KtTypeParameter> listEmptyList = Collections.emptyList();
        if (listEmptyList == null) {
            $$$reportNull$$$0(6);
        }
        return listEmptyList;
    }

    public KtDeclarationWithBody getOwnerFunction() {
        PsiElement parentByStub = getParentByStub();
        if (parentByStub instanceof KtParameterList) {
            return ((KtParameterList) parentByStub).getOwnerFunction();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtNamedDeclarationStub, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public SearchScope getUseScope() {
        KtExpression ownerFunction = getOwnerFunction();
        if (ownerFunction instanceof KtPrimaryConstructor) {
            if (hasValOrVar()) {
                SearchScope useScope = super.getUseScope();
                if (useScope == null) {
                    $$$reportNull$$$0(7);
                }
                return useScope;
            }
            ownerFunction = ((KtPrimaryConstructor) ownerFunction).getContainingClassOrObject();
        }
        if (ownerFunction == null) {
            ownerFunction = (KtExpression) PsiTreeUtil.getParentOfType(this, KtExpression.class);
        }
        if (ownerFunction == null) {
            ownerFunction = this;
        }
        return new LocalSearchScope(ownerFunction);
    }
}
