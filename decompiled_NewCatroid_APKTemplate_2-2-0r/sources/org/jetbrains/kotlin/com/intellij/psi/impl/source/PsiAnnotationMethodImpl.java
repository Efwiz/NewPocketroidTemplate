package org.jetbrains.kotlin.com.intellij.psi.impl.source;

import com.huawei.hms.ml.language.common.utils.Constant;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationMemberValue;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiMethodStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.reference.SoftReference;

/* JADX INFO: loaded from: classes3.dex */
public class PsiAnnotationMethodImpl extends PsiMethodImpl implements PsiAnnotationMethod {
    private SoftReference<PsiAnnotationMemberValue> myCachedDefaultValue;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "visitor", "org/jetbrains/kotlin/com/intellij/psi/impl/source/PsiAnnotationMethodImpl", "accept"));
    }

    public PsiAnnotationMethodImpl(PsiMethodStub psiMethodStub) {
        super(psiMethodStub, JavaStubElementTypes.ANNOTATION_METHOD);
    }

    public PsiAnnotationMethodImpl(ASTNode aSTNode) {
        super(aSTNode);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiMethodImpl
    protected void dropCached() {
        this.myCachedDefaultValue = null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationMethod
    public PsiAnnotationMemberValue getDefaultValue() {
        PsiMethodStub psiMethodStub = (PsiMethodStub) getStub();
        if (psiMethodStub != null) {
            String defaultValueText = psiMethodStub.getDefaultValueText();
            if (StringUtil.isEmpty(defaultValueText)) {
                return null;
            }
            PsiAnnotationMemberValue psiAnnotationMemberValue = (PsiAnnotationMemberValue) SoftReference.dereference(this.myCachedDefaultValue);
            if (psiAnnotationMemberValue != null) {
                return psiAnnotationMemberValue;
            }
            PsiAnnotationMemberValue psiAnnotationMemberValueFindAttributeValue = JavaPsiFacade.getElementFactory(getProject()).createAnnotationFromText("@Foo(" + defaultValueText + Constant.AFTER_QUTO, this).findAttributeValue(null);
            this.myCachedDefaultValue = new SoftReference<>(psiAnnotationMemberValueFindAttributeValue);
            return psiAnnotationMemberValueFindAttributeValue;
        }
        this.myCachedDefaultValue = null;
        boolean z = false;
        for (TreeElement firstChildNode = getNode().getFirstChildNode(); firstChildNode != null; firstChildNode = firstChildNode.getTreeNext()) {
            IElementType elementType = firstChildNode.getElementType();
            if (elementType == JavaTokenType.DEFAULT_KEYWORD) {
                z = true;
            } else if (z && ElementType.ANNOTATION_MEMBER_VALUE_BIT_SET.contains(elementType)) {
                return (PsiAnnotationMemberValue) firstChildNode.getPsi();
            }
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiMethodImpl, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiAnnotationMethod:" + getName();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiMethodImpl, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public final void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(0);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitAnnotationMethod(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }
}
