package org.jetbrains.kotlin.com.intellij.psi.presentation.java;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.core.JavaPsiBundle;
import org.jetbrains.kotlin.com.intellij.extapi.psi.StubBasedPsiElementBase;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiEnumConstant;
import org.jetbrains.kotlin.com.intellij.psi.PsiEnumConstantInitializer;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiFunctionalExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiMember;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.FunctionalExpressionStub;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiExpressionTrimRenderer;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;

/* JADX INFO: loaded from: classes3.dex */
public final class ClassPresentationUtil {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1 || i == 2 || i == 3) {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        } else {
            objArr[0] = "aClass";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/presentation/java/ClassPresentationUtil";
        if (i == 1) {
            objArr[2] = "getNameForElement";
        } else if (i == 2 || i == 3) {
            objArr[2] = "getContextName";
        } else {
            objArr[2] = "getNameForClass";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    private ClassPresentationUtil() {
    }

    public static String getNameForClass(PsiClass psiClass, boolean z) {
        String qualifiedName;
        if (psiClass == null) {
            $$$reportNull$$$0(0);
        }
        if (psiClass instanceof PsiAnonymousClass) {
            if (psiClass instanceof PsiEnumConstantInitializer) {
                PsiEnumConstant enumConstant = ((PsiEnumConstantInitializer) psiClass).getEnumConstant();
                return JavaPsiBundle.message("enum.constant.context", enumConstant.getName(), getContextName(enumConstant, z, false));
            }
            return JavaPsiBundle.message("anonymous.class.context.display", getContextName(psiClass, z, false));
        }
        if (z && (qualifiedName = psiClass.getQualifiedName()) != null) {
            return qualifiedName;
        }
        String name2 = psiClass.getName();
        String contextName = getContextName(psiClass, z);
        return contextName != null ? JavaPsiBundle.message("class.context.display", name2, contextName) : name2;
    }

    private static String getNameForElement(PsiElement psiElement, boolean z, boolean z2) {
        if (psiElement == null) {
            $$$reportNull$$$0(1);
        }
        if (psiElement instanceof PsiClass) {
            return getNameForClass((PsiClass) psiElement, z);
        }
        if (psiElement instanceof PsiMethod) {
            PsiMethod psiMethod = (PsiMethod) psiElement;
            return JavaPsiBundle.message("method.context.display", psiMethod.getName(), getContextName(psiMethod, z, false));
        }
        if ((psiElement instanceof PsiClassOwner) && z2) {
            return null;
        }
        if (psiElement instanceof PsiFile) {
            return ((PsiFile) psiElement).getName();
        }
        if (psiElement instanceof PsiField) {
            return ((PsiField) psiElement).getName() + " in " + getContextName(psiElement, z, false);
        }
        return null;
    }

    public static String getContextName(PsiElement psiElement, boolean z) {
        if (psiElement == null) {
            $$$reportNull$$$0(2);
        }
        return getContextName(psiElement, z, true);
    }

    public static String getContextName(PsiElement psiElement, boolean z, boolean z2) {
        if (psiElement == null) {
            $$$reportNull$$$0(3);
        }
        PsiElement stubOrPsiParentOfType = PsiTreeUtil.getStubOrPsiParentOfType(psiElement, PsiMember.class);
        if (stubOrPsiParentOfType == null) {
            stubOrPsiParentOfType = psiElement.getContainingFile();
        }
        while (stubOrPsiParentOfType != null) {
            String nameForElement = getNameForElement(stubOrPsiParentOfType, z, z2);
            if (nameForElement != null) {
                return nameForElement;
            }
            if (stubOrPsiParentOfType instanceof PsiFile) {
                return null;
            }
            stubOrPsiParentOfType = PsiTreeUtil.getStubOrPsiParent(stubOrPsiParentOfType);
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static String getFunctionalExpressionPresentation(PsiFunctionalExpression psiFunctionalExpression, boolean z) {
        String strRender;
        StubElement greenStub = ((StubBasedPsiElementBase) psiFunctionalExpression).getGreenStub();
        if (greenStub instanceof FunctionalExpressionStub) {
            strRender = ((FunctionalExpressionStub) greenStub).getPresentableText();
        } else {
            strRender = PsiExpressionTrimRenderer.render(psiFunctionalExpression);
        }
        return JavaPsiBundle.message("class.context.display", strRender, getContextName(psiFunctionalExpression, z, false));
    }
}
