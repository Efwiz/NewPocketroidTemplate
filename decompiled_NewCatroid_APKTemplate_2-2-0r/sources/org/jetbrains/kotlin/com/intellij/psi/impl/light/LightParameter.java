package org.jetbrains.kotlin.com.intellij.psi.impl.light;

import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.lang.jvm.types.JvmType;
import org.jetbrains.kotlin.com.intellij.psi.JavaElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiEllipsisType;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;

/* JADX INFO: loaded from: classes3.dex */
public class LightParameter extends LightVariableBuilder<LightVariableBuilder> implements PsiParameter {
    private final PsiElement myDeclarationScope;
    private final boolean myVarArgs;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 21 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 21 ? 3 : 2];
        switch (i) {
            case 1:
            case 4:
            case 8:
            case 13:
            case 17:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 2:
            case 5:
            case 9:
            case 14:
            case 18:
                objArr[0] = "declarationScope";
                break;
            case 3:
            case 7:
            case 12:
            case 16:
            default:
                objArr[0] = "name";
                break;
            case 6:
            case 10:
            case 15:
            case 19:
                objArr[0] = "language";
                break;
            case 11:
            case 20:
                objArr[0] = "modifierList";
                break;
            case 21:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightParameter";
                break;
            case 22:
                objArr[0] = "visitor";
                break;
        }
        if (i != 21) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightParameter";
        } else {
            objArr[1] = "getDeclarationScope";
        }
        if (i != 21) {
            if (i != 22) {
                objArr[2] = "<init>";
            } else {
                objArr[2] = "accept";
            }
        }
        String str2 = String.format(str, objArr);
        if (i == 21) {
            throw new IllegalStateException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiParameter, org.jetbrains.kotlin.com.intellij.lang.jvm.JvmParameter
    public /* bridge */ /* synthetic */ JvmType getType() {
        return super.getType();
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public LightParameter(String str, PsiType psiType, PsiElement psiElement) {
        this(str, psiType, psiElement, psiElement.getLanguage());
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        if (psiType == null) {
            $$$reportNull$$$0(1);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(2);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public LightParameter(String str, PsiType psiType, PsiElement psiElement, Language language) {
        this(str, psiType, psiElement, language, psiType instanceof PsiEllipsisType);
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        if (psiType == null) {
            $$$reportNull$$$0(4);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(5);
        }
        if (language == null) {
            $$$reportNull$$$0(6);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public LightParameter(String str, PsiType psiType, PsiElement psiElement, Language language, LightModifierList lightModifierList) {
        this(str, psiType, psiElement, language, lightModifierList, psiType instanceof PsiEllipsisType);
        if (str == null) {
            $$$reportNull$$$0(7);
        }
        if (psiType == null) {
            $$$reportNull$$$0(8);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(9);
        }
        if (language == null) {
            $$$reportNull$$$0(10);
        }
        if (lightModifierList == null) {
            $$$reportNull$$$0(11);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public LightParameter(String str, PsiType psiType, PsiElement psiElement, Language language, boolean z) {
        this(str, psiType, psiElement, language, new LightModifierList(psiElement.getManager()), z);
        if (str == null) {
            $$$reportNull$$$0(12);
        }
        if (psiType == null) {
            $$$reportNull$$$0(13);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(14);
        }
        if (language == null) {
            $$$reportNull$$$0(15);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LightParameter(String str, PsiType psiType, PsiElement psiElement, Language language, LightModifierList lightModifierList, boolean z) {
        super(psiElement.getManager(), str, psiType, language, lightModifierList);
        if (str == null) {
            $$$reportNull$$$0(16);
        }
        if (psiType == null) {
            $$$reportNull$$$0(17);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(18);
        }
        if (language == null) {
            $$$reportNull$$$0(19);
        }
        if (lightModifierList == null) {
            $$$reportNull$$$0(20);
        }
        this.myDeclarationScope = psiElement;
        this.myVarArgs = z;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiParameter
    public PsiElement getDeclarationScope() {
        PsiElement psiElement = this.myDeclarationScope;
        if (psiElement == null) {
            $$$reportNull$$$0(21);
        }
        return psiElement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor == null) {
            $$$reportNull$$$0(22);
        }
        if (psiElementVisitor instanceof JavaElementVisitor) {
            ((JavaElementVisitor) psiElementVisitor).visitParameter(this);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightVariableBuilder, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "Light Parameter";
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiParameter
    public boolean isVarArgs() {
        return this.myVarArgs;
    }
}
