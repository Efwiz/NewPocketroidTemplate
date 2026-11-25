package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaModuleReference;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaModuleReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiRequiresStatement;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiRequiresStatementStub;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.reference.SoftReference;

/* JADX INFO: loaded from: classes3.dex */
public class PsiRequiresStatementImpl extends JavaStubPsiElement<PsiRequiresStatementStub> implements PsiRequiresStatement {
    private SoftReference<PsiJavaModuleReference> myReference;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "node";
        } else if (i == 2) {
            objArr[0] = "name";
        } else if (i != 3) {
            objArr[0] = "stub";
        } else {
            objArr[0] = "visitor";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiRequiresStatementImpl";
        if (i == 2) {
            objArr[2] = "hasModifierProperty";
        } else if (i != 3) {
            objArr[2] = "<init>";
        } else {
            objArr[2] = "accept";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiRequiresStatementImpl(PsiRequiresStatementStub psiRequiresStatementStub) {
        super(psiRequiresStatementStub, JavaStubElementTypes.REQUIRES_STATEMENT);
        if (psiRequiresStatementStub == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiRequiresStatementImpl(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(1);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiRequiresStatement
    public PsiJavaModuleReferenceElement getReferenceElement() {
        return (PsiJavaModuleReferenceElement) PsiTreeUtil.getChildOfType(this, PsiJavaModuleReferenceElement.class);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiRequiresStatement
    public String getModuleName() {
        PsiRequiresStatementStub psiRequiresStatementStub = (PsiRequiresStatementStub) getGreenStub();
        if (psiRequiresStatementStub != null) {
            return StringUtil.nullize(psiRequiresStatementStub.getModuleName());
        }
        PsiJavaModuleReferenceElement referenceElement = getReferenceElement();
        if (referenceElement != null) {
            return referenceElement.getReferenceText();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiRequiresStatement
    public PsiJavaModuleReference getModuleReference() {
        PsiRequiresStatementStub psiRequiresStatementStub = (PsiRequiresStatementStub) getStub();
        if (psiRequiresStatementStub != null) {
            String strNullize = StringUtil.nullize(psiRequiresStatementStub.getModuleName());
            if (strNullize == null) {
                return null;
            }
            PsiJavaModuleReference psiJavaModuleReference = (PsiJavaModuleReference) SoftReference.dereference(this.myReference);
            if (psiJavaModuleReference != null) {
                return psiJavaModuleReference;
            }
            PsiJavaModuleReference reference = JavaPsiFacade.getInstance(getProject()).getParserFacade().createModuleReferenceFromText(strNullize, this).getReference();
            this.myReference = new SoftReference<>(reference);
            return reference;
        }
        this.myReference = null;
        PsiJavaModuleReferenceElement referenceElement = getReferenceElement();
        if (referenceElement != null) {
            return referenceElement.getReference();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    /* JADX INFO: renamed from: getModifierList */
    public PsiModifierList mo6095getModifierList() {
        return (PsiModifierList) getStubOrPsiChild(JavaStubElementTypes.MODIFIER_LIST);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    public boolean hasModifierProperty(String str) {
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        PsiModifierList psiModifierListMo6095getModifierList = mo6095getModifierList();
        return psiModifierListMo6095getModifierList != null && psiModifierListMo6095getModifierList.hasModifierProperty(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(3);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitRequiresStatement(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiRequiresStatement";
    }
}
