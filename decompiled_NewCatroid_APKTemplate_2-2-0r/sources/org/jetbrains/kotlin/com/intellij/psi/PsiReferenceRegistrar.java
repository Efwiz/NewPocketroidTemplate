package org.jetbrains.kotlin.com.intellij.psi;

import org.antlr.stringtemplate.language.ASTExpr;
import org.catrobat.catroid.common.Constants;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolder;
import org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolderBase;
import org.jetbrains.kotlin.com.intellij.patterns.ElementPattern;

/* JADX INFO: loaded from: classes3.dex */
public abstract class PsiReferenceRegistrar implements UserDataHolder {
    public static final double DEFAULT_PRIORITY = 0.0d;
    public static final double HIGHER_PRIORITY = 100.0d;
    public static final double LOWER_PRIORITY = -100.0d;
    private final UserDataHolderBase myUserDataHolder = new UserDataHolderBase();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = Constants.EXTRA_PROVIDER;
        } else if (i == 2 || i == 3) {
            objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
        } else {
            objArr[0] = "pattern";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/PsiReferenceRegistrar";
        if (i == 2) {
            objArr[2] = "getUserData";
        } else if (i != 3) {
            objArr[2] = "registerReferenceProvider";
        } else {
            objArr[2] = "putUserData";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    public abstract <T extends PsiElement> void registerReferenceProvider(ElementPattern<T> elementPattern, PsiReferenceProvider psiReferenceProvider, double d);

    public void registerReferenceProvider(ElementPattern<? extends PsiElement> elementPattern, PsiReferenceProvider psiReferenceProvider) {
        if (elementPattern == null) {
            $$$reportNull$$$0(0);
        }
        if (psiReferenceProvider == null) {
            $$$reportNull$$$0(1);
        }
        registerReferenceProvider(elementPattern, psiReferenceProvider, 0.0d);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolder
    public <T> T getUserData(Key<T> key) {
        if (key == null) {
            $$$reportNull$$$0(2);
        }
        return (T) this.myUserDataHolder.getUserData(key);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolder
    public <T> void putUserData(Key<T> key, T t) {
        if (key == null) {
            $$$reportNull$$$0(3);
        }
        this.myUserDataHolder.putUserData(key, t);
    }
}
