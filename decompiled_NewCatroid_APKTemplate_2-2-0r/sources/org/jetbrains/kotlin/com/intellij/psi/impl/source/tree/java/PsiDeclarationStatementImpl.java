package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.JavaTokenType;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiDeclarationStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.SourceTreeToPsiMap;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.Factory;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.SharedImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.scope.ElementClassHint;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;

/* JADX INFO: loaded from: classes3.dex */
public class PsiDeclarationStatementImpl extends CompositePsiElement implements PsiDeclarationStatement {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final TokenSet DECLARED_ELEMENT_BIT_SET = TokenSet.create(JavaElementType.LOCAL_VARIABLE, JavaElementType.CLASS);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
            default:
                str = "@NotNull method %s.%s must not return null";
                break;
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                i2 = 3;
                break;
            default:
                i2 = 2;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 2:
                objArr[0] = "child";
                break;
            case 3:
                objArr[0] = "visitor";
                break;
            case 4:
                objArr[0] = "processor";
                break;
            case 5:
                objArr[0] = "state";
                break;
            case 6:
                objArr[0] = "place";
                break;
            default:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiDeclarationStatementImpl";
                break;
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiDeclarationStatementImpl";
                break;
            default:
                objArr[1] = "getDeclaredElements";
                break;
        }
        switch (i) {
            case 1:
                objArr[2] = "getChildRole";
                break;
            case 2:
                objArr[2] = "deleteChildInternal";
                break;
            case 3:
                objArr[2] = "accept";
                break;
            case 4:
            case 5:
            case 6:
                objArr[2] = "processDeclarations";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                throw new IllegalArgumentException(str2);
            default:
                throw new IllegalStateException(str2);
        }
    }

    public PsiDeclarationStatementImpl() {
        super(JavaElementType.DECLARATION_STATEMENT);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDeclarationStatement
    public PsiElement[] getDeclaredElements() {
        PsiElement[] childrenAsPsiElements = getChildrenAsPsiElements(DECLARED_ELEMENT_BIT_SET, PsiElement.ARRAY_FACTORY);
        if (childrenAsPsiElements == null) {
            $$$reportNull$$$0(0);
        }
        return childrenAsPsiElements;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public int getChildRole(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(1);
        }
        if (aSTNode.getElementType() == JavaTokenType.COMMA) {
            return 23;
        }
        return super.getChildRole(aSTNode);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public void deleteChildInternal(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(2);
        }
        TokenSet tokenSet = DECLARED_ELEMENT_BIT_SET;
        if (tokenSet.contains(aSTNode.getElementType())) {
            PsiElement[] declaredElements = getDeclaredElements();
            int length = declaredElements.length;
            if (length == 1) {
                getTreeParent().deleteChildInternal(this);
                return;
            }
            if (length > 0) {
                if (SourceTreeToPsiMap.psiElementToTree(declaredElements[length - 1]) == aSTNode) {
                    removeCommaBefore(aSTNode);
                    SourceTreeToPsiMap.psiToTreeNotNull(declaredElements[length - 2]).addChild(Factory.createSingleLeafElement(JavaTokenType.SEMICOLON, ";", SharedImplUtil.findCharTableByTree(this), getManager()), null);
                } else if (SourceTreeToPsiMap.psiElementToTree(declaredElements[0]) == aSTNode) {
                    CompositeElement compositeElement = (CompositeElement) SourceTreeToPsiMap.psiToTreeNotNull(declaredElements[1]);
                    ASTNode aSTNodeCopyElement = aSTNode.copyElement();
                    ASTNode aSTNodeFindChildByRole = ((CompositeElement) aSTNodeCopyElement).findChildByRole(9);
                    removeCommaBefore(compositeElement);
                    compositeElement.addInternal((TreeElement) aSTNodeCopyElement.getFirstChildNode(), aSTNodeFindChildByRole.getTreePrev(), null, Boolean.FALSE);
                } else {
                    removeCommaBefore(aSTNode);
                }
            }
        }
        super.deleteChildInternal(aSTNode);
        TreeElement firstChildNode = getFirstChildNode();
        if (firstChildNode == null || tokenSet.contains(firstChildNode.getElementType())) {
            return;
        }
        TreeElement treeElement = firstChildNode;
        while (true) {
            TreeElement treeNext = treeElement.getTreeNext();
            if (treeNext == null || DECLARED_ELEMENT_BIT_SET.contains(treeNext.getElementType())) {
                break;
            } else {
                treeElement = treeNext;
            }
        }
        getTreeParent().addInternal(firstChildNode, treeElement, this, Boolean.TRUE);
    }

    private void removeCommaBefore(ASTNode aSTNode) {
        do {
            aSTNode = aSTNode.getTreePrev();
            if (aSTNode == null) {
                break;
            }
        } while (ElementType.JAVA_COMMENT_OR_WHITESPACE_BIT_SET.contains(aSTNode.getElementType()));
        if (aSTNode == null || aSTNode.getElementType() != JavaTokenType.COMMA) {
            return;
        }
        deleteChildInternal(aSTNode);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(3);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitDeclarationStatement(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean processDeclarations(PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, PsiElement psiElement, PsiElement psiElement2) {
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(4);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(5);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(6);
        }
        psiScopeProcessor.handleEvent(PsiScopeProcessor.Event.SET_DECLARATION_HOLDER, this);
        for (PsiElement psiElement3 : getDeclaredElements()) {
            if (psiElement3 != psiElement) {
                if (!psiScopeProcessor.execute(psiElement3, resolveState)) {
                    return false;
                }
            } else {
                ElementClassHint elementClassHint = (ElementClassHint) psiScopeProcessor.getHint(ElementClassHint.KEY);
                if ((psiElement instanceof PsiClass) && ((elementClassHint == null || elementClassHint.shouldProcess(ElementClassHint.DeclarationKind.CLASS)) && !psiScopeProcessor.execute(psiElement, resolveState))) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiDeclarationStatement";
    }
}
