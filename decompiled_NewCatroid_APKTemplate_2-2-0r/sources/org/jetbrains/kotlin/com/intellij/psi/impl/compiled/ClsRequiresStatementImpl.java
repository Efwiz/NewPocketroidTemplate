package org.jetbrains.kotlin.com.intellij.psi.impl.compiled;

import java.util.function.Supplier;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.NotNullLazyValue;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaModuleReference;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaModuleReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiRequiresStatement;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiRequiresStatementStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.SourceTreeToPsiMap;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;

/* JADX INFO: loaded from: classes3.dex */
public final class ClsRequiresStatementImpl extends ClsRepositoryPsiElement<PsiRequiresStatementStub> implements PsiRequiresStatement {
    private final NotNullLazyValue<PsiJavaModuleReferenceElement> myModuleReference;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        } else if (i != 2) {
            objArr[0] = "buffer";
        } else {
            objArr[0] = "name";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsRequiresStatementImpl";
        if (i == 1) {
            objArr[2] = "setMirror";
        } else if (i != 2) {
            objArr[2] = "appendMirrorText";
        } else {
            objArr[2] = "hasModifierProperty";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public ClsRequiresStatementImpl(PsiRequiresStatementStub psiRequiresStatementStub) {
        super(psiRequiresStatementStub);
        this.myModuleReference = NotNullLazyValue.atomicLazy(new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsRequiresStatementImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.m6600xe9d489c0();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$new$0$org-jetbrains-kotlin-com-intellij-psi-impl-compiled-ClsRequiresStatementImpl, reason: not valid java name */
    /* synthetic */ PsiJavaModuleReferenceElement m6600xe9d489c0() {
        return new ClsJavaModuleReferenceElementImpl(this, getStub().getModuleName());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiRequiresStatement
    public PsiJavaModuleReferenceElement getReferenceElement() {
        return this.myModuleReference.getValue();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiRequiresStatement
    public String getModuleName() {
        return getStub().getModuleName();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiRequiresStatement
    public PsiJavaModuleReference getModuleReference() {
        return this.myModuleReference.getValue().getReference();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void appendMirrorText(int i, StringBuilder sb) {
        if (sb == null) {
            $$$reportNull$$$0(0);
        }
        StringUtil.repeatSymbol(sb, io.netty.util.internal.StringUtil.SPACE, i);
        sb.append("requires ");
        appendText(mo6095getModifierList(), i, sb);
        sb.append(getModuleName()).append(";\n");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void setMirror(TreeElement treeElement) throws ClsElementImpl.InvalidMirrorException {
        if (treeElement == null) {
            $$$reportNull$$$0(1);
        }
        setMirrorCheckingType(treeElement, JavaElementType.REQUIRES_STATEMENT);
        setMirror(mo6095getModifierList(), ((PsiRequiresStatement) SourceTreeToPsiMap.treeToPsiNotNull(treeElement)).mo6095getModifierList());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    /* JADX INFO: renamed from: getModifierList */
    public PsiModifierList mo6095getModifierList() {
        StubElement stubElementFindChildStubByType = getStub().findChildStubByType(JavaStubElementTypes.MODIFIER_LIST);
        if (stubElementFindChildStubByType != null) {
            return (PsiModifierList) stubElementFindChildStubByType.getPsi();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    public boolean hasModifierProperty(String str) {
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        PsiModifierList psiModifierListMo6095getModifierList = mo6095getModifierList();
        return psiModifierListMo6095getModifierList != null && psiModifierListMo6095getModifierList.hasModifierProperty(str);
    }

    @Override // java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiRequiresStatement";
    }
}
