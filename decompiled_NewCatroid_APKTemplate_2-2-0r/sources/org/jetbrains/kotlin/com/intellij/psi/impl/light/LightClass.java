package org.jetbrains.kotlin.com.intellij.psi.impl.light;

import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.lang.java.JavaLanguage;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;

/* JADX INFO: loaded from: classes3.dex */
public class LightClass extends AbstractLightClass {
    private final PsiClass myDelegate;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 2 ? 3 : 2];
        if (i != 2) {
            objArr[0] = MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX;
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightClass";
        }
        if (i != 2) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/light/LightClass";
        } else {
            objArr[1] = "getDelegate";
        }
        if (i != 2) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 2) {
            throw new IllegalStateException(str2);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public LightClass(PsiClass psiClass) {
        this(psiClass, JavaLanguage.INSTANCE);
        if (psiClass == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LightClass(PsiClass psiClass, Language language) {
        super(psiClass.getManager(), language);
        if (psiClass == null) {
            $$$reportNull$$$0(1);
        }
        this.myDelegate = psiClass;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass
    /* JADX INFO: renamed from: getDelegate */
    public PsiClass mo6090getDelegate() {
        PsiClass psiClass = this.myDelegate;
        if (psiClass == null) {
            $$$reportNull$$$0(2);
        }
        return psiClass;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement copy() {
        return new LightClass(this);
    }
}
