package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree;

import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiErrorElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiWhiteSpace;
import org.jetbrains.kotlin.com.intellij.psi.TokenType;
import org.jetbrains.kotlin.com.intellij.psi.templateLanguages.OuterLanguageElement;

/* JADX INFO: loaded from: classes3.dex */
public class PsiErrorElementImpl extends CompositePsiElement implements PsiErrorElement {
    private final String myErrorDescription;

    /* JADX WARN: Code duplicated, block: B:21:0x002f  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 3 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 3 || i == 4) ? 2 : 3];
        if (i == 1) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/PsiErrorElementImpl";
        } else if (i == 2) {
            objArr[0] = "visitor";
        } else if (i == 3 || i == 4) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/PsiErrorElementImpl";
        } else {
            objArr[0] = "errorDescription";
        }
        if (i == 1) {
            objArr[1] = "getErrorDescription";
        } else if (i == 3 || i == 4) {
            objArr[1] = "getLanguage";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/source/tree/PsiErrorElementImpl";
        }
        if (i != 1) {
            if (i == 2) {
                objArr[2] = "accept";
            } else if (i != 3 && i != 4) {
                objArr[2] = "<init>";
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 3 && i != 4) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiErrorElementImpl(String str) {
        super(TokenType.ERROR_ELEMENT);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        this.myErrorDescription = str;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiErrorElement
    public String getErrorDescription() {
        String str = this.myErrorDescription;
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        return str;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(2);
        }
        psiElementVisitor.visitErrorElement(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiErrorElement:" + getErrorDescription();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.CompositePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public Language getLanguage() {
        PsiElement nextSibling = this;
        while (true) {
            nextSibling = nextSibling.getNextSibling();
            if (nextSibling == null || (nextSibling instanceof OuterLanguageElement)) {
                break;
            }
            if (!(nextSibling instanceof PsiWhiteSpace) && !(nextSibling instanceof PsiErrorElement)) {
                Language language = nextSibling.getLanguage();
                if (language == null) {
                    $$$reportNull$$$0(4);
                }
                return language;
            }
        }
        PsiElement parent = getLightParent();
        Language language2 = parent == null ? Language.ANY : parent.getLanguage();
        if (language2 == null) {
            $$$reportNull$$$0(3);
        }
        return language2;
    }
}
