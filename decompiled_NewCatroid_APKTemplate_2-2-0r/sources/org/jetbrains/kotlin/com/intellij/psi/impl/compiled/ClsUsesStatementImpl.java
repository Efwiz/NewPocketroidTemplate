package org.jetbrains.kotlin.com.intellij.psi.impl.compiled;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiUsesStatement;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiUsesStatementStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiClassReferenceType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.SourceTreeToPsiMap;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;

/* JADX INFO: loaded from: classes3.dex */
public class ClsUsesStatementImpl extends ClsRepositoryPsiElement<PsiUsesStatementStub> implements PsiUsesStatement {
    private final ClsJavaCodeReferenceElementImpl myClassReference;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "buffer";
        } else {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsUsesStatementImpl";
        if (i != 1) {
            objArr[2] = "appendMirrorText";
        } else {
            objArr[2] = "setMirror";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public ClsUsesStatementImpl(PsiUsesStatementStub psiUsesStatementStub) {
        super(psiUsesStatementStub);
        this.myClassReference = new ClsJavaCodeReferenceElementImpl(this, psiUsesStatementStub.getClassName());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiUsesStatement
    public PsiJavaCodeReferenceElement getClassReference() {
        return this.myClassReference;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiUsesStatement
    public PsiClassType getClassType() {
        return new PsiClassReferenceType(this.myClassReference, (LanguageLevel) null, PsiAnnotation.EMPTY_ARRAY);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void appendMirrorText(int i, StringBuilder sb) {
        if (sb == null) {
            $$$reportNull$$$0(0);
        }
        StringUtil.repeatSymbol(sb, io.netty.util.internal.StringUtil.SPACE, i);
        sb.append("uses ").append(this.myClassReference.getCanonicalText()).append(";\n");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsElementImpl
    public void setMirror(TreeElement treeElement) throws ClsElementImpl.InvalidMirrorException {
        if (treeElement == null) {
            $$$reportNull$$$0(1);
        }
        setMirrorCheckingType(treeElement, JavaElementType.USES_STATEMENT);
        setMirror(getClassReference(), ((PsiUsesStatement) SourceTreeToPsiMap.treeToPsiNotNull(treeElement)).getClassReference());
    }

    @Override // java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiUsesStatement";
    }
}
