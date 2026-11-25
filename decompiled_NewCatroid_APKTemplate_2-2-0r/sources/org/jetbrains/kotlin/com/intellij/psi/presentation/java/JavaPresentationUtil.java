package org.jetbrains.kotlin.com.intellij.psi.presentation.java;

import javax.swing.Icon;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.core.JavaPsiBundle;
import org.jetbrains.kotlin.com.intellij.navigation.ColoredItemPresentation;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentation;
import org.jetbrains.kotlin.com.intellij.openapi.editor.colors.CodeInsightColors;
import org.jetbrains.kotlin.com.intellij.openapi.editor.colors.TextAttributesKey;
import org.jetbrains.kotlin.com.intellij.openapi.project.IndexNotReadyException;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiMember;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiFormatUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;

/* JADX INFO: loaded from: classes3.dex */
public final class JavaPresentationUtil {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "psiField";
        } else if (i != 2) {
            objArr[0] = "psiMethod";
        } else {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/presentation/java/JavaPresentationUtil";
        if (i == 1) {
            objArr[2] = "getFieldPresentation";
        } else if (i != 2) {
            objArr[2] = "getMethodPresentation";
        } else {
            objArr[2] = "getJavaSymbolContainerText";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    private JavaPresentationUtil() {
    }

    public static ColoredItemPresentation getMethodPresentation(final PsiMethod psiMethod) {
        if (psiMethod == null) {
            $$$reportNull$$$0(0);
        }
        return new ColoredItemPresentation() { // from class: org.jetbrains.kotlin.com.intellij.psi.presentation.java.JavaPresentationUtil.1
            public String getPresentableText() {
                return PsiFormatUtil.formatMethod(psiMethod, PsiSubstitutor.EMPTY, 257, 2);
            }

            public TextAttributesKey getTextAttributesKey() {
                try {
                    if (psiMethod.isDeprecated()) {
                        return CodeInsightColors.DEPRECATED_ATTRIBUTES;
                    }
                    return null;
                } catch (IndexNotReadyException unused) {
                    return null;
                }
            }

            @Override // org.jetbrains.kotlin.com.intellij.navigation.ItemPresentation
            public String getLocationString() {
                return JavaPresentationUtil.getJavaSymbolContainerText(psiMethod);
            }

            @Override // org.jetbrains.kotlin.com.intellij.navigation.ItemPresentation
            public Icon getIcon(boolean z) {
                return psiMethod.getIcon(1);
            }
        };
    }

    public static ItemPresentation getFieldPresentation(final PsiField psiField) {
        if (psiField == null) {
            $$$reportNull$$$0(1);
        }
        return new ColoredItemPresentation() { // from class: org.jetbrains.kotlin.com.intellij.psi.presentation.java.JavaPresentationUtil.2
            public String getPresentableText() {
                return psiField.getName();
            }

            public TextAttributesKey getTextAttributesKey() {
                try {
                    if (psiField.isDeprecated()) {
                        return CodeInsightColors.DEPRECATED_ATTRIBUTES;
                    }
                    return null;
                } catch (IndexNotReadyException unused) {
                    return null;
                }
            }

            @Override // org.jetbrains.kotlin.com.intellij.navigation.ItemPresentation
            public String getLocationString() {
                return JavaPresentationUtil.getJavaSymbolContainerText(psiField);
            }

            @Override // org.jetbrains.kotlin.com.intellij.navigation.ItemPresentation
            public Icon getIcon(boolean z) {
                return psiField.getIcon(1);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getJavaSymbolContainerText(PsiElement psiElement) {
        String packageName;
        if (psiElement == null) {
            $$$reportNull$$$0(2);
        }
        PsiElement parentOfType = PsiTreeUtil.getParentOfType(psiElement, (Class<? extends PsiElement>[]) new Class[]{PsiMember.class, PsiFile.class});
        if (parentOfType instanceof PsiClass) {
            PsiClass psiClass = (PsiClass) parentOfType;
            packageName = psiClass.getQualifiedName();
            if (packageName == null) {
                packageName = psiClass.getName();
            }
        } else {
            packageName = parentOfType instanceof PsiJavaFile ? ((PsiJavaFile) parentOfType).getPackageName() : null;
        }
        if (packageName != null) {
            return JavaPsiBundle.message("aux.context.display", packageName);
        }
        return null;
    }
}
