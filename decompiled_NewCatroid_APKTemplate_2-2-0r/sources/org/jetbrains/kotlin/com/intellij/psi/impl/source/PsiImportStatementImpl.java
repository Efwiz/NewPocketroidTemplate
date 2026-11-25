package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiImportStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiImportStatementStub;
import org.jetbrains.kotlin.com.intellij.util.ArrayFactory;

/* JADX INFO: loaded from: classes3.dex */
public class PsiImportStatementImpl extends PsiImportStatementBaseImpl implements PsiImportStatement {
    public static final PsiImportStatementImpl[] EMPTY_ARRAY = new PsiImportStatementImpl[0];
    public static final ArrayFactory<PsiImportStatementImpl> ARRAY_FACTORY = new ArrayFactory() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiImportStatementImpl$$ExternalSyntheticLambda0
        @Override // org.jetbrains.kotlin.com.intellij.util.ArrayFactory
        public final Object[] create(int i) {
            return PsiImportStatementImpl.lambda$static$0(i);
        }
    };

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "visitor", "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiImportStatementImpl", "accept"));
    }

    static /* synthetic */ PsiImportStatementImpl[] lambda$static$0(int i) {
        return i == 0 ? EMPTY_ARRAY : new PsiImportStatementImpl[i];
    }

    public PsiImportStatementImpl(PsiImportStatementStub psiImportStatementStub) {
        super(psiImportStatementStub, JavaStubElementTypes.IMPORT_STATEMENT);
    }

    public PsiImportStatementImpl(ASTNode aSTNode) {
        super(aSTNode);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiImportStatement
    public String getQualifiedName() {
        PsiImportStatementStub psiImportStatementStub = (PsiImportStatementStub) getGreenStub();
        if (psiImportStatementStub != null) {
            return psiImportStatementStub.getImportReferenceText();
        }
        PsiJavaCodeReferenceElement importReference = getImportReference();
        if (importReference == null) {
            return null;
        }
        return importReference.getCanonicalText();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(0);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitImportStatement(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiImportStatement";
    }
}
