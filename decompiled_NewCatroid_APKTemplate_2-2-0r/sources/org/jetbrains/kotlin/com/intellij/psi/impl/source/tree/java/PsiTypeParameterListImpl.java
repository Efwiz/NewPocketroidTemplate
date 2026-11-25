package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterList;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiTypeParameterListStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.JavaStubPsiElement;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;

/* JADX INFO: loaded from: classes3.dex */
public class PsiTypeParameterListImpl extends JavaStubPsiElement<PsiTypeParameterListStub> implements PsiTypeParameterList {
    private static final Logger LOG = Logger.getInstance((Class<?>) PsiTypeParameterListImpl.class);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2 || i == 3 || i == 4) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 1 || i == 2 || i == 3 || i == 4) ? 3 : 2];
        if (i == 1) {
            objArr[0] = "processor";
        } else if (i == 2) {
            objArr[0] = "state";
        } else if (i == 3) {
            objArr[0] = "place";
        } else if (i != 4) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiTypeParameterListImpl";
        } else {
            objArr[0] = "visitor";
        }
        if (i == 1 || i == 2 || i == 3 || i == 4) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiTypeParameterListImpl";
        } else {
            objArr[1] = "getTypeParameters";
        }
        if (i == 1 || i == 2 || i == 3) {
            objArr[2] = "processDeclarations";
        } else if (i == 4) {
            objArr[2] = "accept";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2 && i != 3 && i != 4) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    public PsiTypeParameterListImpl(PsiTypeParameterListStub psiTypeParameterListStub) {
        super(psiTypeParameterListStub, JavaStubElementTypes.TYPE_PARAMETER_LIST);
    }

    public PsiTypeParameterListImpl(ASTNode aSTNode) {
        super(aSTNode);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterList
    public PsiTypeParameter[] getTypeParameters() {
        PsiTypeParameter[] psiTypeParameterArr = (PsiTypeParameter[]) getStubOrPsiChildren(JavaStubElementTypes.TYPE_PARAMETER, PsiTypeParameter.ARRAY_FACTORY);
        if (psiTypeParameterArr == null) {
            $$$reportNull$$$0(0);
        }
        return psiTypeParameterArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterList
    public int getTypeParameterIndex(PsiTypeParameter psiTypeParameter) {
        LOG.assertTrue(psiTypeParameter.getMyParent() == this);
        return PsiImplUtil.getTypeParameterIndex(psiTypeParameter, this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean processDeclarations(PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, PsiElement psiElement, PsiElement psiElement2) {
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(1);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(2);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(3);
        }
        for (PsiTypeParameter psiTypeParameter : getTypeParameters()) {
            if (!psiScopeProcessor.execute(psiTypeParameter, resolveState)) {
                return false;
            }
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(4);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitTypeParameterList(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiTypeParameterList";
    }
}
