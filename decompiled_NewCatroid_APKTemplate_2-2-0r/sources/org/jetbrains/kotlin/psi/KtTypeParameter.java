package org.jetbrains.kotlin.psi;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.search.LocalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.search.SearchScope;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.stubs.KotlinTypeParameterStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;
import org.jetbrains.kotlin.types.Variance;

/* JADX INFO: loaded from: classes6.dex */
public class KtTypeParameter extends KtNamedDeclarationStub<KotlinTypeParameterStub> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
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
            case 8:
            case 9:
                objArr[0] = "org/jetbrains/kotlin/psi/KtTypeParameter";
                break;
            default:
                objArr[0] = "node";
                break;
        }
        switch (i) {
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                objArr[1] = "getVariance";
                break;
            default:
                objArr[1] = "org/jetbrains/kotlin/psi/KtTypeParameter";
                break;
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
            case 8:
            case 9:
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                throw new IllegalStateException(str2);
            default:
                throw new IllegalArgumentException(str2);
        }
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
    public KtTypeParameter(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtTypeParameter(KotlinTypeParameterStub kotlinTypeParameterStub) {
        super(kotlinTypeParameterStub, KtStubElementTypes.TYPE_PARAMETER);
        if (kotlinTypeParameterStub == null) {
            $$$reportNull$$$0(1);
        }
    }

    @Override // org.jetbrains.kotlin.psi.KtElementImplStub, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> ktVisitor, D d) {
        if (ktVisitor == null) {
            $$$reportNull$$$0(2);
        }
        return ktVisitor.visitTypeParameter(this, d);
    }

    public Variance getVariance() {
        KotlinTypeParameterStub kotlinTypeParameterStub = (KotlinTypeParameterStub) getStub();
        if (kotlinTypeParameterStub != null) {
            if (kotlinTypeParameterStub.isOutVariance()) {
                Variance variance = Variance.OUT_VARIANCE;
                if (variance == null) {
                    $$$reportNull$$$0(3);
                }
                return variance;
            }
            if (kotlinTypeParameterStub.isInVariance()) {
                Variance variance2 = Variance.IN_VARIANCE;
                if (variance2 == null) {
                    $$$reportNull$$$0(4);
                }
                return variance2;
            }
            Variance variance3 = Variance.INVARIANT;
            if (variance3 == null) {
                $$$reportNull$$$0(5);
            }
            return variance3;
        }
        KtModifierList modifierList = getModifierList();
        if (modifierList == null) {
            Variance variance4 = Variance.INVARIANT;
            if (variance4 == null) {
                $$$reportNull$$$0(6);
            }
            return variance4;
        }
        if (modifierList.hasModifier(KtTokens.OUT_KEYWORD)) {
            Variance variance5 = Variance.OUT_VARIANCE;
            if (variance5 == null) {
                $$$reportNull$$$0(7);
            }
            return variance5;
        }
        if (modifierList.hasModifier(KtTokens.IN_KEYWORD)) {
            Variance variance6 = Variance.IN_VARIANCE;
            if (variance6 == null) {
                $$$reportNull$$$0(8);
            }
            return variance6;
        }
        Variance variance7 = Variance.INVARIANT;
        if (variance7 == null) {
            $$$reportNull$$$0(9);
        }
        return variance7;
    }

    public KtTypeReference setExtendsBound(KtTypeReference ktTypeReference) {
        KtTypeReference extendsBound = getExtendsBound();
        if (extendsBound == null) {
            if (ktTypeReference != null) {
                return (KtTypeReference) addAfter(ktTypeReference, addAfter(new KtPsiFactory(getProject()).createColon(), mo7392getNameIdentifier()));
            }
            return null;
        }
        if (ktTypeReference == null) {
            PsiElement psiElementFindChildByType = findChildByType(KtTokens.COLON);
            if (psiElementFindChildByType != null) {
                psiElementFindChildByType.delete();
            }
            extendsBound.delete();
            return null;
        }
        return (KtTypeReference) extendsBound.replace(ktTypeReference);
    }

    public KtTypeReference getExtendsBound() {
        return (KtTypeReference) getStubOrPsiChild(KtStubElementTypes.TYPE_REFERENCE);
    }

    @Override // org.jetbrains.kotlin.psi.KtNamedDeclarationStub, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public SearchScope getUseScope() {
        PsiElement psiElement = (KtTypeParameterListOwner) PsiTreeUtil.getParentOfType(this, KtTypeParameterListOwner.class);
        if (psiElement == null) {
            psiElement = this;
        }
        return new LocalSearchScope(psiElement);
    }
}
