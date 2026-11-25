package org.jetbrains.kotlin.psi;

import java.util.Collections;
import java.util.List;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: loaded from: classes6.dex */
public abstract class KtFunctionNotStubbed extends KtTypeParameterListOwnerNotStubbed implements KtFunction {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 2) ? 2 : 3];
        if (i == 1 || i == 2) {
            objArr[0] = "org/jetbrains/kotlin/psi/KtFunctionNotStubbed";
        } else {
            objArr[0] = "node";
        }
        if (i == 1) {
            objArr[1] = "getValueParameters";
        } else if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/psi/KtFunctionNotStubbed";
        } else {
            objArr[1] = "getContextReceivers";
        }
        if (i != 1 && i != 2) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.psi.KtCallableDeclaration
    public PsiElement getColon() {
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtCallableDeclaration
    /* JADX INFO: renamed from: getReceiverTypeReference */
    public KtTypeReference mo7393getReceiverTypeReference() {
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtCallableDeclaration
    /* JADX INFO: renamed from: getTypeReference */
    public KtTypeReference mo7396getTypeReference() {
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtDeclarationWithBody
    public boolean hasDeclaredReturnType() {
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

    @Override // org.jetbrains.kotlin.psi.KtTypeParameterListOwnerNotStubbed, org.jetbrains.kotlin.psi.KtTypeParameterListOwner
    /* JADX INFO: renamed from: getTypeConstraintList */
    public /* bridge */ /* synthetic */ KtTypeConstraintList mo7394getTypeConstraintList() {
        return super.mo7394getTypeConstraintList();
    }

    @Override // org.jetbrains.kotlin.psi.KtTypeParameterListOwnerNotStubbed, org.jetbrains.kotlin.psi.KtTypeParameterListOwner
    public /* bridge */ /* synthetic */ List getTypeConstraints() {
        return super.getTypeConstraints();
    }

    @Override // org.jetbrains.kotlin.psi.KtTypeParameterListOwnerNotStubbed, org.jetbrains.kotlin.psi.KtTypeParameterListOwner
    /* JADX INFO: renamed from: getTypeParameterList */
    public /* bridge */ /* synthetic */ KtTypeParameterList mo7395getTypeParameterList() {
        return super.mo7395getTypeParameterList();
    }

    @Override // org.jetbrains.kotlin.psi.KtTypeParameterListOwnerNotStubbed, org.jetbrains.kotlin.psi.KtTypeParameterListOwner
    public /* bridge */ /* synthetic */ List getTypeParameters() {
        return super.getTypeParameters();
    }

    @Override // org.jetbrains.kotlin.psi.KtNamedDeclarationNotStubbed, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public /* bridge */ /* synthetic */ PsiElement setName(String str) throws IncorrectOperationException {
        return super.setName(str);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtFunctionNotStubbed(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.psi.KtCallableDeclaration
    public KtParameterList getValueParameterList() {
        return (KtParameterList) findChildByType(KtNodeTypes.VALUE_PARAMETER_LIST);
    }

    @Override // org.jetbrains.kotlin.psi.KtCallableDeclaration, org.jetbrains.kotlin.psi.KtDeclarationWithBody
    public List<KtParameter> getValueParameters() {
        KtParameterList valueParameterList = getValueParameterList();
        List<KtParameter> parameters = valueParameterList != null ? valueParameterList.getParameters() : Collections.emptyList();
        if (parameters == null) {
            $$$reportNull$$$0(1);
        }
        return parameters;
    }

    public KtExpression getBodyExpression() {
        return (KtExpression) findChildByClass(KtExpression.class);
    }

    @Override // org.jetbrains.kotlin.psi.KtCallableDeclaration
    public List<KtContextReceiver> getContextReceivers() {
        List<KtContextReceiver> listEmptyList = Collections.emptyList();
        if (listEmptyList == null) {
            $$$reportNull$$$0(2);
        }
        return listEmptyList;
    }

    @Override // org.jetbrains.kotlin.psi.KtCallableDeclaration
    /* JADX INFO: renamed from: setTypeReference */
    public KtTypeReference mo7397setTypeReference(KtTypeReference ktTypeReference) {
        if (ktTypeReference == null) {
            return null;
        }
        throw new IllegalStateException("Lambda expressions can't have type reference");
    }

    @Override // org.jetbrains.kotlin.psi.KtFunction
    public boolean isLocal() {
        PsiElement parent = getMyParent();
        return ((parent instanceof KtFile) || (parent instanceof KtClassBody)) ? false : true;
    }
}
