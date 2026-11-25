package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiImportStaticReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiImportStaticStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiImportStatementStub;
import org.jetbrains.kotlin.com.intellij.util.ArrayFactory;

/* JADX INFO: loaded from: classes3.dex */
public class PsiImportStaticStatementImpl extends PsiImportStatementBaseImpl implements PsiImportStaticStatement {
    public static final PsiImportStaticStatementImpl[] EMPTY_ARRAY = new PsiImportStaticStatementImpl[0];
    public static final ArrayFactory<PsiImportStaticStatementImpl> ARRAY_FACTORY = new ArrayFactory() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiImportStaticStatementImpl$$ExternalSyntheticLambda0
        @Override // org.jetbrains.kotlin.com.intellij.util.ArrayFactory
        public final Object[] create(int i) {
            return PsiImportStaticStatementImpl.lambda$static$0(i);
        }
    };

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "visitor", "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiImportStaticStatementImpl", "accept"));
    }

    static /* synthetic */ PsiImportStaticStatementImpl[] lambda$static$0(int i) {
        return i == 0 ? EMPTY_ARRAY : new PsiImportStaticStatementImpl[i];
    }

    public PsiImportStaticStatementImpl(PsiImportStatementStub psiImportStatementStub) {
        super(psiImportStatementStub, JavaStubElementTypes.IMPORT_STATIC_STATEMENT);
    }

    public PsiImportStaticStatementImpl(ASTNode aSTNode) {
        super(aSTNode);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiImportStaticStatement
    public PsiClass resolveTargetClass() {
        PsiJavaCodeReferenceElement classReference = getClassReference();
        if (classReference == null) {
            return null;
        }
        PsiElement psiElementResolve = classReference.resolve();
        if (psiElementResolve instanceof PsiClass) {
            return (PsiClass) psiElementResolve;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiImportStaticStatement
    public String getReferenceName() {
        PsiImportStaticReferenceElement memberReference;
        if (isOnDemand() || (memberReference = getMemberReference()) == null) {
            return null;
        }
        return memberReference.getCustomReferenceName();
    }

    private PsiImportStaticReferenceElement getMemberReference() {
        if (isOnDemand()) {
            return null;
        }
        return (PsiImportStaticReferenceElement) getImportReference();
    }

    public PsiJavaCodeReferenceElement getClassReference() {
        if (isOnDemand()) {
            return getImportReference();
        }
        PsiImportStaticReferenceElement memberReference = getMemberReference();
        if (memberReference != null) {
            return memberReference.getClassReference();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(0);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitImportStaticStatement(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiImportStaticStatement";
    }
}
