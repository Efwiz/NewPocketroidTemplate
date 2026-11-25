package org.jetbrains.kotlin.com.intellij.psi.impl.light;

import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiIdentifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiMirrorElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public class LightParameterWrapper extends LightElement implements PsiMirrorElement, PsiParameter {
    private final PsiParameter myPrototype;
    private final PsiSubstitutor mySubstitutor;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 3 || i == 4 || i == 6) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 3 || i == 4 || i == 6) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "substitutor";
                break;
            case 2:
            case 3:
            case 4:
            case 6:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightParameterWrapper";
                break;
            case 5:
            case 7:
                objArr[0] = "name";
                break;
            default:
                objArr[0] = "prototype";
                break;
        }
        if (i == 2) {
            objArr[1] = "getPrototype";
        } else if (i == 3) {
            objArr[1] = "getName";
        } else if (i == 4) {
            objArr[1] = "getType";
        } else if (i != 6) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightParameterWrapper";
        } else {
            objArr[1] = "getDeclarationScope";
        }
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 6:
                break;
            case 5:
                objArr[2] = "hasModifierProperty";
                break;
            case 7:
                objArr[2] = "setName";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 3 && i != 4 && i != 6) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LightParameterWrapper(PsiParameter psiParameter, PsiSubstitutor psiSubstitutor) {
        super(psiParameter.getManager(), psiParameter.getLanguage());
        if (psiParameter == null) {
            $$$reportNull$$$0(0);
        }
        if (psiSubstitutor == null) {
            $$$reportNull$$$0(1);
        }
        this.myPrototype = psiParameter;
        this.mySubstitutor = psiSubstitutor;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiMirrorElement
    public PsiParameter getPrototype() {
        PsiParameter psiParameter = this.myPrototype;
        if (psiParameter == null) {
            $$$reportNull$$$0(2);
        }
        return psiParameter;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    public String getName() {
        String name2 = this.myPrototype.getName();
        if (name2 == null) {
            $$$reportNull$$$0(3);
        }
        return name2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiParameter, org.jetbrains.kotlin.com.intellij.lang.jvm.JvmParameter
    public PsiType getType() {
        PsiType psiTypeSubstitute = this.mySubstitutor.substitute(this.myPrototype.getType());
        if (psiTypeSubstitute == null) {
            $$$reportNull$$$0(4);
        }
        return psiTypeSubstitute;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    /* JADX INFO: renamed from: getModifierList */
    public PsiModifierList mo6095getModifierList() {
        return this.myPrototype.mo6095getModifierList();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    public boolean hasModifierProperty(String str) {
        if (str == null) {
            $$$reportNull$$$0(5);
        }
        return this.myPrototype.hasModifierProperty(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiParameter
    public PsiElement getDeclarationScope() {
        PsiElement declarationScope = this.myPrototype.getDeclarationScope();
        if (declarationScope == null) {
            $$$reportNull$$$0(6);
        }
        return declarationScope;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public PsiExpression getInitializer() {
        return this.myPrototype.getInitializer();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiParameter
    public boolean isVarArgs() {
        return this.myPrototype.isVarArgs();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiParameter, org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public PsiTypeElement getTypeElement() {
        return this.myPrototype.getTypeElement();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public boolean hasInitializer() {
        return this.myPrototype.hasInitializer();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public void normalizeDeclaration() throws IncorrectOperationException {
        throw new IncorrectOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable
    public Object computeConstantValue() {
        return this.myPrototype.computeConstantValue();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable, org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner
    /* JADX INFO: renamed from: getNameIdentifier */
    public PsiIdentifier mo7392getNameIdentifier() {
        return this.myPrototype.mo7392getNameIdentifier();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiVariable, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public PsiElement setName(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(7);
        }
        throw new IncorrectOperationException();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "List PSI parameter wrapper: " + this.myPrototype;
    }
}
