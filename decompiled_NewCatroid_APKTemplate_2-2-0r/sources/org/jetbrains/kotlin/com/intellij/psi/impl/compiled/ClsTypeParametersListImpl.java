package org.jetbrains.kotlin.com.intellij.psi.impl.compiled;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterList;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiTypeParameterListStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.SourceTreeToPsiMap;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;

/* JADX INFO: loaded from: classes3.dex */
public class ClsTypeParametersListImpl extends ClsRepositoryPsiElement<PsiTypeParameterListStub> implements PsiTypeParameterList {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 4 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 4 ? 3 : 2];
        switch (i) {
            case 1:
                objArr[0] = "buffer";
                break;
            case 2:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 3:
                objArr[0] = "visitor";
                break;
            case 4:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsTypeParametersListImpl";
                break;
            case 5:
                objArr[0] = "processor";
                break;
            case 6:
                objArr[0] = "state";
                break;
            case 7:
                objArr[0] = "place";
                break;
            default:
                objArr[0] = "stub";
                break;
        }
        if (i != 4) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsTypeParametersListImpl";
        } else {
            objArr[1] = "getTypeParameters";
        }
        switch (i) {
            case 1:
                objArr[2] = "appendMirrorText";
                break;
            case 2:
                objArr[2] = "setMirror";
                break;
            case 3:
                objArr[2] = "accept";
                break;
            case 4:
                break;
            case 5:
            case 6:
            case 7:
                objArr[2] = "processDeclarations";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i == 4) {
            throw new IllegalStateException(str2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ClsTypeParametersListImpl(PsiTypeParameterListStub psiTypeParameterListStub) {
        super(psiTypeParameterListStub);
        if (psiTypeParameterListStub == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void appendMirrorText(int i, StringBuilder sb) {
        if (sb == null) {
            $$$reportNull$$$0(1);
        }
        PsiTypeParameter[] typeParameters = getTypeParameters();
        if (typeParameters.length != 0) {
            sb.append('<');
            for (int i2 = 0; i2 < typeParameters.length; i2++) {
                if (i2 > 0) {
                    sb.append(", ");
                }
                appendText(typeParameters[i2], i, sb);
            }
            sb.append(">");
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void setMirror(TreeElement treeElement) throws ClsElementImpl.InvalidMirrorException {
        if (treeElement == null) {
            $$$reportNull$$$0(2);
        }
        setMirrorCheckingType(treeElement, null);
        setMirrors(getTypeParameters(), ((PsiTypeParameterList) SourceTreeToPsiMap.treeToPsiNotNull(treeElement)).getTypeParameters());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(3);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitTypeParameterList(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterList
    public PsiTypeParameter[] getTypeParameters() {
        PsiTypeParameter[] psiTypeParameterArr = (PsiTypeParameter[]) getStub().getChildrenByType(JavaStubElementTypes.TYPE_PARAMETER, PsiTypeParameter.ARRAY_FACTORY);
        if (psiTypeParameterArr == null) {
            $$$reportNull$$$0(4);
        }
        return psiTypeParameterArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterList
    public int getTypeParameterIndex(PsiTypeParameter psiTypeParameter) {
        return PsiImplUtil.getTypeParameterIndex(psiTypeParameter, this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean processDeclarations(PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, PsiElement psiElement, PsiElement psiElement2) {
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(5);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(6);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(7);
        }
        for (PsiTypeParameter psiTypeParameter : getTypeParameters()) {
            if (!psiScopeProcessor.execute(psiTypeParameter, resolveState)) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiTypeParameterList";
    }
}
