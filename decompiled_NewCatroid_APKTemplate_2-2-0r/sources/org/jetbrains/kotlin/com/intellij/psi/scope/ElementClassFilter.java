package org.jetbrains.kotlin.com.intellij.psi.scope;

import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiEnumConstant;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiPackage;
import org.jetbrains.kotlin.com.intellij.psi.PsiVariable;
import org.jetbrains.kotlin.com.intellij.psi.filters.ElementFilter;

/* JADX INFO: loaded from: classes3.dex */
public final class ElementClassFilter implements ElementFilter {
    private final ElementClassHint.DeclarationKind myKind;
    public static final ElementClassFilter PACKAGE = new ElementClassFilter(ElementClassHint.DeclarationKind.PACKAGE);
    public static final ElementClassFilter VARIABLE = new ElementClassFilter(ElementClassHint.DeclarationKind.VARIABLE);
    public static final ElementClassFilter METHOD = new ElementClassFilter(ElementClassHint.DeclarationKind.METHOD);
    public static final ElementClassFilter CLASS = new ElementClassFilter(ElementClassHint.DeclarationKind.CLASS);
    public static final ElementClassFilter FIELD = new ElementClassFilter(ElementClassHint.DeclarationKind.FIELD);
    public static final ElementClassFilter ENUM_CONST = new ElementClassFilter(ElementClassHint.DeclarationKind.ENUM_CONST);

    @Override // org.jetbrains.kotlin.com.intellij.psi.filters.ElementFilter
    public boolean isClassAcceptable(Class cls) {
        return true;
    }

    private ElementClassFilter(ElementClassHint.DeclarationKind declarationKind) {
        this.myKind = declarationKind;
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.com.intellij.psi.scope.ElementClassFilter$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$intellij$psi$scope$ElementClassHint$DeclarationKind;

        static {
            int[] iArr = new int[ElementClassHint.DeclarationKind.values().length];
            $SwitchMap$com$intellij$psi$scope$ElementClassHint$DeclarationKind = iArr;
            try {
                iArr[ElementClassHint.DeclarationKind.CLASS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$intellij$psi$scope$ElementClassHint$DeclarationKind[ElementClassHint.DeclarationKind.ENUM_CONST.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$intellij$psi$scope$ElementClassHint$DeclarationKind[ElementClassHint.DeclarationKind.FIELD.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$intellij$psi$scope$ElementClassHint$DeclarationKind[ElementClassHint.DeclarationKind.METHOD.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$intellij$psi$scope$ElementClassHint$DeclarationKind[ElementClassHint.DeclarationKind.PACKAGE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$intellij$psi$scope$ElementClassHint$DeclarationKind[ElementClassHint.DeclarationKind.VARIABLE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.filters.ElementFilter
    public boolean isAcceptable(Object obj, PsiElement psiElement) {
        switch (AnonymousClass1.$SwitchMap$com$intellij$psi$scope$ElementClassHint$DeclarationKind[this.myKind.ordinal()]) {
            case 1:
                return obj instanceof PsiClass;
            case 2:
                return obj instanceof PsiEnumConstant;
            case 3:
                return obj instanceof PsiField;
            case 4:
                return obj instanceof PsiMethod;
            case 5:
                return obj instanceof PsiPackage;
            case 6:
                return obj instanceof PsiVariable;
            default:
                return false;
        }
    }
}
