package org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl;

import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiImportStatementBase;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiImportStatementStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiJavaCodeReferenceElementImpl;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubBase;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.reference.SoftReference;
import org.jetbrains.kotlin.com.intellij.util.BitUtil;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public class PsiImportStatementStubImpl extends StubBase<PsiImportStatementBase> implements PsiImportStatementStub {
    private final byte myFlags;
    private SoftReference<PsiJavaCodeReferenceElement> myReference;
    private final String myText;

    public static byte packFlags(boolean z, boolean z2) {
        byte b = z ? (byte) 1 : (byte) 0;
        return z2 ? (byte) (b | 2) : b;
    }

    public PsiImportStatementStubImpl(StubElement stubElement, String str, byte b) {
        super(stubElement, isStatic(b) ? JavaStubElementTypes.IMPORT_STATIC_STATEMENT : JavaStubElementTypes.IMPORT_STATEMENT);
        this.myText = str;
        this.myFlags = b;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiImportStatementStub
    public boolean isStatic() {
        return isStatic(this.myFlags);
    }

    private static boolean isStatic(byte b) {
        return BitUtil.isSet(b, 2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiImportStatementStub
    public boolean isOnDemand() {
        return BitUtil.isSet(this.myFlags, 1);
    }

    public byte getFlags() {
        return this.myFlags;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiImportStatementStub
    public String getImportReferenceText() {
        return this.myText;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiImportStatementStub
    public PsiJavaCodeReferenceElement getReference() {
        PsiJavaCodeReferenceElement staticReference = (PsiJavaCodeReferenceElement) SoftReference.dereference(this.myReference);
        if (staticReference == null) {
            staticReference = isStatic() ? getStaticReference() : getRegularReference();
            this.myReference = new SoftReference<>(staticReference);
        }
        return staticReference;
    }

    private PsiJavaCodeReferenceElement getStaticReference() {
        PsiJavaCodeReferenceElement psiJavaCodeReferenceElementCreateReference = createReference();
        if (psiJavaCodeReferenceElementCreateReference == null) {
            return null;
        }
        if (isOnDemand() && (psiJavaCodeReferenceElementCreateReference instanceof PsiJavaCodeReferenceElementImpl)) {
            ((PsiJavaCodeReferenceElementImpl) psiJavaCodeReferenceElementCreateReference).setKindWhenDummy(PsiJavaCodeReferenceElementImpl.Kind.CLASS_FQ_NAME_KIND);
        }
        return psiJavaCodeReferenceElementCreateReference;
    }

    private PsiJavaCodeReferenceElement getRegularReference() {
        PsiJavaCodeReferenceElement psiJavaCodeReferenceElementCreateReference = createReference();
        if (psiJavaCodeReferenceElementCreateReference == null) {
            return null;
        }
        ((PsiJavaCodeReferenceElementImpl) psiJavaCodeReferenceElementCreateReference).setKindWhenDummy(isOnDemand() ? PsiJavaCodeReferenceElementImpl.Kind.CLASS_FQ_OR_PACKAGE_NAME_KIND : PsiJavaCodeReferenceElementImpl.Kind.CLASS_FQ_NAME_KIND);
        return psiJavaCodeReferenceElementCreateReference;
    }

    private PsiJavaCodeReferenceElement createReference() {
        String importReferenceText = getImportReferenceText();
        if (importReferenceText == null) {
            return null;
        }
        try {
            return JavaPsiFacade.getInstance(getProject()).getParserFacade().createReferenceFromText(importReferenceText, getPsi());
        } catch (IncorrectOperationException unused) {
            return null;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.StubBase, java.util.concurrent.atomic.AtomicReference
    public String toString() {
        StringBuilder sb = new StringBuilder("PsiImportStatementStub[");
        if (isStatic()) {
            sb.append("static ");
        }
        sb.append(getImportReferenceText());
        if (isOnDemand()) {
            sb.append(".*");
        }
        sb.append("]");
        return sb.toString();
    }
}
