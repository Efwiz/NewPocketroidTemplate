package org.jetbrains.kotlin.com.intellij.psi.impl;

import javax.swing.Icon;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentation;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public abstract class FakePsiElement extends PsiElementBase implements ItemPresentation, PsiNamedElement {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 2 ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[i != 2 ? 2 : 3];
        if (i != 2) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/FakePsiElement";
        } else {
            objArr[0] = "name";
        }
        if (i == 1) {
            objArr[1] = "getChildren";
        } else if (i != 2) {
            objArr[1] = "getLanguage";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/FakePsiElement";
        }
        if (i == 2) {
            objArr[2] = "setName";
        }
        String str2 = String.format(str, objArr);
        if (i == 2) {
            throw new IllegalArgumentException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement findElementAt(int i) {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getFirstChild() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.navigation.ItemPresentation
    public Icon getIcon(boolean z) {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getLastChild() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getNextSibling() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public ASTNode getNode() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem
    public ItemPresentation getPresentation() {
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getPrevSibling() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public int getStartOffsetInParent() {
        return 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String getText() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public int getTextLength() {
        return 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public int getTextOffset() {
        return 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public TextRange getTextRange() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean isPhysical() {
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public PsiElement setName(String str) throws IncorrectOperationException {
        if (str != null) {
            return null;
        }
        $$$reportNull$$$0(2);
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean textContains(char c) {
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public Language getLanguage() {
        Language language = Language.ANY;
        if (language == null) {
            $$$reportNull$$$0(0);
        }
        return language;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement[] getChildren() {
        PsiElement[] psiElementArr = PsiElement.EMPTY_ARRAY;
        if (psiElementArr == null) {
            $$$reportNull$$$0(1);
        }
        return psiElementArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public char[] textToCharArray() {
        return new char[0];
    }

    public String getPresentableText() {
        return getName();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.ElementBase, org.jetbrains.kotlin.com.intellij.openapi.util.Iconable
    public final Icon getIcon(int i) {
        return super.getIcon(i);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.ElementBase
    protected final Icon getElementIcon(int i) {
        return super.getElementIcon(i);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiManager getManager() {
        PsiElement parent = getLightParent();
        if (parent != null) {
            return parent.getManager();
        }
        return null;
    }
}
