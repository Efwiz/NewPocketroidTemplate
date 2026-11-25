package org.jetbrains.kotlin.com.intellij.psi.impl.source.javadoc;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceService;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.Constants;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaDocElementType;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocComment;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocTag;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocTagValue;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public class PsiDocTagImpl extends CompositePsiElement implements Constants, PsiDocTag {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final TokenSet TAG_VALUE_BIT_SET;
    private static final TokenSet VALUE_BIT_SET;

    /* JADX WARN: Code duplicated, block: B:30:0x0049  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 5) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 5) ? 3 : 2];
        if (i == 2) {
            objArr[0] = "name";
        } else if (i == 3) {
            objArr[0] = "child";
        } else if (i != 5) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/javadoc/PsiDocTagImpl";
        } else {
            objArr[0] = "visitor";
        }
        if (i == 1) {
            objArr[1] = "getName";
        } else if (i == 2 || i == 3) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/javadoc/PsiDocTagImpl";
        } else if (i == 4) {
            objArr[1] = "getReferences";
        } else if (i != 5) {
            objArr[1] = "getDataElements";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/javadoc/PsiDocTagImpl";
        }
        if (i == 2) {
            objArr[2] = "setName";
        } else if (i == 3) {
            objArr[2] = "getChildRole";
        } else if (i == 5) {
            objArr[2] = "accept";
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 5) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    static {
        TokenSet tokenSetCreate = TokenSet.create(DOC_TAG_VALUE_ELEMENT, DOC_METHOD_OR_FIELD_REF, DOC_PARAMETER_REF);
        TAG_VALUE_BIT_SET = tokenSetCreate;
        VALUE_BIT_SET = TokenSet.orSet(tokenSetCreate, TokenSet.create(DOC_TAG_VALUE_TOKEN, JAVA_CODE_REFERENCE, DOC_COMMENT_DATA, DOC_INLINE_TAG, DOC_REFERENCE_HOLDER));
    }

    public PsiDocTagImpl() {
        super(DOC_TAG);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocTag
    public PsiDocComment getContainingComment() {
        return (PsiDocComment) getLightParent();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocTag
    public PsiElement getNameElement() {
        return findPsiChildByType(DOC_TAG_NAME);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocTag
    public PsiDocTagValue getValueElement() {
        return (PsiDocTagValue) findPsiChildByType(TAG_VALUE_BIT_SET);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocTag
    public PsiElement[] getDataElements() {
        PsiElement[] childrenAsPsiElements = getChildrenAsPsiElements(VALUE_BIT_SET, PsiElement.ARRAY_FACTORY);
        if (childrenAsPsiElements == null) {
            $$$reportNull$$$0(0);
        }
        return childrenAsPsiElements;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    public String getName() {
        if (getNameElement() == null) {
            return "";
        }
        String strSubstring = getNameElement().getText().substring(1);
        if (strSubstring == null) {
            $$$reportNull$$$0(1);
        }
        return strSubstring;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public PsiElement setName(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        PsiImplUtil.setName(getNameElement(), str);
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public int getChildRole(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(3);
        }
        IElementType elementType = aSTNode.getElementType();
        if (elementType == DOC_TAG_NAME) {
            return 105;
        }
        if (elementType == JavaDocElementType.DOC_COMMENT || elementType == DOC_INLINE_TAG) {
            return 106;
        }
        if (elementType == DOC_COMMENT_LEADING_ASTERISKS) {
            return 107;
        }
        return TAG_VALUE_BIT_SET.contains(elementType) ? 242 : 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiReference[] getReferences() {
        PsiReference[] referencesFromProviders = ReferenceProvidersRegistry.getReferencesFromProviders(this, PsiReferenceService.Hints.NO_HINTS);
        if (referencesFromProviders == null) {
            $$$reportNull$$$0(4);
        }
        return referencesFromProviders;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(5);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitDocTag(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiDocTag:" + getNameElement().getText();
    }
}
