package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiCaseLabelElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiCaseLabelElementList;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiParenthesizedExpression;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.ElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaSourceUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;

/* JADX INFO: loaded from: classes3.dex */
public class PsiCaseLabelElementListImpl extends CompositePsiElement implements PsiCaseLabelElementList {
    private volatile PsiCaseLabelElement[] myElements;

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
                objArr[0] = "visitor";
                break;
            case 2:
                objArr[0] = "child";
                break;
            case 3:
                objArr[0] = "processor";
                break;
            case 4:
                objArr[0] = "state";
                break;
            case 5:
            case 6:
                objArr[0] = "place";
                break;
            default:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiCaseLabelElementListImpl";
                break;
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiCaseLabelElementListImpl";
                break;
            default:
                objArr[1] = "getElements";
                break;
        }
        switch (i) {
            case 1:
                objArr[2] = "accept";
                break;
            case 2:
                objArr[2] = "deleteChildInternal";
                break;
            case 3:
            case 4:
            case 5:
                objArr[2] = "processDeclarations";
                break;
            case 6:
                objArr[2] = "oneOfElements";
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

    public PsiCaseLabelElementListImpl() {
        super(JavaElementType.CASE_LABEL_ELEMENT_LIST);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement
    public void clearCaches() {
        super.clearCaches();
        this.myElements = null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiCaseLabelElementList
    public PsiCaseLabelElement[] getElements() {
        PsiCaseLabelElement[] psiCaseLabelElementArr = this.myElements;
        if (psiCaseLabelElementArr == null) {
            psiCaseLabelElementArr = (PsiCaseLabelElement[]) getChildrenAsPsiElements(ElementType.JAVA_CASE_LABEL_ELEMENT_BIT_SET, PsiCaseLabelElement.ARRAY_FACTORY);
            if (psiCaseLabelElementArr.length > 10) {
                this.myElements = psiCaseLabelElementArr;
            }
        }
        if (psiCaseLabelElementArr == null) {
            $$$reportNull$$$0(0);
        }
        return psiCaseLabelElementArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiCaseLabelElementList
    public int getElementCount() {
        PsiCaseLabelElement[] psiCaseLabelElementArr = this.myElements;
        if (psiCaseLabelElementArr != null) {
            return psiCaseLabelElementArr.length;
        }
        return countChildren(ElementType.JAVA_CASE_LABEL_ELEMENT_BIT_SET);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(1);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitCaseLabelElementList(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public TreeElement addInternal(TreeElement treeElement, ASTNode aSTNode, ASTNode aSTNode2, Boolean bool) {
        TreeElement treeElementAddInternal = super.addInternal(treeElement, aSTNode, aSTNode2, bool);
        while (!ElementType.JAVA_CASE_LABEL_ELEMENT_BIT_SET.contains(treeElement.getElementType())) {
            if (treeElement == aSTNode) {
                return treeElementAddInternal;
            }
            treeElement = treeElement.getTreeNext();
        }
        JavaSourceUtil.addSeparatingComma(this, treeElement, ElementType.JAVA_CASE_LABEL_ELEMENT_BIT_SET);
        return treeElementAddInternal;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public void deleteChildInternal(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(2);
        }
        if (ElementType.JAVA_CASE_LABEL_ELEMENT_BIT_SET.contains(aSTNode.getElementType())) {
            JavaSourceUtil.deleteSeparatingComma(this, aSTNode);
        }
        super.deleteChildInternal(aSTNode);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiCaseLabelElementList";
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean processDeclarations(PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, PsiElement psiElement, PsiElement psiElement2) {
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(3);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(4);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(5);
        }
        if (oneOfElements(psiElement2)) {
            return true;
        }
        for (PsiCaseLabelElement psiCaseLabelElement : getElements()) {
            if (!psiCaseLabelElement.processDeclarations(psiScopeProcessor, resolveState, null, psiElement2)) {
                return false;
            }
        }
        return true;
    }

    private boolean oneOfElements(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(6);
        }
        return Arrays.stream(getElements()).map(new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java.PsiCaseLabelElementListImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return PsiCaseLabelElementListImpl.skipParenthesis((PsiCaseLabelElement) obj);
            }
        }).anyMatch(Predicate.isEqual(psiElement));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static PsiCaseLabelElement skipParenthesis(PsiCaseLabelElement psiCaseLabelElement) {
        return !(psiCaseLabelElement instanceof PsiParenthesizedExpression) ? psiCaseLabelElement : PsiUtil.skipParenthesizedExprDown((PsiParenthesizedExpression) psiCaseLabelElement);
    }
}
