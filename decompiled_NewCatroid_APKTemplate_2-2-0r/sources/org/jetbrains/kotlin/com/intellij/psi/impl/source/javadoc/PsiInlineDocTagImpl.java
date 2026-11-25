package org.jetbrains.kotlin.com.intellij.psi.impl.source.javadoc;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.Constants;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.SourceTreeToPsiMap;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaDocElementType;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocComment;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocTagValue;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiInlineDocTag;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public class PsiInlineDocTagImpl extends CompositePsiElement implements Constants, PsiInlineDocTag {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final TokenSet TAG_VALUE_BIT_SET;
    private static final TokenSet VALUE_BIT_SET;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 4) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 4) ? 3 : 2];
        if (i == 2) {
            objArr[0] = "child";
        } else if (i == 3) {
            objArr[0] = "visitor";
        } else if (i != 4) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/javadoc/PsiInlineDocTagImpl";
        } else {
            objArr[0] = "name";
        }
        if (i == 1) {
            objArr[1] = "getName";
        } else if (i == 2 || i == 3 || i == 4) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/javadoc/PsiInlineDocTagImpl";
        } else {
            objArr[1] = "getDataElements";
        }
        if (i == 2) {
            objArr[2] = "getChildRole";
        } else if (i == 3) {
            objArr[2] = "accept";
        } else if (i == 4) {
            objArr[2] = "setName";
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 4) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    static {
        TokenSet tokenSetCreate = TokenSet.create(DOC_TAG_VALUE_ELEMENT, DOC_METHOD_OR_FIELD_REF);
        TAG_VALUE_BIT_SET = tokenSetCreate;
        VALUE_BIT_SET = TokenSet.orSet(tokenSetCreate, TokenSet.create(JAVA_CODE_REFERENCE, DOC_TAG_VALUE_TOKEN, WHITE_SPACE, DOC_COMMENT_DATA, DOC_INLINE_TAG, DOC_REFERENCE_HOLDER, DOC_COMMENT_BAD_CHARACTER));
    }

    public PsiInlineDocTagImpl() {
        super(DOC_INLINE_TAG);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocTag
    public PsiDocComment getContainingComment() {
        ASTNode treeParent = getTreeParent();
        while (treeParent.getElementType() != JavaDocElementType.DOC_COMMENT) {
            treeParent = treeParent.getTreeParent();
        }
        return (PsiDocComment) SourceTreeToPsiMap.treeElementToPsi(treeParent);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocTag
    public PsiElement getNameElement() {
        return findPsiChildByType(DOC_TAG_NAME);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocTag
    public PsiElement[] getDataElements() {
        PsiElement[] childrenAsPsiElements = getChildrenAsPsiElements(VALUE_BIT_SET, PsiElement.ARRAY_FACTORY);
        if (childrenAsPsiElements == null) {
            $$$reportNull$$$0(0);
        }
        return childrenAsPsiElements;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocTag
    public PsiDocTagValue getValueElement() {
        return (PsiDocTagValue) findPsiChildByType(TAG_VALUE_BIT_SET);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    public String getName() {
        PsiElement nameElement = getNameElement();
        if (nameElement == null) {
            return "";
        }
        String strSubstring = nameElement.getText().substring(1);
        if (strSubstring == null) {
            $$$reportNull$$$0(1);
        }
        return strSubstring;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public int getChildRole(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(2);
        }
        IElementType elementType = aSTNode.getElementType();
        if (elementType == DOC_TAG_NAME) {
            return 105;
        }
        if (elementType == JavaDocElementType.DOC_COMMENT || elementType == DOC_INLINE_TAG) {
            return 106;
        }
        if (elementType == DOC_INLINE_TAG_START) {
            return 108;
        }
        if (elementType == DOC_INLINE_TAG_END) {
            return 109;
        }
        return TAG_VALUE_BIT_SET.contains(elementType) ? 242 : 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(3);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitInlineDocTag(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        PsiElement nameElement = getNameElement();
        return "PsiInlineDocTag:" + (nameElement != null ? nameElement.getText() : null);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public PsiElement setName(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(4);
        }
        PsiImplUtil.setName(getNameElement(), str);
        return this;
    }
}
