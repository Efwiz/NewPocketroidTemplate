package org.jetbrains.kotlin.com.intellij.psi.impl.compiled;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameterList;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiParameterListStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.SourceTreeToPsiMap;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;

/* JADX INFO: loaded from: classes3.dex */
public class ClsParameterListImpl extends ClsRepositoryPsiElement<PsiParameterListStub> implements PsiParameterList {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsParameterListImpl";
        } else if (i == 2) {
            objArr[0] = "parameter";
        } else if (i == 3) {
            objArr[0] = "buffer";
        } else if (i == 4) {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        } else if (i != 5) {
            objArr[0] = "stub";
        } else {
            objArr[0] = "visitor";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsParameterListImpl";
        } else {
            objArr[1] = "getParameters";
        }
        if (i != 1) {
            if (i == 2) {
                objArr[2] = "getParameterIndex";
            } else if (i == 3) {
                objArr[2] = "appendMirrorText";
            } else if (i == 4) {
                objArr[2] = "setMirror";
            } else if (i != 5) {
                objArr[2] = "<init>";
            } else {
                objArr[2] = "accept";
            }
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ClsParameterListImpl(PsiParameterListStub psiParameterListStub) {
        super(psiParameterListStub);
        if (psiParameterListStub == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiParameterList
    public PsiParameter[] getParameters() {
        PsiParameter[] psiParameterArr = (PsiParameter[]) getStub().getChildrenByType(JavaStubElementTypes.PARAMETER, PsiParameter.ARRAY_FACTORY);
        if (psiParameterArr == null) {
            $$$reportNull$$$0(1);
        }
        return psiParameterArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiParameterList
    public int getParameterIndex(PsiParameter psiParameter) {
        if (psiParameter == null) {
            $$$reportNull$$$0(2);
        }
        return PsiImplUtil.getParameterIndex(psiParameter, this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiParameterList
    public int getParametersCount() {
        return getStub().getChildrenStubs().size();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiParameterList
    public PsiParameter getParameter(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("index is negative: " + i);
        }
        int i2 = 0;
        for (StubElement stubElement : getStub().getChildrenStubs()) {
            if (stubElement.getStubType() == JavaStubElementTypes.PARAMETER) {
                if (i2 == i) {
                    return (PsiParameter) stubElement.getPsi();
                }
                i2++;
            }
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void appendMirrorText(int i, StringBuilder sb) {
        if (sb == null) {
            $$$reportNull$$$0(3);
        }
        sb.append('(');
        PsiParameter[] parameters = getParameters();
        for (int i2 = 0; i2 < parameters.length; i2++) {
            if (i2 > 0) {
                sb.append(", ");
            }
            appendText(parameters[i2], i, sb);
        }
        sb.append(')');
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String getText() {
        StringBuilder sb = new StringBuilder();
        appendMirrorText(0, sb);
        return sb.toString();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void setMirror(TreeElement treeElement) throws ClsElementImpl.InvalidMirrorException {
        if (treeElement == null) {
            $$$reportNull$$$0(4);
        }
        setMirrorCheckingType(treeElement, null);
        setMirrors(getParameters(), ((PsiParameterList) SourceTreeToPsiMap.treeToPsiNotNull(treeElement)).getParameters());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(5);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitParameterList(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiParameterList";
    }
}
