package org.jetbrains.kotlin.com.intellij.psi.util;

import org.jetbrains.kotlin.com.intellij.codeInsight.runner.JavaMainMethodProvider;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayType;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;

/* JADX INFO: loaded from: classes3.dex */
public final class PsiMethodUtil {
    public static final Condition<PsiClass> MAIN_CLASS = new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.util.PsiMethodUtil$$ExternalSyntheticLambda0
        @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
        public final boolean value(Object obj) {
            return PsiMethodUtil.lambda$static$0((PsiClass) obj);
        }
    };

    static /* synthetic */ boolean lambda$static$0(PsiClass psiClass) {
        if ((psiClass instanceof PsiAnonymousClass) || psiClass.isAnnotationType()) {
            return false;
        }
        if (!psiClass.isInterface() || PsiUtil.isLanguageLevel8OrHigher(psiClass)) {
            return psiClass.mo6092getContainingClass() == null || psiClass.hasModifierProperty("static");
        }
        return false;
    }

    private PsiMethodUtil() {
    }

    public static PsiMethod findMainMethod(PsiClass psiClass) {
        for (JavaMainMethodProvider javaMainMethodProvider : JavaMainMethodProvider.EP_NAME.getExtensionList()) {
            if (javaMainMethodProvider.isApplicable(psiClass)) {
                return javaMainMethodProvider.findMainInClass(psiClass);
            }
        }
        return findMainMethod(psiClass.findMethodsByName("main", true));
    }

    private static PsiMethod findMainMethod(PsiMethod[] psiMethodArr) {
        for (PsiMethod psiMethod : psiMethodArr) {
            if (isMainMethod(psiMethod)) {
                return psiMethod;
            }
        }
        return null;
    }

    public static boolean isMainMethod(PsiMethod psiMethod) {
        if (psiMethod == null || psiMethod.mo6092getContainingClass() == null || !PsiType.VOID.equals(psiMethod.getReturnType()) || !psiMethod.hasModifierProperty("static") || !psiMethod.hasModifierProperty("public")) {
            return false;
        }
        PsiParameter[] parameters = psiMethod.getParameterList().getParameters();
        if (parameters.length != 1) {
            return false;
        }
        PsiType type = parameters[0].getType();
        if (type instanceof PsiArrayType) {
            return ((PsiArrayType) type).getComponentType().equalsToText(CommonClassNames.JAVA_LANG_STRING);
        }
        return false;
    }

    public static boolean hasMainMethod(PsiClass psiClass) {
        for (JavaMainMethodProvider javaMainMethodProvider : JavaMainMethodProvider.EP_NAME.getExtensionList()) {
            if (javaMainMethodProvider.isApplicable(psiClass)) {
                return javaMainMethodProvider.hasMainMethod(psiClass);
            }
        }
        return findMainMethod(psiClass.findMethodsByName("main", true)) != null;
    }

    public static PsiMethod findMainInClass(PsiClass psiClass) {
        if (MAIN_CLASS.value(psiClass)) {
            return findMainMethod(psiClass);
        }
        return null;
    }
}
