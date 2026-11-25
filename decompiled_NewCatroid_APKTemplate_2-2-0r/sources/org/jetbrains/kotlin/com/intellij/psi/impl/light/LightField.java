package org.jetbrains.kotlin.com.intellij.psi.impl.light;

import org.jetbrains.kotlin.com.intellij.lang.java.JavaLanguage;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiIdentifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiClassImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocComment;
import org.jetbrains.kotlin.com.intellij.psi.search.SearchScope;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public class LightField extends LightElement implements PsiField {
    private final PsiClass myContainingClass;
    private final PsiField myField;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 3 || i == 4 || i == 5 || i == 6) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 3 || i == 4 || i == 5 || i == 6) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "field";
                break;
            case 2:
                objArr[0] = "containingClass";
                break;
            case 3:
            case 4:
            case 5:
            case 6:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightField";
                break;
            case 7:
            case 8:
                objArr[0] = "name";
                break;
            default:
                objArr[0] = "manager";
                break;
        }
        if (i == 3) {
            objArr[1] = "getUseScope";
        } else if (i == 4) {
            objArr[1] = "getName";
        } else if (i == 5) {
            objArr[1] = "getNameIdentifier";
        } else if (i != 6) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightField";
        } else {
            objArr[1] = "getType";
        }
        switch (i) {
            case 3:
            case 4:
            case 5:
            case 6:
                break;
            case 7:
                objArr[2] = "setName";
                break;
            case 8:
                objArr[2] = "hasModifierProperty";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 3 && i != 4 && i != 5 && i != 6) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LightField(PsiManager psiManager, PsiField psiField, PsiClass psiClass) {
        super(psiManager, JavaLanguage.INSTANCE);
        if (psiManager == null) {
            $$$reportNull$$$0(0);
        }
        if (psiField == null) {
            $$$reportNull$$$0(1);
        }
        if (psiClass == null) {
            $$$reportNull$$$0(2);
        }
        this.myField = psiField;
        this.myContainingClass = psiClass;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiField, org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    /* JADX INFO: renamed from: setInitializer */
    public void mo6096setInitializer(PsiExpression psiExpression) throws IncorrectOperationException {
        throw new IncorrectOperationException("Not supported");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public SearchScope getUseScope() {
        SearchScope useScope = this.myField.getUseScope();
        if (useScope == null) {
            $$$reportNull$$$0(3);
        }
        return useScope;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean isEquivalentTo(PsiElement psiElement) {
        return PsiClassImplUtil.isFieldEquivalentTo(this, psiElement);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    /* JADX INFO: renamed from: getName */
    public String getMyName() {
        String myName = this.myField.getMyName();
        if (myName == null) {
            $$$reportNull$$$0(4);
        }
        return myName;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiField, org.jetbrains.kotlin.com.intellij.psi.PsiVariable, org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner
    /* JADX INFO: renamed from: getNameIdentifier */
    public PsiIdentifier mo7392getNameIdentifier() {
        PsiIdentifier psiIdentifierMo7392getNameIdentifier = this.myField.mo7392getNameIdentifier();
        if (psiIdentifierMo7392getNameIdentifier == null) {
            $$$reportNull$$$0(5);
        }
        return psiIdentifierMo7392getNameIdentifier;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaDocumentedElement
    /* JADX INFO: renamed from: getDocComment */
    public PsiDocComment mo6091getDocComment() {
        return this.myField.mo6091getDocComment();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDocCommentOwner
    public boolean isDeprecated() {
        return this.myField.isDeprecated();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJvmMember
    /* JADX INFO: renamed from: getContainingClass */
    public PsiClass mo6092getContainingClass() {
        return this.myContainingClass;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiField
    public PsiType getType() {
        PsiType type = this.myField.getType();
        if (type == null) {
            $$$reportNull$$$0(6);
        }
        return type;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public PsiTypeElement getTypeElement() {
        return this.myField.getTypeElement();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public PsiExpression getInitializer() {
        return this.myField.getInitializer();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public boolean hasInitializer() {
        return this.myField.hasInitializer();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public void normalizeDeclaration() throws IncorrectOperationException {
        throw new IncorrectOperationException("Not supported");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public Object computeConstantValue() {
        return this.myField.computeConstantValue();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public PsiElement setName(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(7);
        }
        throw new IncorrectOperationException("Not supported");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    /* JADX INFO: renamed from: getModifierList */
    public PsiModifierList mo6095getModifierList() {
        return this.myField.mo6095getModifierList();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    public boolean hasModifierProperty(String str) {
        if (str == null) {
            $$$reportNull$$$0(8);
        }
        return this.myField.hasModifierProperty(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String getText() {
        return this.myField.getText();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement copy() {
        return new LightField(this.myManager, (PsiField) this.myField.copy(), this.myContainingClass);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public TextRange getTextRange() {
        return this.myField.getTextRange();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement, org.jetbrains.kotlin.com.intellij.pom.PomTarget
    public boolean isValid() {
        return this.myContainingClass.isValid();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "PsiField:" + getMyName();
    }
}
