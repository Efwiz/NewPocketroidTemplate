package org.jetbrains.kotlin.psi;

import java.util.Collections;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.search.SearchScope;
import org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.stubs.KotlinStubWithFqName;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: loaded from: classes6.dex */
public abstract class KtTypeParameterListOwnerStub<T extends KotlinStubWithFqName<?>> extends KtNamedDeclarationStub<T> implements KtTypeParameterListOwner {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 4 || i == 5 || i == 6) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 4 || i == 5 || i == 6) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "nodeType";
                break;
            case 2:
                objArr[0] = "node";
                break;
            case 3:
            case 4:
            case 5:
            case 6:
                objArr[0] = "org/jetbrains/kotlin/psi/KtTypeParameterListOwnerStub";
                break;
            default:
                objArr[0] = "stub";
                break;
        }
        if (i == 3 || i == 4) {
            objArr[1] = "getTypeConstraints";
        } else if (i == 5 || i == 6) {
            objArr[1] = "getTypeParameters";
        } else {
            objArr[1] = "org/jetbrains/kotlin/psi/KtTypeParameterListOwnerStub";
        }
        if (i != 3 && i != 4 && i != 5 && i != 6) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 4 && i != 5 && i != 6) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.psi.KtNamedDeclarationStub, org.jetbrains.kotlin.psi.KtNamedDeclaration
    /* JADX INFO: renamed from: getFqName */
    public /* bridge */ /* synthetic */ FqName mo7391getFqName() {
        return super.mo7391getFqName();
    }

    @Override // org.jetbrains.kotlin.psi.KtNamedDeclarationStub, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    /* JADX INFO: renamed from: getName */
    public /* bridge */ /* synthetic */ String get_name() {
        return super.get_name();
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

    @Override // org.jetbrains.kotlin.psi.KtNamedDeclarationStub, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public /* bridge */ /* synthetic */ SearchScope getUseScope() {
        return super.getUseScope();
    }

    @Override // org.jetbrains.kotlin.psi.KtNamedDeclarationStub, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public /* bridge */ /* synthetic */ PsiElement setName(String str) throws IncorrectOperationException {
        return super.setName(str);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtTypeParameterListOwnerStub(T t, IStubElementType iStubElementType) {
        super(t, iStubElementType);
        if (t == null) {
            $$$reportNull$$$0(0);
        }
        if (iStubElementType == null) {
            $$$reportNull$$$0(1);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtTypeParameterListOwnerStub(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(2);
        }
    }

    @Override // org.jetbrains.kotlin.psi.KtTypeParameterListOwner
    /* JADX INFO: renamed from: getTypeParameterList */
    public KtTypeParameterList mo7395getTypeParameterList() {
        return (KtTypeParameterList) getStubOrPsiChild(KtStubElementTypes.TYPE_PARAMETER_LIST);
    }

    @Override // org.jetbrains.kotlin.psi.KtTypeParameterListOwner
    /* JADX INFO: renamed from: getTypeConstraintList */
    public KtTypeConstraintList mo7394getTypeConstraintList() {
        return (KtTypeConstraintList) getStubOrPsiChild(KtStubElementTypes.TYPE_CONSTRAINT_LIST);
    }

    @Override // org.jetbrains.kotlin.psi.KtTypeParameterListOwner
    public List<KtTypeConstraint> getTypeConstraints() {
        KtTypeConstraintList ktTypeConstraintListMo7394getTypeConstraintList = mo7394getTypeConstraintList();
        if (ktTypeConstraintListMo7394getTypeConstraintList == null) {
            List<KtTypeConstraint> listEmptyList = Collections.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(3);
            }
            return listEmptyList;
        }
        List<KtTypeConstraint> constraints = ktTypeConstraintListMo7394getTypeConstraintList.getConstraints();
        if (constraints == null) {
            $$$reportNull$$$0(4);
        }
        return constraints;
    }

    @Override // org.jetbrains.kotlin.psi.KtTypeParameterListOwner
    public List<KtTypeParameter> getTypeParameters() {
        KtTypeParameterList ktTypeParameterListMo7395getTypeParameterList = mo7395getTypeParameterList();
        if (ktTypeParameterListMo7395getTypeParameterList == null) {
            List<KtTypeParameter> listEmptyList = Collections.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(5);
            }
            return listEmptyList;
        }
        List<KtTypeParameter> parameters = ktTypeParameterListMo7395getTypeParameterList.getParameters();
        if (parameters == null) {
            $$$reportNull$$$0(6);
        }
        return parameters;
    }
}
