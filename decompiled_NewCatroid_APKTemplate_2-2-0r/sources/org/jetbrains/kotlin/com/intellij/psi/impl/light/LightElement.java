package org.jetbrains.kotlin.com.intellij.psi.impl.light;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public abstract class LightElement extends PsiElementBase {
    private final Language myLanguage;
    protected final PsiManager myManager;
    private volatile PsiElement myNavigationElement;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 4 || i == 13) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 4 || i == 13) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "language";
                break;
            case 2:
            case 3:
            case 4:
            case 13:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightElement";
                break;
            case 5:
                objArr[0] = "text";
                break;
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 11:
                objArr[0] = "newElement";
                break;
            case 12:
                objArr[0] = "visitor";
                break;
            case 14:
                objArr[0] = "navigationElement";
                break;
            default:
                objArr[0] = "manager";
                break;
        }
        if (i == 2) {
            objArr[1] = "getLanguage";
        } else if (i == 3) {
            objArr[1] = "getChildren";
        } else if (i == 4) {
            objArr[1] = "textToCharArray";
        } else if (i != 13) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightElement";
        } else {
            objArr[1] = "getNavigationElement";
        }
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 13:
                break;
            case 5:
            case 6:
                objArr[2] = "textMatches";
                break;
            case 7:
                objArr[2] = "checkAdd";
                break;
            case 8:
                objArr[2] = ProductAction.ACTION_ADD;
                break;
            case 9:
                objArr[2] = "addBefore";
                break;
            case 10:
                objArr[2] = "addAfter";
                break;
            case 11:
                objArr[2] = SchemaSymbols.ATTVAL_REPLACE;
                break;
            case 12:
                objArr[2] = "accept";
                break;
            case 14:
                objArr[2] = "setNavigationElement";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 4 && i != 13) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(12);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement copy() {
        return null;
    }

    public PsiElement findElementAt(int i) {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiFile getContainingFile() {
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

    /* JADX INFO: renamed from: getParent */
    public PsiElement getLightParent() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getPrevSibling() {
        return null;
    }

    public int getStartOffsetInParent() {
        return -1;
    }

    public String getText() {
        return null;
    }

    public int getTextOffset() {
        return -1;
    }

    public TextRange getTextRange() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean isPhysical() {
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean isWritable() {
        return false;
    }

    @Override // java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public abstract String toString();

    protected LightElement(PsiManager psiManager, Language language) {
        if (psiManager == null) {
            $$$reportNull$$$0(0);
        }
        if (language == null) {
            $$$reportNull$$$0(1);
        }
        this.myNavigationElement = this;
        this.myManager = psiManager;
        this.myLanguage = language;
    }

    public Language getLanguage() {
        Language language = this.myLanguage;
        if (language == null) {
            $$$reportNull$$$0(2);
        }
        return language;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiManager getManager() {
        return this.myManager;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement[] getChildren() {
        PsiElement[] psiElementArr = PsiElement.EMPTY_ARRAY;
        if (psiElementArr == null) {
            $$$reportNull$$$0(3);
        }
        return psiElementArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public final int getTextLength() {
        String text = getText();
        if (text != null) {
            return text.length();
        }
        return 0;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public char[] textToCharArray() {
        char[] charArray = getText().toCharArray();
        if (charArray == null) {
            $$$reportNull$$$0(4);
        }
        return charArray;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean textMatches(CharSequence charSequence) {
        if (charSequence == null) {
            $$$reportNull$$$0(5);
        }
        return getText().equals(charSequence.toString());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean textMatches(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(6);
        }
        return getText().equals(psiElement.getText());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement, org.jetbrains.kotlin.com.intellij.pom.PomTarget
    public boolean isValid() {
        PsiElement navigationElement = getNavigationElement();
        if (navigationElement != this) {
            return navigationElement.isValid();
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void checkAdd(PsiElement psiElement) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(7);
        }
        throw new IncorrectOperationException(getClass().getName());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement add(PsiElement psiElement) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(8);
        }
        throw new IncorrectOperationException(getClass().getName());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement addBefore(PsiElement psiElement, PsiElement psiElement2) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(9);
        }
        throw new IncorrectOperationException(getClass().getName());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement addAfter(PsiElement psiElement, PsiElement psiElement2) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(10);
        }
        throw new IncorrectOperationException(getClass().getName());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void delete() throws IncorrectOperationException {
        throw new IncorrectOperationException(getClass().getName());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void checkDelete() throws IncorrectOperationException {
        throw new IncorrectOperationException(getClass().getName());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement replace(PsiElement psiElement) throws IncorrectOperationException {
        if (psiElement == null) {
            $$$reportNull$$$0(11);
        }
        throw new IncorrectOperationException(getClass().getName());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getNavigationElement() {
        PsiElement psiElement = this.myNavigationElement;
        if (psiElement == null) {
            $$$reportNull$$$0(13);
        }
        return psiElement;
    }

    public void setNavigationElement(PsiElement psiElement) {
        if (psiElement == null) {
            $$$reportNull$$$0(14);
        }
        PsiElement navigationElement = psiElement.getNavigationElement();
        if (navigationElement != psiElement && navigationElement != null) {
            psiElement = navigationElement;
        }
        this.myNavigationElement = psiElement;
    }
}
