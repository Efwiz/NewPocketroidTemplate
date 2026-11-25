package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiImportStatementBase;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiImportStatementStub;
import org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore;

/* JADX INFO: loaded from: classes3.dex */
public abstract class PsiImportStatementBaseImpl extends JavaStubPsiElement<PsiImportStatementStub> implements PsiImportStatementBase {
    public static final PsiImportStatementBaseImpl[] EMPTY_ARRAY = new PsiImportStatementBaseImpl[0];

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiImportStatementBase
    public boolean isForeignFileImport() {
        return false;
    }

    protected PsiImportStatementBaseImpl(PsiImportStatementStub psiImportStatementStub, IStubElementType iStubElementType) {
        super(psiImportStatementStub, iStubElementType);
    }

    protected PsiImportStatementBaseImpl(ASTNode aSTNode) {
        super(aSTNode);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiImportStatementBase
    public boolean isOnDemand() {
        PsiImportStatementStub psiImportStatementStub = (PsiImportStatementStub) getGreenStub();
        if (psiImportStatementStub != null) {
            return psiImportStatementStub.isOnDemand();
        }
        return calcTreeElement().findChildByRoleAsPsiElement(100) != null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiImportStatementBase
    public PsiJavaCodeReferenceElement getImportReference() {
        PsiUtilCore.ensureValid(this);
        PsiImportStatementStub psiImportStatementStub = (PsiImportStatementStub) getStub();
        if (psiImportStatementStub != null) {
            return psiImportStatementStub.getReference();
        }
        return (PsiJavaCodeReferenceElement) calcTreeElement().findChildByRoleAsPsiElement(99);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiImportStatementBase
    public PsiElement resolve() {
        PsiJavaCodeReferenceElement importReference = getImportReference();
        if (importReference == null) {
            return null;
        }
        return importReference.resolve();
    }
}
