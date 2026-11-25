package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.java;

import java.util.Iterator;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiResourceList;
import org.jetbrains.kotlin.com.intellij.psi.PsiResourceListElement;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.SyntaxTraverser;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaElementType;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;

/* JADX INFO: loaded from: classes3.dex */
public class PsiResourceListImpl extends CompositePsiElement implements PsiResourceList {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 2 || i == 3 || i == 4 || i == 5) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 1 || i == 2 || i == 3 || i == 4 || i == 5) ? 3 : 2];
        if (i == 1) {
            objArr[0] = "visitor";
        } else if (i == 2) {
            objArr[0] = "processor";
        } else if (i == 3) {
            objArr[0] = "state";
        } else if (i == 4) {
            objArr[0] = "place";
        } else if (i != 5) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiResourceListImpl";
        } else {
            objArr[0] = "child";
        }
        if (i == 1 || i == 2 || i == 3 || i == 4 || i == 5) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/java/PsiResourceListImpl";
        } else {
            objArr[1] = "iterator";
        }
        if (i == 1) {
            objArr[2] = "accept";
        } else if (i == 2 || i == 3 || i == 4) {
            objArr[2] = "processDeclarations";
        } else if (i == 5) {
            objArr[2] = "deleteChildInternal";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2 && i != 3 && i != 4 && i != 5) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    public PsiResourceListImpl() {
        super(JavaElementType.RESOURCE_LIST);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiResourceList
    public int getResourceVariablesCount() {
        int i = 0;
        for (PsiElement firstChild = getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
            if (firstChild instanceof PsiResourceListElement) {
                i++;
            }
        }
        return i;
    }

    @Override // java.lang.Iterable
    public Iterator<PsiResourceListElement> iterator() {
        Iterator<PsiResourceListElement> it = SyntaxTraverser.psiTraverser().children(this).filter(PsiResourceListElement.class).iterator();
        if (it == null) {
            $$$reportNull$$$0(0);
        }
        return it;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(1);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitResourceList(this);
        } else {
            psiElementVisitor.visitElement(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean processDeclarations(PsiScopeProcessor psiScopeProcessor, ResolveState resolveState, PsiElement psiElement, PsiElement psiElement2) {
        if (psiScopeProcessor == null) {
            $$$reportNull$$$0(2);
        }
        if (resolveState == null) {
            $$$reportNull$$$0(3);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(4);
        }
        return PsiImplUtil.processDeclarationsInResourceList(this, psiScopeProcessor, resolveState, psiElement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositeElement
    public void deleteChildInternal(ASTNode aSTNode) {
        if (aSTNode == null) {
            $$$reportNull$$$0(5);
        }
        if ((aSTNode.getPsi() instanceof PsiResourceListElement) && getResourceVariablesCount() == 1) {
            getTreeParent().deleteChildInternal(this);
        } else {
            super.deleteChildInternal(aSTNode);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiResourceList:" + getText();
    }
}
